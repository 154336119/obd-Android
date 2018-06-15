package com.slb.ttdandroidframework;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.ui.fragment.BaseFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    public static final int HOME_FRIST = 0;
    public static final int HOME_PRODUCT = 1;
    public static final int HOME_ORDER = 2;
    public static final int HOME_INVESTOR = 3;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    @BindView(R.id.bottomBar)
    RadioGroup bottomBar;
    private int prePosition;
    private BaseFragment[] mFragments = new BaseFragment[4];

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
