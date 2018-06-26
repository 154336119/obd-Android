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
import com.slb.ttdandroidframework.ui.contract.ReadErrorCodeContract;

import static com.slb.ttdandroidframework.http.RetrofitSerciveFactory.provideComService;


/**
 * Created by Gifford on 2017/11/7.
 */

public class ReadErrorCodePresenter extends AbstractBasePresenter<ReadErrorCodeContract.IView>
		implements ReadErrorCodeContract.IPresenter<ReadErrorCodeContract.IView>{

}
