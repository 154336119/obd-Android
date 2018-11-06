package danfei.shulaibao.widget.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.slb.frame.http2.retrofit.HttpDataResutl;
import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.frame.http2.rxjava.HttpResultFun;
import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.utils.rx.RxUtil;
import java.util.ArrayList;
import java.util.List;
import danfei.shulaibao.widget.R;
import rx.Observable;
import rx.Subscriber;
/**
 * 作者:刁剑
 * 时间:2016/9/22
 * 注释:用Github和是哪个Brvah框架写的下拉刷新上拉加载框架
 */
public abstract class BaseBrvahRefreshActivity<V,T extends IBasePresenter,K,A>  extends BaseRefreshActivity<V,T> implements
        SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    public static final int FIRST_PAGE=1;
    public final static int PAGE_SIZE=10;
    protected SwipeRefreshLayout mRefresh;
    protected BaseQuickAdapter mAdapter;
    private int mTotalPage;
    public int mCurrentPage;
    private int mTotalCounter;
    /**网络请求类*/
    protected Observable<HttpResult<K,A>> mObservable;
    /**加载更多的网络请求*/
    protected Observable<HttpResult<K,A>> mObservableLoadMore;

    protected List<A> mList=new ArrayList();
    private int mCurrentCounter = 0;
    private boolean isErr;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_brvah_refresh;
    }
    protected int setEmptyLatyouId(){
        return R.layout.view_refresh_empty;
    }

    private View setEmptyView(){
        LayoutInflater inflater=LayoutInflater.from(this);
        ViewGroup empty= (ViewGroup) inflater.inflate(setEmptyLatyouId(),null);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        empty.setLayoutParams(params);
        return empty;
    }
    @Override
    protected RecyclerView.Adapter initAdapter() {
        mAdapter= (BaseQuickAdapter) setAdapter();
        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);
        mCurrentCounter=mAdapter.getData().size();
        mAdapter.setEmptyView(setEmptyView());
        ViewGroup viewGroup=customSlideHideView();
        if (viewGroup!=null){
            mAdapter.addHeaderView(customSlideHideView());
            mRefresh.setProgressViewOffset(true, 10, actionBarHeight);
        }
        return mAdapter;
    }

    @Override
    public void initView() {
        mRefresh= (SwipeRefreshLayout)findViewById(R.id.refresh);
        super.initView();
        //设置下拉刷新控件偏移量
        mRefresh.setOnRefreshListener(this);
        //网络请求
        mObservable=requestHttp();
        if (mObservable!=null){
            getListData();
        }


        mRecyclerView.addOnItemTouchListener(onItemClickListener);
        mRecyclerView.addOnItemTouchListener(onItemChildClickListener);
    }
    /**item点击事件绑定*/
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
            onItemClickListener(view,baseQuickAdapter,i);
        }
    };

    /**item子控件点击事件*/
    private OnItemChildClickListener onItemChildClickListener=new OnItemChildClickListener() {
        @Override
        public void onSimpleItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//            onItemChildClickListener(view,baseQuickAdapter,i);
            onItemChildClickListener(view,baseQuickAdapter,i);
        }
    };
    /**
     * 自动刷新
     */
    protected void autoRefresh(){
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(true);
            }
        });
    }

    /**
     * 自动刷新完成
     */
    protected void autoRefreshComplete(){
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(false);
            }
        });
    }
    @Override
    public void onRefresh() {
        mCurrentPage=FIRST_PAGE;
        mAdapter.setEnableLoadMore(false);
        getListData();
    }


    @Override
    public void onLoadMoreRequested() {
        mRefresh.setEnabled(false);
        mCurrentPage++;
        mObservableLoadMore=requestHttp();
        loadMoreListData();
    }

    /**
     * 网络请求方法
     * @return
     */
    protected Observable<HttpResult<K,A>> requestHttp(){
        return null;
    }
    protected Observable<HttpResult<K,A>> requestHttpLoadMore(){
        return null;
    }
    /**
     * 获取数据
     */
    protected void getListData(){
        autoRefresh();
        if(mObservable == null){
            mAdapter.loadMoreComplete();
            autoRefreshComplete();
            return;
        }
        mObservable.compose(RxUtil.<HttpResult<K,A>>applySchedulersForRetrofit())
                .map(new HttpResultFun<K, A>())
                .subscribe(new Subscriber<HttpDataResutl<K, A>>() {
                    @Override
                    public void onCompleted() {
                        onHttpCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onHttpError(e);
                    }

                    @Override
                    public void onNext(HttpDataResutl<K, A> kvHttpDataResutl) {
                        onHttpNext(kvHttpDataResutl);
                    }
                });
    }

    protected void loadMoreListData(){
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter>=mTotalCounter){
                    mAdapter.loadMoreEnd(false);
                }else {
                    if (isErr){
                        mObservableLoadMore.compose(RxUtil.<HttpResult<K,A>>applySchedulersForRetrofit())
                                .map(new HttpResultFun<K, A>())
                                .subscribe(new Subscriber<HttpDataResutl<K, A>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        onHttpError(e);
                                        mAdapter.loadMoreFail();
                                        if (mCurrentCounter>FIRST_PAGE){
                                            mCurrentCounter--;
                                        }
                                    }

                                    @Override
                                    public void onNext(HttpDataResutl<K, A> kvHttpDataResutl) {
                                        mAdapter.addData(kvHttpDataResutl.getList());
                                        mCurrentCounter=mAdapter.getData().size();
                                        mAdapter.loadMoreComplete();
                                    }
                                });
                    }else {
                        isErr=true;
                        mAdapter.loadMoreFail();
                    }
                }
                mRefresh.setEnabled(true);
            }
        });
        mRefresh.setEnabled(true);
    }
    /**请求网络完成*/
    protected void onHttpCompleted(){
        autoRefreshComplete();
    }
    /**请求网络错误*/
    protected void onHttpError(Throwable e){
//        Logger.d("onError()");
        autoRefreshComplete();
    }
    /**请求网络成功*/
    protected void onHttpNext(HttpDataResutl<K, A> kvHttpDataResutl){
        mList.clear();
        mTotalPage = kvHttpDataResutl.getTotalPage();
        mTotalCounter=kvHttpDataResutl.getTotal();
        mCurrentPage = kvHttpDataResutl.getCurrentPage();
        if(kvHttpDataResutl.getList()!=null){
            mList.addAll(kvHttpDataResutl.getList());
            mAdapter.setNewData(mList);
            mCurrentCounter=mList.size();
            mAdapter.setEnableLoadMore(true);
            isErr=true;
        }
//        mList.addAll(kvHttpDataResutl.getList());
//        mAdapter.setNewData(mList);
//        mCurrentCounter=mList.size();
//        mRefresh.setRefreshing(false);
//        mAdapter.setEnableLoadMore(true);
//        isErr=true;
        List<A> list=kvHttpDataResutl.getList();
        if (list==null){
            list=new ArrayList<>();
        }
//        mList.addAll(list);
        mAdapter.setNewData(mList);
        mCurrentCounter=mList.size();
//        mRefresh.setRefreshing(false);
        mAdapter.setEnableLoadMore(true);
        isErr=true;
    }
}
