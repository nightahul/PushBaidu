package com.skynet.utils;

import android.location.Location;

public class LocationUtils
{
	public static final String TAG = LocationUtils.class.getSimpleName();

	public static void d(Location location)
	{
		if (location != null)
		{
			LogUtils.d(TAG, format(location));
		}
	}

	public static String format(Location location)
	{
		StringBuffer sb = new StringBuffer();
		if (location != null)
		{

			sb.append("你当前的经纬度:");
			sb.append("(");
			sb.append(location.getLatitude());
			sb.append(",");
			sb.append(location.getLongitude());
			sb.append(")");
		}
		return sb.toString();
	}

}
