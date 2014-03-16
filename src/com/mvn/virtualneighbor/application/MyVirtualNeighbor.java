package com.mvn.virtualneighbor.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.mvn.virtualneighbor.util.Util;

public class MyVirtualNeighbor extends Application {

	private Util util;
	private List<Activity> runningActivities;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setRunningActivities(new ArrayList<Activity>());
		setUtil(new Util());
	}

	public void onTerminate() {
		super.onTerminate();
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public void addRunningActivity(Activity activity) {
		runningActivities.add(activity);
	}

	public void removeRunningActivity(Activity activity) {
		runningActivities.remove(activity);
	}

	public List<Activity> getRunningActivities() {
		return runningActivities;
	}

	public void setRunningActivities(List<Activity> runningActivities) {
		this.runningActivities = runningActivities;
	}
}
