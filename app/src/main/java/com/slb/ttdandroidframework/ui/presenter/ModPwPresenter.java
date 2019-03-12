package com.slb.ttdandroidframework.ui.presenter;


import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
import com.slb.ttdandroidframework.ui.contract.ModPasswordContract;
import com.slb.ttdandroidframework.ui.contract.MyComListContract;

/**
 * 李彬杰
 * 注释: 修改密码
 */

public class ModPwPresenter extends AbstractBasePresenter<ModPasswordContract.IView>
        implements ModPasswordContract.IPresenter<ModPasswordContract.IView> {
    @Override
    public void reset(String oldPw, String newPw, String againNewPw) {
        if(TextUtils.isEmpty(oldPw)){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_your_current_password));
            return;
        }
        if(TextUtils.isEmpty(newPw)){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_your_new_password));
            return;
        }
        if(TextUtils.isEmpty(againNewPw)){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_your_new_password));
            return;
        }
        if(!newPw.equals(againNewPw)){
            mView.showMsg(Base.getContext().getString(R.string.The_two_password_is_inconsistent));
            return;
        }
        String id = Base.getUserEntity().getId();
        OkGo.<LzyResponse<Object>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ "api/user/edit-password")//
                .tag(this)//
                .params("email", Base.getUserEntity().getEmail())//
                .params("oldPassword", oldPw)//
                .params("newPassword", againNewPw)//
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<LzyResponse<Object>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<Object>> response) {
                        mView.resetSuccess();
                    }
                });
    }
}
