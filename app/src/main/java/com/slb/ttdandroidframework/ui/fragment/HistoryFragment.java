package com.slb.ttdandroidframework.ui.fragment;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.HistoryContract;
import com.slb.ttdandroidframework.ui.contract.HomeContract;
import com.slb.ttdandroidframework.ui.presenter.HistoryPresenter;
import com.slb.ttdandroidframework.ui.presenter.HomePresenter;


public class HistoryFragment
		extends BaseMvpFragment<HistoryContract.IView, HistoryContract.IPresenter>
		implements HistoryContract.IView{

	@Override
	protected boolean hasToolbar() {
		return false;
	}

	public static HistoryFragment newInstance(){
		HistoryFragment instance=new HistoryFragment();
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
}
