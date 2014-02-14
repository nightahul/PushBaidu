package com.skynet.utils;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class JSonMessageHandler extends Handler
{
	static String TAG = JSonMessageHandler.class.getSimpleName();

	protected View view;

	public JSonMessageHandler()
	{
		super();
	}

	public JSonMessageHandler(View view)
	{
		super();
		this.view = view;
	}

	public View getView()
	{
		return view;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	@Override
	public void handleMessage(Message msg)
	{
		try
		{
			super.handleMessage(msg);
			Bundle data = msg.getData();

			String json_value = data.getString("value");
			JSONObject json = new JSONObject(json_value);
			handleMessageJson(json);
		}
		catch (Exception e)
		{
			LogUtils.e(TAG, e);
		}
	}

	public void handleMessageJson(JSONObject json)
	{

	}
}
