package com.slb.ttdandroidframework.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.ui.adapter.FreezeFrameAdapter;
import com.slb.ttdandroidframework.ui.adapter.ModuleFiveAdapter;
import com.slb.ttdandroidframework.ui.contract.MyComListContract;
import com.slb.ttdandroidframework.ui.presenter.MyComListPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import danfei.shulaibao.widget.refresh.BaseBrvahRefreshActivity;

/**
 * 作者：Juan on 2017/7/30 18:33
 * 邮箱：154336119@qq.com
 * 描述：Module 5
 */
public class ModuleFiveActivity extends BaseBrvahRefreshActivity<MyComListContract.IView,MyComListContract.IPresenter,Object,MoudleFiveEntity> implements MyComListContract.IView{
    @Override
    public MyComListContract.IPresenter initPresenter() {
        return new MyComListPresenter();
    }

    @Override
    protected String setToolbarTitle() {
        return "MODE 5";
    }
    @Override
    protected RecyclerView.Adapter setAdapter() {
        mAdapter = new ModuleFiveAdapter(mList);
        return mAdapter;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_module_five;
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
            MoudleFiveEntity freezeFrameEntity = new MoudleFiveEntity();
            mList.add(freezeFrameEntity);
        }
        mAdapter.addData(mList);
    }

//    @Override
//    protected Observable<HttpResult<Object, CookBillEntity>> requestHttp() {
//        return RetrofitSerciveFactory.provideComService().getCookBillList(1);
//    }
}
