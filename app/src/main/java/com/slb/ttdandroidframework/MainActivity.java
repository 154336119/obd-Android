package com.slb.ttdandroidframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONArray;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.ui.fragment.BaseFragment;
import com.slb.ttdandroidframework.event.ChoiseComEvent;
import com.slb.ttdandroidframework.event.RefreshMineObdListtEvent;
import com.slb.ttdandroidframework.event.RefreshMineVehicleListtEvent;
import com.slb.ttdandroidframework.event.RefreshObdListtEvent;
import com.slb.ttdandroidframework.event.RefreshVehicleListtEvent;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.ObdSEntity;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.http.bean.VehiclesEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
import com.slb.ttdandroidframework.ui.fragment.DataFragment;
import com.slb.ttdandroidframework.ui.fragment.HistoryFragment;
import com.slb.ttdandroidframework.ui.fragment.HomeFragment;
import com.slb.ttdandroidframework.ui.fragment.MineFragment;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    public static final int HOME_HOME = 0;
    public static final int HOME_DATA = 1;
    public static final int HOME_HISTORY = 2;
    public static final int HOME_MINE = 3;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    @BindView(R.id.bottomBar)
    RadioGroup bottomBar;
    private int prePosition;
    private BaseFragment[] mFragments = new BaseFragment[4];
    protected boolean hasToolbar() {
        return false;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean rxBusRegist() {
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mFragments[HOME_HOME] = HomeFragment.newInstance();
            mFragments[HOME_DATA] = DataFragment.newInstance();
            mFragments[HOME_HISTORY] = HistoryFragment.newInstance();
            mFragments[HOME_MINE] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.mainFrame, HOME_HOME,
                    mFragments[HOME_HOME],
                    mFragments[HOME_DATA],
                    mFragments[HOME_HISTORY],
                    mFragments[HOME_MINE]);
        } else {
            mFragments[HOME_HOME] = findFragment(HomeFragment.class);
            mFragments[HOME_DATA] = findFragment(DataFragment.class);
            mFragments[HOME_HISTORY] = findFragment(HistoryFragment.class);
            mFragments[HOME_MINE] = findFragment(MineFragment.class);
        }
    }
    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        prePosition = 0;
        bottomBar.setOnCheckedChangeListener(this);
        bottomBar.check(R.id.rb_home);
        getObdList();
        getVehicleList();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                showHideFragment(mFragments[0], mFragments[prePosition]);
                prePosition = 0;
                break;
            case R.id.rb_data:
                showHideFragment(mFragments[1], mFragments[prePosition]);
                prePosition = 1;

                break;
            case R.id.rb_history:
                showHideFragment(mFragments[2], mFragments[prePosition]);
                prePosition = 2;

                break;
            case R.id.rb_mine:
                showHideFragment(mFragments[3], mFragments[prePosition]);
                prePosition = 3;
                break;
        }
    }
    public void stateUpdate(final ObdCommandJob job){
        DataFragment fragment = (DataFragment)mFragments[HOME_DATA];
        fragment.onObdCommandJobEvent(job);
    }

    /**
     * 获取obd列表
     */
    public void getObdList(){
        OkGo.<LzyResponse<ObdSEntity>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/user/"+Base.getUserEntity().getId()+"/obd")
                .tag(this)//
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<ObdSEntity>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ObdSEntity>> response) {
                        ObdSEntity entity = response.body().data;
                        if(entity!=null && entity.getObds()!=null && entity.getObds().size()>0){
                            Base.getUserEntity().setObdEntityList(entity.getObds());
                            RxBus.get().post(new RefreshMineObdListtEvent());
                        }
                    }
                });
    }

    /**
     * 获取车辆列表
     */
    public void getVehicleList(){
        OkGo.<LzyResponse<VehiclesEntity>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/user/"+Base.getUserEntity().getId()+"/vehicle")
                .tag(this)//
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<VehiclesEntity>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<VehiclesEntity>> response) {
                        VehiclesEntity entity = response.body().data;
                        if(entity!=null && entity.getVehicles()!=null && entity.getVehicles().size()>0){
                            Base.getUserEntity().setVehicleEntityList(entity.getVehicles());
                            RxBus.get().post(new RefreshMineVehicleListtEvent());
                        }
                    }
                });
    }

    @Subscribe
    public void onRefreshObdListEvent(RefreshObdListtEvent event) {
        getObdList();
    }

    @Subscribe
    public void onRefreshVehicleListEvent(RefreshVehicleListtEvent event) {
        getVehicleList();
    }

}
