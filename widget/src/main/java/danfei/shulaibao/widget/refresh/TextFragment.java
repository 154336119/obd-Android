package danfei.shulaibao.widget.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.frame.ui.presenter.IBaseFragmentPresenter;

import rx.Observable;


/**
 * 作者:刁剑
 * 时间:2016/9/20
 * 注释:
 */
public class TextFragment extends BaseBrvahRefreshFragment {
    public static TextFragment newInstance(){
        return new TextFragment();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
//        List<TextEntity> mDatas=new ArrayList<>();
//        for (int i=0;i<20;i++){
//            mDatas.add(new TextEntity("sldf"+i,"速度快放假"));
//        }
//        mAdapter=new TextAdapter(mDatas);
        return null;
    }

    @Override
    protected void onItemClickListener(View view, RecyclerView.Adapter adapter, int position) {

    }

    @Override
    public IBaseFragmentPresenter initPresenter() {
        return null;
    }

    @Override
    protected Observable<HttpResult> requestHttp() {
        return super.requestHttp();
    }
}
