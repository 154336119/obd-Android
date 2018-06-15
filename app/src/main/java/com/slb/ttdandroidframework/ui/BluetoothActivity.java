package com.slb.ttdandroidframework.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.util.io.AbstractGatewayService;
import com.slb.ttdandroidframework.util.io.ObdGatewayService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {
    private static final String TAG = BluetoothActivity.class.getName();
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Button On,Off,Visible,list;
    private BluetoothAdapter ba;
    private Set<BluetoothDevice> pairedDevices;
    private ListView lv;
    private AbstractGatewayService service;
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG, className.toString() + " service is bound");
//            isServiceBound = true;
            service = ((AbstractGatewayService.AbstractGatewayServiceBinder) binder).getService();
            service.setContext(BluetoothActivity.this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Intent serviceIntent = new Intent(this, ObdGatewayService.class);
        bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
//        test();
    }


    public void test(){
        ba = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> bluetoothDevices = ba.getBondedDevices();

        for ( BluetoothDevice bluetoothDevice : bluetoothDevices ){
            System.out.println("==========================================================");
            System.out.println(bluetoothDevice.getName());
            System.out.println(bluetoothDevice.getAddress());
            System.out.println(bluetoothDevice.getUuids());
            System.out.println(bluetoothDevice.getBondState());
            System.out.println(bluetoothDevice.getType());
            System.out.println("==========================================================");

//            try {
//                BluetoothSocket socket = connect(bluetoothDevice);
//                System.out.println("socket is connected ---------> "+socket.isConnected());
//                InputStream inputStream = socket.getInputStream();
//                String line = "" ;
//                byte[] bytes = new byte[1024];
//                while( inputStream.read(bytes) != -1 ){
//                    System.out.println("*******************    "+String.valueOf(bytes));
//                }
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }

    }
}
