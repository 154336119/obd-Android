package com.slb.ttdandroidframework.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.util.io.BluetoothManager;

import java.io.IOException;

/**
 * Created by juan on 2018/7/30.
 */

public class BluetoothUtil {
    private static BluetoothDevice devInstance;
    private static BluetoothSocket sockInstance;

    public static String getRemoteDevice() {
        return remoteDevice;
    }

    public static void setRemoteDevice(String remoteDevice) {
        BluetoothUtil.remoteDevice = remoteDevice;
    }

    private static String remoteDevice =null;
    public static boolean isIsRunning() {
        return isRunning;
    }

    public static void setIsRunning(boolean isRunning) {
        BluetoothUtil.isRunning = isRunning;
    }

    public static boolean isRunning = false;
    public static BluetoothDevice getDeviceInstance() throws IOException {
//            final String remoteDevice  = (String) SharedPreferencesUtils.getParam(Base.getContext(), BizcContant.PARA_DEV_ADDR,"");\
        final String remoteDevice = getRemoteDevice();
        if (remoteDevice == null || "".equals(remoteDevice)) {
//            Logger.d("获取设备失败");
            throw new IOException();
        } else {
            final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            devInstance = btAdapter.getRemoteDevice(remoteDevice);
            btAdapter.cancelDiscovery();
        }
        return devInstance;
    }

    public static void setSockInstance(BluetoothSocket sockInstance) {
        BluetoothUtil.sockInstance = sockInstance;
    }

    public static BluetoothSocket getSockInstance() throws IOException {
        if (sockInstance == null) {
            try {
                final String remoteDevice = getRemoteDevice();
                if (remoteDevice == null || "".equals(remoteDevice)) {
                    Logger.d("获取设备失败");
                    isRunning = false;
                    RxBus.get().post(new ObdConnectStateEvent(false));
                    throw new IOException();
                } else {
                    final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                    devInstance = btAdapter.getRemoteDevice(remoteDevice);
                    btAdapter.cancelDiscovery();
                }

                sockInstance = BluetoothManager.connect(devInstance);
            } catch (Exception e) {
                e.printStackTrace();
                isRunning = false;
                sockInstance = null;
                Logger.d("sock连接失败失败");
                RxBus.get().post(new ObdConnectStateEvent(false));
                //closeSocket(sockInstance);
                throw new IOException();
            }
        }
        return sockInstance;
    }

    private static void closeSocket(BluetoothSocket sock) {
        if (sock != null)
            try {
                sock.close();
            } catch (IOException e) {
            }
    }

}
