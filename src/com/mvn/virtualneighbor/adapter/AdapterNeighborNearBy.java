package com.mvn.virtualneighbor.adapter;

import com.mvn.virtualneighbor.ui.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterNeighborNearBy extends ArrayAdapter<String> {

	private Activity mActivity;
	private String[] mNames;
	private String[] mProfessions;

	public AdapterNeighborNearBy(Activity context, int resource,
			String[] objects, String[] professions) {
		super(context, resource, objects);
		mActivity = context;
		mNames = objects;
		this.mProfessions = professions;
	}

	static class Holder {
		TextView mTextViewname;
		TextView mTextViewprofession;
		ImageView mImageView;
		Button mButtonfollow;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = mActivity.getLayoutInflater().inflate(
					R.layout.neighnor_nearby_adapter, null);
			holder.mTextViewname = (TextView) convertView
					.findViewById(R.id.textView_adapter_neighbor_nearby_name);
			holder.mTextViewprofession = (TextView) convertView
					.findViewById(R.id.textView_adapter_neighbor_nearby_profession);
			holder.mButtonfollow = (Button) convertView
					.findViewById(R.id.button_adapter_neighbor_nearby_follow);
			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.imageView_adapter_neighbor_nearby);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.mTextViewname.setText(mNames[position]);
		holder.mTextViewprofession.setText(mProfessions[position]);

		return convertView;
	}

}
