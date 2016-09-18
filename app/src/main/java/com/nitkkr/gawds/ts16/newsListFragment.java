package com.nitkkr.gawds.ts16;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class newsListFragment extends Fragment
{
	ArrayList<MessageDbHelper.MessageData> MessageDataList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.fragment_news_list, container, false);
		MessageDbHelper dbHelper=new MessageDbHelper(getContext());
		//==============================
		MessageDataList=dbHelper.ReadDatabaseMessage(dbHelper.getReadableDatabase());
		dbHelper.close();
		if (MessageDataList.size() == 0)
		{
			view.findViewById(R.id.NoNews).setVisibility(View.VISIBLE);
		}
		else
		{
			view.findViewById(R.id.NoNews).setVisibility(View.INVISIBLE);
			ListView listView = (ListView) view.findViewById(R.id.NewsList);
			Log.d("Mesage count", String.valueOf(MessageDataList.size()));
			listView.setAdapter(new newsItemAdapter(MessageDataList, getContext()));
		}
		return view;
	}
	class newsItemAdapter extends BaseAdapter
	{

		ArrayList<MessageDbHelper.MessageData> list;
		Context context;

		newsItemAdapter(ArrayList<MessageDbHelper.MessageData> list, Context context)
		{
			this.context=context;
			this.list=list;
		}

		@Override
		public int getCount()
		{
			return list.size();
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
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				LayoutInflater inflater=((Activity)context).getLayoutInflater();
				convertView=inflater.inflate(R.layout.news_item_layout,parent,false);
			}
			MessageDbHelper.MessageData data=list.get(position);
			(( TextView)convertView.findViewById(R.id.news_Title)).setText(data.Title);
			(( TextView)convertView.findViewById(R.id.news_date)).setText(data.Date);
			(( TextView)convertView.findViewById(R.id.news_content)).setText(data.News);
			return convertView;
		}
	}
}
