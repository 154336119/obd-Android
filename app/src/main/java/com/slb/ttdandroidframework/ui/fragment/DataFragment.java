package com.slb.ttdandroidframework.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.HistoryDriveDataEntity;
import com.slb.ttdandroidframework.ui.adapter.HistoryDriveDataAdapter;
import com.slb.ttdandroidframework.ui.contract.DataContract;
import com.slb.ttdandroidframework.ui.presenter.DataPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DataFragment
        extends BaseMvpFragment<DataContract.IView, DataContract.IPresenter>
        implements DataContract.IView {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private List<HistoryDriveDataEntity> mList = new ArrayList<>();
    private HistoryDriveDataAdapter mAdapter;
    @Override
    protected boolean hasToolbar() {
        return false;
    }

    public static DataFragment newInstance() {
        DataFragment instance = new DataFragment();
        return instance;
    }

    @Override
    public DataContract.IPresenter initPresenter() {
        return new DataPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        //测试
//        for (int i = 0; i < 5; i++) {
//            HistoryDriveDataEntity entity = new HistoryDriveDataEntity();
//            mList.add(entity);
//        }
//        mAdapter = new HistoryDriveDataAdapter(mList);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(_mActivity)
//                        .color(Color.parseColor("#2B3139"))
//                        .sizeResId(R.dimen.investor_dot_height)
//                        .build());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
