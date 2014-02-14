package com.skynet.pushbaidu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.common.logging.Log;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.headray.framework.services.function.StringToolKit;
import com.skynet.pushbaidu.adapter.ListViewAdapter;
import com.skynet.pushbaidu.application.PushApplication;
import com.skynet.pushbaidu.push.Utils;
import com.skynet.utils.HttpRunnable;
import com.skynet.utils.JSonMessageHandler;
import com.skynet.utils.LogUtils;

public class MainActivity extends Activity implements OnScrollListener
{
	public static final String TAG = MainActivity.class.getSimpleName();

	List<Map<String, Object>> msgs = new ArrayList<Map<String, Object>>();

	private String _appid = null;

	private String _userid = null;

	private String _channelid = null;
	
	private String _loginname = null;

	private int count = 0;

	private static int msg_num = 100;
	
	private int reg = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, TAG + " onCreate.");
		init_baidu_push_withapikey();
		get_session();
		init_tab_friend();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void get_session()
	{
		_loginname = (String)((PushApplication)getApplication()).getData().get("_loginname");
	}

	protected void init_baidu_push_withapikey()
	{
		PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, Utils.getMetaValue(MainActivity.this, "api_key"));
	}

	public void init_tab_friend()
	{
		try
		{
			ListView listview = (ListView) findViewById(R.id.msgview);
			ListViewAdapter adapter = new ListViewAdapter(this, msgs);
			listview.setAdapter(adapter);

			listview.setOnScrollListener(this);

			adapter.notifyDataSetChanged();
		}
		catch (Exception e)
		{
			LogUtils.d(TAG, e);
		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
		String action = intent.getAction();

		if (Utils.ACTION_RESPONSE.equals(action))
		{
			handle_intent_action_response(intent);
		}
		else if (Utils.ACTION_MESSAGE.equals(action))
		{
			handle_intent_action_message(intent);
		}
		else
		{
			Log.i(TAG, "Activity normally start!");
		}
	}

	public void handle_intent_action_response(Intent intent)
	{
		String method = intent.getStringExtra(Utils.RESPONSE_METHOD);

		if (PushConstants.METHOD_BIND.equals(method))
		{
			String toastStr = "";
			int errorCode = intent.getIntExtra(Utils.RESPONSE_ERRCODE, 0);
			if (errorCode == 0)
			{
				String content = intent.getStringExtra(Utils.RESPONSE_CONTENT);

				try
				{
					JSONObject json = new JSONObject(content);
					JSONObject params = json.getJSONObject("response_params");
					_appid = params.getString("appid");
					_channelid = params.getString("channel_id");
					_userid = params.getString("user_id");
					
					
					if (!StringToolKit.isBlank(_loginname))
					{
						String url = LoginActivity.getHOST_URL() + "/regmobile?loginname=" + _loginname + "&muserid=" + _userid + "&mchannelid=" + _channelid;
						new Thread(new HttpRunnable(url, new JSonMessageHandler()
						{
							@Override
							public void handleMessageJson(JSONObject json)
							{
								handle_regmobile(json);
							}

						})).start();
					}

					toastStr = "Bind Success";
				}
				catch (Exception e)
				{
					LogUtils.e(TAG, e);
				}
			}
			else
			{
				toastStr = "Bind Fail, Error Code: " + errorCode;
				if (errorCode == 30607)
				{
					Log.d("Bind Fail", "update channel token-----!");
				}
			}

			Toast.makeText(this, toastStr, Toast.LENGTH_LONG).show();
		}
	}

	public void handle_intent_action_message(Intent intent)
	{
		String message = intent.getStringExtra(Utils.EXTRA_MESSAGE);
		String summary = "Receive message from server:\n\t";

		Log.d(TAG, summary + message);

		try
		{
			JSONObject json = new JSONObject(message);
			handler_intent.handleMessageJson(json);
		}
		catch (Exception e)
		{
			LogUtils.e(TAG, e);
		}

		Log.d(TAG, message);
	}

	JSonMessageHandler handler_intent = new JSonMessageHandler()
	{
		@Override
		public void handleMessageJson(JSONObject json)
		{
			try
			{
				handle_push_test(json);
			}
			catch (Exception e)
			{
				LogUtils.e(TAG, e);
			}
		}
	};

	public void handle_push_test(JSONObject json)
	{
		try
		{
			String msg_content = json.getString("msg_content");
			count++;

			TextView tv = (TextView) findViewById(R.id.showcount);
			tv.setText("共收到 " + count + " 条信息。");

			Map<String, Object> msg = new HashMap<String, Object>();

			msg.put("msg", msg_content);
			msg.put("num", Integer.valueOf(count));

			if (msgs.size() >= msg_num)
			{
				msgs.remove(0);
			}

			msgs.add(msg);

			ListView listview = (ListView) findViewById(R.id.msgview);
			((ListViewAdapter) listview.getAdapter()).notifyDataSetChanged();

			Log.d(TAG, msg_content.toString());
		}
		catch (Exception e)
		{
			LogUtils.e(TAG, e);
		}
	}
	
	public void handle_regmobile(JSONObject json)
	{
		try
		{
			String state = json.getString("state");
			reg = 1;
			Log.d(TAG, "regmobile success.");
		}
		catch (Exception e)
		{
			LogUtils.e(TAG, e);
		}
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

}
