package com.slb.ttdandroidframework.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.MyApplication;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ConnectEvent;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.event.ResetEvent;
import com.slb.ttdandroidframework.ui.contract.DeviceContract;
import com.slb.ttdandroidframework.ui.presenter.DevicePresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;
import com.slb.ttdandroidframework.util.io.AbstractGatewayService;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.slb.ttdandroidframework.util.io.ObdGatewayService;

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
    private boolean isBluetoothEnable = false ;
    private boolean isOBDConnected = true ;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice obdDevice = null ;
    private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
    private ServiceConnection serviceConn = MyApplication.getServiceConn();
    BroadcastReceiver discoveryResult;
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
        if(BluetoothUtil.isRunning){
            IvState1.setBackgroundResource(R.mipmap.ic_connect);
            TvConnectState.setText("已连接");
            TvConnectState.setTextColor(Color.parseColor("#FF00FF00"));
            IvIvIcon.setBackgroundResource(R.mipmap.ic_blue_connect);
        }
    }


    @OnClick(R.id.mTvAgain)
    public void onViewClicked() {
//        if(deviceList.size() == 0){
//            showToastMsg("暂时找到设备");
//            return;
//        }
        getBondedDevices();
        @SuppressLint("RestrictedApi") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.dialog));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("连接到OBD");
        String[] bondedNames = new String[deviceList.size()];

        for (int i=0; i<deviceList.size(); i++ ){
            bondedNames[i] = deviceList.get(i).getName();
            SharedPreferencesUtils.setParam(DeviceActivity.this,PARA_DEV_ADDR,deviceList.get(i).getAddress());
        }
        builder.setSingleChoiceItems(bondedNames,0,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferencesUtils.setParam(DeviceActivity.this,PARA_DEV_ADDR,deviceList.get(which).getAddress());
                obdDevice = deviceList.get(which);
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RxBus.get().post(new ConnectEvent());
                showLoadingDialog("连接中");
//                dialog.dismiss();
                try {
                    if( BluetoothUtil.getSockInstance()!=null){
                        RxBus.get().post(new ObdConnectStateEvent(true));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showToastMsg("连接失败");
                } finally {
                    hideWaitDialog();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog= builder.create();
//        Button btnPositive = (Button)alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        btnPositive.setTextColor(Color.BLACK);
        alertDialog.show();
    }


    public void getBondedDevices(){
        deviceList.clear();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceList.add(device);
                Log.d(TAG,"已绑定设备:   "+device.getName() + " - " + device.getAddress());
            }
        }
    }

    private void doBindService() {
            Intent serviceIntent = new Intent(Base.getContext(), ObdGatewayService.class);
            bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
    }

    public void enableBluetooth(){
        if ( !isBluetoothEnable ){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,0);

        }else{
            Log.d("","蓝牙已打开");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode --> "+requestCode);
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
        if(event.isConnect()){
             IvState1.setBackgroundResource(R.mipmap.ic_connect);
             TvConnectState.setText("已连接");
             TvConnectState.setTextColor(Color.parseColor("#FF00FF00"));
             IvIvIcon.setBackgroundResource(R.mipmap.ic_blue_connect);
//            MyApplication.getService().queueJob(new ObdCommandJob(new PendingTroubleCodesCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new EchoOffCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new LineFeedOffCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new TimeoutCommand(62)));
//            MyApplication.getService().queueJob(new ObdCommandJob(new AmbientAirTemperatureCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new EngineCoolantTemperatureCommand()));
        }else{
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

        Logger.d(cmdResult);
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
            if( BluetoothUtil.getSockInstance()!=null){
                RxBus.get().post(new ObdConnectStateEvent(true));
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToastMsg("连接失败");
        }
        hideWaitDialog();
    }

    public void startDiscovery(){
       discoveryResult = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"接收到广播！！！！！！！！！！！！！！");
                String remoteDeviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceList.add(remoteDevice);
                Log.d(TAG,"发现蓝牙设备 : "+remoteDeviceName);
            }
        };

        registerReceiver(discoveryResult,new IntentFilter(BluetoothDevice.ACTION_FOUND));
        bluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(discoveryResult);
    }
}
