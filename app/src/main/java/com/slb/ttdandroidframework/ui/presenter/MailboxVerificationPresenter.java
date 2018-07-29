package com.slb.ttdandroidframework.ui.presenter;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.orhanobut.logger.Logger;
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
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
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
		if(TextUtils.isEmpty(email)){
			mView.showMsg("请输入邮箱");
			return;
		}
		OkGo.<LzyResponse<Object>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.verifycodeReset)//
				.tag(this)//
				.params("email", email)//
				.isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
				.execute(new DialogCallback<LzyResponse<Object>>(this.mView) {
					@Override
					public void onSuccess(Response<LzyResponse<Object>> response) {
						mView.showCountdown();
					}
				});

	}
	}
