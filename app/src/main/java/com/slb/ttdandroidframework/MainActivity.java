package com.slb.ttdandroidframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.ui.fragment.BaseFragment;
import com.slb.ttdandroidframework.ui.fragment.DataFragment;
import com.slb.ttdandroidframework.ui.fragment.HistoryFragment;
import com.slb.ttdandroidframework.ui.fragment.HomeFragment;
import com.slb.ttdandroidframework.ui.fragment.MineFragment;

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
}
