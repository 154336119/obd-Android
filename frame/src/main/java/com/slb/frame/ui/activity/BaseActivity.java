package com.slb.frame.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.slb.frame.R;
import com.slb.frame.ui.toolbar.ToolbarBack;
import com.slb.frame.ui.toolbar.ToolbarContext;
import com.slb.frame.ui.widget.LoadingDialog;
import com.slb.frame.ui.widget.nodata.LoadingAndRetryManager;
import com.slb.frame.utils.PermissionListener;
import com.slb.frame.utils.hooklistener.HookCore;
import com.slb.frame.utils.hooklistener.HookListenerContract;
import com.slb.frame.utils.hooklistener.ListenerManager;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * 描述：非MVP模式的activity基类
 * Created by Lee
 * on 2016/9/14.
 */
public abstract class BaseActivity extends ShakeActivity implements LifecycleProvider<ActivityEvent> {
    private static final int CODE_REQUEST_PERMISSION = 1;
    private PermissionListener mPermissionListener;
    private Toast mToast;
    /** 加载等待框 */
    private LoadingDialog mLoadingDialog;
    /**无数据 无网络的类*/
    protected LoadingAndRetryManager mLoadingAndRetryManager;
    public final String TAG = getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    public abstract int getLayoutId();
    public void initActionBar() {}
    public void initView() {}
    public void addStack(){}
    public void initData() {}
    public void getIntentExtras(){}
    protected boolean addSwipeRefreshView(){
        return false;
    }
    /**
     * 是否开启rxbus注册以及销毁时取消注册
     * @return
     */
    protected boolean rxBusRegist(){return false;}
    /**
     * rxbus发送消息
     * @param object
     */
    protected void rxBusSendMessage(@NonNull Object object){RxBus.get().post(object);}
    protected Toolbar mToolbar;
    protected View mUnderLine;
    /**
     * 加载Fragment到布局
     */
    protected void addFragment(){}
    protected void addFragment(Bundle savedInstanceState){
        if (savedInstanceState==null){
            addFragment();
        }
    }
    /**
     * 是否设置toolbar
     * @return
     */
    protected boolean hasToolbar() {return true;}
    protected String setToolbarTitle() {return "";}
    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册rxbus
        if (rxBusRegist()){
            RxBus.get().register(this);
        }
        getIntentExtras();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setView();
        addStack();
        initView();
        initData();
        addFragment(savedInstanceState);
        //无数据类初始化
//        mLoadingAndRetryManager=LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
//            @Override
//            public void setRetryEvent(View retryView) {
//                BaseActivity.this.setRetryEvent(retryView);
//            }
//        });
//        mLoadingAndRetryManager.showContent();
        lifecycleSubject.onNext(ActivityEvent.CREATE);


    }

    /**
     * 设置布局和toolbar
     */
    private void setView() {
        if (hasToolbar()) {
            ViewGroup root = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.include_toolbar, null);
            if (addSwipeRefreshView()){//是否添加SwipeRefreshView
                ViewGroup swipeRefresh= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.view_swipe_refresh_layout,null);
                ViewGroup child= (ViewGroup) LayoutInflater.from(this).inflate(getLayoutId(), swipeRefresh);
                root.addView(child);
            }else {
                LayoutInflater.from(this).inflate(getLayoutId(), root);
            }
            setContentView(root);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            initActionBar();
            ToolbarContext toolbarContext = new ToolbarContext();
            toolbarContext.setToolBar(new ToolbarBack());
            toolbarContext.configure(this, mToolbar, setToolbarTitle());
            mUnderLine=findViewById(R.id.underLine);
        } else {
            if (addSwipeRefreshView()){//是否添加SwipeRefreshView
                ViewGroup swipeRefresh= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.view_swipe_refresh_layout,null);
                LayoutInflater.from(this).inflate(getLayoutId(), swipeRefresh);
                setContentView(swipeRefresh);
            }else {
                setContentView(getLayoutId());
            }
            initActionBar();
        }
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        if (rxBusRegist()){
            RxBus.get().unregister(this);
        }
    }

    @NonNull
    @Override
    @CheckResult
    public Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @NonNull
    @Override
    @CheckResult
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @NonNull
    @Override
    @CheckResult
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    /**
     * 系统toast提示
     *
     * @param msg
     */
    public void showToastMsg(final String msg) {
        this. runOnUiThread(new Runnable() {

            @Override

            public void run() {

                if (mToast != null) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        mToast.cancel();
                    }
                } else {
                    mToast = Toast.makeText(BaseActivity.this, "", Toast.LENGTH_LONG);
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                    mToast.setDuration(Toast.LENGTH_LONG);
                }
                if (TextUtils.isEmpty(msg)) {
                    return;
                }

                View contentView = View.inflate(BaseActivity.this, R.layout.base_toast, null);
                TextView tvMsg =  contentView.findViewById(R.id.tv_toast_msg);
                mToast.setView(contentView);
                tvMsg.setText(msg);
//                mToast.setText(msg);
                mToast.show();

            }

        });

    }

    /**
     * 显示加载框
     *
     * @param msg
     */
    public void showWaitDialog(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.setText(msg);
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /** 隐藏加载等待框 */
    public void hideWaitDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 网络错误页面点击事件设置
     * @param retryView
     */
    protected void setRetryEvent(View retryView)
    {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ListenerManager.Builer builer=new ListenerManager.Builer();
        builer.buildOnClickListener(new HookListenerContract.OnClickListener() {
            @Override
            public void doInListener(View v) {
//                Toast.makeText(BaseActivity.this, "单击时我执行", Toast.LENGTH_SHORT).show();
                Log.e("packageNam","packageNam:"+BaseActivity.this.getClass());
            }
        }).buildOnLongClickListener(new HookListenerContract.OnLongClickListener() {
            @Override
            public void doInListener(View v) {
//                Toast.makeText(BaseActivity.this, "长按时我执行", Toast.LENGTH_SHORT).show();
            }
        }).buildOnFocusChangeListener(new HookListenerContract.OnFocusChangeListener() {
            @Override
            public void doInListener(View v, boolean hasFocus) {
//                Toast.makeText(BaseActivity.this, "焦点变化时我执行", Toast.LENGTH_SHORT).show();
            }
        });
        HookCore.getInstance().startHook(this, ListenerManager.create(builer));
    }

    /**
     *
     * 判断mainactivity是否处于栈顶
     * @return  true在栈顶false不在栈顶
     */
    private boolean isMainActivityTop(){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(BaseActivity.class.getName());
    }
    /**监听页面内容是否发生变化*/
    protected void textChangeListener(final Button button, final TextView...textViews){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int j = 0;
                int len = textViews.length;
                for(int i =0;i<len;i++){
                    if(!TextUtils.isEmpty(textViews[i].getText().toString())){
                        j++;
                    }
                }
                if(j == len){
                    button.setEnabled(true);
                }else{
                    button.setEnabled(false);
                }
            }
        };
        int len = textViews.length;
        for(int i =0;i<len;i++){
            textViews[i].addTextChangedListener(textWatcher);
        }
    }

    /** 隐藏系统软键盘 */
    public void hideSoftInput() {
        if (getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**动态申请权限*/
    public void requestPermissions(String[] permissions, PermissionListener listener) {
        Activity activity = this;
        if (null == activity) {
            return;
        }
        mPermissionListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            //权限没有授权
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), CODE_REQUEST_PERMISSION);
        } else {
            mPermissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int result = grantResults[i];
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;

            default:
                break;
        }
    }


    public void showPermissionDialog() {

    }
    /**
     * 华为的权限管理页面
     */
    protected void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(getAppDetailSettingIntent());
        }
    }



    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }
}
