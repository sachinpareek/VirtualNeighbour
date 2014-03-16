package com.mvn.virtualneighbor.ui;

import java.util.HashMap;
import java.util.Stack;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.mvn.virtualneighbor.application.MyVirtualNeighbor;
import com.mvn.virtualneighbor.interfaces.ImportantMethods;
import com.mvn.virtualneighbor.slidemenu.SlideMenu;
import com.mvn.virtualneighbor.util.Util;
import com.mvn.virtualneighbor.util.UtilConstants;

public class HomeScreen extends BaseSlideMenuActivity implements
		ImportantMethods, OnClickListener, OnTabChangeListener {

	// ** Final Variables **\\
	private final String TAG = "HomeScreen";

	// ** Private Members **\\
	private SlideMenu mSlideMenu;
	private ImageView mImageViewLeftMenu;
	private ImageView mImageViewRightMenu;
	private ImageView mImageViewWall;
	private Context mContext;
	private TabHost mTabHost;
	private HashMap<String, Stack<Fragment>> mStacks;
	private String mCurrentTab;
	private RelativeLayout mLayoutMyProfile;
	private Button mButtonNeighborhoodWall;
	private Button mButtonMyWall;
	private Button mButtonBecomeLocalsPopular;
	private Button mButtonShareExp;
	private Button mButtonMeetLocals;
	private Button mButtonNeighborsNearBy;
	private WallScreen abc;
	private Util mUtilObj;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		initialization();
		// Main Screen
		setSlideRole(R.layout.activity_home_screen);
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
		setUpTabs();
	}

	private void setUpTabs() {
		mTabHost.setup();
		TabHost.TabSpec spec = mTabHost.newTabSpec(UtilConstants.TAB_ALL);
		mTabHost.setCurrentTab(-3);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(mContext, UtilConstants.TAB_NAME_ALL));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(UtilConstants.TAB_LOCALS);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(mContext, UtilConstants.TAB_NAME_LOCALS));
		mTabHost.addTab(spec);
	}

	public void pushFragments(String tag, Fragment fragment,
			boolean shouldAnimate, boolean shouldAdd, boolean isBack) {
		if (shouldAdd)
			mStacks.get(tag).push(fragment);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	public void popFragments() {
		Fragment fragment = mStacks.get(mCurrentTab).elementAt(
				mStacks.get(mCurrentTab).size() - 2);
		mStacks.get(mCurrentTab).pop();

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		// ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		finish();
		/*
		 * if (((BaseFragment) mStacks.get(mCurrentTab).lastElement())
		 * .onBackPressed() == false) { if (mStacks.get(mCurrentTab).size() ==
		 * 1) { if (mCurrentTab.equals(UtilConstants.TAB_ALL)) { //
		 * List<Activity> activities = ((MyVirtualNeighbor) // getApplication())
		 * // .getRunningActivities(); // for (Activity activity : activities) {
		 * // activity.finish(); // } } else mTabHost.setCurrentTab(0); } else {
		 * popFragments(); } }
		 */}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (mStacks.get(mCurrentTab).size() == 0) {
			return;
		}
		mStacks.get(mCurrentTab).lastElement()
				.onActivityResult(requestCode, resultCode, data);
	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	@Override
	public void onTabChanged(String tabId) {
		mCurrentTab = tabId;

		if (mStacks.get(tabId).size() == 0) {
			if (tabId.equals(UtilConstants.TAB_ALL)) {
				abc = new WallScreen();
				pushFragments(tabId, abc, true, true, false);
			} else if (tabId.equals(UtilConstants.TAB_LOCALS)) {
				pushFragments(tabId, new WallScreen(), true, true, false);
			}
		} else {
			pushFragments(tabId, mStacks.get(tabId).lastElement(), true, false,
					false);
		}
	}

	@Override
	public void onClick(View v) {switch (v.getId()) {
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
	}}

	@Override
	public void initialization() {
		mUtilObj= ((MyVirtualNeighbor)getApplication()).getUtil();
		mContext = getApplicationContext();
		mStacks = new HashMap<String, Stack<Fragment>>();
		mStacks.put(UtilConstants.TAB_ALL, new Stack<Fragment>());
		mStacks.put(UtilConstants.TAB_LOCALS, new Stack<Fragment>());

	}

	@SuppressWarnings("deprecation")
	@Override
	public void findAllIds() {
		mImageViewLeftMenu = (ImageView) findViewById(R.id.imageView_header_left_menu);
		mImageViewRightMenu = (ImageView) findViewById(R.id.imageView_header_right_menu);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
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
		mImageViewLeftMenu.setOnClickListener(this);
		mImageViewRightMenu.setOnClickListener(this);
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

	}

	

}