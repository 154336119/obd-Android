package com.slb.ttdandroidframework.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ModNickNameEvent;
import com.slb.ttdandroidframework.event.RefreshMineObdListtEvent;
import com.slb.ttdandroidframework.event.RefreshMineVehicleListtEvent;
import com.slb.ttdandroidframework.ui.activity.CarInfoActivity;
import com.slb.ttdandroidframework.ui.activity.ObdInfoActivity;
import com.slb.ttdandroidframework.ui.activity.SettingActivity;
import com.slb.ttdandroidframework.ui.adapter.MineCarAdapter;
import com.slb.ttdandroidframework.ui.adapter.MineObdAdapter;
import com.slb.ttdandroidframework.ui.contract.MineContract;
import com.slb.ttdandroidframework.ui.presenter.MinePresenter;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class MineFragment
        extends BaseMvpFragment<MineContract.IView, MineContract.IPresenter>
        implements MineContract.IView {

    @BindView(R.id.TvHead)
    CircleImageView TvHead;
    @BindView(R.id.Rl01)
    RelativeLayout Rl01;
    Unbinder unbinder;
    @BindView(R.id.Rv01)
    RecyclerView Rv01;
    @BindView(R.id.TvName)
    TextView TvName;
    @BindView(R.id.TvSetting)
    TextView TvSetting;

    private MineCarAdapter mAdapterCar;
    private MineObdAdapter mAdapterObd;

    @Override
    protected boolean hasToolbar() {
        return false;
    }


    public static MineFragment newInstance() {
        MineFragment instance = new MineFragment();
        return instance;
    }

    @Override
    public MineContract.IPresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        TvName.setText(Base.getUserEntity().getEmail());

        mAdapterCar = new MineCarAdapter(Base.getUserEntity().getVehicleEntityList());
        Rv01.setLayoutManager(new LinearLayoutManager(_mActivity));
        Rv01.setAdapter(mAdapterCar);
        Rv01.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(_mActivity)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.line_height_1)
                        .build());

        mAdapterCar.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(BizcContant.PARA_CAR, mAdapterCar.getData().get(position));
                bundle.putInt(BizcContant.PARA_OPERATION, BizcContant.EDIT);
                ActivityUtil.next(_mActivity, CarInfoActivity.class, bundle, false);
            }
        });
        refreshCars();
        if (Base.getUserEntity().getNickname() != null) {
            TvName.setText(Base.getUserEntity().getNickname());
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Rl01,R.id.TvSetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Rl01:
                ActivityUtil.next(_mActivity, CarInfoActivity.class);
                break;
            case R.id.TvSetting:
                ActivityUtil.next(_mActivity, SettingActivity.class);
                break;
//            case R.id.TvCar01:
//                ActivityUtil.next(_mActivity, CarInfoActivity.class);
//                break;
//            case R.id.TvObd01:
//                ActivityUtil.next(_mActivity, ObdInfoActivity.class);
        }
    }

    /**
     * 更新车辆列表
     */
    public void refreshCars() {
        mAdapterCar.getData().clear();
        mAdapterCar.setNewData(Base.getUserEntity().getVehicleEntityList());
    }


    @Override
    protected boolean rxBusRegist() {
        return true;
    }

    @Subscribe
    public void onRefreshVehicleListEvent(RefreshMineVehicleListtEvent event) {
        refreshCars();
    }

    @Subscribe
    public void onNickeNameChangeEvent(ModNickNameEvent event) {
        TvName.setText(event.getName());
    }
}
