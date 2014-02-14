package com.skynet.pushbaidu;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.headray.framework.services.function.StringToolKit;
import com.skynet.pushbaidu.application.PushApplication;
import com.skynet.pushbaidu.push.Utils;
import com.skynet.utils.HttpRunnable;
import com.skynet.utils.JSonMessageHandler;
import com.skynet.utils.LogUtils;

public class LoginActivity extends Activity
{
	public static final String TAG = LoginActivity.class.getSimpleName();

	protected static String HOST_URL;

	private String _appid = null;

	private String _userid = null;

	private String _channelid = null;

	private String _loginname = null;

	// 记录当前活动Activity;
	public static String _currentactivity = "";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init_service_hosts();
		init_weather();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		LoginActivity._currentactivity = "MainActivity";
	}

	public void init_service_hosts()
	{
		RadioGroup group = (RadioGroup) this.findViewById(R.id.login_rg_servergroup);
		group.check(R.id.login_rb_server01);

		// 绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int position)
			{
				init_service_host(group);
			}
		});
		init_service_host(group);
	}

	private void init_service_host(RadioGroup group)
	{
		int radioButtonId = group.getCheckedRadioButtonId();
		// 根据ID获取RadioButton的实例
		if (radioButtonId == R.id.login_rb_server01)
		{
			HOST_URL = "http://192.168.1.144:1337";
		}
		else
		{
			HOST_URL = "http://homegarden.jd-app.com";
		}
	}

	protected void init_service_host()
	{
		HOST_URL = Utils.getMetaValue(LoginActivity.this, "service_host_url");
	}

	public void init_weather()
	{
		updateAnimtation("heavyrain");
	}

	private void updateAnimtation(String iconDescription)
	{
	}

	public void login(View view)
	{
		String loginname = ((EditText) findViewById(R.id.login_et_loginname)).getText().toString();

		if (StringToolKit.isBlank(loginname))
		{
			return;
		}
		
		_loginname = loginname;

		((PushApplication)getApplication()).getData().put("_loginname", _loginname);
		
		go_main(view);
	}

	public void go_main(View view)
	{
		go_main();
	}

	public void go_main()
	{
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
	}

	public static String getHOST_URL()
	{
		return HOST_URL;
	}

}
