package com.mvn.virtualneighbour.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mvn.virtualneighbour.twitter.Twitter_Handler.TwDialogListener;

public class Twitter_Login {

	private final Twitter_Handler mTwitter;
	private final Activity activity;

	public Twitter_Login(Activity act) {
		this.activity = act;
		mTwitter = new Twitter_Handler(activity);
	}

	public void loginToTwitter() {
		mTwitter.setListener(mTwLoginDialogListener);

		if (mTwitter.hasAccessToken()) {
			showLoginDialog();

		} else {
			mTwitter.authorize();
		}
	}

	private void showLoginDialog() {
		new LoginTask().execute();
	}

	private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

		@Override
		public void onError(String value) {
			showToast("Login Failed");
			mTwitter.resetAccessToken();
		}

		@Override
		public void onComplete(String value) {
			showLoginDialog();
		}
	};

	void showToast(final String msg) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

			}
		});

	}

	class LoginTask extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Getting data");
			pDialog.setCancelable(false);
			pDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... twitt) {
			try {
				getLoginInformation(mTwitter.twitterObj);
				return "success";

			} catch (Exception e) {
				
				e.printStackTrace();
				return "Login Failed!!!";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
			if (null != result && result.equals("success")) {
				showToast("Login Successfully");
			} else {
				showToast(result);
			}
			super.onPostExecute(result);
		}
	}


	public void getLoginInformation(Twitter twitter) throws Exception {
		try {
			User user = twitter.showUser(twitter.getId());
			System.out.println("user");
		} catch (TwitterException e) {
			Toast.makeText(activity,"Please Try Again",Toast.LENGTH_SHORT).show();
			throw e;
		}
	}

	public void Authorize_UserDetail() {

	}
}
