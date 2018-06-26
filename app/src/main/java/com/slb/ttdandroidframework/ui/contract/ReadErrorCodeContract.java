package com.slb.ttdandroidframework.ui.contract;

import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * Created by Gifford on 2017/11/7.
 */

public class ReadErrorCodeContract {
	public interface IView extends IBaseLoadingDialogView {
	}
	public interface IPresenter<T> extends IBasePresenter<T> {

	}
}
