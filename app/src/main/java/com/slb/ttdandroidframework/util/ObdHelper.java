package com.slb.ttdandroidframework.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;

import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnableToConnectException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import com.hwangjr.rxbus.RxBus;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.ui.activity.DeviceActivity;
import com.slb.ttdandroidframework.util.config.BizcContant;

/**
 * Created by Veronika on 07.09.2016.
 */

public class ObdHelper {
    private static final String TAG = ObdHelper.class.getSimpleName();
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final int CANNOT_CONNECT_TO_DEVICE = 1;
    public static final int OBD_COMMAND_FAILURE = 10;
    public static final int OBD_COMMAND_FAILURE_IO = 11;
    public static final int OBD_COMMAND_FAILURE_UTC = 12;
    public static final int OBD_COMMAND_FAILURE_IE = 13;
    public static final int OBD_COMMAND_FAILURE_MIS = 14;
    public static final int OBD_COMMAND_FAILURE_NODATA = 15;

    private BaseActivity DeviceActivity;
    private Handler mHandler;

    private static BluetoothAdapter bluetoothAdapter;
    private static Set<BluetoothDevice> pairedDevices;

    private final Runnable mQueueCommands = new Runnable() {
        public void run() {
            new Handler().postDelayed(mQueueCommands, 400);
        }
    };

    private GetTroubleCodesTask gtct;

    public ObdHelper(Handler mHandler, BaseActivity DeviceActivity) {
        this.DeviceActivity = DeviceActivity;
        this.mHandler = mHandler;
        gtct = new GetTroubleCodesTask();
    }

    public void connectToDevice() {
        String remoteDevice =  (String) SharedPreferencesUtils.getParam(Base.getContext(), BizcContant.PARA_DEV_ADDR,"");
        if (remoteDevice == null | "".equals(remoteDevice))
            Log.e(TAG, "No bt device is paired.");
        else
            gtct.execute(remoteDevice);
    }

    private static void connectViaBluetooth() {
        if (bluetoothAdapter == null)
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static int checkBluetoothEnabled() {
        connectViaBluetooth();
        if (bluetoothAdapter == null)
            return -1;
        else if (!bluetoothAdapter.isEnabled()) {
            return 0;
        } else {
            return 1;
        }
    }

    public static Set<BluetoothDevice> getPairedDevice() {
        if(bluetoothAdapter==null)
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON)
            pairedDevices = bluetoothAdapter.getBondedDevices();
        return pairedDevices;
    }

    private class GetTroubleCodesTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            DeviceActivity.showWaitDialog(BizcContant.STR_CONNECTING);
        }
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            BluetoothDevice dev;
            //Get the current thread's token
            synchronized (this) {
                Log.d(TAG, "Starting service..");
                final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                dev = btAdapter.getRemoteDevice(params[0]);

                Log.d(TAG, "Stopping Bluetooth discovery.");
                btAdapter.cancelDiscovery();

                Log.d(TAG, "Starting OBD connection..");
                BluetoothSocket sock;
                // Instantiate a BluetoothSocket for the remote device and connect it.
                try {
                    sock = BluetoothUtil.getSockInstance();
                } catch (Exception e) {
                    Log.e(TAG, "There was an error while establishing connection. -> " + e.getMessage());
                    Log.d(TAG, "Message received on handler here");
                    mHandler.obtainMessage(CANNOT_CONNECT_TO_DEVICE).sendToTarget();
                    return null;
                }
                try {
                    // Let's configure the connection.
                    Log.d(TAG, "Queueing jobs for connection configuration..");
                    ObdResetCommand tcoc = new ObdResetCommand();
                    tcoc.run(sock.getInputStream(), sock.getOutputStream());
                    onProgressUpdate(8);
                    result = tcoc.getFormattedResult();
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
                    RxBus.get().post(new ObdConnectStateEvent(true));
                    DeviceActivity.hideWaitDialog();
                    return null;
                } catch (Exception e) {
                    Log.e("DTCERR", e.getMessage());
                    mHandler.obtainMessage(OBD_COMMAND_FAILURE).sendToTarget();
                } finally {
                   // closeSocket(sock);
                }
            }
            return result;
        }

        private void closeSocket(BluetoothSocket sock) {
            if (sock != null)
                try {
                    sock.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (!TextUtils.isEmpty(result)) {
                RxBus.get().post(new ObdConnectStateEvent(true));
                Log.d(TAG, result);
            } else {
                Log.e(TAG, "No result (Nullpointer).");
//                BluetoothUtil.setIsRunning(false);
            }
        }
    }

    /**
     * Instantiates a BluetoothSocket for the remote device and connects it.
     * See http://stackoverflow.com/questions/18657427/ioexception-read-failed-socket-might-closed-bluetooth-on-android-4-3/18786701#18786701
     *
     * @param dev The remote device to connect to
     * @return The BluetoothSocket
     * @throws IOException
     */
    private static BluetoothSocket connect(BluetoothDevice dev) throws IOException {
        BluetoothSocket sock = null;
        BluetoothSocket sockFallback;

        Log.d(TAG, "Starting Bluetooth connection..");
        try {
            sock = dev.createRfcommSocketToServiceRecord(MY_UUID);
            sock.connect();
        } catch (Exception e1) {
            Log.e(TAG, "There was an error while establishing Bluetooth connection. Falling back..", e1);
            if(sock!=null) {
                Class<?> clazz = sock.getRemoteDevice().getClass();
                Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
                try {
                    Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                    Object[] params = new Object[]{Integer.valueOf(1)};
                    sockFallback = (BluetoothSocket) m.invoke(sock.getRemoteDevice(), params);
                    sockFallback.connect();
                    sock = sockFallback;
                } catch (Exception e2) {
                    Log.e(TAG, "Couldn't fallback while establishing Bluetooth connection.", e2);
                    throw new IOException(e2.getMessage());
                }
            }
        }
        return sock;
    }
}