package com.slb.ttdandroidframework.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.MyApplication;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ConnectEvent;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.ui.contract.DeviceContract;
import com.slb.ttdandroidframework.ui.presenter.DevicePresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.ObdHelper;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.slb.ttdandroidframework.util.io.ObdGatewayService;
import com.slb.ttdandroidframework.weight.CustomDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.slb.ttdandroidframework.util.config.BizcContant.PARA_DEV_ADDR;

public class DeviceActivity extends BaseMvpActivity<DeviceContract.IView, DeviceContract.IPresenter>
        implements DeviceContract.IView {
    private static final int NO_BLUETOOTH_DEVICE_SELECTED = 0;
    private static final int CANNOT_CONNECT_TO_DEVICE = 1;
    private static final int NO_DATA = 3;
    private static final int OBD_COMMAND_FAILURE = 10;
    private static final int OBD_COMMAND_FAILURE_IO = 11;
    private static final int OBD_COMMAND_FAILURE_UTC = 12;
    private static final int OBD_COMMAND_FAILURE_IE = 13;
    private static final int OBD_COMMAND_FAILURE_MIS = 14;
    private static final int OBD_COMMAND_FAILURE_NODATA = 15;

    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    @BindView(R.id.mTvAgain)
    TextView mTvAgain;
    @BindView(R.id.IvState1)
    ImageView IvState1;
    @BindView(R.id.TvConnectState)
    TextView TvConnectState;
    @BindView(R.id.IvIvIcon)
    ImageView IvIvIcon;

    private final int REQUEST_ENABLE_BT = 0xa01;
    @BindView(R.id.RlContectState)
    RelativeLayout RlContectState;
    private boolean isBluetoothEnable = false;
    private boolean isOBDConnected = true;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice obdDevice = null;
    private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
    private ServiceConnection serviceConn = MyApplication.getServiceConn();
    BroadcastReceiver discoveryResult;
    private CustomDialog mCommonAlertDialog;
    private Handler mHandler = new Handler(new Handler.Callback() {
        private ObdHelper obdHelper;

        public boolean handleMessage(Message msg) {
            Log.d(TAG, "Message received on handler");
            switch (msg.what) {
                case NO_BLUETOOTH_DEVICE_SELECTED:
                    showToastMsg("连接失败");
                    // showToastMsg(getString(R.string.text_bluetooth_nodevice));
                    break;
                case CANNOT_CONNECT_TO_DEVICE:
                    showToastMsg("连接失败");
                    //    showToastMsg(getString(R.string.text_bluetooth_error_connecting));
                    break;
                case OBD_COMMAND_FAILURE:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure));
                    break;
                case OBD_COMMAND_FAILURE_IO:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " IO");
                    break;
                case OBD_COMMAND_FAILURE_IE:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " IE");
                    break;
                case OBD_COMMAND_FAILURE_MIS:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " MIS");
                    break;
                case OBD_COMMAND_FAILURE_UTC:
                    showToastMsg("连接失败");
                    //  showToastMsg(getString(R.string.text_obd_command_failure) + " UTC");
                    break;
                case OBD_COMMAND_FAILURE_NODATA:
                    RxBus.get().post(new ObdServiceStateEvent(true));
                    // showToastMsg(getString(R.string.text_noerrors));
                    break;
                case NO_DATA:
                    showToastMsg(getString(R.string.text_dtc_no_data));
                    break;
            }
            hideWaitDialog();
            return false;
        }
    });

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public DeviceContract.IPresenter initPresenter() {
        return new DevicePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_device;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        if (BluetoothUtil.isRunning) {
            IvState1.setBackgroundResource(R.mipmap.ic_connect);
            TvConnectState.setText("已连接");
            TvConnectState.setTextColor(Color.parseColor("#FF00FF00"));
            IvIvIcon.setBackgroundResource(R.mipmap.ic_blue_connect);
        }
    }


    public void getBondedDevices() {
        deviceList.clear();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceList.add(device);
                Log.d(TAG, "已绑定设备:   " + device.getName() + " - " + device.getAddress());
            }
        }
    }

    private void doBindService() {
        Intent serviceIntent = new Intent(Base.getContext(), ObdGatewayService.class);
        bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
    }

    public void enableBluetooth() {
        if (!isBluetoothEnable) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 0);

        } else {
            Log.d("", "蓝牙已打开");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode --> " + requestCode);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
//                bluetoothStatus.setText("已打开");
//                bluetoothBtn.setEnabled(false);
            }
            if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "放弃打开蓝牙！");
            }
        } else {
            Log.d(TAG, "打开蓝牙异常！");
            return;
        }
    }

    @Subscribe
    public void onObdConnectStateEvent(ObdConnectStateEvent event) {
        if (event.isConnect()) {
            IvState1.setBackgroundResource(R.mipmap.ic_connect);
            TvConnectState.setText("已连接");
            TvConnectState.setTextColor(Color.parseColor("#FF00FF00"));
            IvIvIcon.setBackgroundResource(R.mipmap.ic_blue_connect);
            BluetoothUtil.setIsRunning(true);
//            MyApplication.getService().queueJob(new ObdCommandJob(new PendingTroubleCodesCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new EchoOffCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new LineFeedOffCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new TimeoutCommand(62)));
//            MyApplication.getService().queueJob(new ObdCommandJob(new AmbientAirTemperatureCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new EngineCoolantTemperatureCommand()));
            hideWaitDialog();
        } else {
            BluetoothUtil.setIsRunning(false);
            IvState1.setBackgroundResource(R.mipmap.ic_un_connect);
            TvConnectState.setText("未连接");
            TvConnectState.setTextColor(Color.parseColor("#FFF9010A"));
            IvIvIcon.setBackgroundResource(R.mipmap.ic_blue_un_connect);
        }
    }

    @Override
    protected boolean rxBusRegist() {
        return true;
    }

    @Subscribe
    public void onObdCommandJobEvent(ObdCommandJob job) {
        final String cmdName = job.getCommand().getName();
        String cmdResult = job.getCommand().getFormattedResult();
//        PendingTroubleCodesCommand command = (PendingTroubleCodesCommand) job.getCommand();

//        Logger.d(cmdResult);
//        Logger.d(command.getName());
//        Logger.d(command.getName());
//        Logger.d(command.getResult());
//        Logger.d(command.getFormattedResult());
//        Logger.d(command.getCalculatedResult());
//        final String cmdID = LookUpCommand(cmdName);
//
//        if (dataScroll.findViewWithTag(cmdID) != null) {
//            TextView existingTV = (TextView) dataScroll.findViewWithTag(cmdID);
//            existingTV.setText(cmdResult);
//        } else addTableRow(cmdID, cmdName, cmdResult);
//        commandResult.put(cmdID, cmdResult);

    }

    @Subscribe
    public void onConnectEvent(ConnectEvent event) {
        showLoadingDialog("连接中");
//                dialog.dismiss();
        try {
            if (BluetoothUtil.getSockInstance() != null) {
                RxBus.get().post(new ObdServiceStateEvent(true));
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToastMsg("连接失败");
        }
        hideWaitDialog();
    }

    public void startDiscovery() {
        discoveryResult = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "接收到广播！！！！！！！！！！！！！！");
                String remoteDeviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceList.add(remoteDevice);
                Log.d(TAG, "发现蓝牙设备 : " + remoteDeviceName);
            }
        };

        registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        bluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(discoveryResult);
    }

    @OnClick({R.id.mTvAgain, R.id.RlContectState, R.id.mIvBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvAgain:
                //        if(deviceList.size() == 0){
//            showToastMsg("暂时找到设备");
//            return;
//        }
                getBondedDevices();
                @SuppressLint("RestrictedApi") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.dialog));
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("连接到OBD");
                String[] bondedNames = new String[deviceList.size()];

                for (int i = 0; i < deviceList.size(); i++) {
                    bondedNames[i] = deviceList.get(i).getName();
                }
                if (deviceList.size() > 0) {
                    SharedPreferencesUtils.setParam(DeviceActivity.this, PARA_DEV_ADDR, deviceList.get(0).getAddress());
                }
                builder.setSingleChoiceItems(bondedNames, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferencesUtils.setParam(DeviceActivity.this, PARA_DEV_ADDR, deviceList.get(which).getAddress());
                        obdDevice = deviceList.get(which);
                        BluetoothUtil.setRemoteDevice(obdDevice.getAddress());
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String remoteDevice = (String) SharedPreferencesUtils.getParam(Base.getContext(), BizcContant.PARA_DEV_ADDR, "");
                        BluetoothUtil.setRemoteDevice(remoteDevice);
                        ObdHelper obdHelper = new ObdHelper(mHandler, DeviceActivity.this);
                        obdHelper.connectToDevice();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
//        Button btnPositive = (Button)alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        btnPositive.setTextColor(Color.BLACK);
                alertDialog.show();
                break;
            case R.id.RlContectState:
                if (BluetoothUtil.isRunning) {
                    showDisConnectDialog();
                }
                break;
            case R.id.mIvBack:
                finish();
                break;
        }
    }

    /**
     * 断开连接dialog
     */
    private void showDisConnectDialog() {
        CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog
                .setTitle("提示")
                .setMessage("是否断开设备连接？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothUtil.setIsRunning(false);
                        BluetoothUtil.setSockInstance(null);
                        RxBus.get().post(new ObdServiceStateEvent(false));
                        RxBus.get().post(new ObdConnectStateEvent(false));
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
