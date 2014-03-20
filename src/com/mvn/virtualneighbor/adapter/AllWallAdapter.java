package com.mvn.virtualneighbor.adapter;

import org.w3c.dom.Text;

import com.mvn.virtualneighbor.ui.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AllWallAdapter extends ArrayAdapter<String>{

	private String[] data;
	private Activity activity;
	public AllWallAdapter(Activity context, String[] objects) {
		super(context, R.layout.wall_adapter, objects);
		this.data = objects	;
		this.activity = context ;
	}
	
	static class Holder{
		ImageView imageViewWall;
		TextView textViewName;
		TextView textViewWallFollowers;
		TextView textViewWallFollowings;
		ImageView imageViewWallMenu;
		ImageView imageViewExpImage;
		TextView textViewLikes;
		TextView textViewComments;
		TextView textViewExpLocation;
		TextView textViewDiscount300Yards;
		TextView textViewDiscountOnLikes;
		TextView textViewDiscountOnComments;
		Button buttonLike;
		Button buttonComment;
		Button buttonShare;
		TextView textViewWallTime;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return convertView;
	}

}
