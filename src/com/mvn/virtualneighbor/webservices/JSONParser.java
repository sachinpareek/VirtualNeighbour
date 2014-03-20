package com.mvn.virtualneighbor.webservices;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.mvn.virtualneighbor.exception.VirtualNeighborException;

public class JSONParser {

	public static HttpGet httpGet;
	public static HttpPost httppost;

	static String charset = "UTF-8";
	static String param1 = "value1";
	static String param2 = "value2";

	public static String getJSONFromUrl(String url) throws VirtualNeighborException,
			IOException {
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
	
	public static String sendJsonArray(JSONArray requestData, String url)
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

	public static String uploadImageOnServer(String path, String urladdress) {

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
				response = "success";
			} else {
				response = "fail";
			}
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String uploadMultipleImageOnServer(List<String> path,
			String urladdress,JSONObject requestData) {
		System.out.println("urladdress is "+urladdress);
		HttpPost httppost = new HttpPost(urladdress);
		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		String data = null;
		InputStream is;
		HttpResponse response;

		try {
			if (path.size() > 0)
				mpEntity.addPart("image1", new FileBody(new File(path.get(0))));
			if (path.size() > 1)
				mpEntity.addPart("image2", new FileBody(new File(path.get(1))));
			if (path.size() > 2)
				mpEntity.addPart("image3", new FileBody(new File(path.get(2))));
			if (path.size() > 3)
				mpEntity.addPart("image4", new FileBody(new File(path.get(3))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
		
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
			System.out.println("response is "+data );

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
