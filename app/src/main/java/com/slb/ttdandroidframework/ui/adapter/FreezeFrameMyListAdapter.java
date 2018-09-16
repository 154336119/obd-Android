package com.slb.ttdandroidframework.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameInsideEntity;
import com.slb.ttdandroidframework.ui.adapter.base.CommonBaseAdapter;


/**
 *
 */
public class FreezeFrameMyListAdapter extends CommonBaseAdapter<FreezeFrameInsideEntity> {

	public FreezeFrameMyListAdapter(Context context) {
		super(context);
	}
	private ViewHolder mHolder;
	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			mHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.adapter_mylist_freeze_frame, parent, false);
			mHolder.TvDes = (TextView) convertView.findViewById(R.id.TvDes);
			mHolder.TvValue = (TextView) convertView.findViewById(R.id.TvValue);
			mHolder.TvUnit = (TextView) convertView.findViewById(R.id.TvUtil);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		FreezeFrameInsideEntity entity = getItem(position);
		if(!TextUtils.isEmpty(entity.getDes())){
			mHolder.TvDes.setText(entity.getDes());
		}
		if(!TextUtils.isEmpty(entity.getValue())){
			mHolder.TvValue.setText(entity.getValue());
		}
		if(!TextUtils.isEmpty(entity.getUtil())){
			mHolder.TvUnit.setText(entity.getUtil());
		}
		return convertView;
	}
	static class ViewHolder {
		private TextView TvDes;
		private TextView TvValue;
		private TextView TvUnit;
	}

}
