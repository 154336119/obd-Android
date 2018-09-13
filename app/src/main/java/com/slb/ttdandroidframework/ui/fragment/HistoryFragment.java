package com.slb.ttdandroidframework.ui.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.slb.frame.ui.fragment.BaseFragment;
import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.RefreshMineObdListtEvent;
import com.slb.ttdandroidframework.event.RefreshMineVehicleListtEvent;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;
import com.slb.ttdandroidframework.ui.adapter.HisroryChoiseCarNumAdapter;
import com.slb.ttdandroidframework.ui.adapter.HisroryChoiseOBDAdapter;
import com.slb.ttdandroidframework.ui.contract.HistoryContract;
import com.slb.ttdandroidframework.ui.presenter.HistoryPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.hoang8f.android.segmented.SegmentedGroup;


public class HistoryFragment
        extends BaseMvpFragment<HistoryContract.IView, HistoryContract.IPresenter>
        implements HistoryContract.IView, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.segmented)
    SegmentedGroup segmented;
    @BindView(R.id.mTvScreen)
    TextView mTvScreen;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    Unbinder unbinder;
    @BindView(R.id.mLLcontent)
    LinearLayout mLLcontent;
    @BindView(R.id.mLLleft)
    LinearLayout mLLRight;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.RvObd)
    RecyclerView RvObd;
    @BindView(R.id.RvPlateNumber)
    RecyclerView RvPlateNumber;
    @BindView(R.id.TvStartTime)
    TextView TvStartTime;
    @BindView(R.id.TvEndTime)
    TextView TvEndTime;
    @BindView(R.id.TvResetting)
    Button mTvResetting;
    @BindView(R.id.TvComfirm)
    Button mTvComfirm;
    private BaseFragment[] mFragments = new BaseFragment[3];
    public static final int HD = 0;//历史行车
    public static final int HF = 1; //历史故障
    private int prePosition = 0;
    private boolean isOpen = false;
    private HisroryChoiseCarNumAdapter mCarNumAdapter;
    private HisroryChoiseOBDAdapter mOBDAdapter;
    private List<VehicleEntity> mChoiseCarList = new ArrayList<>();
    private List<ObdEntity> mChoiseObdList = new ArrayList<>();
    private HistoricalFailureFragment mHistoricalFailureFragment;
    //数据
    private VehicleEntity mChoiseVehicleEntity;
    private ObdEntity mChoiseObdEntity;
    @Override
    protected boolean hasToolbar() {
        return false;
    }

    public static HistoryFragment newInstance() {
        HistoryFragment instance = new HistoryFragment();
        return instance;
    }

    @Override
    public HistoryContract.IPresenter initPresenter() {
        return new HistoryPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        segmented.check(R.id.RbHistoricalFailure);
        segmented.setOnCheckedChangeListener(this);
//        if (savedInstanceState == null) {
//            mFragments[HD] = HistoricalDrivingFragment.newInstance();
//            mHistoricalFailureFragment = HistoricalFailureFragment.newInstance();
//            mFragments[HF] = mHistoricalFailureFragment;
//            loadMultipleRootFragment(R.id.mainFrame, HD, mFragments[HD], mFragments[HF]);
//        } else {
//            mFragments[HD] = findFragment(HistoricalDrivingFragment.class);
//            mHistoricalFailureFragment = findFragment(HistoricalFailureFragment.class);
//            mFragments[HF] = mHistoricalFailureFragment;
//        }
        if (savedInstanceState == null) {
            mHistoricalFailureFragment = HistoricalFailureFragment.newInstance();
            mFragments[HF] = mHistoricalFailureFragment;
            loadMultipleRootFragment(R.id.mainFrame, HF, mFragments[HF]);
        } else {
            mHistoricalFailureFragment = findFragment(HistoricalFailureFragment.class);
            mFragments[HF] = mHistoricalFailureFragment;
        }

//        //测试
//        for(int i=0;i<3;i++){
//            ChoiseCarNumEntity entity = new ChoiseCarNumEntity();
//            ChoiseObdEntity choiseObdEntity = new ChoiseObdEntity();
//            mChoiseCarList.add(entity);
//            mChoiseObdList.add(choiseObdEntity);
//        }
        mChoiseCarList = Base.getUserEntity().getVehicleEntityList();
        mChoiseObdList = Base.getUserEntity().getObdEntityList();


        RvObd.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mOBDAdapter = new HisroryChoiseOBDAdapter(mChoiseObdList);
        RvObd.setAdapter(mOBDAdapter);
        RvObd.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(_mActivity)
                        .color(Color.parseColor("#3C444F"))
                        .sizeResId(R.dimen.distance_20)
                        .build());
        RvPlateNumber.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mCarNumAdapter = new HisroryChoiseCarNumAdapter(mChoiseCarList);
        RvPlateNumber.setAdapter(mCarNumAdapter);
        RvPlateNumber.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(_mActivity)
                        .color(Color.parseColor("#3C444F"))
                        .sizeResId(R.dimen.distance_20)
                        .build());
        mOBDAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mOBDAdapter.setItemSel(position);
                mChoiseObdEntity = (ObdEntity) adapter.getItem(position);
            }
        });

        mCarNumAdapter.setOnItemClickListener( new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCarNumAdapter.setItemSel(position);
                mChoiseVehicleEntity = (VehicleEntity) adapter.getItem(position);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.RbHistoricalDriving:
                showHideFragment(mFragments[HD], mFragments[prePosition]);
                prePosition = HD;
                break;
            case R.id.RbHistoricalFailure:
                showHideFragment(mFragments[HF], mFragments[prePosition]);
                prePosition = HF;
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.mTvScreen, R.id.TvStartTime, R.id.TvEndTime,R.id.TvResetting, R.id.TvComfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvScreen:
                if (isOpen) {
                    mDrawerLayout.closeDrawers();   //关闭侧边栏的菜单
                    isOpen = false;
                } else {
                    mDrawerLayout.openDrawer(mLLRight);  //显示左边的菜单栏的控制
                    isOpen = true;
                }
                break;
            case R.id.TvStartTime:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);//当前年
                int month = calendar.get(Calendar.MONTH);//当前月
                int day = calendar.get(Calendar.DAY_OF_MONTH);//当前日
                //new一个日期选择对话框的对象,并设置默认显示时间为当前的年月日时间.
                DatePickerDialog dialog = new DatePickerDialog(_mActivity, DatePickerDialog.THEME_HOLO_LIGHT, mStartDateListener, year, month, day);
                dialog.show();
                break;
            case R.id.TvEndTime:
                Calendar calendar1 = Calendar.getInstance();
                int year1 = calendar1.get(Calendar.YEAR);//当前年
                int month1 = calendar1.get(Calendar.MONTH);//当前月
                int day1 = calendar1.get(Calendar.DAY_OF_MONTH);//当前日
                //new一个日期选择对话框的对象,并设置默认显示时间为当前的年月日时间.
                DatePickerDialog dialog1 = new DatePickerDialog(_mActivity, DatePickerDialog.THEME_HOLO_LIGHT, mEndDateListener, year1, month1, day1);
                dialog1.show();
                break;
            case R.id.TvResetting:
                break;
            case R.id.TvComfirm:
                mPresenter.getHistoryErrorCode(mChoiseObdEntity,mChoiseVehicleEntity,TvStartTime.getText().toString(),TvEndTime.getText().toString());
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener mStartDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear +1;
            String mresult=String.format("%0"+2+"d",monthOfYear);
            TvStartTime.setText(years + "-" + mresult + "-" + String.format("%0"+2+"d",dayOfMonth));
        }
    };
    private DatePickerDialog.OnDateSetListener mEndDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear +1;
            String mresult=String.format("%0"+2+"d",monthOfYear);
            TvEndTime.setText(years + "-" + mresult + "-" + String.format("%0"+2+"d",dayOfMonth));
        }
    };

    @Override
    protected boolean rxBusRegist() {
        return true;
    }

    @Subscribe
    public void onRefreshMineObdListEvent(RefreshMineObdListtEvent event) {
        mChoiseObdList = Base.getUserEntity().getObdEntityList();
        mOBDAdapter.setNewData(mChoiseObdList);
        if(mChoiseObdList!=null &&mChoiseObdList.size()>0){
            mChoiseObdEntity = mChoiseObdList.get(0);
        }
    }

    @Subscribe
    public void onRefreshVehicleListEvent(RefreshMineVehicleListtEvent event) {
        mChoiseCarList = Base.getUserEntity().getVehicleEntityList();
        mCarNumAdapter.setNewData(mChoiseCarList);
        if(mChoiseCarList!=null &&mChoiseCarList.size()>0){
            mChoiseVehicleEntity = mChoiseCarList.get(0);
        }
    }

    @Override
    public void getHistoryErrorCodeSuccess(List<HistoryErrorCodeEntity> list) {
        mHistoricalFailureFragment.setList(list);
    }
}
