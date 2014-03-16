package com.mvn.virtualneighbor.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *  This class Detects the Network or Internet Status 
 * 
 * @author girnar
 *
 */
public class ConnectionDetector {
	
/**
 *  @param this takes the Activity name or context 
 *  @return this return boolean value 
 *  
 *  true  -  if network or internet is available
 *  false -  if network is not available
 * 
 */
	public static boolean isConnectingToInternet(Context context){
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  if (connectivity != null) 
		  {
			  NetworkInfo[] info = connectivity.getAllNetworkInfo();
			  if (info != null) 
				  for (int i = 0; i < info.length; i++) 
					  if (info[i].getState() == NetworkInfo.State.CONNECTED)
					  {
						  return true;
					  }

		  }
		  return false;
	}
}
