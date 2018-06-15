package com.slb.ttdandroidframework.ui;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.protocol.ResetTroubleCodesCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.slb.ttdandroidframework.util.io.AbstractGatewayService;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.slb.ttdandroidframework.util.io.ObdGatewayService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.slb.ttdandroidframework.util.config.BizcContant.PARA_DEV_ADDR;


public class WeepakeActivity extends AppCompatActivity {
    private String TAG = WeepakeActivity.class.getName();
    public Map<String, String> commandResult = new HashMap<String, String>();
    private TextView bluetoothStatus;
    private TextView obdStatus;
    private Button bluetoothBtn;
    private Button ObdButton;
    private Button clearButton;
    private ScrollView dataScroll;
    private TableLayout tl;
 private void initView(){
     bluetoothStatus = (TextView) findViewById(R.id.tv_bluetooth_status);
     obdStatus = (TextView) findViewById(R.id.tv_obd_status);
     bluetoothBtn = (Button) findViewById(R.id.btn_bluetooth_status);
     ObdButton = (Button) findViewById(R.id.btn_obd_status);
     dataScroll = (ScrollView) findViewById(R.id.data_scroll);
     tl = (TableLayout) findViewById(R.id.data_table);
     clearButton = (Button) findViewById(R.id.btn_obd_clear);
 }

    private final int REQUEST_ENABLE_BT = 0xa01;
    private boolean isBluetoothEnable = false ;
    private boolean isOBDConnected = true ;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice obdDevice = null ;
    private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();

    private AbstractGatewayService service;
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG, className.toString() + " service is bound");
//            isServiceBound = true;
            service = ((AbstractGatewayService.AbstractGatewayServiceBinder) binder).getService();
            service.setContext(WeepakeActivity.this);
            Log.d(TAG, "Starting live data");
            try {
                service.startService();
//                if (preRequisites)
//                    btStatusTextView.setText(getString(R.string.status_bluetooth_connected));
            } catch (IOException ioe) {
                Log.e(TAG, "Failure Starting live data");
//                btStatusTextView.setText(getString(R.string.status_bluetooth_error_connecting));
//                doUnbindService();
            }
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        // This method is *only* called when the connection to the service is lost unexpectedly
        // and *not* when the client unbinds (http://developer.android.com/guide/components/bound-services.html)
        // So the isServiceBound attribute should also be set to false when we unbind from the service.
        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, className.toString() + " service is unbound");
        }
    };

    private final Runnable mQueueCommands = new Runnable() {
        @Override
        public void run() {
            if (service != null && service.isRunning() && service.queueEmpty()) {
                queueCommands();
            }
            new Handler().postDelayed(mQueueCommands,10000);
        }
    };

    private void queueCommands() {
            for (ObdCommand Command : ObdConfig.getCommands()) {
                    service.queueJob(new ObdCommandJob(Command));
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weepake);
        initView();
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClearCommands();
            }
        });
        bluetoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBluetooth();
            }
        });

        ObdButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getBondedDevices();
                AlertDialog.Builder builder = new AlertDialog.Builder(WeepakeActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("连接到OBD");
                String[] bondedNames = new String[deviceList.size()];

                for (int i=0; i<deviceList.size(); i++ ){
                    bondedNames[i] = deviceList.get(i).getName();
                    SharedPreferencesUtils.setParam(WeepakeActivity.this,PARA_DEV_ADDR,deviceList.get(i).getAddress());
                }

                builder.setSingleChoiceItems(bondedNames,0,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferencesUtils.setParam(WeepakeActivity.this,PARA_DEV_ADDR,deviceList.get(which).getAddress());
                        obdDevice = deviceList.get(which);
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doBindService();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });
        new Handler().post(mQueueCommands);
    }

    public void startDiscovery(){
        BroadcastReceiver discoveryResult = new BroadcastReceiver() {
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
//        if (!isServiceBound) {
//            Log.d(TAG, "Binding OBD service..");
//            if (preRequisites) {
//                btStatusTextView.setText(getString(R.string.status_bluetooth_connecting));
                Intent serviceIntent = new Intent(this, ObdGatewayService.class);
                bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
//            } else {
//                btStatusTextView.setText(getString(R.string.status_bluetooth_disabled));
//                Intent serviceIntent = new Intent(this, MockObdGatewayService.class);
//                bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
//            }
//        }
    }

    public void enableBluetooth(){
        if ( !isBluetoothEnable ){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,0);

        }else{
            Log.d("","蓝牙已打开");

        }
    }

    public void conntectObd(){

    }

    public static String LookUpCommand(String txt) {
        for (AvailableCommandNames item : AvailableCommandNames.values()) {
            if (item.getValue().equals(txt)) return item.name();
        }
        return txt;
    }

    public void updateTextView(final TextView view, final String txt) {
        new Handler().post(new Runnable() {
            public void run() {
                view.setText(txt);
            }
        });
    }

    public void stateUpdate(final ObdCommandJob job) {
        final String cmdName = job.getCommand().getName();
        String cmdResult = job.getCommand().getFormattedResult();
        final String cmdID = LookUpCommand(cmdName);

        if (dataScroll.findViewWithTag(cmdID) != null) {
            TextView existingTV = (TextView) dataScroll.findViewWithTag(cmdID);
            existingTV.setText(cmdResult);
        } else addTableRow(cmdID, cmdName, cmdResult);
        commandResult.put(cmdID, cmdResult);
    }

    private void addTableRow(String id, String key, String val) {

        TableRow tr = new TableRow(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins(TABLE_ROW_MARGIN, TABLE_ROW_MARGIN, TABLE_ROW_MARGIN,
//                TABLE_ROW_MARGIN);
        tr.setLayoutParams(params);

        TextView name = new TextView(this);
        name.setGravity(Gravity.RIGHT);
        name.setText(key + ": ");
        TextView value = new TextView(this);
        value.setGravity(Gravity.LEFT);
        value.setText(val);
        value.setTag(id);
        tr.addView(name);
        tr.addView(value);
        tl.addView(tr, params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode --> "+requestCode);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                bluetoothStatus.setText("已打开");
                bluetoothBtn.setEnabled(false);
            }
            if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "放弃打开蓝牙！");
            }
        } else {
            Log.d(TAG, "打开蓝牙异常！");
            return;
        }
    }

    public void addClearCommands() {
        service.queueJob(new ObdCommandJob(new ResetTroubleCodesCommand()));
    }

}
