package com.slb.ttdandroidframework.ui.activity;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.github.pires.obd.commands.control.PermanentTroubleCodesCommand;
import com.github.pires.obd.commands.control.TroubleCodesCommand;
import com.github.pires.obd.commands.protocol.ResetTroubleCodesCommand;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.ui.adapter.FreezeFrameAdapter;
import com.slb.ttdandroidframework.ui.contract.MyComListContract;
import com.slb.ttdandroidframework.ui.presenter.MyComListPresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.Map;

import danfei.shulaibao.widget.refresh.BaseBrvahRefreshActivity;
import rx.Observable;

import static com.slb.ttdandroidframework.ui.activity.ReadErrorCodeActivity.DATA_OK_CODE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IO;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_MIS;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_NODATA;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_UTC;

/**
 * 作者：Juan on 2017/7/30 18:33
 * 邮箱：154336119@qq.com
 * 描述：冻结帧
 */
public class FreezeFrameActivity extends BaseBrvahRefreshActivity<MyComListContract.IView,MyComListContract.IPresenter,Object,FreezeFrameEntity> implements MyComListContract.IView{
    private BluetoothSocket sock;
    private GetPermanentTroubleCodesTask mCodesTask;
    private Handler mHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "Message received on handler");
            switch (msg.what) {
                case ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED:
                    showToastMsg(getString(R.string.text_bluetooth_nodevice));
                    break;
                case ObdConfig.CANNOT_CONNECT_TO_DEVICE:
                    showToastMsg(getString(R.string.text_bluetooth_error_connecting));
                    break;
                case ObdConfig.OBD_COMMAND_FAILURE:
                    showToastMsg(getString(R.string.text_obd_command_failure));
                    break;
                case OBD_COMMAND_FAILURE_IO:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " IO");
                    break;
                case OBD_COMMAND_FAILURE_IE:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " IE");
                    break;
                case OBD_COMMAND_FAILURE_MIS:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " MIS");
                    break;
                case OBD_COMMAND_FAILURE_UTC:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " UTC");
                    break;
                case OBD_COMMAND_FAILURE_NODATA:
                    showToastMsg(getString(R.string.text_noerrors));
                    break;
                case ObdConfig.NO_DATA:
                    showToastMsg(getString(R.string.text_dtc_no_data));
                    ///finish();
                    break;
                case DATA_OK_CODE:
                    dataOkCode((PermanentTroubleCodesCommand) msg.obj);
                    break;
            }
            return false;
        }
    });
    @Override
    public MyComListContract.IPresenter initPresenter() {
        return new MyComListPresenter();
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.freeze_frame);
    }
    @Override
    protected RecyclerView.Adapter setAdapter() {
        mAdapter = new FreezeFrameAdapter(mList);
        return mAdapter;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_freeze_frame;
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.line_height_1)
                        .build());
//        //测试
//        for (int i = 0; i < 10; i++) {
//            FreezeFrameEntity freezeFrameEntity = new FreezeFrameEntity();
//            mList.add(freezeFrameEntity);
//        }
//        mAdapter.addData(mList);
        try {
            sock = BluetoothUtil.getSockInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executeCodeCommand();
    }

//    @Override
//    protected Observable<HttpResult<Object, CookBillEntity>> requestHttp() {
//        return RetrofitSerciveFactory.provideComService().getCookBillList(1);
//    }
private class GetPermanentTroubleCodesTask extends AsyncTask<String, Integer, PermanentTroubleCodesCommand> {
    @Override
    protected void onPreExecute() {
        //Create a new progress dialog
        showWaitDialog("刷新中");
    }

    @Override
    protected PermanentTroubleCodesCommand doInBackground(String... params) {
        PermanentTroubleCodesCommand result = null;
        hideWaitDialog();
        //Get the current thread's token
        synchronized (this) {
            try {
                onProgressUpdate(5);
                PermanentTroubleCodesCommand permanentTroubleCodesCommand  = new PermanentTroubleCodesCommand();
                permanentTroubleCodesCommand.run(sock.getInputStream(), sock.getOutputStream());
                result = permanentTroubleCodesCommand;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("DTCERR", e.getMessage());
                mHandler.obtainMessage(OBD_COMMAND_FAILURE_IO).sendToTarget();
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("DTCERR", e.getMessage());
                mHandler.obtainMessage(OBD_COMMAND_FAILURE_IE).sendToTarget();
                return null;
            } catch (UnableToConnectException e) {
                e.printStackTrace();
                Log.e("DTCERR", e.getMessage());
                mHandler.obtainMessage(OBD_COMMAND_FAILURE_UTC).sendToTarget();
                return null;
            } catch (MisunderstoodCommandException e) {
                e.printStackTrace();
                Log.e("DTCERR", e.getMessage());
                mHandler.obtainMessage(OBD_COMMAND_FAILURE_MIS).sendToTarget();
                return null;
            } catch (NoDataException e) {
                Log.e("DTCERR", e.getMessage());
                mHandler.obtainMessage(OBD_COMMAND_FAILURE_NODATA).sendToTarget();
                return null;
            } catch (Exception e) {
                Log.e("DTCERR", e.getMessage());
                mHandler.obtainMessage(ObdConfig.OBD_COMMAND_FAILURE).sendToTarget();
            } finally {
                hideWaitDialog();
            }

        }

        return result;
    }

    public void closeSocket(BluetoothSocket sock) {
        if (sock != null)
            // close socket
            try {
                sock.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
//            progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(PermanentTroubleCodesCommand result) {
        mHandler.obtainMessage(DATA_OK_CODE, result).sendToTarget();
    }
}

    public void dataOkCode(PermanentTroubleCodesCommand obdCommand) {
//        Map<String, String> dtcVals = getDict(R.array.dtc_keys, R.array.dtc_values);
//        String value = null;
//        if(obdCommand!=null && !TextUtils.isEmpty(obdCommand.getName())){
//            for (String dtcCode : obdCommand.getFormattedResult().split("\n")) {
//                value = dtcVals.get(dtcCode);
//                ErrorCodeEntity errorCodeEntity = new ErrorCodeEntity();
//                errorCodeEntity.setTitle(dtcCode);
//                errorCodeEntity.setValue(value);
//                mAdapter01.getData().add(errorCodeEntity);
//                mCodeNum = mCodeNum +1;
//            }
//            mTvConfirmErrorCodeNum.setText(mCodeNum+getString(R.string.confirmed_dtcs));
////            if(mAdapter01.getData()!=null && mAdapter01.getData().size()>0){
////                gethttpdataOkCodePidDetails((obdCommand));
////            }
//        }
        hideWaitDialog();
    }
    /**
     * 请求冻结帧
     */
    public void executeCodeCommand(){
        try {
            if(BluetoothUtil.getDeviceInstance() == null){
                mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            }else{
                mCodesTask = new GetPermanentTroubleCodesTask();
                mCodesTask.execute();
            }
        } catch (IOException e) {
            mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().post(new ObdServiceStateEvent(true));
    }
}
