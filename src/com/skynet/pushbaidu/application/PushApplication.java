package com.skynet.pushbaidu.application;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

public class PushApplication extends Application
{
	private Map<String, Object> data = new HashMap<String, Object>();

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	public Map<String, Object> getData()
	{
		return data;
	}

	public void setData(Map<String, Object> data)
	{
		this.data = data;
	}
	
	
}