package com.slb.ttdandroidframework.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.ui.adapter.base.CommonBaseAdapter;


/**
 *
 */
public class ModeFiveMyListAdapter extends CommonBaseAdapter<MoudleFiveEntity> {

	public ModeFiveMyListAdapter(Context context) {
		super(context);
	}
	private ViewHolder mHolder;
	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			mHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.adapter_mylist_mode_five, parent, false);
			mHolder.TvMin = (TextView) convertView.findViewById(R.id.TvMin);
			mHolder.TvMax = (TextView) convertView.findViewById(R.id.TvMax);
			mHolder.TvValue = (TextView) convertView.findViewById(R.id.TvValue);
			mHolder.TvNum = (TextView) convertView.findViewById(R.id.TvNum);
			mHolder.TvDes = (TextView) convertView.findViewById(R.id.TvDes);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		MoudleFiveEntity entity = getItem(position);
		if(!TextUtils.isEmpty(entity.getMax())){
			mHolder.TvMax.setText("最大值："+entity.getMax());
		}
		if(!TextUtils.isEmpty(entity.getMin())){
			mHolder.TvMin.setText("最小值："+entity.getMin());
		}
		if(!TextUtils.isEmpty(entity.getValue())){
			mHolder.TvValue.setText("测试值："+entity.getValue());
		}
		if(!TextUtils.isEmpty(entity.getNum())){
			mHolder.TvNum.setText("编号："+entity.getNum());
		}
		if(!TextUtils.isEmpty(entity.getName())){
			mHolder.TvDes.setText(entity.getName());
		}
		return convertView;
	}
	static class ViewHolder {
		private TextView TvMin;
		private TextView TvMax;
		private TextView TvValue;
		private TextView TvNum;
		private TextView TvDes;
	}

}
