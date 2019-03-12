package com.slb.ttdandroidframework.ui.fragment;

import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.frame.utils.ActivityUtil;
import com.slb.frame.utils.ConvertUtils;
import com.slb.frame.utils.ScreenUtils;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.ui.activity.AddDeviceListActivity;
import com.slb.ttdandroidframework.ui.activity.DeviceActivity;
import com.slb.ttdandroidframework.ui.activity.EmissionTestActivity;
import com.slb.ttdandroidframework.ui.activity.FreezeFrameActivity;
import com.slb.ttdandroidframework.ui.activity.Mode5Activity;
import com.slb.ttdandroidframework.ui.activity.Mode6Activity;
import com.slb.ttdandroidframework.ui.activity.NewMode5Activity;
import com.slb.ttdandroidframework.ui.activity.NewMode6Activity;
import com.slb.ttdandroidframework.ui.activity.NewReadErrorCodeActivity;
import com.slb.ttdandroidframework.ui.activity.ReadErrorCodeActivity;
import com.slb.ttdandroidframework.ui.activity.TroubleLightSActivity;
import com.slb.ttdandroidframework.ui.contract.HomeContract;
import com.slb.ttdandroidframework.ui.presenter.HomePresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.weight.CustomDialog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.slb.ttdandroidframework.util.BluetoothUtil.isRunning;


public class HomeFragment
        extends BaseMvpFragment<HomeContract.IView, HomeContract.IPresenter>
        implements HomeContract.IView {


    @BindView(R.id.mTvReadErrorCode)
    TextView mTvReadErrorCode;
    @BindView(R.id.Fl01)
    FrameLayout mFl01;
    @BindView(R.id.mTvFreezeFrame)
    TextView mTvFreezeFrame;
    @BindView(R.id.Fl02)
    FrameLayout mFl02;
    @BindView(R.id.mTvEmissionTest)
    TextView mTvEmissionTest;
    @BindView(R.id.Fl03)
    FrameLayout mFl03;
    @BindView(R.id.mTvOxygen)
    TextView mTvOxygen;
    @BindView(R.id.Fl04)
    FrameLayout mFl04;
    @BindView(R.id.mTvTroubleLightState)
    TextView mTvTroubleLightState;
    @BindView(R.id.Fl05)
    FrameLayout mFl05;
    @BindView(R.id.mTvVehicleMonitoring)
    TextView mTvVehicleMonitoring;
    @BindView(R.id.Fl06)
    FrameLayout mFl06;
    Unbinder unbinder;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tvConnect)
    TextView tvConnect;
    private CustomDialog mCommonAlertDialog;
    @Override
    protected boolean hasToolbar() {
        return false;
    }

    public static HomeFragment newInstance() {
        HomeFragment instance = new HomeFragment();
        return instance;
    }

    @Override
    public HomeContract.IPresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        int w = (ScreenUtils.getScreenWidth(_mActivity)) / 2 - ConvertUtils.dp2px(_mActivity, 20);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFl01.getLayoutParams();
        params.height = w;//设置当前控件布局的高度
        params.width = w;
        mFl01.setLayoutParams(params);//将设置好的布局参数应用到控件中

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) mFl02.getLayoutParams();
        params2.height = w;//设置当前控件布局的高度
        params2.width = w;
        mFl02.setLayoutParams(params2);//将设置好的布局参数应用到控件中

        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) mFl03.getLayoutParams();
        params3.height = w;//设置当前控件布局的高度
        params3.width = w;
        mFl03.setLayoutParams(params3);//将设置好的布局参数应用到控件中

        RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) mFl04.getLayoutParams();
        params4.height = w;//设置当前控件布局的高度
        params4.width = w;
        mFl04.setLayoutParams(params4);//将设置好的布局参数应用到控件中

        RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams) mFl05.getLayoutParams();
        params5.height = w;//设置当前控件布局的高度
        params5.width = w;
        mFl05.setLayoutParams(params5);//将设置好的布局参数应用到控件中

        RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams) mFl06.getLayoutParams();
        params6.height = w;//设置当前控件布局的高度
        params6.width = w;
        mFl06.setLayoutParams(params6);//将设置好的布局参数应用到控件中


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @OnClick({R.id.Fl01, R.id.Fl02, R.id.Fl03, R.id.Fl04, R.id.Fl05, R.id.Fl06,R.id.tvConnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fl01:
                //读取故障码
                if(!checkObd()){return;}
                RxBus.get().post(new ObdServiceStateEvent(false));
                ActivityUtil.next(_mActivity, NewReadErrorCodeActivity.class);
                break;
            case R.id.Fl02:
                //冻结帧
                if(!checkObd()){return;}
                RxBus.get().post(new ObdServiceStateEvent(false));
                ActivityUtil.next(_mActivity, FreezeFrameActivity.class);
                break;
            case R.id.Fl03:
                //尾气检测
                if(!checkObd()){return;}
                RxBus.get().post(new ObdServiceStateEvent(false));
                ActivityUtil.next(_mActivity, EmissionTestActivity.class);
                break;
            case R.id.Fl04:
                //氧气传感器
                if(!checkObd()){return;}
                RxBus.get().post(new ObdServiceStateEvent(false));
                ActivityUtil.next(_mActivity, NewMode5Activity.class);
                break;
            case R.id.Fl05:
                //故障灯状态
                if(!checkObd()){return;}
                RxBus.get().post(new ObdServiceStateEvent(false));
                ActivityUtil.next(_mActivity, TroubleLightSActivity.class);
                break;
            case R.id.Fl06:
                //车载监控
                if(!checkObd()){return;}
                RxBus.get().post(new ObdServiceStateEvent(false));
                ActivityUtil.next(_mActivity, NewMode6Activity.class);
                break;
            case R.id.tvConnect:
                ActivityUtil.next(_mActivity,AddDeviceListActivity.class);
                break;
        }
    }

    @Subscribe
    public void onObdConnectStateEvent(ObdConnectStateEvent event) {
        if(event.isConnect()){
            tvConnect.setText("Connected");
            String dev = (String) SharedPreferencesUtils.getParam(Base.getContext(), BizcContant.SP_CONNECT_DEV, "");
            tv01.setText(dev);
        }else{
            tvConnect.setText("Connect");
            tv01.setText("obd");
        }
    }



    /**
     * 显示dialog
     */
    private void showDialog() {
        CustomDialog.Builder dialog = new CustomDialog.Builder(_mActivity);
        dialog
                .setTitle(getString(R.string.prompt))
                .setMessage(getString(R.string.prompt))
                .setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothUtil.setIsRunning(false);
                        BluetoothUtil.setSockInstance(null);
                        BluetoothUtil.setRemoteDevice(null);
                        RxBus.get().post(new ObdServiceStateEvent(false));
                        RxBus.get().post(new ObdConnectStateEvent(false));
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mCommonAlertDialog = dialog.create();
        mCommonAlertDialog.setCanceledOnTouchOutside(false);
        mCommonAlertDialog.show();
    }
    @Override
    protected boolean rxBusRegist() {
        return true;
    }

    private boolean checkObd(){
        if(isRunning){
            return true;
        }else{
            showToastMsg(getString(R.string.OBD_is_not_connect));
            return false;
        }

//        try {
//            BluetoothSocket sock = BluetoothUtil.getSockInstance();
//            return true;
//        } catch (IOException e) {
//            showToastMsg("OBD未连接");
//            return false;
//        }
    }
    /**
     * 断开连接dialog
     */
    private void showDisConnectDialog() {
        CustomDialog.Builder dialog = new CustomDialog.Builder(_mActivity);
        dialog
                .setTitle("提示")
                .setMessage("是否断开设备连接？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothUtil.setIsRunning(false);
                        BluetoothUtil.setSockInstance(null);
                        RxBus.get().post(new ObdServiceStateEvent(false));
                        RxBus.get().post(new ObdConnectStateEvent(false));
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mCommonAlertDialog = dialog.create();
        mCommonAlertDialog.setCanceledOnTouchOutside(false);
        mCommonAlertDialog.show();
    }
}
