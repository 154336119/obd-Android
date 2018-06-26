package com.slb.ttdandroidframework.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;
import com.slb.ttdandroidframework.ui.adapter.base.CommonBaseAdapter;


/**
 *
 */
public class EmissionTestMyListAdapter extends CommonBaseAdapter<EmissionTestSmallEntity> {

	public EmissionTestMyListAdapter(Context context) {
		super(context);
	}
	private ViewHolder mHolder;
	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			mHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.adapter_mylist_emission_test, parent, false);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
//		FieldValuesEntity entity = getItem(position);
//			mHolder.TvTxt.setText(entity.getFieldName()+"："+entity.getFieldValue());
		return convertView;
	}
	static class ViewHolder {
		private TextView TvTxt;
	}

}
