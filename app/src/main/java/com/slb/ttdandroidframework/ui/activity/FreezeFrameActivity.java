package com.slb.ttdandroidframework.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.ui.adapter.FreezeFrameAdapter;
import com.slb.ttdandroidframework.ui.contract.MyComListContract;
import com.slb.ttdandroidframework.ui.presenter.MyComListPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import danfei.shulaibao.widget.refresh.BaseBrvahRefreshActivity;
import rx.Observable;

/**
 * 作者：Juan on 2017/7/30 18:33
 * 邮箱：154336119@qq.com
 * 描述：冻结帧
 */
public class FreezeFrameActivity extends BaseBrvahRefreshActivity<MyComListContract.IView,MyComListContract.IPresenter,Object,FreezeFrameEntity> implements MyComListContract.IView{
    @Override
    public MyComListContract.IPresenter initPresenter() {
        return new MyComListPresenter();
    }

    @Override
    protected String setToolbarTitle() {
        return "冻结帧";
    }
    @Override
    protected RecyclerView.Adapter setAdapter() {
        mAdapter = new FreezeFrameAdapter(mList);
        return mAdapter;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_freeze_frame;
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.line_height_1)
                        .build());
        //测试
        for (int i = 0; i < 10; i++) {
            FreezeFrameEntity freezeFrameEntity = new FreezeFrameEntity();
            mList.add(freezeFrameEntity);
        }
        mAdapter.addData(mList);
    }

//    @Override
//    protected Observable<HttpResult<Object, CookBillEntity>> requestHttp() {
//        return RetrofitSerciveFactory.provideComService().getCookBillList(1);
//    }
}
