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
    public static BluetoothDevice getDeviceInstance() {
        if (devInstance == null) {
            final String remoteDevice  = (String) SharedPreferencesUtils.getParam(Base.getContext(), BizcContant.PARA_DEV_ADDR,"");
            if (remoteDevice == null || "".equals(remoteDevice)) {
                Logger.d("获取设备失败");
                return null;
            } else {
                final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                devInstance = btAdapter.getRemoteDevice(remoteDevice);
                   btAdapter.cancelDiscovery();
            }
        }
        return devInstance;
    }

    public static BluetoothSocket getSockInstance() {
        if (sockInstance == null) {
            try {
                if(getDeviceInstance() == null){
                    return  null;
                }
                sockInstance = BluetoothManager.connect(getDeviceInstance());
              } catch (IOException e) {
                e.printStackTrace();
                Logger.d("sock连接失败失败");
            }
        }
        return sockInstance;
    }


}
