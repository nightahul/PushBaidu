package com.skynet.pushbaidu.push;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.skynet.pushbaidu.MainActivity;
import com.skynet.utils.LogUtils;

public class PushMessageReceiver extends BroadcastReceiver
{
	public static final String TAG = PushMessageReceiver.class.getSimpleName();

	AlertDialog.Builder builder;

	@Override
	public void onReceive(final Context context, Intent intent)
	{
		Log.d(TAG, ">>> Receive intent: \r\n" + intent);

		Log.d(TAG, intent.getAction());

		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE))
		{
			handle_action_message(context, intent);
		}
		else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE))
		{
			handle_action_receive(context, intent);
		}
		else if (intent.getAction().equals(PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK))
		{
			handle_action_receiver_notification(context, intent);
		}
	}

	// 处理消息
	public void handle_action_message(Context context, Intent intent)
	{
		// 获取消息内容
		String message = intent.getExtras().getString(PushConstants.EXTRA_PUSH_MESSAGE_STRING);

		Log.i(TAG, message);

		Intent responseIntent = null;
		responseIntent = new Intent(Utils.ACTION_MESSAGE);
		responseIntent.putExtra(Utils.EXTRA_MESSAGE, message);
		responseIntent.setClass(context, MainActivity.class);
		responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(responseIntent);
	}

	// 处理通知
	public void handle_action_receiver_notification(Context context, Intent intent)
	{
		// 后期添加
		Log.d(TAG, "intent=" + intent.toUri(0));
		Log.d(TAG, "EXTRA_EXTRA = " + intent.getStringExtra(PushConstants.EXTRA_EXTRA));

		Intent aIntent = new Intent();
		aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		aIntent.setClass(context, MainActivity.class);
		String title = intent.getStringExtra(PushConstants.EXTRA_NOTIFICATION_TITLE);
		aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_TITLE, title);
		String content = intent.getStringExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT);
		aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT, content);

		context.startActivity(aIntent);

	}

	// 获取消息内容
	// 处理绑定等方法的返回数据
	// PushManager.startWork()的返回值通过PushConstants.METHOD_BIND获得
	public void handle_action_receive(Context context, Intent intent)
	{
		// 获取方法
		final String method = intent.getStringExtra(PushConstants.EXTRA_METHOD);
		// 方法返回错误码。若绑定返回错误（非0），则应用将不能正常接收消息。
		// 绑定失败的原因有多种，如网络原因，或access token过期。
		// 请不要在出错时进行简单的startWork调用，这有可能导致死循环。
		// 可以通过限制重试次数，或者在其他时机重新调用来解决。
		int errorCode = intent.getIntExtra(PushConstants.EXTRA_ERROR_CODE, PushConstants.ERROR_SUCCESS);
		String content = "";
		if (intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT) != null)
		{
			// 返回内容
			content = new String(intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));
		}

		// 用户在此自定义处理消息,以下代码为demo界面展示用
		Log.d(TAG, "onMessage: method : " + method);
		Log.d(TAG, "onMessage: result : " + errorCode);
		Log.d(TAG, "onMessage: content : " + content);
		Toast.makeText(context, "method : " + method + "\n result: " + errorCode + "\n content = " + content, Toast.LENGTH_SHORT).show();

		// 可选。通知用户点击事件处理
		Intent responseIntent = null;
		responseIntent = new Intent(Utils.ACTION_RESPONSE);
		responseIntent.putExtra(Utils.RESPONSE_METHOD, method);
		responseIntent.putExtra(Utils.RESPONSE_ERRCODE, errorCode);
		responseIntent.putExtra(Utils.RESPONSE_CONTENT, content);
		responseIntent.setClass(context, MainActivity.class);
		responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(responseIntent);

	}

}
