package com.slb.frame.http2.rxjava;

import com.slb.frame.ui.presenter.AbstractBasePresenter;

import rx.Observable;
import rx.Subscriber;

/**
 * 描述：
 * Created by Lee
 * on 2017/1/19.
 */
public class BindPrssenterOpterator<T> implements Observable.Operator<T, T> {
    private AbstractBasePresenter presenter;
    private ActivityLifecycle activityLifecycle;
    public BindPrssenterOpterator(AbstractBasePresenter presenter) {
        this(presenter, ActivityLifecycle.OnDestroy);
    }
    public BindPrssenterOpterator(AbstractBasePresenter presenter, ActivityLifecycle activityLifecycle) {
        this.presenter = presenter;
        this.activityLifecycle = activityLifecycle;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        presenter.addSubscriber(subscriber,activityLifecycle);
        return subscriber;
    }
}
