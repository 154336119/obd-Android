package com.slb.ttdandroidframework.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.github.pires.obd.commands.control.TroubleCodesCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ResetTroubleCodesCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.google.gson.internal.LinkedTreeMap;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.frame.utils.DateUtils;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.MyApplication;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.event.ResetEvent;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.PidEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.ui.adapter.ErrorCodeAdapter;
import com.slb.ttdandroidframework.ui.contract.ReadErrorCodeContract;
import com.slb.ttdandroidframework.ui.presenter.ReadErrorCodePresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.slb.ttdandroidframework.util.io.AbstractGatewayService;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.slb.ttdandroidframework.weight.CustomDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.slb.ttdandroidframework.ui.WeepakeActivity.LookUpCommand;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IO;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_MIS;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_NODATA;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_UTC;

public class ReadErrorCodeActivity extends BaseActivity {
    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    @BindView(R.id.mTvAgain)
    TextView mTvAgain;
    @BindView(R.id.TvConfirmErrorCodeNum)
    TextView mTvConfirmErrorCodeNum;
    @BindView(R.id.CheckboxConfirmErrorCode)
    CheckBox mCheckboxConfirmErrorCode;
    @BindView(R.id.TvWaitErrorCodeNum)
    TextView mTvWaitErrorCodeNum;
    @BindView(R.id.CheckboxWaitErrorCode)
    CheckBox mCheckboxWaitErrorCode;
    @BindView(R.id.Rv01)
    RecyclerView Rv01;
    @BindView(R.id.Rv02)
    RecyclerView Rv02;
    @BindView(R.id.NestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.BtnClearError)
    Button BtnClearError;
    private CustomDialog mCommonAlertDialog;
    private ErrorCodeAdapter mAdapter01;
    private ErrorCodeAdapter mAdapter02;

    private List<ErrorCodeEntity> mConfirmErrorCodeList = new ArrayList<>();
    private List<ErrorCodeEntity> mWaitErrorCodeList = new ArrayList<>();

//    public ProgressDialog progressDialog;
    private GetTroubleCodesTask mCodesTask;
    private GetWaitTroubleCodesTask mCodesTaskWait;
    private ClearTroubleCodesTask mCodesTaskClear;

    public static final int DATA_OK_CODE= 100;
    public static final int DATA_OK_CODE_WAIT = 101;
    public static final int DATA_OK_CODE_Clear = 102;
    private int mCodeNum = 0;
    private int mWaitCodeNum = 0;
    private BluetoothSocket sock;
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
                    dataOkCode((TroubleCodesCommand) msg.obj);
                    break;
                case DATA_OK_CODE_WAIT:
                    dataOkWaitCode((PendingTroubleCodesCommand) msg.obj);
                    if(mCodeNum == 0 && mWaitCodeNum ==0){

                    }else{
                        showDialog();
                    }
                    break;
                case DATA_OK_CODE_Clear:
                    dataOkClearCode((ResetTroubleCodesCommand) msg.obj);
                    break;

            }
            return false;
        }
    });
    @Override
    protected boolean hasToolbar() {
        return false;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_read_error_code;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);

        mNestedScrollView.setNestedScrollingEnabled(false);
//        //测试
//        for (int i = 0; i < 10; i++) {
//            ErrorCodeEntity errorCodeEntity = new ErrorCodeEntity();
//            mConfirmErrorCodeList.add(errorCodeEntity);
//            mWaitErrorCodeList.add(errorCodeEntity);
//        }

        mAdapter01 = new ErrorCodeAdapter(mConfirmErrorCodeList);
        Rv01.setLayoutManager(new LinearLayoutManager(this));
        Rv01.setAdapter(mAdapter01);
        Rv01.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.line_height)
                        .build());

        mAdapter02 = new ErrorCodeAdapter(mWaitErrorCodeList);
        Rv02.setLayoutManager(new LinearLayoutManager(this));
        Rv02.setAdapter(mAdapter02);
        Rv02.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.line_height)
                        .build());

        mCheckboxConfirmErrorCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Rv01.setVisibility(View.VISIBLE);
                } else {
                    Rv01.setVisibility(View.GONE);
                }
            }
        });

        mCheckboxWaitErrorCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Rv02.setVisibility(View.VISIBLE);
                } else {
                    Rv02.setVisibility(View.GONE);
                }
            }
        });

            if(!BluetoothUtil.isRunning){
                showToastMsg(getString(R.string.text_bluetooth_nodevice));
            }
        mTvConfirmErrorCodeNum.setText(mCodeNum+getString(R.string.confirmed_dtcs));
        mTvWaitErrorCodeNum.setText(mWaitCodeNum+getString(R.string.pending_dtcs));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().post(new ObdServiceStateEvent(true));
    }

    @OnClick({R.id.mIvBack, R.id.mTvAgain,R.id.BtnClearError})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIvBack:
                finish();
                break;
            case R.id.mTvAgain:
                if(!BluetoothUtil.isRunning){
                    showToastMsg(getString(R.string.text_bluetooth_nodevice));
                    return;
                }
                mCodeNum = 0;
                mWaitCodeNum = 0;
                mAdapter01.getData().clear();
                mAdapter02.getData().clear();
                mTvConfirmErrorCodeNum.setText(mCodeNum+getString(R.string.confirmed_dtcs));
                mTvWaitErrorCodeNum.setText(mWaitCodeNum+getString(R.string.pending_dtcs));
                try {
                    sock = BluetoothUtil.getSockInstance();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executeCodeCommand();
                executeWaitCodeCommand();
//                executeCommand(new ModifiedTroubleCodesObdCommand());
//                executeCommand(new ModifiedPendingTroubleCodesCommand());
                break;
            case R.id.BtnClearError:
                if(!BluetoothUtil.isRunning){
                    showToastMsg(getString(R.string.text_bluetooth_nodevice));
                    return;
                }
                executeResetTroubleCodesCommand();
                break;
        }
    }

    public void dataOkCode(TroubleCodesCommand obdCommand) {
        Map<String, String> dtcVals = getDict(R.array.dtc_keys, R.array.dtc_values);
        String value = null;
        if(obdCommand!=null && !TextUtils.isEmpty(obdCommand.getName())){
            for (String dtcCode : obdCommand.getFormattedResult().split("\n")) {
                value = dtcVals.get(dtcCode);
                ErrorCodeEntity errorCodeEntity = new ErrorCodeEntity();
                errorCodeEntity.setTitle(dtcCode);
                errorCodeEntity.setValue(value);
                mAdapter01.getData().add(errorCodeEntity);
                mCodeNum = mCodeNum +1;
            }
            mTvConfirmErrorCodeNum.setText(mCodeNum+getString(R.string.confirmed_dtcs));
//            if(mAdapter01.getData()!=null && mAdapter01.getData().size()>0){
//                gethttpdataOkCodePidDetails((obdCommand));
//            }
        }
        hideWaitDialog();
    }

    private void gethttpdataOkCodePidDetails(final TroubleCodesCommand obdCommand){
        if(obdCommand!=null && !TextUtils.isEmpty(obdCommand.getName())){
            String confirmPids =  obdCommand.getFormattedResult().replaceAll("\n",",");
        OkGo.<LzyResponse<Object>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/command/dtc")
                .tag(this)
                .params("userId",Base.getUserEntity().getId())
                .params("pids",confirmPids)
                .isMultipart(true)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<Object>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<Object>> response) {
                        LinkedTreeMap map =  (LinkedTreeMap)response.body().data;
                        for(int i=0;i<mAdapter01.getData().size();i++){
                            ErrorCodeEntity errorCodeEntity= mAdapter01.getData().get(i);
                            if( map.containsKey(errorCodeEntity.getTitle())){
                               Object object = map.get(errorCodeEntity.getTitle());
                               if(object!=null){
                                   LinkedTreeMap map1  = (LinkedTreeMap)map.get(errorCodeEntity.getTitle());
                                   String va =  (String)map1.get("description");
                                   errorCodeEntity.setDes(va);
                                   mAdapter01.setData(i,errorCodeEntity);
                               }
                            }
                        }
                        hideWaitDialog();
                    }
                });
        }
    }


    @Override
    protected boolean rxBusRegist() {
        return true;
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
    private class GetTroubleCodesTask extends AsyncTask<String, Integer, TroubleCodesCommand> {
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            showWaitDialog("loading...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected TroubleCodesCommand doInBackground(String... params) {
            TroubleCodesCommand result = null;
            hideWaitDialog();
            //Get the current thread's token
            synchronized (this) {
                try {
                    TroubleCodesCommand tcoc  = new TroubleCodesCommand();
                    tcoc.run(sock.getInputStream(), sock.getOutputStream());
                    result = tcoc;
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
        protected void onPostExecute(TroubleCodesCommand result) {
            mHandler.obtainMessage(DATA_OK_CODE, result).sendToTarget();
        }
    }

    /**
     * 请求错误码
     */
    public void executeCodeCommand(){
        try {
            if(BluetoothUtil.getDeviceInstance() == null){
                mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            }else{
                mCodesTask = new GetTroubleCodesTask();
                mCodesTask.execute();
            }
        } catch (IOException e) {
            mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            e.printStackTrace();
        }
    }

    //////////////////////////////////////等待错误码//////////////////////////////////


    private class GetWaitTroubleCodesTask extends AsyncTask<String, Integer, PendingTroubleCodesCommand> {
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            showWaitDialog("loading...");
        }

        @Override
        protected PendingTroubleCodesCommand doInBackground(String... params) {
            PendingTroubleCodesCommand result = null;
            hideWaitDialog();
            //Get the current thread's token
            synchronized (this) {
                try {
                    PendingTroubleCodesCommand tcoc  = new PendingTroubleCodesCommand();
                    tcoc.run(sock.getInputStream(), sock.getOutputStream());
                    result = tcoc;
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
        protected void onPostExecute(PendingTroubleCodesCommand result) {
            mHandler.obtainMessage(DATA_OK_CODE_WAIT, result).sendToTarget();
        }
    }

    /**
     * 请求等待错误码
     */
    public void executeWaitCodeCommand(){
//        tagObdCommand = command;
        //判断设备连接
//        if (remoteDevice == null || "".equals(remoteDevice)) {
//            Log.e(TAG, "No Bluetooth device has been selected.");
//            mHandler.obtainMessage(NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
//        } else {
//            gtct = new GetTroubleCodesTask();
//            gtct.execute(remoteDevice);
//        }
        try {
            if(BluetoothUtil.getDeviceInstance() == null){
                mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            }else{
                mCodesTaskWait = new GetWaitTroubleCodesTask();
                mCodesTaskWait.execute();
            }
        } catch (IOException e) {
            mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            e.printStackTrace();
        }
    }
    public void dataOkWaitCode(PendingTroubleCodesCommand obdCommand) {
        Map<String, String> dtcVals = getDict(R.array.dtc_keys, R.array.dtc_values);
        String value = null;
        if(obdCommand!=null && !TextUtils.isEmpty(obdCommand.getName())){
            for (String dtcCode : obdCommand.getFormattedResult().split("\n")) {
                value = dtcVals.get(dtcCode);
                ErrorCodeEntity errorCodeEntity = new ErrorCodeEntity();
                errorCodeEntity.setTitle(dtcCode);
                errorCodeEntity.setValue(value);
                mAdapter02.getData().add(errorCodeEntity);
                mWaitCodeNum = mWaitCodeNum +1;
            }
            mTvWaitErrorCodeNum.setText(mWaitCodeNum+getString(R.string.pending_dtcs));
//            if(mAdapter02.getData()!=null && mAdapter02.getData().size()>0){
//                gethttpdataOkWaitCodePidDetails((obdCommand));
//            }
        }
        hideWaitDialog();
    }

    private void gethttpdataOkWaitCodePidDetails(final PendingTroubleCodesCommand obdCommand){
        if(obdCommand!=null && !TextUtils.isEmpty(obdCommand.getName())){
            String waitPids =  obdCommand.getFormattedResult().replaceAll("\n",",");
            OkGo.<LzyResponse<Object>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/command/dtc")
                    .tag(this)
                    .params("userId",Base.getUserEntity().getId())
                    .params("pids",waitPids)
                    .isMultipart(true)
                    .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                    .execute(new ActivityDialogCallback<LzyResponse<Object>>(this) {
                        @Override
                        public void onSuccess(Response<LzyResponse<Object>> response) {
                            LinkedTreeMap map =  (LinkedTreeMap)response.body().data;
                            for(int i=0;i<mAdapter02.getData().size();i++){
                                ErrorCodeEntity errorCodeEntity= mAdapter02.getData().get(i);
                                if( map.containsKey(errorCodeEntity.getTitle())){
                                    Object object = map.get(errorCodeEntity.getTitle());
                                    if(object!=null){
                                        LinkedTreeMap map1  = (LinkedTreeMap)map.get(errorCodeEntity.getTitle());
                                        String va =  (String)map1.get("description");
                                        errorCodeEntity.setDes(va);
                                        mAdapter02.setData(i,errorCodeEntity);
                                    }
                                }
                            }
                            hideWaitDialog();
                        }
                    });
        }
    }

    //////////////////////////////////////清除故障码//////////////////////////////////

    private class ClearTroubleCodesTask extends AsyncTask<String, Integer, ResetTroubleCodesCommand> {
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            showWaitDialog("清除中");
        }

        @Override
        protected ResetTroubleCodesCommand doInBackground(String... params) {
            ResetTroubleCodesCommand result = null;
            hideWaitDialog();
            //Get the current thread's token
            synchronized (this) {
                try {
                    onProgressUpdate(5);
                    ResetTroubleCodesCommand tcoc  = new ResetTroubleCodesCommand();
                    tcoc.run(sock.getInputStream(), sock.getOutputStream());
                    result = tcoc;
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
        protected void onPostExecute(ResetTroubleCodesCommand result) {
                mHandler.obtainMessage(DATA_OK_CODE_Clear, result).sendToTarget();
        }
    }

    /**
     * 请求等待错误码
     */
    public void executeResetTroubleCodesCommand(){

        try {
            if(BluetoothUtil.getDeviceInstance() == null){
                mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            }else{
                mCodesTaskClear = new ClearTroubleCodesTask();
                mCodesTaskClear.execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
            mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
        }
    }
    public void dataOkClearCode(ResetTroubleCodesCommand obdCommand) {
        mCodeNum = 0;
        mWaitCodeNum = 0;
        mAdapter01.getData().clear();
        mAdapter02.getData().clear();
        mTvConfirmErrorCodeNum.setText(mCodeNum+getString(R.string.confirmed_dtcs));
        mTvWaitErrorCodeNum.setText(mWaitCodeNum+getString(R.string.pending_dtcs));
        mCheckboxConfirmErrorCode.setChecked(false);
        mCheckboxWaitErrorCode.setChecked(false);
        hideWaitDialog();
    }

    /**
     * 显示dialog
     */
    private void showDialog( ) {
        CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog
                .setTitle("提示")
                .setMessage("是否上传错误码？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Base.getUserEntity().getVehicleEntityList() == null ||Base.getUserEntity().getObdEntityList() == null){
                            showToastMsg("暂未绑定设备或汽车");
                            mCommonAlertDialog.dismiss();
                            return;
                        }
                        String confirmPids ="";
                        String pendingPids ="";
                        for(ErrorCodeEntity errorCodeEntity : mAdapter01.getData()){
                            confirmPids  = confirmPids +  errorCodeEntity.getTitle()+",";
                        }
                        for(ErrorCodeEntity errorCodeEntity : mAdapter02.getData()){
                            pendingPids  = pendingPids +  errorCodeEntity.getTitle()+",";
                        }
                        confirmPids = confirmPids.substring(0,confirmPids.length()-1);
                        Logger.d("confirmPids:"+confirmPids);

                        pendingPids = pendingPids.substring(0,pendingPids.length()-1);
                        Logger.d("pendingPids:"+pendingPids);
                        Bundle bundle = new Bundle();
                        bundle.putString(BizcContant.PARA_CONFIRM_PIS,confirmPids);
                        bundle.putString(BizcContant.PARA_PENDING_PIS,pendingPids);
                        ActivityUtil.next(ReadErrorCodeActivity.this,SubmitErrorCodeActivity.class,bundle,false);
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mCommonAlertDialog = dialog.create();
        mCommonAlertDialog.setCanceledOnTouchOutside(false);
        mCommonAlertDialog.show();
    }


}
