package com.slb.frame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/1
 *     desc  : 设备相关工具类
 * </pre>
 */
public class DeviceUtils {
    static DeviceUuidFactory uuidFactory;
    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @param context 上下文
     * @return MAC地址
     */
//    public static String getMacAddress(Context context) {
//        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifi.getConnectionInfo();
//        if (info != null) {
//            String macAddress = info.getMacAddress();
//            if (macAddress != null) {
//                return macAddress.replace(":", "");
//            }
//        }
//        return null;
//    }

    /**
     * 获取设备MAC地址
     * <p/>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */

    public static String getMacAddress(Context context) {
        if(uuidFactory ==null){
            uuidFactory = new DeviceUuidFactory(context);
        }
        if(uuidFactory.getUUid().toString().length()>30){
            return uuidFactory.getUUid().substring(0,30).replaceAll("-","");
        }else{
            return uuidFactory.getUUid().toString().replaceAll("-","");
        }
    }

    /**
     * 获取设备厂商，如Xiaomi
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    public static class DeviceUuidFactory {
        protected static final String PREFS_FILE = "device_id.xml";
        protected static final String PREFS_DEVICE_ID = "device_id";
        protected static UUID uuid;

        public static String getUUid(){
            return uuid.toString();
        }
        public DeviceUuidFactory(Context context) {
            if (uuid == null) {
                synchronized (DeviceUuidFactory.class) {
                    if (uuid == null) {
                        final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                        final String id = prefs.getString(PREFS_DEVICE_ID, null);
                        if (id != null) {
                            // Use the ids previously computed and stored in the prefs file
                            uuid = UUID.fromString(id);
                        } else {
                            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                            // Use the Android ID unless it's broken, in which case fallback on deviceId,
                            // unless it's not available, then fallback on a random number which we store
                            // to a prefs file
                            try {
                                if (!"9774d56d682e549c".equals(androidId)) {
                                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                                } else {
                                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                                }
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            }
                            // Write the value out to the prefs file
                            prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
                        }
                    }
                }
            }
        }
    }
}