package com.mvn.virtualneighbor.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mvn.virtualneighbor.slidemenu.SlideMenu;
import com.mvn.virtualneighbor.ui.NeighborhoodWallScreen;
import com.mvn.virtualneighbor.ui.NeighborsNerbyScreen;
import com.mvn.virtualneighbor.ui.ProfileScreen;
import com.mvn.virtualneighbor.ui.R;
import com.mvn.virtualneighbor.ui.ShareExpScreen;

public class Util {

	public void startNewActivity(Activity activity, Intent intent,
			boolean isFromTop) {
		activity.startActivity(intent);
		if (isFromTop)
			activity.overridePendingTransition(
					R.anim.layout_animation_come_visible_from_top,
					R.anim.slide_down_from_top);
		else
			activity.overridePendingTransition(
					R.anim.layout_animation_row_left_from_right_side,
					R.anim.slide_down_from_top);
	}

	public void startNewActivityFromLeft(Activity activity, Intent intent) {
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.slide_down_from_top,
				R.anim.layout_animation_row_right_slide);
		// activity.finish();
	}

	public void finishActivity(Activity activity, boolean isToTop) {
		activity.finish();
		if (isToTop)
			activity.overridePendingTransition(R.anim.slide_down_from_top,
					R.anim.layout_animation_goes_bottum_to_top);
		else
			activity.overridePendingTransition(R.anim.slide_down_from_top,
					R.anim.layout_animation_row_right_slide);
	}

	public void leftMenuClickEvent(SlideMenu mSlideMenu) {
		mSlideMenu.open(false, true);
	}

	public void rightMenuClickEvent(SlideMenu mSlideMenu) {
		mSlideMenu.open(true, true);
	}

	public void wallPostClickEvent(SlideMenu mSlideMenu) {
		mSlideMenu.open(true, true);
	}

	public void myProfileClickEvent(Context mActivity) {
		Intent intent = new Intent(mActivity, ProfileScreen.class);
		mActivity.startActivity(intent);
	}

	public void neighborhoodWallClickEvent(Context mActivity) {
		Intent intent = new Intent(mActivity, NeighborhoodWallScreen.class);
		mActivity.startActivity(intent);
	}

	public void neighborsNearByClickEvent(Context mActivity) {
		Intent intent = new Intent(mActivity, NeighborsNerbyScreen.class);
		mActivity.startActivity(intent);
	}

	public void becomeLocalsPopularClickEvent(Context mActivity) {
		// Intent intent = new Intent(this, NeighborhoodWallScreen.class);
		// startActivity(intent);
	}

	public void meetLocalsClickEvent(Context mActivity) {
		// Intent intent = new Intent(this, NeighborhoodWallScreen.class);
		// startActivity(intent);
	}

	public void shareExpClickEvent(Context mActivity) {
		Intent intent = new Intent(mActivity, ShareExpScreen.class);
		mActivity.startActivity(intent);
	}

	public void myWallClickEvent(Context mActivity) {
		// Intent intent = new Intent(this, WallScreen.class);
		// startActivity(intent);
	}

}
