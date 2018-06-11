package com.slb.frame.utils;

import android.os.Environment;

/**
 * 描述：
 * Created by Lee
 */
public class AppConfig {
    /**SD卡文件存储根目录 */
    private static final String FILE_ROOT_URL = "/ppcfund";
    /**SD卡文件存储-图片目录 */
    public static final String FILE_IMG_URL  = "/ppcfund-img";
    /**assets路径 */
    public static final String PATH_ASSETS= Environment.getExternalStorageDirectory().getAbsolutePath() +"/assets/经办人授权函模板.docx";

}
