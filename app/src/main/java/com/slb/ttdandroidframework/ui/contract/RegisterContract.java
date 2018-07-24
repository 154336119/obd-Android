package com.slb.ttdandroidframework.ui.contract;

import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * Created by Gifford on 2017/11/7.
 */

public class RegisterContract {
	public interface IView extends IBaseLoadingDialogView {
		void getVcodeSuccess();
		void registerSuccess();
	}
	public interface IPresenter<T> extends IBasePresenter<T> {
		void getVcode(String email);
		void register(String mUserName, String mPassword,String code);
	}
}
