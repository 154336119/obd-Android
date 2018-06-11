package com.slb.frame.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.slb.frame.R;
import com.slb.frame.utils.ScreenShotUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 刁剑
 * Created on 2016/11/2.
 * 注释:
 */

public class ShakeActivity extends SupportActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Vibrator mVibrator;//手机震动
    private SoundPool mSoundPool;//摇一摇音效
    private int mWeiChatAudio;

    private MyHandler mHandler;
    private static final int START_SHAKE = 0x1;
    private static final int AGAIN_SHAKE = 0x2;
    private static final int END_SHAKE = 0x3;
    //记录摇动状态
    private boolean isShake = false;

    //要上报的按钮
    private List<View> mReportButtonList=new ArrayList<>();
    //截屏种类判断
    private boolean mHasScrollView=false;
    //截屏包含了ScrollView的情况
    private ScrollView mScrollView;
    //要截取拼接的Toolbar
    private Toolbar mToolbar;

    /**是否开启摇一摇,根据APP环境判断，dev环境下才开启*/
    private boolean isDev=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //用packageName判断当前app属于哪个环境
//        String packageName=appInfo.metaData.getString("PACKAGE");
        String packageName=getPackageName();
        if (packageName.equals("com.shulaibao.danerfei.dev")){
            isDev=true;
        }

        //初始化SoundPool
        mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        mWeiChatAudio = mSoundPool.load(this, R.raw.weichat_audio, 1);

        //获取Vibrator震动服务
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mHandler = new MyHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isDev){
            //获取 SensorManager 负责管理传感器
            mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
            if (mSensorManager != null) {
                //获取加速度传感器
                mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                if (mAccelerometerSensor != null) {
                    mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
                }
            }
            //上报处理
            List<View> child=getAllChildViews(this);
            for (int i=0;i<child.size();i++){
                View view=child.get(i);
                if (view.hasOnClickListeners()){
                    mReportButtonList.add(view);
                }
                if (view instanceof ScrollView && !mHasScrollView){
                    mHasScrollView=true;
                    mScrollView= (ScrollView) view;
                }
                if (view instanceof Toolbar){
                    mToolbar= (Toolbar) view;
                }
            }
        }
    }

    @Override
    protected void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (isDev){
            if (mSensorManager != null) {
                mSensorManager.unregisterListener(this);
            }
        }
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 37 || Math.abs(y) > 37 || Math
                    .abs(z) > 37) && !isShake) {
                isShake = true;
                // TODO: 2016/10/19 实现摇动逻辑, 摇动后进行震动
                Thread thread = new Thread() {
                    @Override
                    public void run() {


                        super.run();
                        try {
                            Log.d("shake", "onSensorChanged: 摇动");

                            //开始震动 发出提示音 展示动画效果
                            mHandler.obtainMessage(START_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            //再来一次震动提示
                            mHandler.obtainMessage(AGAIN_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            mHandler.obtainMessage(END_SHAKE).sendToTarget();


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 遍历需要监听Listenerd的Activity
     * @param activity
     * @return
     */
    public List<View> getAllChildViews(Activity activity) {
        View view = activity.getWindow().getDecorView();
        return getAllChildViews(view);
    }

    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    private static class MyHandler extends Handler {
        private WeakReference<ShakeActivity> mReference;
        private ShakeActivity mActivity;
        public MyHandler(ShakeActivity activity) {
            mReference = new WeakReference<>(activity);
            if (mReference != null) {
                mActivity = mReference.get();
            }
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_SHAKE://摇一摇开始执行
                    //This method requires the caller to hold the permission VIBRATE.
                    mActivity.mVibrator.vibrate(300);
                    //发出提示音
                    mActivity.mSoundPool.play(mActivity.mWeiChatAudio, 1, 1, 0, 0, 1);

                    //绑定事件的按钮信息上传和截屏
                    Bitmap bitmapAll=null;
                    Bitmap toolbarBitmap=ScreenShotUtils.getViewBitmap(mActivity.mToolbar);
                    if (mActivity.mHasScrollView){//含有Scrollview，进行拼接上传
                        ViewGroup viewGroup= (ViewGroup) mActivity.mScrollView.getParent();
                        if (viewGroup instanceof LinearLayout){
                            if (viewGroup.getChildCount()<=1){//
                                Bitmap scrollViewBitmap= ScreenShotUtils.getBitmapByView(mActivity.mScrollView);
                                bitmapAll=ScreenShotUtils.mergeBitmap_TB(toolbarBitmap,scrollViewBitmap,true);
                            }else {//拼接
                                Bitmap newBitmap=null;
                                for (int i=0;i<viewGroup.getChildCount();i++){
                                    View child=viewGroup.getChildAt(i);
                                    if (child instanceof ScrollView){
                                        newBitmap=ScreenShotUtils.getBitmapByView((ScrollView) child);
                                    }else {
                                        newBitmap=ScreenShotUtils.getViewBitmap(child);
                                    }
                                    if (bitmapAll==null){
                                        bitmapAll=toolbarBitmap;
                                    }
                                    bitmapAll=ScreenShotUtils.mergeBitmap_TB(bitmapAll,newBitmap,true);
                                }
                            }
                        }else {//

                            Bitmap scrollViewBitmap= ScreenShotUtils.getBitmapByView(mActivity.mScrollView);
                            bitmapAll=ScreenShotUtils.mergeBitmap_TB(toolbarBitmap,scrollViewBitmap,true);
                        }
                    }else {//不含Scrollview,整屏上传
                        bitmapAll= ScreenShotUtils.takeScreenShot(mActivity);
                    }
                    try {
                        saveImage(bitmapAll);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<mActivity.mReportButtonList.size();i++){
                        View view=mActivity.mReportButtonList.get(i);
                        Log.e("view","getX:"+view.getX());
                        Log.e("view","getY:"+view.getY());
                        Log.e("view","getWidth:"+view.getWidth());
                        Log.e("view","getHeight:"+view.getHeight());
                        Log.e("view","getMeasuredWidth:"+view.getMeasuredWidth());
                        Log.e("view","getMeasuredHeight:"+view.getMeasuredHeight());
                    }
                    break;
                case AGAIN_SHAKE:
                    mActivity.mVibrator.vibrate(300);
                    break;
                case END_SHAKE:
                    //整体效果结束, 将震动设置为false
                    mActivity.isShake = false;
//                    // 展示上下两种图片回来的效果
//                    mActivity.startAnimation(true);
                    break;
            }
        }
        public File saveImage(Bitmap bmp) throws IOException {
            File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "diaojian");
            if (!appDir.exists() || !appDir.isDirectory()) {
                appDir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir.getAbsolutePath()+"/"+fileName);

            OutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);//将bg输出至文件
            fOut.flush();
            fOut.close(); // do not forget to close the stream
            // 最后通知图库更新
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+appDir.getAbsolutePath())));
            mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            return null;
        }
    }
}

