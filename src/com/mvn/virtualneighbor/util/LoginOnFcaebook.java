package com.mvn.virtualneighbor.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.codehaus.jackson.map.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.mvn.virtualneighbor.webservices.JSONParser;
import com.mvn.virtualneighbour.facebook.FacebookData;

public class LoginOnFcaebook {

	private static final String APP_ID = UtilConstants.FB_ID;
	private static final String[] PERMISSIONS = new String[] { "basic_info",
			"publish_stream" };
	private static final String TOKEN = "access_token";
	private static final String EXPIRES = "expires_in";
	private static final String KEY = "facebook-credentials";

	private Facebook facebook;
	private WebTask task;
	private Activity activity;
	private ProgressDialog pDialog;
	private FacebookData wrapper;

	public boolean saveCredentials(Facebook facebook) {
		Editor editor = activity
				.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
		editor.putString(TOKEN, facebook.getAccessToken());
		editor.putLong(EXPIRES, facebook.getAccessExpires());
		return editor.commit();
	}

	public boolean restoreCredentials(Facebook facebook) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(
				KEY, Context.MODE_PRIVATE);
		facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
		facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
		return facebook.isSessionValid();
	}

	public void loginOnFB(FacebookData wrapper,Activity activity) {
		this.activity = activity;
		this.wrapper = wrapper;
		initialization();
		login();
	}

	private void initialization() {
		facebook = new Facebook(APP_ID);
		restoreCredentials(facebook);
		task = new WebTask();
		pDialog = new ProgressDialog(activity);
	}

	@SuppressLint("NewApi")
	private void executeTask() {
		if (task.getStatus() == AsyncTask.Status.FINISHED) {
			task = new WebTask();
		}

		if (task.getStatus() != AsyncTask.Status.RUNNING) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				task.execute();
			}
		}
	}

	@SuppressLint("NewApi")
	public void login() {
		if (!facebook.isSessionValid()) {
			loginAndgetProfileInfo();
		} else {
			executeTask();
		}
	}

	private class WebTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.setMessage("Loading...");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			getProfileInfo();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
		}
	}

	public void getProfileInfo(){
		
		String access_token = null;
		  try {
			  SharedPreferences sharedPreferences = activity
						.getSharedPreferences(KEY, Context.MODE_PRIVATE);
			   access_token = sharedPreferences.getString(TOKEN, null);
			   
			   String response = JSONParser.getJSONFromUrl(UtilConstants.FB_URL+access_token);
			   ObjectMapper mapper = new ObjectMapper();
			    this.wrapper = mapper.readValue(response, FacebookData.class);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		}

	public void loginAndgetProfileInfo() {
		facebook.authorize(activity, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH,
				new LoginDialogListener());
	}

	/*
	 * public void postToWall(String message) { Bundle parameters = new
	 * Bundle(); parameters.putString("message", message);
	 * parameters.putString("description", "Barcode App"); try{ FileInputStream
	 * is = new FileInputStream(Constants.image); ByteArrayOutputStream bs = new
	 * ByteArrayOutputStream(); int data = 0; while ((data = is.read()) != -1)
	 * bs.write(data); is.close(); byte[] raw = bs.toByteArray(); //
	 * parameters.putString("method", "photos.upload");
	 * parameters.putByteArray("source", raw); bs.close(); }catch(Exception e){
	 * e.printStackTrace(); } // dent message and picture as bundle
	 * 
	 * try { facebook.request("me"); String response =
	 * facebook.request("me/photos", parameters, "POST"); Log.d("Tests",
	 * "got response: " + response); if (response == null || response.equals("")
	 * || response.equals("false")) { showToastMessage("Blank response."); }
	 * else { showToastMessage("Message posted to your facebook wall!"); } }
	 * catch (Exception e) { showToastMessage("Failed to post to wall!");
	 * e.printStackTrace(); } }
	 */
	class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			saveCredentials(facebook);
			executeTask();
		}

		public void onFacebookError(FacebookError error) {
			showToastMessage("Authentication with Facebook failed!");
		}

		public void onError(DialogError error) {
			showToastMessage("Authentication with Facebook failed!");
		}

		public void onCancel() {
			showToastMessage("Authentication with Facebook cancelled!");
		}
	}

	private void showToastMessage(final String message) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
