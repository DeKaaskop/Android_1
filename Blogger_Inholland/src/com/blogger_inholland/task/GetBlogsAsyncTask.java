package com.blogger_inholland.task;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.blogger_inholland.config.BlogConstants;
import com.blogger_inholland.response.BlogListResponse;
import com.google.gson.Gson;

public class GetBlogsAsyncTask extends AsyncTask<Object, Void, BlogListResponse> {

	//FIELDS
	private final static String CHARSET = "UTF-8";
	private final static String BOUNDARY = "12342398523458AAABBDD";
//	private final static String CRLF = "\r\n";

	private BlogListResponse blr;

	//METHODS
	@Override
	protected BlogListResponse doInBackground(Object... params) {
		Gson gson = new Gson();
		try {
			URL url = new URL(BlogConstants.API_URL + "listblogs");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", CHARSET);
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			connection.connect();
			OutputStream output = null;
			try {
				output = connection.getOutputStream();
				output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
			} catch(Exception e) {
				Log.e("Upload", "Error", e);
			} finally {
			}
			InputStream is = new BufferedInputStream(connection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8*1024);
			blr = gson.fromJson(reader, BlogListResponse.class);
			Log.d("Get", "Reponse: " + blr.responseObject);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return blr;
	}
	
	@Override
	protected void onPostExecute(BlogListResponse result) {
		super.onPostExecute(result);
	}
}
