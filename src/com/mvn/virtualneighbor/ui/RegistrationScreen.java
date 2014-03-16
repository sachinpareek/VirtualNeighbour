package com.mvn.virtualneighbor.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mvn.virtualneighbor.interfaces.ImportantMethods;
import com.mvn.virtualneighbor.util.ConnectionDetector;
import com.mvn.virtualneighbor.util.UtilConstants;
import com.mvn.virtualneighbor.webservices.JSONParser;

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

	private WebTask task;
	private RelativeLayout loadingFrame;
	private String response;
	private ArrayList<String> imagePaths;
	private Bitmap imageBitmap;
	private Dialog dialog;
	private Bitmap photo;
	protected boolean isImageSet;

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

		loadingFrame = (RelativeLayout) findViewById(R.id.loadingview);
		// wheelLoading = (ImageView) rootView.findViewById(R.id.wheel);
		// ((MyApplication) activity.getApplication()).getUtil()
		// .setRotationAnimation(wheelLoading,activity);
		loadingFrame.setVisibility(View.GONE);
	}

	@Override
	public void setListeners() {
		buttonRegister.setOnClickListener(this);
		imageViewUserProfilePic.setOnClickListener(this);
	}

	@Override
	public void setAdapters() {
		// Set Adapter on Country Spinner.
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.countryArray, R.layout.country_spinner);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinnerCountry.setAdapter(adapter);
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
			/*
			 * if (null != response && response.contains("success")) {
			 * Toast.makeText(activity, "Uploaded Successfully!!!",
			 * Toast.LENGTH_SHORT).show(); ((Screen3_TabSelect) activity)
			 * .OpenInventoryFragmentFromSellFragmetn2(); for(int i =
			 * 0;i<imagePaths.size();i++){ File file = new
			 * File(imagePaths.get(i)); file.delete(); } } else {
			 * Toast.makeText(activity, response, Toast.LENGTH_SHORT).show(); }
			 */
		}
	}

	private JSONObject getUplaodedJsonData() {
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
			jobj.put(UtilConstants.PASSWORD, editTextPassword.getText().toString().trim());
			jobj.put(UtilConstants.COUNTRY_CODE, spinnerCountry
					.getSelectedItem().toString());
			jobj.put(UtilConstants.ZIP_CODE, editTextZipCode.getText()
					.toString().trim());
			
			jarr.put(jobj);
			jobj1.put("json_data", jarr); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj;
	}

	private void uploadDataToServer() {
		try {
			response = "";
			response = JSONParser.uploadMultipleImageOnServer(
					imagePaths,
					UtilConstants.URL_REGISTERATION,getUplaodedJsonData());

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
				imageViewUserProfilePic
						.setBackgroundResource(R.drawable.ic_launcher);
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
