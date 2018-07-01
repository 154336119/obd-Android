package com.slb.ttdandroidframework.ui.fragment;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.DataContract;
import com.slb.ttdandroidframework.ui.presenter.DataPresenter;


public class HistoricalDrivingFragment
		extends BaseMvpFragment<DataContract.IView, DataContract.IPresenter>
		implements DataContract.IView{

	@Override
	protected boolean hasToolbar() {
		return false;
	}

	public static HistoricalDrivingFragment newInstance(){
		HistoricalDrivingFragment instance=new HistoricalDrivingFragment();
		return instance;
	}

	@Override
	public DataContract.IPresenter initPresenter() {
		return new DataPresenter();
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_historical_driving;
	}
}
