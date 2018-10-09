package com.slb.ttdandroidframework.ui.activity;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.command.mode2.Service2Command;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameInsideEntity;
import com.slb.ttdandroidframework.ui.adapter.FreezeFrameAdapter;
import com.slb.ttdandroidframework.ui.adapter.HistoryErrorDetailAdapter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.Mode2Util;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slb.ttdandroidframework.ui.activity.ReadErrorCodeActivity.DATA_OK_CODE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.DATA_OK;
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
public class FreezeFrameActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private FreezeFrameAdapter mAdapter;
    private BluetoothSocket sock;
    private GetPermanentTroubleCodesTask mCodesTask;
    private List<FreezeFrameEntity> mList = new ArrayList<>();
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
                    mList = (List<FreezeFrameEntity>)msg.obj;
                    mAdapter.setNewData(mList);
                    break;
            }
            return false;
        }
    });


    @Override
    protected String setToolbarTitle() {
        return getString(R.string.freeze_frame);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_freeze_frame;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mAdapter = new FreezeFrameAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_20)
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
    private class GetPermanentTroubleCodesTask extends AsyncTask<String, Integer, List<FreezeFrameEntity>> {
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            showWaitDialog("刷新中");
        }

        @Override
        protected List<FreezeFrameEntity> doInBackground(String... params) {
            List<FreezeFrameEntity> list = new ArrayList<>();
            //Get the current thread's token
            synchronized (this) {
                try {
                    for(int i = 0;i<5;i++){
                         //构造数据
                        FreezeFrameEntity freezeFrameEntity = new FreezeFrameEntity();
                        List<FreezeFrameInsideEntity> mInsideList = new ArrayList<>();

                        List<Service2Command> service2CommandList = Mode2Util.getService2CommandForIndex(i);
                         for(int j=0; j<service2CommandList.size();j++){
                            FreezeFrameInsideEntity freezeFrameInsideEntity = new FreezeFrameInsideEntity();

                             Service2Command service2Command = service2CommandList.get(j);
                            service2Command.run(sock.getInputStream(), sock.getOutputStream());
                            String pid = service2Command.getResult();
                            Integer pid10 = null;
//                            Logger.d("=========pid:"+pid);
                            switch(j){
                                case 0:
                                    //第一条是pid数据
                                    if(!TextUtils.isEmpty(pid)){
                                        pid =  pid.substring(pid.length()-4,pid.length());
                                        pid = "P"+pid;
                                    }
//                                    Logger.d("=========pid1:"+pid);
                                    freezeFrameEntity.setPid(pid);
                                    break;
                                case 1:
                                    freezeFrameInsideEntity.setDes("计算的荷载阈值");
                                    freezeFrameInsideEntity.setValue(service2Command.getPidData04()+"");
                                    freezeFrameInsideEntity.setUtil("%");
                                    mInsideList.add(freezeFrameInsideEntity);
                                    break;
                                case 2:
                                    freezeFrameInsideEntity.setDes("发动机冷却液温度");
                                    freezeFrameInsideEntity.setValue(service2Command.getPidData05()+"");
                                    freezeFrameInsideEntity.setUtil("C");
                                    mInsideList.add(freezeFrameInsideEntity);
                                    break;
                                case 3:
                                    freezeFrameInsideEntity.setDes("进气歧管绝对压力");
                                    freezeFrameInsideEntity.setValue(service2Command.getPidData0b()+"");
                                    freezeFrameInsideEntity.setUtil("kPa");
                                    mInsideList.add(freezeFrameInsideEntity);
                                    break;
                                case 4:
                                    freezeFrameInsideEntity.setDes("发动机RPM");
                                    freezeFrameInsideEntity.setValue(service2Command.getPidData0c()+"");
                                    freezeFrameInsideEntity.setUtil("RPM");
                                    mInsideList.add(freezeFrameInsideEntity);
                                    break;
                                case 5:
                                    freezeFrameInsideEntity.setDes("车辆速度");
                                    freezeFrameInsideEntity.setValue(service2Command.getPidData0d()+"");
                                    freezeFrameInsideEntity.setUtil("km/h");
                                    mInsideList.add(freezeFrameInsideEntity);
                                    break;
                                case 6:
                                    freezeFrameInsideEntity.setDes("空气流速");
                                    freezeFrameInsideEntity.setValue(service2Command.getPidData10()+"");
                                    freezeFrameInsideEntity.setUtil("g/s");
                                    mInsideList.add(freezeFrameInsideEntity);
                                    break;
                                case 7:
                                    freezeFrameInsideEntity.setDes("节气门绝对位置");
                                    freezeFrameInsideEntity.setValue(service2Command.getPidData11()+"");
                                    freezeFrameInsideEntity.setUtil("%");
                                    mInsideList.add(freezeFrameInsideEntity);
                                    break;
                            }
                        }
                        freezeFrameEntity.setmInsideList(mInsideList);
                        list.add(freezeFrameEntity);
//                        mAdapter.setNewData(mList);
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
        protected void onPostExecute(List<FreezeFrameEntity> result) {
            hideWaitDialog();
            mHandler.obtainMessage(DATA_OK_CODE, result).sendToTarget();
        }
    }

    private void dataOk(String res) {
        ListView lv = (ListView) findViewById(R.id.listView);
        Map<String, String> dtcVals = getDict(R.array.dtc_keys, R.array.dtc_values);
        //TODO replace below codes (res) with aboce dtcVals
        //String tmpVal = dtcVals.get(res.split("\n"));
        //String[] dtcCodes = new String[]{};
        ArrayList<String> dtcCodes = new ArrayList<String>();
        //int i =1;
        if (res != null) {
            for (String dtcCode : res.split("\n")) {
                dtcCodes.add(dtcCode + " : " + dtcVals.get(dtcCode));
                Log.d("TEST", dtcCode + " : " + dtcVals.get(dtcCode));
            }
        } else {
            dtcCodes.add("There are no errors");
        }
        ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dtcCodes);
        lv.setAdapter(myarrayAdapter);
        lv.setTextFilterEnabled(true);
    }

    /**
     * 请求冻结帧
     */
    public void executeCodeCommand() {
        try {
            if (BluetoothUtil.getDeviceInstance() == null) {
                mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            } else {
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

    Map<String, String> getDict(int keyId, int valId) {
        String[] keys = getResources().getStringArray(keyId);
        String[] vals = getResources().getStringArray(valId);

        Map<String, String> dict = new HashMap<String, String>();
        for (int i = 0, l = keys.length; i < l; i++) {
            dict.put(keys[i], vals[i]);
        }

        return dict;
    }
}
