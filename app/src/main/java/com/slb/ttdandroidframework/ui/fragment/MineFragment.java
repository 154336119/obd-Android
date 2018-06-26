package com.slb.ttdandroidframework.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.activity.DeviceActivity;
import com.slb.ttdandroidframework.ui.contract.MineContract;
import com.slb.ttdandroidframework.ui.presenter.MinePresenter;

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
    @BindView(R.id.Tv01)
    TextView Tv01;
    @BindView(R.id.Rl01)
    RelativeLayout Rl01;
    @BindView(R.id.line1)
    ImageView line1;
    @BindView(R.id.Tv02)
    TextView Tv02;
    @BindView(R.id.Rl02)
    RelativeLayout Rl02;
    @BindView(R.id.line2)
    ImageView line2;
    Unbinder unbinder;

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
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Rl01, R.id.Rl02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Rl01:
                break;
            case R.id.Rl02:
                ActivityUtil.next(_mActivity, DeviceActivity.class);
                break;
        }
    }
}
