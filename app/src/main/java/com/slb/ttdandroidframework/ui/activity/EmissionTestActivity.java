package com.slb.ttdandroidframework.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.EmissionTestEntity;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.ui.adapter.EmissionTestAdapter;
import com.slb.ttdandroidframework.ui.adapter.ErrorCodeAdapter;
import com.slb.ttdandroidframework.ui.contract.EmissionTestContract;
import com.slb.ttdandroidframework.ui.presenter.EmissionTestPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmissionTestActivity extends BaseMvpActivity<EmissionTestContract.IView, EmissionTestContract.IPresenter>
        implements EmissionTestContract.IView {

    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    @BindView(R.id.mTvAgain)
    TextView mTvAgain;
    @BindView(R.id.can_content_view)
    RecyclerView canContentView;

    private EmissionTestAdapter mAdapter;

    private List<EmissionTestEntity> mList = new ArrayList<>();

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public EmissionTestContract.IPresenter initPresenter() {
        return new EmissionTestPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_emission_test;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);

        //测试
        for (int i = 0; i < 2; i++) {
            EmissionTestEntity emissionTestEntity = new EmissionTestEntity();
            mList.add(emissionTestEntity);
        }

        mAdapter = new EmissionTestAdapter(mList);
        canContentView.setLayoutManager(new LinearLayoutManager(this));
        canContentView.setAdapter(mAdapter);
        canContentView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_10)
                        .build());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.mIvBack, R.id.mTvAgain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIvBack:
                finish();
                break;
            case R.id.mTvAgain:
                break;
        }
    }
}
