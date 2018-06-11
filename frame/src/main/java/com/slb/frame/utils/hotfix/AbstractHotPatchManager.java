package com.slb.frame.utils.hotfix;

import android.content.Context;
import android.os.Environment;

import com.lzy.okserver.download.DownloadInfo;
import com.lzy.okserver.download.DownloadManager;
import com.orhanobut.logger.Logger;
import com.slb.frame.utils.FileUtils;

import java.io.File;

/**
 * 描述：热修复相关工具
 * Created by Lee
 * on 2016/10/19.
 */
public abstract class AbstractHotPatchManager {
    /**SD卡文件存储根目录 */
    public static final String FILE_ROOT_URL = "/base/";
    /**apatch保存路径 */
    public static final String UPDATE_APATCH_SAVE_PATH = FILE_ROOT_URL + "apatch/";
    /**安装包的名字*/
    public static final String DOWNLOAD_APATCH_NAME = "df.apatch";
    private DownloadInfo mDownloadInfo;
    private DownloadManager mDownloadManager;
    private String mDirPath;
    public void downLoadPatch(String storeFilePath,String url){

    }
    private String getApatchName(){
        try{
            if(FileUtils.haveSDCard()){
                //当前外部存储设备的目录
                File sdFile = Environment.getExternalStorageDirectory();
                if(!sdFile.exists()){
                    sdFile.mkdir();
                }
                Logger.d("**************sdFile.getAbsolutePath():"+sdFile.getAbsolutePath());
                //当前外部存储设备的目录/base/apatch/
                mDirPath = sdFile.getAbsolutePath()+UPDATE_APATCH_SAVE_PATH;
                File dirFile = new File(mDirPath);
                if(!dirFile.exists()){
                    dirFile.mkdir();
                }
                Logger.d("****************dirFile.getAbsolutePath():"+dirFile.getAbsolutePath());
                File file = new File(mDirPath,DOWNLOAD_APATCH_NAME);
                if(file.exists()){
                    file.delete();
                }
                //当前apatch目录/base/apatch/df.apatch
                Logger.d("****************file.getAbsolutePath():"+file.getAbsolutePath());
                return file.getAbsolutePath();
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 设置 已经修复的 标记
     */
    public abstract void setApatchTag();
    /**
     * 获取是否已经 热修复 的tag
     * @return
     */
    public abstract String getApatchTag();

    /**
     * 清空tag（用于版本升级以后）
     */
    public abstract  void cleanApatchTag(Context context);
}
