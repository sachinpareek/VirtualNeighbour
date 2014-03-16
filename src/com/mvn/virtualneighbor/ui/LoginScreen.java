package com.mvn.virtualneighbor.ui;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mvn.virtualneighbor.interfaces.ImportantMethods;
import com.mvn.virtualneighbor.util.ConnectionDetector;
import com.mvn.virtualneighbor.util.DialogShow;
import com.mvn.virtualneighbor.util.LoginOnFcaebook;
import com.mvn.virtualneighbor.util.UtilConstants;
import com.mvn.virtualneighbor.webservices.JSONParser;
import com.mvn.virtualneighbour.facebook.FacebookData;

public class LoginScreen extends Activity implements ImportantMethods,
		OnClickListener {

	// ** Final Variables **\\
	private final String TAG = "LoginScreen";
	private TextView textViewForgotPassword;
	private TextView textViewHeader;
	private EditText mEditTextUserName;
	private EditText mEditTextPassword;
	private Button mButtonLogin;

	private Logintask loginTask;
	private Context mContext;
	private SharedPreferences prefs;
	private RelativeLayout loadingView;
	// private LoginWrapper loginWrapper;
	private String userName;
	private String password;
	private RelativeLayout fbLayout;
	private RelativeLayout twitterLayout;
	private RelativeLayout linkedinLayout;
	private FacebookData facebookWrapper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialization();
		setContentView(R.layout.activity_login_screen);
		findAllIds();
		setListeners();
		// Sets underline to forgot password text.
		textViewForgotPassword.setText(Html
				.fromHtml(getString(R.string.forgot_password)));
		textViewHeader.setText(R.string.loginText);
	}

	@Override
	public void initialization() {
		// For Remove title bar from activity.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = getApplicationContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		loginTask = new Logintask();
	}

	@Override
	public void findAllIds() {
		textViewForgotPassword = (TextView) findViewById(R.id.textView_activity_login_screen_forgot_password);
		textViewHeader = (TextView) findViewById(R.id.textView_login_header);
		mButtonLogin = (Button) findViewById(R.id.button_activity_login_screen_login);
		mEditTextUserName = (EditText) findViewById(R.id.editText_activity_login_screen_email);
		mEditTextPassword = (EditText) findViewById(R.id.editText_activity_login_screen_password);
		loadingView = (RelativeLayout) findViewById(R.id.loadingview);
		fbLayout = (RelativeLayout) findViewById(R.id.fblayout);
		twitterLayout = (RelativeLayout) findViewById(R.id.twitterlayout);
		linkedinLayout = (RelativeLayout) findViewById(R.id.linkedinlayout);
	}


	@Override
	public void setListeners() {
		mButtonLogin.setOnClickListener(this);
		fbLayout.setOnClickListener(this);
		twitterLayout.setOnClickListener(this);
		linkedinLayout.setOnClickListener(this);

	}

	@Override
	public void setAdapters() {
		// TODO Auto-generated method stub

	}
	
	private void facebookLoginClicked(){
		LoginOnFcaebook login = new LoginOnFcaebook();
		login.loginOnFB(facebookWrapper,LoginScreen.this);
	}
	
	private void twitterLoginClicked() {

	}

	private void linkedinLoginClicked() {

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_activity_login_screen_login:
			loginButtonCLickEvent();
			break;
		case R.id.fblayout:
			facebookLoginClicked();
			break;
		case R.id.twitterlayout:
			twitterLoginClicked();
			break;
		case R.id.linkedinlayout:
			linkedinLoginClicked();
			break;

		default:
			break;
		}
	}

	private void loginButtonCLickEvent() {
		userName = mEditTextUserName.getText().toString().trim();
		password = mEditTextPassword.getText().toString().trim();
		if (validate()) {
			if (ConnectionDetector.isConnectingToInternet(mContext))
				executeTask();
			else
				DialogShow.closeAppWithAlert(LoginScreen.this);
			// Intent intent = new Intent(this, HomeScreen.class);
			// startActivity(intent);
		}
	}

	@SuppressLint("NewApi")
	private void executeTask() {
		if (loginTask.getStatus() == AsyncTask.Status.FINISHED) {
			loginTask = new Logintask();
		}
		if (loginTask.getStatus() != AsyncTask.Status.RUNNING) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				loginTask.execute();
			}
		}
	}

	private class Logintask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingView.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			getDataFromServer();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loadingView.setVisibility(View.GONE);

			// if(null != loginWrapper &&
			// loginWrapper.getDealerValidate().getIsValid().equalsIgnoreCase("Authentication Success")){
			// if(loginWrapper.getDealerValidate().getRole().equalsIgnoreCase("3")){
			// saveDataInPreference();
			// startNextActivity();
			// }else{
			// Toast.makeText(Screen2_LoginActivity.this,
			// "Not Authorized...!!!", Toast.LENGTH_SHORT).show();
			// }
			// }else{
			// Toast.makeText(Screen2_LoginActivity.this,
			// "Invalid UserName Password...!!!", Toast.LENGTH_SHORT).show();
			// }
		}
	}

	private boolean validate() {

		if (mEditTextUserName.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (mEditTextPassword.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;

	}

	private void getDataFromServer() {
		ObjectMapper mapper = new ObjectMapper();
		try {

			String response = JSONParser.sendData(getUplaodedJsonData(), UtilConstants.URL_LOGIN);
//			loginWrapper = mapper.readValue(response, LoginWrapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject getUplaodedJsonData() {
		JSONObject jobj = new JSONObject();

		try {
			jobj.put(UtilConstants.EMAIL, mEditTextUserName.getText()
					.toString().trim());
			jobj.put(UtilConstants.PASSWORD, mEditTextPassword.getText()
					.toString().trim());
			jobj.put(UtilConstants.LOGIN_TYPE, "email");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj;
	}

}
