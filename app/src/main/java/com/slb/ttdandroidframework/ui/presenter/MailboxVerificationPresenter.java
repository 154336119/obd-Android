package com.slb.ttdandroidframework.ui.presenter;

import android.text.TextUtils;

import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.frame.http2.rxjava.BaseSubscriber;
import com.slb.frame.http2.rxjava.BindPrssenterOpterator;
import com.slb.frame.http2.rxjava.HttpEntityFun;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.frame.utils.RegexUtils;
import com.slb.frame.utils.rx.RxUtil;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.http.RetrofitSerciveFactory;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.ui.contract.LoginContract;
import com.slb.ttdandroidframework.ui.contract.MailboxVerificationContract;

import static com.slb.ttdandroidframework.http.RetrofitSerciveFactory.provideComService;


/**
 * Created by Gifford on 2017/11/7.
 */

public class MailboxVerificationPresenter extends AbstractBasePresenter<MailboxVerificationContract.IView>
		implements MailboxVerificationContract.IPresenter<MailboxVerificationContract.IView>{

	@Override
	public void getCode(String email) {
		if (TextUtils.isEmpty(email)) {
			mView.showMsg("请输入手机号码");
			return;
		}
		if (!RegexUtils.isEmail(email)) {
			mView.showMsg("请输入正确的手机号");
			return;
		}
		RetrofitSerciveFactory.provideComService().sendMsgCode(email)
				.lift(new BindPrssenterOpterator<HttpResult<Object, Object>>(this))
				.compose(RxUtil.<HttpResult<Object, Object>>applySchedulersForRetrofit())
				.map(new HttpEntityFun<Object, Object>())
				.subscribe(new BaseSubscriber<Object>(this.mView) {
					@Override
					public void onNext(Object object) {
						super.onNext(object);
						mView.showCountdown();
						mView.showMsg("手机验证码已发送请查看手机短信");
					}
				});
	}
}
