package com.slb.frame.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.slb.frame.R;
import com.slb.frame.ui.toolbar.ToolbarContext;
import com.slb.frame.ui.toolbar.ToolbarFragmentBack;
import com.slb.frame.ui.widget.LoadingDialog;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import me.yokeyword.fragmentation.SupportFragment;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * 描述：
 * Created by Lee
 * on 2016/9/14.
 */
public abstract class BaseFragment extends SupportFragment implements LifecycleProvider<FragmentEvent>{
    public Toast mToast;
    protected View mRootView = null;
    public final String TAG = getClass().getSimpleName();
    private BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    private LoadingDialog mLoadingDialog;
    /**无数据 无网络的类*/
//    protected LoadingAndRetryManager mLoadingAndRetryManager;
    public abstract int getLayoutId();
    public void initActionBar(){};
    public void initView(View view){}
    public void initData(){}
    protected boolean hasToolbar() {return false;}
    protected String setToolbarTitle() {return "";}
    private Toolbar mToolbar;
    protected boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }
    /**
     * 是否开启rxbus注册以及销毁时取消注册
     * @return
     */
    protected boolean rxBusRegist(){
        return false;
    }
    /**
     * rxbus发送消息
     * @param object
     */
    protected void rxBusSendMessage(@NonNull Object object){
        RxBus.get().post(object);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (rxBusRegist()){
            RxBus.get().register(this);
        }
        initActionBar();
    }


    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        setView();
        initView(mRootView);
        initData();
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        //无数据类初始化
//        mLoadingAndRetryManager=LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
//            @Override
//            public void setRetryEvent(View retryView) {
//                BaseFragment.this.setRetryEvent(retryView);
//            }
//        });
//        mLoadingAndRetryManager.showContent();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        if (rxBusRegist()){
            RxBus.get().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }


    @NonNull
    @Override
    public Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject,event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    public void showToastMsg(final String msg) {
        getActivity(). runOnUiThread(new Runnable() {

            @Override

            public void run() {

                if (mToast != null) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        mToast.cancel();
                    }
                } else {
                    mToast = Toast.makeText(getContext(), "", Toast.LENGTH_LONG);
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                }
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                mToast.setText(msg);
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
            mLoadingDialog = new LoadingDialog(getContext());
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
    public void setRetryEvent(View retryView)
    {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    /**
     * 设置布局和toolbar
     */
    private void setView() {
        if (hasToolbar()) {
            ViewGroup root = (ViewGroup) LayoutInflater.from(_mActivity).inflate(R.layout.include_fragment_toolbar, null);
            LayoutInflater.from(_mActivity).inflate(getLayoutId(), root);
            mRootView=root;
            mToolbar = (Toolbar)mRootView.findViewById(R.id.toolbar);
            mToolbar.setTitle("");
            _mActivity.setSupportActionBar(mToolbar);
            initActionBar();
            ToolbarContext toolbarContext = new ToolbarContext();
            toolbarContext.setToolBar(new ToolbarFragmentBack());
            toolbarContext.configure(_mActivity, mToolbar, setToolbarTitle());
           // mToolbar.setNavigationIcon(setNavigationIcon());
        } else {
            initActionBar();
        }
    }
    protected int setNavigationIcon(){
        return R.mipmap.back_white;
    }

    public void showMsg(String msg) {
        showToastMsg(msg);
    }

    public void showLoadingDialog(String msg) {
        showWaitDialog(msg);
    }

    public void loadingDialogDismiss() {
        hideWaitDialog();
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }
}
