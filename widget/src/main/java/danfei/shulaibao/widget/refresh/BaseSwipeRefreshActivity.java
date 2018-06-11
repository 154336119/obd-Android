package danfei.shulaibao.widget.refresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.slb.frame.ui.activity.BaseActivity;

import danfei.shulaibao.widget.R;

/**
 * 刁剑
 * Created on 2017/1/24.
 * 注释:
 */

public abstract class BaseSwipeRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected boolean isRefresh(){
        return true;
    }
    @Override
    protected boolean addSwipeRefreshView() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(isRefresh());
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }
}
