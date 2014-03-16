package com.mvn.virtualneighbor.ui;

import com.mvn.virtualneighbor.application.MyVirtualNeighbor;
import com.mvn.virtualneighbor.interfaces.ImportantMethods;
import com.mvn.virtualneighbor.slidemenu.SlideMenu;
import com.mvn.virtualneighbor.util.Util;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class NeighborsNerbyScreen extends BaseSlideMenuActivity implements
		ImportantMethods, OnClickListener {

	// ** Final Variables **\\
	private final String TAG = "NeighborsNerbyScreen";

	// ** Private Members **\\
	private SlideMenu mSlideMenu;
	private RelativeLayout mLayoutMyProfile;
	private Button mButtonNeighborhoodWall;
	private Button mButtonMyWall;
	private Button mButtonBecomeLocalsPopular;
	private Button mButtonShareExp;
	private Button mButtonMeetLocals;
	private Button mButtonNeighborsNearBy;
	private ImageView mImageViewWall;
	private Util mUtilObj;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		initialization();
		// Main Screen
		setSlideRole(R.layout.activity_neighbors_nerby_screen);
		// Left Menus
		setSlideRole(R.layout.slide_left_panel);
		// Right Menus
		setSlideRole(R.layout.slide_right_panel);
		mSlideMenu = getSlideMenu();
		Interpolator interpolator = new DecelerateInterpolator(0.6f);
		mSlideMenu.setInterpolator(interpolator);
		findAllIds();
		setListeners();
		setAdapters();
	}

	@Override
	public void initialization() {
		mUtilObj = ((MyVirtualNeighbor) getApplication()).getUtil();
	}

	@Override
	public void findAllIds() {
		mImageViewWall = (ImageView) findViewById(R.id.imageView_header_wall);
		mImageViewWall.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.header_center_buttons_background_clicked));
		mLayoutMyProfile = (RelativeLayout) findViewById(R.id.layout_slide_left_panel_my_profile);
		mButtonMyWall = (Button) findViewById(R.id.button_slide_left_panel_my_wall);
		mButtonNeighborhoodWall = (Button) findViewById(R.id.button_slide_left_panel_neighborhood_wall);
		mButtonBecomeLocalsPopular = (Button) findViewById(R.id.button_slide_right_panel_how_to_become_locals_popular);
		mButtonShareExp = (Button) findViewById(R.id.button_slide_right_panel_share_exp);
		mButtonMeetLocals = (Button) findViewById(R.id.button_slide_right_panel_meet_locals);
		mButtonNeighborsNearBy = (Button) findViewById(R.id.button_slide_right_panel_neighbor_nearby);

	}

	@Override
	public void setListeners() {
		mImageViewWall.setOnClickListener(this);
		mLayoutMyProfile.setOnClickListener(this);
		mButtonMyWall.setOnClickListener(this);
		mButtonNeighborhoodWall.setOnClickListener(this);
		mButtonBecomeLocalsPopular.setOnClickListener(this);
		mButtonMeetLocals.setOnClickListener(this);
		mButtonShareExp.setOnClickListener(this);
		mButtonNeighborsNearBy.setOnClickListener(this);
	}

	@Override
	public void setAdapters() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView_header_left_menu:
			mUtilObj.leftMenuClickEvent(mSlideMenu);
			break;
		case R.id.imageView_header_right_menu:
			mUtilObj.rightMenuClickEvent(mSlideMenu);
			break;
		case R.id.imageView_header_post:
			break;
		case R.id.layout_slide_left_panel_my_profile:
			mUtilObj.myProfileClickEvent(this);
			mSlideMenu.close(true);
			break;
		case R.id.button_slide_left_panel_my_wall:
			mUtilObj.myWallClickEvent(this);
			mSlideMenu.close(true);
			break;
		case R.id.button_slide_left_panel_neighborhood_wall:
			mUtilObj.neighborhoodWallClickEvent(this);
			mSlideMenu.close(true);
			break;
		case R.id.button_slide_right_panel_how_to_become_locals_popular:
			mUtilObj.becomeLocalsPopularClickEvent(this);
			mSlideMenu.close(true);
			break;
		case R.id.button_slide_right_panel_neighbor_nearby:
			mUtilObj.neighborsNearByClickEvent(this);
			mSlideMenu.close(true);
			break;
		case R.id.button_slide_right_panel_meet_locals:
			mUtilObj.meetLocalsClickEvent(this);
			mSlideMenu.close(true);
			break;
		case R.id.button_slide_right_panel_share_exp:
			mUtilObj.shareExpClickEvent(this);
			mSlideMenu.close(true);
			break;
		default:
			break;
		}
	}

}