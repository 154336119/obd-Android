package com.slb.ttdandroidframework.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.ui.adapter.FreezeFrameAdapter;
import com.slb.ttdandroidframework.ui.adapter.HistoryErrorAdapter;
import com.slb.ttdandroidframework.ui.contract.DataContract;
import com.slb.ttdandroidframework.ui.contract.MyComFragmentListContract;
import com.slb.ttdandroidframework.ui.presenter.DataPresenter;
import com.slb.ttdandroidframework.ui.presenter.MyComFragmentListPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import danfei.shulaibao.widget.refresh.BaseBrvahRefreshFragment;

public class HistoricalFailureFragment
		extends BaseBrvahRefreshFragment<MyComFragmentListContract.IView,MyComFragmentListContract.IPresenter,Object,HistoryErrorCodeEntity>
		implements MyComFragmentListContract.IView{
	@Override
	public void initView(View view) {
		super.initView(view);
//		mRecyclerView.addItemDecoration(
//				new HorizontalDividerItemDecoration.Builder(_mActivity)
//						.color(Color.parseColor("#2B3139"))
//						.sizeResId(R.dimen.distance_10)
//						.build());
		//测试
		for (int i = 0; i < 10; i++) {
			HistoryErrorCodeEntity historyErrorCodeEntity = new HistoryErrorCodeEntity();
			mList.add(historyErrorCodeEntity);
		}
		mAdapter.addData(mList);
	}

	@Override
	protected boolean hasToolbar() {
		return false;
	}

	public static HistoricalFailureFragment newInstance(){
		HistoricalFailureFragment instance=new HistoricalFailureFragment();
		return instance;
	}

	@Override
	public MyComFragmentListContract.IPresenter initPresenter() {
		return new MyComFragmentListPresenter();
	}

	@Override
	protected RecyclerView.Adapter setAdapter() {
		mAdapter = new HistoryErrorAdapter(mList);
		return mAdapter;
	}
}
