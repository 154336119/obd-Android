package danfei.shulaibao.widget.refresh.slidehide;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import danfei.shulaibao.widget.refresh.BaseRefreshFragment;

/**
 * 作者:刁剑
 * 时间:2016/9/20
 * 注释:滑动隐藏正文类
 */
public class HeadSlideHideContext {
    private IHeadSlideHide iHeadSlideHide;
    public void setHeadSlideHide(IHeadSlideHide iHeadSlideHide){
        this.iHeadSlideHide=iHeadSlideHide;
    }
    public View add(Context context){
        return iHeadSlideHide.addSlideHideView(context);
    }
}
