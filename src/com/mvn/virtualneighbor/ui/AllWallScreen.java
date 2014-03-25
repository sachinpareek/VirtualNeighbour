package com.mvn.virtualneighbor.ui;

import com.mvn.virtualneighbor.adapter.AllWallAdapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AllWallScreen extends BaseFragment {

	private Activity mActivity;
	private View rootView;
	private AllWallAdapter mAdapter;
	private ListView mListView;
	private String[] data = {"aaa","bbb","aaa","bbb","aaa","bbb"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("o ce=rajdasd");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initialization();
		findAllIds(inflater, container, savedInstanceState);
		displayData();
		return rootView;
	}

	private void findAllIds(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.all_wall_fragment, container,
				false);
		mListView = (ListView) rootView.findViewById(R.id.listView_wall);
	}

	private void initialization() {
	}
	
	private void displayData(){
		mAdapter = new  AllWallAdapter(mActivity, data);
		mListView.setAdapter(mAdapter);
	}

}
