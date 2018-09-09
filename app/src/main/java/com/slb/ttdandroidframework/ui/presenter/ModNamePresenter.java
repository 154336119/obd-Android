package com.slb.ttdandroidframework.ui.presenter;


import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.frame.utils.rx.RxBus;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.event.ModNickNameEvent;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.ui.contract.ModNameContract;
import com.slb.ttdandroidframework.ui.contract.ModPasswordContract;

/**
 * 李彬杰
 * 注释: 修改昵称
 */

public class ModNamePresenter extends AbstractBasePresenter<ModNameContract.IView>
        implements ModNameContract.IPresenter<ModNameContract.IView> {
    @Override
    public void modName(final String name) {
        if(TextUtils.isEmpty(name)){
            mView.showMsg("请输入新昵称");
            return;
        }
        OkGo.<LzyResponse<Object>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ "api/user/edit-nickname")//
                .tag(this)//
                .params("userId", Base.getUserEntity().getId())//
                .params("nickname", name)//
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<LzyResponse<Object>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<Object>> response) {
                        RxBus.getInstance().post(new ModNickNameEvent(name));
                        Base.getUserEntity().setNickname(name);
                        mView.success();
                    }
                });
    }
}
