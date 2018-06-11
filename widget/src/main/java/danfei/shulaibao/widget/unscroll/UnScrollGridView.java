package danfei.shulaibao.widget.unscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**刁剑
 * 取消滚动的GridView
 * Created by Administrator on 2016/4/21.
 */
public class UnScrollGridView extends GridView {
    public UnScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
