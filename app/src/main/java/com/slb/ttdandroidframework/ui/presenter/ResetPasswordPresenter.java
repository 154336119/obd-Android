package com.slb.ttdandroidframework.ui.presenter;


import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
import com.slb.ttdandroidframework.ui.contract.MyComListContract;
import com.slb.ttdandroidframework.ui.contract.ResetPasswordContract;

/**
 * 李彬杰
 * 注释: 重置密码
 */

public class ResetPasswordPresenter extends AbstractBasePresenter<ResetPasswordContract.IView>
        implements ResetPasswordContract.IPresenter<ResetPasswordContract.IView> {
    @Override
    public void reset(String email, String password, String verifyCode) {
        if(TextUtils.isEmpty(email)){
            mView.showMsg("请输入邮箱");
            return;
        }if(TextUtils.isEmpty(password)){
            mView.showMsg("请输入密码");
            return;
        }
        if(TextUtils.isEmpty(verifyCode)){
            mView.showMsg("请输入验证码");
            return;
        }

        OkGo.<LzyResponse<Object>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.registReset)//
                .tag(this)//
                .params("email", email)//
                .params("password ", password)//
                .params("verifyCode ", verifyCode)//
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<LzyResponse<Object>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<Object>> response) {
                        mView.resetSuccess();
                    }
                });

    }
}
