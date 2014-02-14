package com.skynet.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class HttpUtils
{
	static String TAG = "HttpUtils";

	public static String getInputFromHttpConnection(String url)
	{
		String text = null;
		try
		{
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("POST");
			InputStream in = (InputStream) conn.getContent();
			text = readInputStream(in, "UTF-8");
			in.close();
			conn.disconnect();
		}
		catch (Exception e)
		{
			Log.d(TAG, e.getMessage());
		}

		return text;
	}

	public static JSONObject getJSONFromHttpConnection(String url)
	{
		JSONObject jObj = null;
		try
		{
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("POST");
			InputStream in = (InputStream) conn.getContent();
			String json = readInputStream(in, "UTF-8");
			String json1 = json;
			conn.disconnect();
			System.out.println(json);

			if (json != null && json.startsWith("\ufeff"))
			{
				json = json.substring(1);
			}

			try
			{
				json1 = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1);
			}
			catch (Exception e)
			{
				json1 = json;
			}

			jObj = new JSONObject(json1);
			System.out.println("json:" + json1);
			System.out.println("jObj:" + jObj);

		}
		catch (Exception e)
		{
			System.out.println(e);
		}

		return jObj;
	}

	public static String readInputStream(InputStream inSream, String charsetName) throws Exception
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1)
		{
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}

	public static InputStream getImageViewInputStream(String urlpath) throws IOException
	{
		InputStream inputStream = null;
		URL url = new URL(urlpath);
		if (url != null)
		{
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(3000);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoInput(true);
			int response_code = httpURLConnection.getResponseCode();
			if (response_code == 200)
			{
				inputStream = httpURLConnection.getInputStream();
			}
		}
		return inputStream;
	}

	public static Drawable getDrawable(String urlpath)
	{
		Drawable d = null;
		try
		{
			URL url = new URL(urlpath);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream in;
			in = conn.getInputStream();
			d = Drawable.createFromStream(in, "background.jpg");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return d;
	}

}
