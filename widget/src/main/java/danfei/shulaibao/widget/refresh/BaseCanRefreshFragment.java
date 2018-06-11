package danfei.shulaibao.widget.refresh;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import danfei.shulaibao.widget.R;
/**
 * 作者:刁剑
 * 时间:2016/9/20
 * 注释:用github上的CanRefresh
 */
public abstract class BaseCanRefreshFragment extends BaseRefreshFragment implements
        CanRefreshLayout.OnRefreshListener,CanRefreshLayout.OnLoadMoreListener,MultiItemTypeAdapter.OnItemClickListener{
    protected CanRefreshLayout mRefresh;

    /**万能适配器*/
    protected MultiItemTypeAdapter mAdapter;
    /**adapter的addHead和addFooter*/
    protected HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    /**数据为空时的布局*/
    protected EmptyWrapper mEmptyWrapper;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_refresh;
    }
    @Override
    protected RecyclerView.Adapter initAdapter() {
        mAdapter= (MultiItemTypeAdapter) setAdapter();
        mAdapter.setOnItemClickListener(this);
        mHeaderAndFooterWrapper=new HeaderAndFooterWrapper(mAdapter);
        mEmptyWrapper=new EmptyWrapper(mHeaderAndFooterWrapper);
        mEmptyWrapper.setEmptyView(R.layout.text);
        ViewGroup viewGroup=customSlideHideView();
        if (viewGroup!=null){
            mHeaderAndFooterWrapper.addHeaderView(customSlideHideView());
        }
        return mEmptyWrapper;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mRefresh= (CanRefreshLayout) view.findViewById(R.id.refresh);
        mRefresh.setStyle(1,0);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setOnLoadMoreListener(this);
        mRefresh.autoRefresh();
    }

    @Override
    public void onRefresh() {
        unableScroll();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresh.refreshComplete();
                ableScroll();
            }
        }, 2000);
    }


    @Override
    public void onLoadMore() {
        unableScroll();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresh.loadMoreComplete();
                ableScroll();
            }
        }, 2000);
    }

//    @Override
//    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//        onItemClickListener(view,holder,position);
//    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
