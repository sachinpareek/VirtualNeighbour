package com.mvn.virtualneighbor.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mvn.virtualneighbor.application.MyVirtualNeighbor;
import com.mvn.virtualneighbor.interfaces.ImportantMethods;
import com.mvn.virtualneighbor.util.ConnectionDetector;
import com.mvn.virtualneighbor.util.LoginOnFcaebook;
import com.mvn.virtualneighbor.util.UtilConstants;
import com.mvn.virtualneighbor.webservices.JSONParser;
import com.mvn.virtualneighbor.webservices.JsonRegistrationStepOneData;
import com.mvn.virtualneighbor.webservices.JsonRegistrationStepOneMap;
import com.mvn.virtualneighbour.facebook.FacebookData;
import com.mvn.virtualneighbour.twitter.Twitter_Login;

public class RegistrationScreen extends Activity implements ImportantMethods,
		OnClickListener {

	protected static final int RESULT_CAPTURE_IMAGE = 1;
	private static final int RESULT_LOAD_IMAGE = 2;
	// ** Final Variables **\\
	private final String TAG = "RegistrationScreen";
	private Spinner spinnerCountry;
	private EditText editTextFirstName;
	private EditText editTextEmail;
	private EditText editTextPassword;
	private EditText editTextLastName;
	private EditText editTextZipCode;
	private Button buttonRegister;
	private TextView textViewHeader;
	private ImageView imageViewUserProfilePic;
	private JsonRegistrationStepOneMap normalRegistrationWrapper;

	private WebTask task;
	private RelativeLayout loadingFrame;
	private String response;
	private ArrayList<String> imagePaths;
	private Bitmap imageBitmap;
	private Dialog dialog;
	private Bitmap photo;
	protected boolean isImageSet;
	private RelativeLayout fbLayout;
	private RelativeLayout twitterLayout;
	private RelativeLayout linkedinLayout;
	private FacebookData facebookWrapper;
	private RegisterationFromSocialSite registerationUsingSocialTask;
	private LinearLayout mLayoutBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialization();
		setContentView(R.layout.activity_registration_screen);
		findAllIds();
		setListeners();
		setAdapters();
		setHintColors();
		textViewHeader.setText(R.string.registerText);
	}

	private void setHintColors() {
		// Set Hint color o black.
		editTextFirstName.setHintTextColor(getResources().getColor(
				R.color.black));
		editTextLastName.setHintTextColor(getResources()
				.getColor(R.color.black));
		editTextZipCode
				.setHintTextColor(getResources().getColor(R.color.black));
		editTextEmail.setHintTextColor(getResources().getColor(R.color.black));
		editTextPassword.setHintTextColor(getResources()
				.getColor(R.color.black));
	}

	@Override
	public void initialization() {
		// For Remove title bar from activity.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		task = new WebTask();
		registerationUsingSocialTask = new RegisterationFromSocialSite();
		imagePaths = new ArrayList<String>();
	}

	@Override
	public void findAllIds() {
		textViewHeader = (TextView) findViewById(R.id.textView_login_header);
		spinnerCountry = (Spinner) findViewById(R.id.spinner_activity_registration_screen_country);
		editTextFirstName = (EditText) findViewById(R.id.editText_activity_registration_screen_first_name);
		editTextEmail = (EditText) findViewById(R.id.editText_activity_registration_screen_email);
		editTextPassword = (EditText) findViewById(R.id.editText_activity_registration_screen_password);
		buttonRegister = (Button) findViewById(R.id.button_activity_registration_screen_login);
		editTextLastName = (EditText) findViewById(R.id.editText_activity_registration_screen_last_name);
		editTextZipCode = (EditText) findViewById(R.id.editText_activity_registration_screen_zip_code);
		imageViewUserProfilePic = (ImageView) findViewById(R.id.imageView_activity_registration_screen_user_image);
		mLayoutBack = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		loadingFrame = (RelativeLayout) findViewById(R.id.loadingview);
		// wheelLoading = (ImageView) rootView.findViewById(R.id.wheel);
		// ((MyApplication) activity.getApplication()).getUtil()
		// .setRotationAnimation(wheelLoading,activity);
		loadingFrame.setVisibility(View.GONE);
		fbLayout = (RelativeLayout) findViewById(R.id.fblayout);
		twitterLayout = (RelativeLayout) findViewById(R.id.twitterlayout);
		linkedinLayout = (RelativeLayout) findViewById(R.id.linkedinlayout);
	}

	@Override
	public void setListeners() {
		buttonRegister.setOnClickListener(this);
		imageViewUserProfilePic.setOnClickListener(this);
		mLayoutBack.setOnClickListener(this);
		// fbLayout.setOnClickListener(this);
		// twitterLayout.setOnClickListener(this);
		// linkedinLayout.setOnClickListener(this);
	}

	@Override
	public void setAdapters() {
		// Set Adapter on Country Spinner.
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.countryArray, R.layout.country_spinner);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinnerCountry.setAdapter(adapter);
	}

	private void facebookLoginClicked() {
		LoginOnFcaebook login = new LoginOnFcaebook();
		login.loginOnFB(facebookWrapper, RegistrationScreen.this);
		System.out.println("wrapper");
	}

	private void twitterLoginClicked() {
		Twitter_Login twit_login = new Twitter_Login(RegistrationScreen.this);
		twit_login.loginToTwitter();
	}

	private void linkedinLoginClicked() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_activity_registration_screen_login:
			registerButtonClickEvent();
			break;
		case R.id.imageView_activity_registration_screen_user_image:
			profilePicButtonClickEvent();
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
		case R.id.linear_layout_img_bck:
			((MyVirtualNeighbor) getApplication()).getUtil().finishActivity(
					this, false);
			break;
		default:
			break;
		}
	}

	private String saveImageIntoSDCard(Bitmap bitmap) {

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + UtilConstants.IMAGE_PATH);
		if (!myDir.exists())
			myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-" + n + ".png";
		File file = new File(myDir, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}

	private void registerButtonClickEvent() {
		imagePaths.clear();
		if (validate()) {
			if (imageBitmap != null) {
				imagePaths.add(saveImageIntoSDCard(imageBitmap));
			}
			executeTask();
		}
	}

	private void profilePicButtonClickEvent() {
		showDialog();
	}

	@SuppressLint("NewApi")
	private void executeTask() {
		if (ConnectionDetector.isConnectingToInternet(this
				.getApplicationContext())) {
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
		} else {
			Toast.makeText(this.getApplicationContext(),
					UtilConstants.INTERNET_CONNECTION_WARNING,
					Toast.LENGTH_SHORT).show();
		}
	}

	private class WebTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingFrame.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			uploadDataToServer();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loadingFrame.setVisibility(View.GONE);

			if (null != normalRegistrationWrapper
					&& normalRegistrationWrapper.getData().getMessage()
							.equalsIgnoreCase("success")) {
				Intent intent = new Intent(RegistrationScreen.this,
						RegistrationActivity2.class);
				((MyVirtualNeighbor) getApplication()).getUtil()
						.startNewActivity(RegistrationScreen.this, intent,
								false);
			} else {
				Toast.makeText(RegistrationScreen.this, response,
						Toast.LENGTH_LONG).show();
			}

		}
	}

	@SuppressLint("NewApi")
	private void executeRegisterationFromSocialSiteTask() {
		if (registerationUsingSocialTask.getStatus() == AsyncTask.Status.FINISHED) {
			registerationUsingSocialTask = new RegisterationFromSocialSite();
		}
		if (registerationUsingSocialTask.getStatus() != AsyncTask.Status.RUNNING) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				registerationUsingSocialTask
						.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				registerationUsingSocialTask.execute();
			}
		}
	}

	private class RegisterationFromSocialSite extends
			AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingFrame.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			verifyFromSocialSite();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loadingFrame.setVisibility(View.GONE);

		}
	}

	private void verifyFromSocialSite() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String response = JSONParser.sendData(getLoginVerifyJsonData(),
					UtilConstants.URL_SOCIAL_SITE_LOGIN_AUTHENTICATION_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JSONObject getLoginVerifyJsonData() {
		JSONObject jobj = new JSONObject();

		try {
			if (facebookWrapper != null) {

			}
			jobj.put(UtilConstants.LOGIN_TYPE, "email");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj;
	}

	private JSONArray getUplaodedJsonData() {
		JSONObject jobj = new JSONObject();
		JSONObject jobj1 = new JSONObject();
		JSONArray jarr = new JSONArray();

		try {
			jobj.put(UtilConstants.FIRST_NAME, editTextFirstName.getText()
					.toString().trim());
			jobj.put(UtilConstants.LAST_NAME, editTextLastName.getText()
					.toString().trim());
			jobj.put(UtilConstants.LOGIN_TYPE, "email");
			jobj.put(UtilConstants.EMAIL, editTextEmail.getText().toString()
					.trim());
			jobj.put(UtilConstants.PASSWORD, editTextPassword.getText()
					.toString().trim());
			jobj.put(UtilConstants.COUNTRY_CODE, spinnerCountry
					.getSelectedItem().toString());
			jobj.put(UtilConstants.ZIP_CODE, editTextZipCode.getText()
					.toString().trim());

			jarr.put(jobj);
			jobj1.put("json_data", jarr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jarr;
	}

	private void uploadDataToServer() {
		ObjectMapper mapper = ((MyVirtualNeighbor) getApplication())
				.getObjectMapper();
		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			response = "";

			try {
				if (imagePaths.size() > 0)
					mpEntity.addPart("image1",
							new FileBody(new File(imagePaths.get(0))));
				if (imagePaths.size() > 1)
					mpEntity.addPart("image2",
							new FileBody(new File(imagePaths.get(1))));
				if (imagePaths.size() > 2)
					mpEntity.addPart("image3",
							new FileBody(new File(imagePaths.get(2))));
				if (imagePaths.size() > 3)
					mpEntity.addPart("image4",
							new FileBody(new File(imagePaths.get(3))));
				mpEntity.addPart(UtilConstants.FIRST_NAME, new StringBody(
						editTextFirstName.getText().toString()));
				mpEntity.addPart(UtilConstants.LAST_NAME, new StringBody(
						editTextLastName.getText().toString()));
				mpEntity.addPart(UtilConstants.EMAIL, new StringBody(
						editTextEmail.getText().toString()));
				mpEntity.addPart(UtilConstants.PASSWORD, new StringBody(
						editTextPassword.getText().toString()));
				mpEntity.addPart(UtilConstants.COUNTRY_CODE, new StringBody(
						spinnerCountry.getSelectedItem().toString()));
				mpEntity.addPart(UtilConstants.ZIP_CODE, new StringBody(
						editTextZipCode.getText().toString()));
				mpEntity.addPart(UtilConstants.LOGIN_TYPE, new StringBody(
						"email"));

			} catch (Exception e) {
				e.printStackTrace();
			}
			response = ((MyVirtualNeighbor) getApplication()).getJsonParser()
					.uploadMultipleImageOnServer(
							UtilConstants.URL_REGISTERATION,mpEntity);
			// response = ((MyVirtualNeighbor) getApplication()).getJsonParser()
			// .sendJsonArray(getUplaodedJsonData(),
			// UtilConstants.URL_REGISTERATION);
			// JSONObject jsonObject = new JSONObject("MESSAGE");
			// normalRegistrationWrapper = new JsonRegistrationStepOneData();
			// normalRegistrationWrapper.setMESSAGE(jsonObject.toString());
			 normalRegistrationWrapper = mapper.readValue(response,
			 JsonRegistrationStepOneMap.class);
			System.out.println("response is :" + response);
			// System.out.println(normalRegistrationWrapper.getType().get(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validate() {

		if (editTextFirstName.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (editTextLastName.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (editTextEmail.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (editTextPassword.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (spinnerCountry.getSelectedItem().toString()
				.equalsIgnoreCase("Please select country")) {
			Toast.makeText(this, "Select brand", Toast.LENGTH_SHORT).show();
			return false;
		} else if (editTextZipCode.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter zip code", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		return true;

	}

	private void showDialog() {
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog);

		Button dialogButton = (Button) dialog
				.findViewById(R.id.button_custom_dialog_takePhoto);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PackageManager pm = getPackageManager();
				if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
					Intent i = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					// i.putExtra(MediaStore.EXTRA_OUTPUT,
					// MyFileContentProvider1.CONTENT_URI);
					startActivityForResult(i, RESULT_CAPTURE_IMAGE);
				} else {
					Toast.makeText(RegistrationScreen.this,
							"Camera is not available", Toast.LENGTH_LONG)
							.show();
				}
				dialog.dismiss();
			}
		});

		Button dialogButton1 = (Button) dialog
				.findViewById(R.id.button_custom_dialog_existingPhoto);
		dialogButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fireIntentForAddPhoto();
				dialog.dismiss();
			}
		});

		Button dialogButton2 = (Button) dialog
				.findViewById(R.id.button_custom_dialog_close);
		dialogButton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		Button imageRemove_btn = (Button) dialog
				.findViewById(R.id.button_custom_dialog_remove);
		if (!isImageSet) {
			imageRemove_btn.setVisibility(View.GONE);
		}
		imageRemove_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isImageSet = false;
				imageBitmap = null;
				imageViewUserProfilePic.setImageBitmap(null);
				imageViewUserProfilePic.setBackgroundResource(R.drawable.next);
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void fireIntentForAddPhoto() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = this.getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			photo = BitmapFactory.decodeFile(picturePath);

		} else if (requestCode == RESULT_CAPTURE_IMAGE
				&& resultCode == Activity.RESULT_OK) {
			// Uri selectedImage = data.getData();
			// BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inPurgeable = true;
			// options.inSampleSize = -1;
			// Bitmap photo = BitmapFactory.decodeFile( _path, options );
			photo = (Bitmap) data.getExtras().get("data");
			// File out = new File(activity.getFilesDir(), "newImage1.jpg");
			// photo = BitmapFactory.decodeFile(out.getAbsolutePath());

		}
		imageViewUserProfilePic.measure(MeasureSpec.UNSPECIFIED,
				MeasureSpec.UNSPECIFIED);
		int w = imageViewUserProfilePic.getMeasuredWidth();
		int h = imageViewUserProfilePic.getMeasuredHeight();

		imageBitmap = Bitmap.createScaledBitmap(photo, w, h, false);
		imageViewUserProfilePic.setImageBitmap(imageBitmap);
		isImageSet = true;

	}

}
