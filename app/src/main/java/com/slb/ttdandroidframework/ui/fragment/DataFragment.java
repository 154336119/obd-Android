package com.slb.ttdandroidframework.ui.fragment;

import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.DataContract;
import com.slb.ttdandroidframework.ui.contract.HomeContract;
import com.slb.ttdandroidframework.ui.presenter.DataPresenter;
import com.slb.ttdandroidframework.ui.presenter.HomePresenter;


public class DataFragment
		extends BaseMvpFragment<DataContract.IView, DataContract.IPresenter>
		implements DataContract.IView{


	public static DataFragment newInstance(){
		DataFragment instance=new DataFragment();
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
}
