package com.slb.ttdandroidframework.ui.contract;

import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * Created by Gifford on 2017/11/7.
 */

public class MailboxVerificationContract {
	public interface IView extends IBaseLoadingDialogView {
		/*** 显示倒计时*/
		void showCountdown();
	}
	public interface IPresenter<T> extends IBasePresenter<T> {
		/*** 获取验证码*/
		void getCode(String mobile);
	}
}
