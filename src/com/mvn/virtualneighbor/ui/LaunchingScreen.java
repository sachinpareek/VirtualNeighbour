package com.mvn.virtualneighbor.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LaunchingScreen extends Activity {

	// ** Final Variables **\\
	private final String TAG = "LaunchingScreen";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launching_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launching_screen, menu);
		return true;
	}

}
