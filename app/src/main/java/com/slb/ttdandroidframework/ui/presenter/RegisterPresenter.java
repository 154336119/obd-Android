package com.slb.ttdandroidframework.ui.presenter;

import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.frame.http2.rxjava.BaseSubscriber;
import com.slb.frame.http2.rxjava.BindPrssenterOpterator;
import com.slb.frame.http2.rxjava.HttpEntityFun;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.frame.utils.rx.RxUtil;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.ui.contract.LoginContract;
import com.slb.ttdandroidframework.ui.contract.RegisterContract;

import static com.slb.ttdandroidframework.http.RetrofitSerciveFactory.provideComService;


/**
 * Created by Gifford on 2017/11/7.
 */

public class RegisterPresenter extends AbstractBasePresenter<RegisterContract.IView>
		implements RegisterContract.IPresenter<RegisterContract.IView>{
	@Override
	public void register(String mUserName, String mPassword) {
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
							mView.registerSuccess();
						}
					}
				});
	}
}
