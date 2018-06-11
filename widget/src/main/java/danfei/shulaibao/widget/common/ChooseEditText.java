package danfei.shulaibao.widget.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import danfei.shulaibao.widget.R;


/**
 * 可选择可见与否的密码框
 * Created by Administrator on 2017/6/13.
 */

public class ChooseEditText extends AppCompatEditText {
    private static final int ICON_OPEN_DEFAULT = R.mipmap.eye_open;
    private static final int ICON_CLOSE_DEFAULT = R.mipmap.eye_close;
    private boolean isOpen = false;

    private Drawable drawableOpen;
    private Drawable drawableClose;

    public ChooseEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ChooseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChooseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 获取自定义属性
        drawableOpen = getResources().getDrawable(ICON_OPEN_DEFAULT);
        drawableClose = getResources().getDrawable(ICON_CLOSE_DEFAULT);
        updateIcon();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 点击是的 x 坐标
            int xDown = (int) event.getX();
            // 清除按钮的起始区间大致为[getWidth() - getCompoundPaddingRight(), getWidth()]，
            // 点击的x坐标在此区间内则可判断为点击了清除按钮
            if (xDown >= (getWidth() - getCompoundPaddingRight()) && xDown < getWidth()) {
                updateIcon();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 更新清除按钮图标显示
     */
    public void updateIcon() {
        Drawable[] drawables = getCompoundDrawables();
        if (isOpen) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawableOpen,
                    drawables[3]);
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawableClose,
                    drawables[3]);
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        isOpen = !isOpen;
    }
}
