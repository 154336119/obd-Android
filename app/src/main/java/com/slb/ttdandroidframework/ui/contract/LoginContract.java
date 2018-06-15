package com.slb.ttdandroidframework.ui.contract;

import android.content.Context;
import android.widget.AutoCompleteTextView;

import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

import java.util.List;

import rx.Subscription;

/**
 * Created by Gifford on 2017/11/7.
 */

public class LoginContract {
	public interface IView extends IBaseLoadingDialogView {
		void loginSuccess();
	}
	public interface IPresenter<T> extends IBasePresenter<T> {

		void login(String mUserName, String mPassword);
	}
}
