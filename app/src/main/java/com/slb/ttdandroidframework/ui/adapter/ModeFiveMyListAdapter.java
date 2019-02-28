package com.slb.ttdandroidframework.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.ui.adapter.base.CommonBaseAdapter;
import com.slb.ttdandroidframework.util.config.Mode5Util;


/**
 *
 */
public class ModeFiveMyListAdapter extends CommonBaseAdapter<ModeSixEntity> {

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
			mHolder.IvState = (ImageView) convertView.findViewById(R.id.IvState);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		ModeSixEntity entity = getItem(position);
		if(!TextUtils.isEmpty(entity.getMax())){
			mHolder.TvMax.setText(mContext.getString(R.string.max)+entity.getMax()+entity.getUnit());
		}else{
			mHolder.TvMax.setText(mContext.getString(R.string.max)+mContext.getString(R.string.nothing));
		}
		if(!TextUtils.isEmpty(entity.getMin())){
			mHolder.TvMin.setText(mContext.getString(R.string.min)+entity.getMin()+entity.getUnit());
		}else{
			mHolder.TvMin.setText(mContext.getString(R.string.min)+mContext.getString(R.string.nothing));
		}
		if(!TextUtils.isEmpty(entity.getValue())){
			mHolder.TvValue.setText(mContext.getString(R.string.values)+entity.getValue()+entity.getUnit());
		}else{
			mHolder.TvValue.setText(mContext.getString(R.string.values)+mContext.getString(R.string.nothing));
		}
		if(!TextUtils.isEmpty(entity.getTid())){
			mHolder.TvNum.setText("$"+entity.getTid());
		}
		if(!TextUtils.isEmpty(entity.getTid())){
			mHolder.TvDes.setText(Mode5Util.getDes(entity.getTid()));
		}

		if(!entity.isState()){
			mHolder.TvValue.setTextColor(mContext.getResources().getColor(R.color.colors_b5));
			mHolder.IvState.setImageResource(R.mipmap.ic_not_ok);
		}else{
			mHolder.TvValue.setTextColor(mContext.getResources().getColor(R.color.white));
			mHolder.IvState.setImageResource(R.mipmap.ic_ok);
		}
		return convertView;
	}
	static class ViewHolder {
		private TextView TvMin;
		private TextView TvMax;
		private TextView TvValue;
		private TextView TvNum;
		private TextView TvDes;
		private ImageView IvState;
	}

}
