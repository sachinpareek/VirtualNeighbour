package com.mvn.virtualneighbor.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	
	public HomeScreen mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActivity = (HomeScreen) this.getActivity();
	}
  
	public boolean onBackPressed() {
		return false;
	}

	
}
