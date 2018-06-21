package com.slb.ttdandroidframework.ui.fragment;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.HomeContract;
import com.slb.ttdandroidframework.ui.presenter.HomePresenter;


public class HomeFragment
		extends BaseMvpFragment<HomeContract.IView, HomeContract.IPresenter>
		implements HomeContract.IView{


	public static HomeFragment newInstance(){
		HomeFragment instance=new HomeFragment();
		return instance;
	}

	@Override
	public HomeContract.IPresenter initPresenter() {
		return new HomePresenter();
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_home;
	}
}
