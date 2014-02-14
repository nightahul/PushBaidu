package com.skynet.pushbaidu.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skynet.pushbaidu.R;

public class ListViewAdapter extends BaseAdapter
{
	public List<Map<String, Object>> items;

	private LayoutInflater inflater;

	private float x, ux;

	private Context mContext;

	public ListViewAdapter(Context context, List<Map<String, Object>> items)
	{
		this.items = items;
		this.mContext = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	@Override
	public Object getItem(int position)
	{
		return items.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent)
	{

		ViewHolder viewHolder = null;
		if (view == null)
		{
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.view_msgs, null);
			viewHolder.num = (TextView) view.findViewById(R.id.listfriends_tv_num);
			viewHolder.msg = (TextView) view.findViewById(R.id.listfriends_tv_msg);
			viewHolder.muserid = (TextView) view.findViewById(R.id.listfriends_tv_muserid);
			viewHolder.mchannelid = (TextView) view.findViewById(R.id.listfriends_tv_mchannelid);

			view.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) view.getTag();
		}

		Map<String, Object> item = items.get(position);

		viewHolder.num.setText(String.valueOf(item.get("num")));
		viewHolder.msg.setText((String) item.get("msg"));

		viewHolder.muserid.setText((String) item.get("muserid"));
		viewHolder.mchannelid.setText((String) item.get("mchannelid"));

		return view;
	}

	public void addItem(Map item)
	{
		items.add(item);
	}

	public void addAllItem(List<Map> items)
	{
		for (Map t : items)
		{
			addItem(t);
		}
	}

	final static class ViewHolder
	{
		TextView num;

		TextView msg;

		TextView muserid;

		TextView mchannelid;
	}
}
