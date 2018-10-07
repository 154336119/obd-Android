package com.slb.ttdandroidframework.ui.activity;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.pires.obd.commands.ObdMultiCommand;
import com.github.pires.obd.commands.control.DistanceMILOnCommand;
import com.github.pires.obd.commands.control.DistanceSinceCCCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.hwangjr.rxbus.RxBus;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.command.mil.Command4D;
import com.slb.ttdandroidframework.command.mil.Command4E;
import com.slb.ttdandroidframework.command.mil.MilObdMultiCommand;
import com.slb.ttdandroidframework.command.mode5.Service5Command;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.http.bean.BankSensorEntiity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.http.bean.TroubleLightSEntity;
import com.slb.ttdandroidframework.ui.adapter.FreezeFrameAdapter;
import com.slb.ttdandroidframework.ui.adapter.ModeSixAdapter;
import com.slb.ttdandroidframework.ui.adapter.TroubleLightSAdapter;
import com.slb.ttdandroidframework.ui.contract.MyComListContract;
import com.slb.ttdandroidframework.ui.presenter.MyComListPresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.Mode5Util;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import danfei.shulaibao.widget.refresh.BaseBrvahRefreshActivity;

import static com.slb.ttdandroidframework.ui.activity.ReadErrorCodeActivity.DATA_OK_CODE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IO;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_MIS;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_NODATA;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_UTC;

/**
 * 作者：Juan on 2017/7/30 18:33
 * 邮箱：154336119@qq.com
 * 描述：故障灯
 */
public class TroubleLightSActivity extends BaseActivity{
    private ModeSixTask mModeSixTask;
    private BluetoothSocket sock;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private TroubleLightSAdapter mAdapter;
    private List<TroubleLightSEntity> mList = new ArrayList<>();
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
                    mList = (List<TroubleLightSEntity>)msg.obj;
                    mAdapter.setNewData(mList);
                    break;
            }
            return false;
        }
    });

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.mil_status);
    }

    protected RecyclerView.Adapter setAdapter() {
        mAdapter = new TroubleLightSAdapter(mList);
        return mAdapter;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_trouble_lightse;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mAdapter = new TroubleLightSAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_20)
                        .build());
        try {
            sock = BluetoothUtil.getSockInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                executeMode6Command();
                mHandler.postDelayed(this, 3000);
            }
        },1000);
    }

    /**
     * 请求mode6
     */
    public void executeMode6Command() {
        try {
            if (BluetoothUtil.getDeviceInstance() == null) {
                mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            } else {
                mModeSixTask = new ModeSixTask();
                mModeSixTask.execute();
            }
        } catch (IOException e) {
            mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            e.printStackTrace();
        }
    }
    //    @Override
//    protected Observable<HttpResult<Object, CookBillEntity>> requestHttp() {
//        return RetrofitSerciveFactory.provideComService().getCookBillList(1);
//    }
    private class ModeSixTask extends AsyncTask<String, Integer, List<TroubleLightSEntity>> {
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
//            showWaitDialog("刷新中");
        }

        @Override
        protected List<TroubleLightSEntity> doInBackground(String... params) {
            List<TroubleLightSEntity> list = new ArrayList<>();
            //Get the current thread's token
            synchronized (this) {
                try {
                    MilObdMultiCommand obdMultiCommand = new MilObdMultiCommand();
                    obdMultiCommand.add(new DistanceSinceCCCommand());
                    obdMultiCommand.add(new Command4E());
                    obdMultiCommand.add(new Command4D());
                    obdMultiCommand.add(new DistanceMILOnCommand());
                    obdMultiCommand.add(new RuntimeCommand());

                    obdMultiCommand.sendCommands(sock.getInputStream(), sock.getOutputStream());
                    list = obdMultiCommand.getList();

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
//                    mHandler.obtainMessage(OBD_COMMAND_FAILURE_NODATA).sendToTarget();
                    mHandler.obtainMessage(DATA_OK_CODE, list).sendToTarget();
                } catch (Exception e) {
                    Log.e("DTCERR", e.getMessage());
                    mHandler.obtainMessage(ObdConfig.OBD_COMMAND_FAILURE).sendToTarget();
                } finally {
                    hideWaitDialog();
                }

            }

            return list;
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
        protected void onPostExecute(List<TroubleLightSEntity> result) {
//            hideWaitDialog();
            mHandler.obtainMessage(DATA_OK_CODE, result).sendToTarget();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().post(new ObdServiceStateEvent(true));
    }

}
