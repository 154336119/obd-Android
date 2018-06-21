package com.slb.ttdandroidframework.ui.contract;

import com.slb.frame.ui.presenter.IBaseFragmentPresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * Created by Gifford on 2018/1/5.
 */

public class MineContract {
	public interface IView extends IBaseLoadingDialogView {
	}
	public interface IPresenter<T> extends IBaseFragmentPresenter<T> {
	}
}
