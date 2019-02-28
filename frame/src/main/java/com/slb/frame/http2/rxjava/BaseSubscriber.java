package com.slb.frame.http2.rxjava;


import android.text.TextUtils;

import com.slb.frame.ui.view.IBaseLoadingDialogView;

import rx.Subscriber;

/**
 * 描述：
 * Created by Lee
 * on 2017/1/19.
 */
public class BaseSubscriber<T> extends Subscriber<T> {
    /**MVP view接口*/
    private IBaseLoadingDialogView mView;
    public BaseSubscriber(IBaseLoadingDialogView view) {
        mView = view;
    }
    @Override
    public void onStart() {
        super.onStart();
        mView.showLoadingDialog("loading...");
    }

    @Override
    public void onError(Throwable e) {
        mView.loadingDialogDismiss();
        if(!TextUtils.isEmpty(e.getMessage())){
            mView.showMsg(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        mView.loadingDialogDismiss();
    }

    @Override
    public void onCompleted() {

    }

}
