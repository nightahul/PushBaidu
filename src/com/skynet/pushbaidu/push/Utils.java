package com.skynet.pushbaidu.push;

import com.baidu.android.common.logging.Log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class Utils
{
	public static final String TAG = "Utils";

	public static final String RESPONSE_METHOD = "method";

	public static final String RESPONSE_CONTENT = "content";

	public static final String RESPONSE_ERRCODE = "errcode";

	public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";

	public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";

	public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";

	protected static final String EXTRA_ACCESS_TOKEN = "access_token";

	public static final String EXTRA_MESSAGE = "message";

	// 获取AppKey
	public static String getMetaValue(Context context, String metaKey)
	{
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null)
		{
			return null;
		}
		try
		{
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if (null != ai)
			{
				metaData = ai.metaData;
			}
			if (null != metaData)
			{
				apiKey = metaData.getString(metaKey);
			}
		}
		catch (NameNotFoundException e)
		{
			Log.d(TAG, e.getMessage());
		}
		return apiKey;
	}

}
