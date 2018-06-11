package danfei.shulaibao.widget.common.autoedt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.slb.frame.cache.ACache;

import java.util.ArrayList;
import java.util.List;

import danfei.shulaibao.widget.R;


/**
 * Created by Administrator on 2017/6/13.
 */

public class ClearAutoCompleteEditText extends AutoCompleteTextView implements IAutoPhoneDelete,AdapterView.OnItemClickListener{
    /**
     * 默认的清除按钮图标资源
     */
    private static final int ICON_CLEAR_DEFAULT = R.mipmap.close;
    private static final String KEY_PHONE_NUMBERS = "key_phone_numbers";

    /**
     * 清楚按钮的图标
     */
    private Drawable drawableClear;
    private int beforePhone = 0;
    private int afterPhone = 0;
    private Context mContext;
    private AutoCompleteAdapter mAdapter;
    private List<PhoneContactEntity> mList=new ArrayList<>();
    public ClearAutoCompleteEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ClearAutoCompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearAutoCompleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext=context;
        adapterReset();
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        // 获取清除按钮图标资源
        int iconClear =
                typedArray.getResourceId(R.styleable.ClearEditText_iconClear, ICON_CLEAR_DEFAULT);
        drawableClear = getResources().getDrawable(iconClear);
        updateIconClear();
        typedArray.recycle();

        // 设置TextWatcher用于更新清除按钮显示状态
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                beforePhone = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateIconClear();
                afterPhone = editable.length();
                if (beforePhone < afterPhone) {//增加
                    if (editable.length() == 3 || editable.length() == 8) {
                        setText(getText().toString() + " ");
                        setSelection(length());
                    }
                    if (editable.length() == 4 && !getText().toString().substring(length() - 1, length()).equals(" ")) {
                        setText(getText().toString().substring(0, length() - 1) + " " + getText().toString().substring(length() - 1, length()));
                        setSelection(length());
                    }
                    if (editable.length() == 9 && !getText().toString().substring(length() - 1, length()).equals(" ")) {
                        setText(getText().toString().substring(0, length() - 1) + " " + getText().toString().substring(length() - 1, length()));
                        setSelection(length());
                    }
                } else {//减少
                    if (editable.length() == 9 || editable.length() == 4) {
                        setText(getText().toString().substring(0, getText().toString().length() - 1));
                        setSelection(length());
                    }
                }
            }
        });
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        updateIconClear();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 点击是的 x 坐标
            int xDown = (int) event.getX();
            // 清除按钮的起始区间大致为[getWidth() - getCompoundPaddingRight(), getWidth()]，
            // 点击的x坐标在此区间内则可判断为点击了清除按钮
            if (xDown >= (getWidth() - getCompoundPaddingRight()) && xDown < getWidth()) {
                // 清空文本
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 更新清除按钮图标显示
     */
    public void updateIconClear() {
        // 获取设置好的drawableLeft、drawableTop、drawableRight、drawableBottom
        Drawable[] drawables = getCompoundDrawables();
        if (length() > 0 && isFocused()) //输入框有输入内容才显示删除按钮
        {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawableClear,
                    drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null,
                    drawables[3]);
        }
    }

    /**
     * 设置清除按钮图标样式
     *
     * @param resId 图标资源id
     */
    public void setIconClear(@DrawableRes int resId) {
        drawableClear = getResources().getDrawable(resId);
        updateIconClear();
    }

    /**
     * 增加电话号码
     * @param phone
     */
    public void setAutoComplete(String phone){
        ACache aCache=ACache.get(mContext);
        String phones=aCache.getAsString(KEY_PHONE_NUMBERS);
        if (TextUtils.isEmpty(phones)) {
            phones = phone + ",";
            aCache.put(KEY_PHONE_NUMBERS, phones);
        } else {
            String[] arr = phones.split(",");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals(phone)) {
                    list.add(arr[i]);
                }
            }
            list.add(0, phone);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i) + ",");
            }
            aCache.put(KEY_PHONE_NUMBERS, sb.toString());
        }
        getAutoComplete();
    }
    public void getAutoComplete() {
        ACache aCache = ACache.get(mContext);
        String phones=aCache.getAsString(KEY_PHONE_NUMBERS);
        if (TextUtils.isEmpty(phones)){
            return;
        }
        String[] arr=phones.split(",");
        final List<PhoneContactEntity> list=new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            PhoneContactEntity pc=new PhoneContactEntity();
            pc.setName(arr[i]);
            list.add(pc);
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 重置适配器
     */
    private void adapterReset(){
        mAdapter=new AutoCompleteAdapter(mContext,mList,this);
        setAdapter(mAdapter);
        getAutoComplete();
        setOnItemClickListener(this);
    }

    @Override
    public void delete(final List<PhoneContactEntity> list) {
        mList.clear();
        adapterReset();
    }

    @Override
    public void textChangeFilter(List<PhoneContactEntity> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setText(mList.get(position).getName());
        setSelection(mList.get(position).getName().length());
    }

}
