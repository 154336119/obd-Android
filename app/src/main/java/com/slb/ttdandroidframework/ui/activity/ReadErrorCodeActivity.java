package com.slb.ttdandroidframework.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.ui.adapter.ErrorCodeAdapter;
import com.slb.ttdandroidframework.ui.contract.ReadErrorCodeContract;
import com.slb.ttdandroidframework.ui.presenter.ReadErrorCodePresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadErrorCodeActivity extends BaseMvpActivity<ReadErrorCodeContract.IView, ReadErrorCodeContract.IPresenter>
        implements ReadErrorCodeContract.IView {

    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    @BindView(R.id.mTvAgain)
    TextView mTvAgain;
    @BindView(R.id.TvConfirmErrorCodeNum)
    TextView mTvConfirmErrorCodeNum;
    @BindView(R.id.CheckboxConfirmErrorCode)
    CheckBox mCheckboxConfirmErrorCode;
    @BindView(R.id.TvWaitErrorCodeNum)
    TextView mTvWaitErrorCodeNum;
    @BindView(R.id.CheckboxWaitErrorCode)
    CheckBox mCheckboxWaitErrorCode;
    @BindView(R.id.Rv01)
    RecyclerView Rv01;
    @BindView(R.id.Rv02)
    RecyclerView Rv02;
    @BindView(R.id.NestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.BtnClearError)
    Button BtnClearError;

    private ErrorCodeAdapter mAdapter01;
    private ErrorCodeAdapter mAdapter02;

    private List<ErrorCodeEntity> mConfirmErrorCodeList = new ArrayList<>();
    private List<ErrorCodeEntity> mWaitErrorCodeList = new ArrayList<>();

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public ReadErrorCodeContract.IPresenter initPresenter() {
        return new ReadErrorCodePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_error_code;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mNestedScrollView.setNestedScrollingEnabled(false);
        //测试
        for (int i = 0; i < 10; i++) {
            ErrorCodeEntity errorCodeEntity = new ErrorCodeEntity();
            mConfirmErrorCodeList.add(errorCodeEntity);
            mWaitErrorCodeList.add(errorCodeEntity);
        }

        mAdapter01 = new ErrorCodeAdapter(mConfirmErrorCodeList);
        Rv01.setLayoutManager(new LinearLayoutManager(this));
        Rv01.setAdapter(mAdapter01);
        Rv01.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.line_height)
                        .build());

        mAdapter02 = new ErrorCodeAdapter(mWaitErrorCodeList);
        Rv02.setLayoutManager(new LinearLayoutManager(this));
        Rv02.setAdapter(mAdapter02);
        Rv02.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.line_height)
                        .build());

        mCheckboxConfirmErrorCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Rv01.setVisibility(View.VISIBLE);
                } else {
                    Rv01.setVisibility(View.GONE);
                }
            }
        });

        mCheckboxWaitErrorCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Rv02.setVisibility(View.VISIBLE);
                } else {
                    Rv02.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.mIvBack, R.id.mTvAgain,R.id.BtnClearError})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIvBack:
                break;
            case R.id.mTvAgain:
                break;
            case R.id.BtnClearError:
                break;
        }
    }
}
