package danfei.shulaibao.widget.refresh;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.frame.ui.presenter.IBasePresenter;

import danfei.shulaibao.widget.R;
import danfei.shulaibao.widget.refresh.slidehide.HeadSlideHideContext;
import danfei.shulaibao.widget.refresh.slidehide.IHeadSlideHide;
import danfei.shulaibao.widget.refresh.slidehide.NoHeadSlideHide;

/**
 * 作者:刁剑
 * 时间:2016/9/19
 * 注释:下拉刷新框架的父类
 */
public abstract class BaseRefreshActivity<V,T extends IBasePresenter> extends BaseMvpActivity<V,T> {
    protected RecyclerView mRecyclerView;

    /**头部滑动隐藏的布局及相关参数*/
    protected ViewGroup hideHead;
    protected int disy=0;//recyclerview滑动距离
    protected boolean isShow=true;//是否显示滑动隐藏栏
    protected int actionBarHeight;//滑动隐藏栏的高度

    /**设置适配器*/
    protected abstract RecyclerView.Adapter setAdapter();
    /**单项点击事件*/
    protected void onItemClickListener(View view, RecyclerView.ViewHolder holder, int position){

    }
    /**单项点击事件*/
    protected void onItemClickListener(View view, RecyclerView.Adapter adapter, int position){

    }
    protected void onItemChildClickListener(View view, RecyclerView.Adapter adapter, int position){

    }


    @Override
    public void initView() {
        super.initView();
        hideHead= (ViewGroup)findViewById(R.id.hidehead);
        mRecyclerView= (RecyclerView)findViewById(R.id.can_content_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(initAdapter());

    }

    protected RecyclerView.Adapter initAdapter(){
        return setAdapter();
    }
    /**设置滑动隐藏布局,默认为空*/
    protected IHeadSlideHide setHeadSlideHideView(){
        return new NoHeadSlideHide();
    }
    /**加载头部滑动隐藏布局*/
    protected ViewGroup customSlideHideView(){
        HeadSlideHideContext headSlideHideContext=new HeadSlideHideContext();
        IHeadSlideHide iHeadSlideHide=setHeadSlideHideView();
        headSlideHideContext.setHeadSlideHide(iHeadSlideHide);
        View view=headSlideHideContext.add(this);
        ViewGroup viewGroup=null;
        if (view!=null){
            hideHead.addView(view);
            hideHead.setVisibility(View.VISIBLE);
            //用加载的view相同的高度来占位
            FrameLayout frameLayout=new FrameLayout(this);
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            int height = view.getMeasuredHeight();
            actionBarHeight=height;
            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,height);
            frameLayout.setLayoutParams(params);
            if (!(iHeadSlideHide instanceof NoHeadSlideHide)){
                scrollShowAndHideView();
            }
            viewGroup=frameLayout;
        }
        return viewGroup;
    }

    /**开启滑动隐藏*/
    @TargetApi(Build.VERSION_CODES.M)
    public void scrollShowAndHideView(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem == 0) {
                    if (!isShow) {
                        isShow = true;
                        showToolbar(hideHead, actionBarHeight);
                    }
                } else {
                    if (disy > 25 && isShow) {
                        isShow = false;
                        hideToolbar(hideHead, actionBarHeight);
                        disy = 0;
                    }
                    if (disy < -25 && !isShow) {
                        isShow = true;
                        showToolbar(hideHead, actionBarHeight);
                        disy = 0;
                    }
                }
                if ((isShow && dy > 0) || (!isShow && dy < 0)) {
                    disy += dy;
                }
            }
        });
    }
    /**隐藏滑动栏动画*/
    private void hideToolbar(View view,int actionBarHeight){
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,View.TRANSLATION_Y,0,-actionBarHeight);
        animator.setDuration(500);
        animator.start();
    }
    /**显示滑动栏动画*/
    private void showToolbar(View view,int actionBarHeight){
        ObjectAnimator animator=ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -actionBarHeight, 0);
        animator.setDuration(500);
        animator.start();
    }

    /**禁止滑动*/
    protected void unableScroll(){
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }
    /**允许滑动*/
    protected void ableScroll(){
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }
}
