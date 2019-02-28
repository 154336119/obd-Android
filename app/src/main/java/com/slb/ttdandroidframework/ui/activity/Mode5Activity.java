package com.slb.ttdandroidframework.ui.activity;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.protocol.ObdRawCommand;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.command.mode2.Service2Command;
import com.slb.ttdandroidframework.command.mode5.Service5Command;
import com.slb.ttdandroidframework.command.mode6.Mode6AvailablePidsCommand_01_20;
import com.slb.ttdandroidframework.command.mode6.Mode6AvailablePidsCommand_21_40;
import com.slb.ttdandroidframework.command.mode6.Mode6AvailablePidsCommand_41_60;
import com.slb.ttdandroidframework.command.mode6.Service6Command;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.http.bean.BankSensorEntiity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameInsideEntity;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.ui.adapter.ModeSixAdapter;
import com.slb.ttdandroidframework.ui.adapter.ModuleFiveAdapter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.Mode2Util;
import com.slb.ttdandroidframework.util.config.Mode5Util;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slb.ttdandroidframework.ui.activity.ReadErrorCodeActivity.DATA_OK_CODE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IO;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_MIS;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_NODATA;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_UTC;

/**
 * 作者：Juan on 2017/7/30 18:33
 * 邮箱：154336119@qq.com
 * 描述：mode6
 */
public class Mode5Activity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private ModuleFiveAdapter mAdapter;
    private BluetoothSocket sock;
    private ModeSixTask mModeSixTask;
    private List<BankSensorEntiity> mList = new ArrayList<>();
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
//                    dataOk((String) msg.obj);
                    mList = (List<BankSensorEntiity>)msg.obj;
                    mAdapter.setNewData(mList);
                    break;
            }
            return false;
        }
    });


    @Override
    protected String setToolbarTitle() {
        return getString(R.string.mode_5);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mode_six;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mAdapter = new ModuleFiveAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_5)
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
        executeMode6Command();
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
    private class ModeSixTask extends AsyncTask<String, Integer, List<BankSensorEntiity>> {
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            showWaitDialog("loading...");
        }

        @Override
        protected List<BankSensorEntiity> doInBackground(String... params) {
            List<BankSensorEntiity> list = new ArrayList<>();
            //Get the current thread's token
            synchronized (this) {
                try {
                    for(int i = 0;i<8;i++){
                        //构造数据
                        BankSensorEntiity bankSensorEntiity = new BankSensorEntiity();
                        bankSensorEntiity.setBankSensorName(Mode5Util.getBankSensorNameForIndex(i));
                        List<MoudleFiveEntity> mInsideList = new ArrayList<>();
                        List<Service5Command> service5CommandList = Mode5Util.getService5CommandForIndex(i);
                        for(int j=0; j<service5CommandList.size();j++){
                            Service5Command service5Command = service5CommandList.get(j);
                            service5Command.run(sock.getInputStream(), sock.getOutputStream());
                            mInsideList.add(service5Command.getMoudleFiveEntity());
                        }
//                        bankSensorEntiity.setList(mInsideList);
                        list.add(bankSensorEntiity);
                    }
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
        protected void onPostExecute(List<BankSensorEntiity> result) {
            hideWaitDialog();
            mHandler.obtainMessage(DATA_OK_CODE, result).sendToTarget();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().post(new ObdServiceStateEvent(true));
    }

}
