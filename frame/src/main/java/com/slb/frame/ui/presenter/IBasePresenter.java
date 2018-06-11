package com.slb.frame.ui.presenter;


/**
 * 描述：
 * Created by Lee
 * on 2016/9/14.
 */
public interface IBasePresenter<T> {
    void attach(T view);
    void deattach();
    void onResume();
//    void attachFragmentLife(Observable<ActivityEvent> observable);
}
