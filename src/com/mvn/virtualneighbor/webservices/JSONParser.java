package com.mvn.virtualneighbor.webservices;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.mvn.virtualneighbor.exception.VirtualNeighborException;
import com.mvn.virtualneighbor.util.UtilConstants;

public class JSONParser {

	public static HttpGet httpGet;
	public static HttpPost httppost;

	static String charset = "UTF-8";
	static String param1 = "value1";
	static String param2 = "value2";

	public static String getJSONFromUrl(String url)
			throws VirtualNeighborException, IOException {
		Log.d("Test", "URL is :" + url);

		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true); // Triggers POST.
		connection.setUseCaches(false);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charset);

		// try {
		// output.write(query.getBytes(charset));
		// } finally {
		// try { output.close(); } catch (IOException logOrIgnore) {}
		// }
		InputStream is = connection.getInputStream();
		String data = "";
		/*
		 * InputStream is = null; String data = ""; try { DefaultHttpClient
		 * httpClient = new DefaultHttpClient();
		 * 
		 * httpGet = new HttpGet(url);
		 * 
		 * HttpResponse httpResponse = httpClient.execute(httpGet); HttpEntity
		 * httpEntity = httpResponse.getEntity(); int statusCode =
		 * httpResponse.getStatusLine().getStatusCode();
		 * 
		 * if (statusCode == 204) { data = null; }
		 * 
		 * if (statusCode == 200) {
		 */
		// is = httpEntity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"iso-8859-1"));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		data = sb.toString();
		is.close();
		// }

		/*
		 * } catch (Exception e) { throw new CarDekhoException(e.getMessage());
		 * }
		 */
		Log.d("Test", "url is " + url);
		// System.out.println("data is "+data);
		Log.d("Test", "rESPONSE  is :" + data);

		return data;
	}

	public static String uploadData(List<NameValuePair> requestData, String url)
			throws VirtualNeighborException {

		String data = null;
		HttpClient httpclient = new DefaultHttpClient();
		httppost = new HttpPost(url);
		InputStream is = null;

		try {
			httppost.setEntity(new UrlEncodedFormEntity(requestData));
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity httpEntity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 204) {
				data = null;
			}

			if (statusCode == 200) {
				is = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				data = sb.toString();
				is.close();
			}

		} catch (Exception e) {
			throw new VirtualNeighborException(e.getMessage());
		}
		return data;
	}

	public static String sendData(JSONObject requestData, String url)
			throws VirtualNeighborException {

		InputStream is = null;
		String data = "";
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			/*
			 * httpPost.addHeader("X_REST_USERNAME",
			 * UtilityConstants.X_REST_USERNAME);
			 * httpPost.addHeader("X_REST_PASSWORD",
			 * UtilityConstants.X_REST_PASSWORD);
			 */

			if (null != requestData) {
				StringEntity se = new StringEntity(requestData.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				httpPost.setEntity(se);
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 204) {
				data = null;
			}
			if (statusCode == 200) {
				is = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				data = sb.toString();

				is.close();
			}

		} catch (Exception e) {
			throw new VirtualNeighborException(e.getMessage());
		}
		return data;
	}

	public String sendJsonArray(JSONArray requestData, String url)
			throws VirtualNeighborException {

		InputStream is = null;
		String data = "";
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			/*
			 * httpPost.addHeader("X_REST_USERNAME",
			 * UtilityConstants.X_REST_USERNAME);
			 * httpPost.addHeader("X_REST_PASSWORD",
			 * UtilityConstants.X_REST_PASSWORD);
			 */

			if (null != requestData) {
				StringEntity se = new StringEntity(requestData.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				httpPost.setEntity(se);
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 204) {
				data = null;
			}
			if (statusCode == 200) {
				is = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				data = sb.toString();

				is.close();
			}

		} catch (Exception e) {
			throw new VirtualNeighborException(e.getMessage());
		}
		return data;
	}

	public String uploadImageOnServer(String path, String urladdress) {

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		String response = null;
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		int serverResponseCode = 0;
		File sourceFile = new File(path);

		try {

			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(urladdress);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("uploaded_file", path);
			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
					+ path + "\"" + lineEnd);
			dos.writeBytes(lineEnd);
			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			serverResponseCode = conn.getResponseCode();
			if (serverResponseCode == 200) {
				response = conn.getResponseMessage();
			} else {
				response = conn.getResponseMessage();
			}
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/*public static String uploadMultipleImageOnServer(List<String> path,
			String urladdress, JSONArray requestData) {
		System.out.println("urladdress is " + urladdress);
		HttpPost httppost = new HttpPost(urladdress);
		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		String data = null;
		InputStream is;
		HttpResponse response;

		try {
			if (path.size() > 0)
				mpEntity.addPart("image1", new FileBody(new File(path.get(0))));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			if (null != requestData) {
				StringEntity se = new StringEntity(requestData.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				httppost.setEntity(se);
			}

			DefaultHttpClient httpClient = new DefaultHttpClient();
			httppost.setEntity(mpEntity);
			response = httpClient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 204) {
				data = null;
			}
			if (statusCode == 200) {
				is = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				data = sb.toString();

				is.close();
			}
			System.out.println("response is " + data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}*/
	
	public String uploadMultipleImageOnServer(String urladdress,MultipartEntity mpEntity) {
		HttpPost httppost = new HttpPost(urladdress);
//		MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		String data = null;
		InputStream is;
		HttpResponse response;
		
		
		
		
		
		/*if (null != requestData) {
			StringEntity se;
			try {
				se = new StringEntity(requestData.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				httppost.setEntity(se);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		
		try{

//			mpEntity.addPart("data", new StringBody(nameValuePairs.toString()));
			
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			httppost.setEntity(mpEntity);
//			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpClient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 204) {
				data = null;
			}
			if (statusCode == 200) {
				is = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				data = sb.toString();

				is.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	
	public static String uploadFile(String filePath, String fileName,String url) {
		InputStream is = null;
		String res = "";
		  InputStream inputStream;
		  try {
		    inputStream = new FileInputStream(new File(filePath));
		    byte[] data;
		    try {
		      data = IOUtils.toByteArray(inputStream);
		 
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost(url);
		 
		      InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), fileName);
		      MultipartEntity multipartEntity = new MultipartEntity();
		      multipartEntity.addPart("file", inputStreamBody);
		      httpPost.setEntity(multipartEntity);
		 
		      HttpResponse httpResponse = httpClient.execute(httpPost);
		      HttpEntity httpEntity = httpResponse.getEntity();
		      // Handle response back from script.
		      if(httpResponse != null) {
		    	  is = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"));
					StringBuilder sb = new StringBuilder();
					String line = "";
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					res = sb.toString();

					is.close();
		      } else { // Error, no response.
		 
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  } catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		  }
		  return res;
		}

	/*public static int uploadFile(String sourceFileUri, String upLoadServerUri) {

		InputStream is;
		
		int serverResponseCode = 0;

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;

		DataOutputStream dos = null;

		String lineEnd = "\r\n";

		String twoHyphens = "--";

		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;

		byte[] buffer;

		int maxBufferSize = 1 * 1024 * 1024;

		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			return 0;

		}

		else

		{

			try {

				// open a URL connection to the Servlet

				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);

				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL

				conn = (HttpURLConnection) url.openConnection();

				conn.setDoInput(true); // Allow Inputs

				conn.setDoOutput(true); // Allow Outputs

				conn.setUseCaches(false); // Don't use a Cached Copy

				conn.setRequestMethod("POST");

				conn.setRequestProperty("Connection", "Keep-Alive");

				conn.setRequestProperty("ENCTYPE", "multipart/form-data");

				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);

				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);

				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""

						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size

				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);

				buffer = new byte[bufferSize];

				// read file and write it into form...

				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);

					bytesAvailable = fileInputStream.available();

					bufferSize = Math.min(bytesAvailable, maxBufferSize);

					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...

				dos.writeBytes(lineEnd);

				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)

				serverResponseCode = conn.getResponseCode();

				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "

				+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {
				}

				// close the streams //

				fileInputStream.close();

				dos.flush();

				dos.close();

			} catch (MalformedURLException ex) {

				ex.printStackTrace();

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

			} catch (Exception e) {
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);

			}

			return serverResponseCode;

		} // End else block

	}*/

}
