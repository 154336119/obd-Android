package danfei.shulaibao.widget.refresh.slidehide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import danfei.shulaibao.widget.R;

/**
 * 作者:刁剑
 * 时间:2016/9/20
 * 注释:测试用的滑动隐藏栏
 */
public class ScrollSlideHide implements IHeadSlideHide {
    @Override
    public View addSlideHideView(Context context) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.text_view_scroll, null,false);
        return view;
    }
}
