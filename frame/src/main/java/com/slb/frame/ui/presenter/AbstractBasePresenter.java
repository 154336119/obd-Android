package com.slb.frame.ui.presenter;


import com.slb.frame.http2.rxjava.ActivityLifecycle;
import com.slb.frame.http2.rxjava.SubscriberWrapper;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：
 * Created by Lee
 * on 2016/9/14.
 */
public class AbstractBasePresenter<V> {
    private List<SubscriberWrapper> subscribers = new LinkedList<>();
    public rx.Observable<ActivityEvent> mObservable;
    public V mView;

    public void attach(V view){
        mView = view;
    }

    public void deattach(){
        mView = null;
        subscribersClear();
    }

    public void onResume(){}

    public void addSubscriber(Subscriber subscriber, ActivityLifecycle unsubscribeOn) {
        subscribers.add(new SubscriberWrapper(subscriber, unsubscribeOn));
    }

    private void subscribersClear(){
        Iterator<SubscriberWrapper> it = subscribers.iterator();
        while (it.hasNext()) {
            SubscriberWrapper subscriberWrapper = it.next();
            if (subscriberWrapper.unsubscribeOn == ActivityLifecycle.OnDestroy) {
                subscriberWrapper.subscriber.unsubscribe();
                it.remove();
            }
        }
    }
}
