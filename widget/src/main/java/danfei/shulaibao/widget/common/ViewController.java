package danfei.shulaibao.widget.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 刁剑
 * Created on 2017/11/24
 * 注释:
 */

public abstract class ViewController<T>{
    private T mData;
    private Context mContext;
    private View mView;

    public ViewController(Context context) {
        this.mContext = context;
    }

    public void attachRoot(ViewGroup root) {
        int resLayoutId = this.resLayoutId();
        if(resLayoutId <= 0) {
            throw new IllegalStateException("Please check your layout id in resLayoutId() method");
        } else {
            this.mView = LayoutInflater.from(this.mContext).inflate(resLayoutId, root, false);
            root.addView(this.mView);
            this.onCreatedView(this.mView);
        }
    }
    public void replaceRoot(ViewGroup root){
        int resLayoutId = this.resLayoutId();
        if(resLayoutId <= 0) {
            throw new IllegalStateException("Please check your layout id in resLayoutId() method");
        } else {
            this.mView = LayoutInflater.from(this.mContext).inflate(resLayoutId, root, false);
            root.removeAllViews();
            root.addView(this.mView);
            this.onCreatedView(this.mView);
        }
    }
    public void fillData(T data) {
        this.mData = data;
        if(this.mData != null) {
            this.onBindView(data);
        }

    }

    protected abstract int resLayoutId();

    protected abstract void onCreatedView(View var1);

    protected abstract void onBindView(T var1);

    public Context getContext() {
        return this.mContext;
    }

    public View getView() {
        return this.mView;
    }

    public T getData() {
        return this.mData;
    }
}
