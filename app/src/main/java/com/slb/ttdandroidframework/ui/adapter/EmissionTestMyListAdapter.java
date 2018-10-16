package com.slb.ttdandroidframework.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
			mHolder.TvTitle = (TextView) convertView.findViewById(R.id.TvTitle);
			mHolder.TvContent = (TextView) convertView.findViewById(R.id.TvContent);
			mHolder.Iv = (ImageView) convertView.findViewById(R.id.Iv);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		EmissionTestSmallEntity entity = getItem(position);
		mHolder.TvTitle.setText(entity.getName());

		if(entity.getShowState()){
			mHolder.TvContent.setVisibility(View.VISIBLE);
			if(entity.getOK()){
				mHolder.TvContent.setText("OK");
				mHolder.Iv.setImageResource(R.mipmap.ic_ok);
			}else{
				mHolder.TvContent.setText("Unvailable");
				mHolder.Iv.setImageResource(R.mipmap.ic_na);
			}

		}else{
			mHolder.TvContent.setVisibility(View.GONE);
		}

		if("Check Engine Light is On".equals(entity.getName())
				||entity.getName().endsWith("Trouble Codes")){
			if(entity.getOK()){
				mHolder.Iv.setImageResource(R.mipmap.ic_ok);
			}else{
				mHolder.Iv.setImageResource(R.mipmap.ic_not_ok );
			}
		}
		return convertView;
	}
	static class ViewHolder {
		private TextView TvTitle;
		private TextView TvContent;
		private ImageView Iv;
	}

}
