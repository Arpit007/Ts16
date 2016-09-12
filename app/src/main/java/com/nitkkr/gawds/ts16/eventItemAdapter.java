package com.nitkkr.gawds.ts16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Home Laptop on 05-Sep-16.
 */
public class eventItemAdapter extends BaseAdapter
{
	private ArrayList<eventData> dataList;
	Context context;

	public eventItemAdapter(ArrayList<eventData> dataList, Context context)
	{
		this.context=context;
		this.dataList=dataList;
	}

	@Override
	public int getCount()
	{
		return dataList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater inflater=((Activity)context).getLayoutInflater();
			convertView=inflater.inflate(R.layout.event_recycler_item,parent,false);
		}
		((TextView)convertView.findViewById(R.id.event_name)).setText(dataList.get(position).eventName);
		((CheckBox)convertView.findViewById(R.id.starrred)).setChecked(dataList.get(position).isBookmarked());
		((TextView)convertView.findViewById(R.id.recycler_event_date)).setText(dataList.get(position).Day);
		((TextView)convertView.findViewById(R.id.recycler_event_time)).setText(dataList.get(position).Time);
		((CheckBox) convertView.findViewById(R.id.starrred) ).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				dataList.get(position).updateBookmark(isChecked);
			}
		});
		return convertView;
	}
}