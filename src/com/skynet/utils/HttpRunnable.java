package com.skynet.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpRunnable implements Runnable
{
	static String TAG = "HttpRunnable";

	String key;

	String url;

	Handler handler;

	public HttpRunnable(String url, Handler handler)
	{
		super();
		this.url = url;
		this.handler = handler;
		LogUtils.d(TAG, url);
	}

	public HttpRunnable(String key, String url, Handler handler)
	{
		super();
		this.key = key;
		this.url = url;
		this.handler = handler;
	}

	public void run()
	{
		Message msg = new Message();
		Bundle data = new Bundle();
		try
		{
			String json_value = HttpUtils.getInputFromHttpConnection(url);
			data.putString("key", key);
			data.putString("value", json_value);
		}
		catch (Exception e)
		{
			LogUtils.d(TAG, e);
		}
		finally
		{
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

}
