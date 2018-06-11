package com.slb.frame.utils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
