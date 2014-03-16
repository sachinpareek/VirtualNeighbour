package com.mvn.virtualneighbor.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NeighborhoodTypeSelectionScreen extends Activity {

	// ** Final Variables **\\
	private final String TAG = "NeighborhoodTypeSelectionScreen";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neighborhood_type_selection_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.neighborhood_type_selection_screen,
				menu);
		return true;
	}

}
