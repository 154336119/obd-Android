package com.slb.ttdandroidframework.ui.fragment;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.MineContract;
import com.slb.ttdandroidframework.ui.presenter.MinePresenter;



public class MineFragment
		extends BaseMvpFragment<MineContract.IView, MineContract.IPresenter>
		implements MineContract.IView{


	public static MineFragment newInstance(){
		MineFragment instance=new MineFragment();
		return instance;
	}

	@Override
	public MineContract.IPresenter initPresenter() {
		return new MinePresenter();
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_mine;
	}
}
