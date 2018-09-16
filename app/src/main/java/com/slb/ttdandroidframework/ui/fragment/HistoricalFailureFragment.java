package com.slb.ttdandroidframework.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.slb.frame.ui.fragment.BaseFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.HistoryErrorEvent;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;
import com.slb.ttdandroidframework.ui.adapter.DataAdapter;
import com.slb.ttdandroidframework.ui.adapter.HistoryErrorAdapter;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HistoricalFailureFragment
        extends BaseFragment {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
//    HistoryErrorAdapter mAdapter;
//    List<HistoryErrorCodeEntity> mList;
private DataAdapter mAdapter;
    List<DataEntity> mList = new ArrayList<>();
    public static HistoricalFailureFragment newInstance(){
        HistoricalFailureFragment instance=new HistoricalFailureFragment();
        return instance;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_historical_failure;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

//                new HorizontalDividerItemDecoration.Builder(this)
//                        .color(Color.parseColor("#2B3139"))
//                        .sizeResId(R.dimen.distance_1)
//                        .build());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    protected boolean rxBusRegist() {
        return true;
    }
    @Subscribe
    public void onHistoryErrorEvent(HistoryErrorEvent event) {
//        mAdapter = new HistoryErrorAdapter(event.getmList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
                //测试
        for (int i = 0; i < 5; i++) {
            DataEntity entity = new DataEntity();
            mList.add(entity);
        }
        mAdapter = new DataAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
    }


    public void setList(List<HistoryErrorCodeEntity> list){
//        mList = list;
//        mAdapter = new HistoryErrorAdapter(mList);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
//        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
//        mRecyclerView.addItemDecoration(
    }

}
