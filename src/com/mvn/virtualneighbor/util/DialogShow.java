package com.mvn.virtualneighbor.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;

/**
 *  this class is used for showing and dismiss the progress dialog for Async Task in Activities
 *
 */
public class DialogShow {
	
	public static ProgressDialog showProgressDialog1(ProgressDialog p_desc_Dialog,String data){
//		"Please Wait While Getting Data...."
		p_desc_Dialog.getWindow().setGravity(Gravity.BOTTOM);
		p_desc_Dialog.setMessage(data);
		p_desc_Dialog.setIndeterminate(false);
		p_desc_Dialog.setCancelable(false);
		p_desc_Dialog.show();
		return p_desc_Dialog;
	}
	
	/**
	 *  this method close progress dialog 
	 *  @param this takes the progress dialog instance which we want to close
	 * 
	 */
	public static void closeProgressDialog1(ProgressDialog progressDialog){
		progressDialog.dismiss();
	}
	

	/**
	 * this method shows the progress dialog
	 * @param ProgressDialog Instance 
	 *  @return ProgressDialog with title and message
	 * 
	 */
	public static ProgressDialog showProgressDialog(ProgressDialog p_desc_Dialog){
		p_desc_Dialog.setMessage("Please Wait While Getting Data....");
		p_desc_Dialog.setIndeterminate(false);
		p_desc_Dialog.setCancelable(false);
		p_desc_Dialog.show();
		return p_desc_Dialog;
	}
	
	/**
	 *  this method close progress dialog 
	 *  @param this takes the progress dialog instance which we want to close
	 * 
	 */
	public static void closeProgressDialog(ProgressDialog progressDialog){
		progressDialog.dismiss();
	}
	
	/**
	 * this method show  Alert Dialog when internet connection is not available
	 * 
	 * @param context
	 */

//	@SuppressLint("NewApi")
	public static void closeAppWithAlert(final Context context){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("No Network");
		alertDialog.setMessage(UtilConstants.INTERNET_CONNECTION_WARNING);
//		alertDialog.setIcon(R.drawable.fail);
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
//				((Activity)context).finish();
			}});				
		alertDialog.setCancelable(false);	
		alertDialog.show();
	}		
	
	public static void showNoData(final Context context){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setMessage("No Data Available for this product");		
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
		
			}});	
		alertDialog.show();
	}
}
