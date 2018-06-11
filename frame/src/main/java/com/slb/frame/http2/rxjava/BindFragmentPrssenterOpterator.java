package com.slb.frame.http2.rxjava;

import com.slb.frame.ui.presenter.AbstractBaseFragmentPresenter;

import rx.Observable;
import rx.Subscriber;

/**
 * 刁剑
 * Created on 2017/2/8.
 * 注释:
 */

public class BindFragmentPrssenterOpterator<T> implements Observable.Operator<T,T> {
    private AbstractBaseFragmentPresenter presenter;
    private ActivityLifecycle activityLifecycle;
    public BindFragmentPrssenterOpterator(AbstractBaseFragmentPresenter presenter){
        this(presenter,ActivityLifecycle.OnDestroy);
    }
    public BindFragmentPrssenterOpterator(AbstractBaseFragmentPresenter presenter,ActivityLifecycle activityLifecycle){
        this.presenter=presenter;
        this.activityLifecycle=activityLifecycle;
    }
    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        presenter.addSubscriber(subscriber,activityLifecycle);
        return subscriber;
    }
}
