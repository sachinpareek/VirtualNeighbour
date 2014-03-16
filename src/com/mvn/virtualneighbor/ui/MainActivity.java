package com.mvn.virtualneighbor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.mvn.virtualneighbor.application.MyVirtualNeighbor;
import com.mvn.virtualneighbor.interfaces.ImportantMethods;

public class MainActivity extends Activity implements ImportantMethods,
		OnClickListener {

	// ** Private Members **\\
	private Button mButtonLogin;
	private Button mButtonRegister;
	private MyVirtualNeighbor mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialization();
		setContentView(R.layout.activity_main);
		findAllIds();
		setListeners();
	}

	@Override
	public void initialization() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mApplication = (MyVirtualNeighbor) getApplication();
		mApplication.addRunningActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mApplication.removeRunningActivity(this);
	}

	@Override
	public void findAllIds() {
		mButtonLogin = (Button) findViewById(R.id.button_activity_main_login);
		mButtonRegister = (Button) findViewById(R.id.button_activity_main_register);
	}

	@Override
	public void setListeners() {
		mButtonLogin.setOnClickListener(this);
		mButtonRegister.setOnClickListener(this);
	}

	@Override
	public void setAdapters() {
		// Do Nothing.
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_activity_main_login:
			loginButtonClickEvent();
			break;
		case R.id.button_activity_main_register:
			registerButtonClickeEvent();
			break;

		default:
			break;
		}
	}

	private void loginButtonClickEvent() {
		Intent intent = new Intent(this, LoginScreen.class);
		mApplication.getUtil().startNewActivity(this, intent, false);
	}

	private void registerButtonClickeEvent() {
		Intent intent = new Intent(this, RegistrationScreen.class);
		mApplication.getUtil().startNewActivity(this, intent, false);
	}

}
