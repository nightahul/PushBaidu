package com.skynet.utils;

import android.util.Log;

public class LogUtils
{
	static String TAG = LogUtils.class.getSimpleName();
	
	public static void i(String tag, Exception e)
	{
		try
		{
			if (e.getMessage() == null)
			{
				Log.i(tag, e.toString());
			}
			else
			{
				Log.i(tag, e.getMessage());
			}
		}
		catch (Exception ex)
		{
			Log.e(TAG, "执行异常.");
		}
	}
	
	public static void d(String tag, Exception e)
	{
		try
		{
			if (e.getMessage() == null)
			{
				Log.d(tag, e.toString());
			}
			else
			{
				Log.d(tag, e.getMessage());
			}
		}
		catch (Exception ex)
		{
			Log.e(TAG, "执行异常.");
		}
	}

	public static void e(String tag, Exception e)
	{
		try
		{
			if (e.getMessage() == null)
			{
				Log.e(tag, e.toString());
			}
			else
			{
				Log.e(tag, e.getMessage());
			}
		}
		catch (Exception ex)
		{
			Log.e(TAG, "执行异常.");
		}
	}
	
	public static void d(String tag, String e)
	{
		try
		{
			if (e==null)
			{
				Log.d(tag, "NULL");
			}
			else
			{
				Log.d(tag, e);
			}
		}
		catch (Exception ex)
		{
			Log.e(TAG, "执行异常.");
		}
	}
}
