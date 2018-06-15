package com.slb.ttdandroidframework.ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;

import com.slb.frame.http2.exception.ResultException;
import com.slb.frame.http2.retrofit.HttpDataResutl;
import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.frame.http2.rxjava.BaseSubscriber;
import com.slb.frame.http2.rxjava.BindPrssenterOpterator;
import com.slb.frame.http2.rxjava.HttpEntityFun;
import com.slb.frame.http2.rxjava.HttpResultFun;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.frame.utils.RegexUtils;
import com.slb.frame.utils.rx.RxUtil;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.ui.contract.LoginContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.slb.ttdandroidframework.http.RetrofitSerciveFactory.provideComService;


/**
 * Created by Gifford on 2017/11/7.
 */

public class LoginPresenter extends AbstractBasePresenter<LoginContract.IView>
		implements LoginContract.IPresenter<LoginContract.IView>{
	@Override
	public void login(String mUserName, String mPassword) {
		provideComService().login(mUserName, mPassword)
				.lift(new BindPrssenterOpterator<HttpResult<UserEntity, Object>>(this))
				.compose(RxUtil.<HttpResult<UserEntity,Object>>applySchedulersForRetrofit())
				.map(new HttpEntityFun<UserEntity, Object>())
				.subscribe(new BaseSubscriber<UserEntity>(this.mView) {
					@Override
					public void onNext(UserEntity entity) {
						super.onNext(entity);
						if(entity!=null){
							Base.setUserEntity(entity);
							mView.loginSuccess();
						}
					}
				});
	}
}
