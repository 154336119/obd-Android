package com.slb.ttdandroidframework.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.HistoryDriveDataEntity;
import com.slb.ttdandroidframework.ui.adapter.HistoryDriveDataAdapter;
import com.slb.ttdandroidframework.ui.contract.MyComFragmentListContract;
import com.slb.ttdandroidframework.ui.presenter.MyComFragmentListPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import danfei.shulaibao.widget.refresh.BaseBrvahRefreshFragment;


public class HistoricalDrivingFragment
		extends BaseBrvahRefreshFragment<MyComFragmentListContract.IView,MyComFragmentListContract.IPresenter,Object,HistoryDriveDataEntity>
		implements MyComFragmentListContract.IView{

	@Override
	protected boolean hasToolbar() {
		return false;
	}

	public static HistoricalDrivingFragment newInstance(){
		HistoricalDrivingFragment instance=new HistoricalDrivingFragment();
		return instance;
	}

	@Override
	protected RecyclerView.Adapter setAdapter() {
		for (int i = 0; i < 5; i++) {
			HistoryDriveDataEntity entity = new HistoryDriveDataEntity();
			mList.add(entity);
		}
		mAdapter = new HistoryDriveDataAdapter(mList);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.addItemDecoration(
				new HorizontalDividerItemDecoration.Builder(_mActivity)
						.color(Color.parseColor("#2B3139"))
						.sizeResId(R.dimen.investor_dot_height)
						.build());
		return mAdapter;
	}

	@Override
	public MyComFragmentListContract.IPresenter initPresenter() {
		return new MyComFragmentListPresenter();
	}
}
