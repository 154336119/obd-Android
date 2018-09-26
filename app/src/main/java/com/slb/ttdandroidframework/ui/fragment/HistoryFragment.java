package com.slb.ttdandroidframework.ui.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.RefreshMineObdListtEvent;
import com.slb.ttdandroidframework.event.RefreshMineVehicleListtEvent;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;
import com.slb.ttdandroidframework.ui.activity.HistoryDetailActivity;
import com.slb.ttdandroidframework.ui.adapter.DataAdapter;
import com.slb.ttdandroidframework.ui.adapter.HisroryChoiseCarNumAdapter;
import com.slb.ttdandroidframework.ui.adapter.HisroryChoiseOBDAdapter;
import com.slb.ttdandroidframework.ui.adapter.HistoryErrorAdapter;
import com.slb.ttdandroidframework.ui.contract.HistoryContract;
import com.slb.ttdandroidframework.ui.presenter.HistoryPresenter;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HistoryFragment
        extends BaseMvpFragment<HistoryContract.IView, HistoryContract.IPresenter>
        implements HistoryContract.IView {

    @BindView(R.id.mTvScreen)
    TextView mTvScreen;
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
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private boolean isOpen = false;
    private HisroryChoiseCarNumAdapter mCarNumAdapter;
    private HisroryChoiseOBDAdapter mOBDAdapter;
    private List<VehicleEntity> mChoiseCarList = new ArrayList<>();
    private List<ObdEntity> mChoiseObdList = new ArrayList<>();
    //数据
    private VehicleEntity mChoiseVehicleEntity;
    private ObdEntity mChoiseObdEntity;

    private HistoryErrorAdapter mAdapter;
    private List<HistoryErrorCodeEntity> mList =new ArrayList<>();
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

        mCarNumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCarNumAdapter.setItemSel(position);
                mChoiseVehicleEntity = (VehicleEntity) adapter.getItem(position);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(_mActivity)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_10)
                        .build());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter = new HistoryErrorAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryErrorCodeEntity errorCodeEntity = (HistoryErrorCodeEntity) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(BizcContant.PARA_HISTORY_DETAIL, (ArrayList<? extends Parcelable>) errorCodeEntity.getDtcs());
                ActivityUtil.next(_mActivity, HistoryDetailActivity.class,bundle,false);
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.mTvScreen, R.id.TvStartTime, R.id.TvEndTime, R.id.TvResetting, R.id.TvComfirm})
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
                mPresenter.getHistoryErrorCode(mChoiseObdEntity, mChoiseVehicleEntity, TvStartTime.getText().toString(), TvEndTime.getText().toString());
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener mStartDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            String mresult = String.format("%0" + 2 + "d", monthOfYear);
            TvStartTime.setText(years + "-" + mresult + "-" + String.format("%0" + 2 + "d", dayOfMonth));
        }
    };
    private DatePickerDialog.OnDateSetListener mEndDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            String mresult = String.format("%0" + 2 + "d", monthOfYear);
            TvEndTime.setText(years + "-" + mresult + "-" + String.format("%0" + 2 + "d", dayOfMonth));
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
        if (mChoiseObdList != null && mChoiseObdList.size() > 0) {
            mChoiseObdEntity = mChoiseObdList.get(0);
        }
    }

    @Subscribe
    public void onRefreshVehicleListEvent(RefreshMineVehicleListtEvent event) {
        mChoiseCarList = Base.getUserEntity().getVehicleEntityList();
        mCarNumAdapter.setNewData(mChoiseCarList);
        if (mChoiseCarList != null && mChoiseCarList.size() > 0) {
            mChoiseVehicleEntity = mChoiseCarList.get(0);
        }
    }

    @Override
    public void getHistoryErrorCodeSuccess(List<HistoryErrorCodeEntity> list) {
        mAdapter.setNewData(list);
        mDrawerLayout.closeDrawers();   //关闭侧边栏的菜单
        isOpen = false;
    }
}
