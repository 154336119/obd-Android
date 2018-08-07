package com.slb.ttdandroidframework.ui.presenter;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.frame.http2.rxjava.BaseSubscriber;
import com.slb.frame.http2.rxjava.BindPrssenterOpterator;
import com.slb.frame.http2.rxjava.HttpEntityFun;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.frame.utils.rx.RxUtil;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
import com.slb.ttdandroidframework.ui.contract.LoginContract;
import com.slb.ttdandroidframework.ui.contract.RegisterContract;

import static com.slb.ttdandroidframework.http.RetrofitSerciveFactory.provideComService;


/**
 * Created by Gifford on 2017/11/7.
 */

public class RegisterPresenter extends AbstractBasePresenter<RegisterContract.IView>
		implements RegisterContract.IPresenter<RegisterContract.IView>{
	@Override
	public void getVcode(String email) {
		if(TextUtils.isEmpty(email)){
			mView.showMsg("请输入邮箱");
			return;
		}
		OkGo.<String>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.verifycode)//
				.tag(this)//
				.params("email", email)//
				.isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						//注意这里已经是在主线程了
						mView.loadingDialogDismiss();
						mView.getVcodeSuccess();
					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);
					}

					@Override
					public void onStart(Request<String, ? extends Request> request) {
						super.onStart(request);
						mView.showLoadingDialog("加载中");
					}
				});
	}

	@Override
	public void register(String mUserName, String mPassword,String code) {
		if(TextUtils.isEmpty(mUserName)){
			mView.showMsg("请输入邮箱");
			return;
		}if(TextUtils.isEmpty(mPassword)){
			mView.showMsg("请输入密码");
			return;
		}
		if(TextUtils.isEmpty(code)){
			mView.showMsg("请输入验证码");
			return;
		}

		OkGo.<String>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.regist)//
				.tag(this)//
				.params("email", mUserName)//
				.params("password ", mPassword)//
				.params("verifyCode ", code)//
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						//注意这里已经是在主线程了
						mView.loadingDialogDismiss();
						mView.registerSuccess();
					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);
					}

					@Override
					public void onStart(Request<String, ? extends Request> request) {
						super.onStart(request);
						mView.showLoadingDialog("加载中");
					}
				});

	}
}
