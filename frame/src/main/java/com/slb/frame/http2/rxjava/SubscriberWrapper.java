package com.slb.frame.http2.rxjava;

import rx.Subscriber;

/**
 * 描述：rxjava 生命周期
 * Created by Lee
 * on 2017/1/19.
 */
public class SubscriberWrapper {
    public Subscriber subscriber;
    public ActivityLifecycle unsubscribeOn;

    public SubscriberWrapper(Subscriber subscriber, ActivityLifecycle unsubscribeOn) {
        this.subscriber = subscriber;
        this.unsubscribeOn = unsubscribeOn;
    }
}
