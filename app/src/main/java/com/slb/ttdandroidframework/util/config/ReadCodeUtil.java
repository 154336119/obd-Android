package com.slb.ttdandroidframework.util.config;

import android.text.TextUtils;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.orhanobut.logger.Logger;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.MyApplication;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juan on 2018/10/31.
 */

public class ReadCodeUtil {
    public static List<ErrorCodeEntity> dataOkWaitCode(ObdCommand obdCommand) {
        List<ErrorCodeEntity> list = new ArrayList<>();
        Map<String, String> dtcVals = getDict(R.array.dtc_keys, R.array.dtc_values);
        String value = null;
        if(obdCommand!=null && !TextUtils.isEmpty(obdCommand.getName())) {
            for (String dtcCode : obdCommand.getFormattedResult().split("\n")) {
                value = dtcVals.get(dtcCode);
                ErrorCodeEntity errorCodeEntity = new ErrorCodeEntity();
                errorCodeEntity.setTitle(dtcCode);
                errorCodeEntity.setValue(value);
                list.add(errorCodeEntity);
                Logger.d("读取故障码：List<ErrorCodeEntity>:title-"+errorCodeEntity.getTitle()+".value-"+errorCodeEntity.getValue());
            }
        }
        Logger.d("读取故障码：List<ErrorCodeEntity>.size():"+list.size());
        return list;
    }

    public static Map<String, String> getDict(int keyId, int valId) {
        String[] keys = Base.getContext().getResources().getStringArray(keyId);
        String[] vals = Base.getContext().getResources().getStringArray(valId);
        Map<String, String> dict = new HashMap<String, String>();
        for (int i = 0, l = keys.length; i < l; i++) {
            dict.put(keys[i], vals[i]);
        }
        return dict;
    }
}
