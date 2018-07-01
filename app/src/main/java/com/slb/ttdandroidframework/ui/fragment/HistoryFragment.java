package com.slb.ttdandroidframework.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.slb.frame.ui.fragment.BaseFragment;
import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.HistoryContract;
import com.slb.ttdandroidframework.ui.presenter.HistoryPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.hoang8f.android.segmented.SegmentedGroup;


public class HistoryFragment
        extends BaseMvpFragment<HistoryContract.IView, HistoryContract.IPresenter>
        implements HistoryContract.IView,  RadioGroup.OnCheckedChangeListener  {

    @BindView(R.id.segmented)
    SegmentedGroup segmented;
    @BindView(R.id.mTvScreen)
    TextView mTvScreen;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    Unbinder unbinder;
    private BaseFragment[] mFragments = new BaseFragment[3];
    public static final int HD = 0;//历史行车
    public static final int HF = 1; //历史故障
    private int prePosition = 0;
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
        segmented.check(R.id.RbHistoricalDriving);
        segmented.setOnCheckedChangeListener(this);
        if (savedInstanceState == null) {
            mFragments[HD] = HistoricalDrivingFragment.newInstance();
            mFragments[HF] = HistoricalFailureFragment.newInstance();
            loadMultipleRootFragment(R.id.mainFrame, HD, mFragments[HD], mFragments[HF]);
        } else {
            mFragments[HD] = findFragment(HistoricalDrivingFragment.class);
            mFragments[HF] = findFragment(HistoricalFailureFragment.class);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.mTvScreen)
    public void onViewClicked() {
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
}
