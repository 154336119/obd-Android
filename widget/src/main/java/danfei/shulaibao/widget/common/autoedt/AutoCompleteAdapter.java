package danfei.shulaibao.widget.common.autoedt;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.slb.frame.cache.ACache;

import java.util.ArrayList;
import java.util.List;

import danfei.shulaibao.widget.R;

/**
 * 刁剑
 * 2017/6/14
 * 注释:
 */

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    private static final String KEY_PHONE_NUMBERS = "key_phone_numbers";
    private ArrayFilter mFilter;
    private List<PhoneContactEntity> mList;
    private Context mContext;
    private ArrayList<PhoneContactEntity> mUnfilteredData;
    private IAutoPhoneDelete mIAutoPhoneDelete;
    public AutoCompleteAdapter(Context context, List<PhoneContactEntity> list, IAutoPhoneDelete iAutoPhoneDelete){
        mList=list;
        mContext=context;
        mIAutoPhoneDelete=iAutoPhoneDelete;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolder holder;
        if (convertView==null){
            view= View.inflate(mContext, R.layout.adapter_auto_complete,null);

            holder=new ViewHolder();
            holder.TvTitle= (TextView) view.findViewById(R.id.TvTitle);
            holder.IvClear= (ImageView) view.findViewById(R.id.IvClear);
            view.setTag(holder);
        }else {
            view=convertView;
            holder= (ViewHolder) view.getTag();
        }
        holder.TvTitle.setText(mList.get(i).getName());
        holder.IvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除本地相应缓存
                ACache aCache = ACache.get(mContext);
                String phones=aCache.getAsString(KEY_PHONE_NUMBERS);
                String phone=mList.get(i).getName();
                aCache.put(KEY_PHONE_NUMBERS, phones.replace(phone+",",""));
                //列表刷新
                mList.remove(i);
                notifyDataSetChanged();
                //重置适配器
//                RxBus.get().post(RxBusTag.AutoCompleteDeleteEvent,mList);
                mIAutoPhoneDelete.delete(mList);
            }
        });
        return view;
    }

    static class ViewHolder{
        private TextView TvTitle;
        private ImageView IvClear;
    }
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results=new FilterResults();
            if (mUnfilteredData==null){
                mUnfilteredData=new ArrayList<>(mList);
            }

            if (prefix==null || prefix.length()==0){
                ArrayList<PhoneContactEntity> list=mUnfilteredData;
                results.values=list;
                results.count=list.size();
            }else {
                String prefixString=prefix.toString().toLowerCase();

                ArrayList<PhoneContactEntity> unfilteredValues=mUnfilteredData;
                int count=unfilteredValues.size();

                ArrayList<PhoneContactEntity> newValues=new ArrayList<>(count);

                for (int i=0;i<count;i++){
                    PhoneContactEntity pc=unfilteredValues.get(i);
                    if (pc!=null){
                        if (pc.getName()!=null && pc.getName().startsWith(prefixString)){
                            newValues.add(pc);
                        }
                    }
                }
                results.values=newValues;
                results.count=newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            mList= (List<PhoneContactEntity>) results.values;
            mIAutoPhoneDelete.textChangeFilter(mList);
            if (results.count>0){
                notifyDataSetChanged();
            }else {
                notifyDataSetInvalidated();
            }
        }
    }
}
