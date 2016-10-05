package com.nitkkr.gawds.ts16;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class newsListFragment extends Fragment
{
	ArrayList<MessageDbHelper.MessageData> MessageDataList=null;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_news_list, container, false);

		UpdateNews();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("Hola!","Here");
		UpdateNews();
	}



	private void UpdateNews()
	{
		if (view==null)
			return;

		if(MessageDbHelper.isUpdated(getContext()) || MessageDataList==null)
		{
			if(MessageDataList!=null)
				MessageDataList.clear();


		}
		MessageDbHelper helper=new MessageDbHelper(getContext());
		MessageDataList = helper.ReadDatabaseMessage(helper.getReadableDatabase());
		helper.close();
		if (MessageDataList.size() == 0)
		{
			view.findViewById(R.id.NoNews).setVisibility(View.VISIBLE);
			Toast.makeText(getContext(),"Please wait until we load data!\nMake Sure you have a working Internet Connection!",Toast.LENGTH_LONG).show();
		}
		else
		{
			view.findViewById(R.id.NoNews).setVisibility(View.INVISIBLE);
			ListView listView = (ListView) view.findViewById(R.id.NewsList);
			Log.d("Mesage count", String.valueOf(MessageDataList.size()));
			listView.setAdapter(new newsItemAdapter(MessageDataList, getContext()));
		}
	}

	@Override
	public void setMenuVisibility(boolean menuVisible)
	{
		super.setMenuVisibility(menuVisible);
		if(menuVisible)
			UpdateNews();
	}

	class newsItemAdapter extends BaseAdapter
	{

		ArrayList<MessageDbHelper.MessageData> list=null;
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

			MessageDbHelper.MessageData data=list.get(getCount()-position-1);

			(( TextView)convertView.findViewById(R.id.news_Title)).setText(data.Title);

			try
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date=simpleDateFormat.parse(data.Date);
				simpleDateFormat.applyPattern("hh:mm a, dd MMM yyyy");
				(( TextView)convertView.findViewById(R.id.news_date)).setText(simpleDateFormat.format(date));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			(( TextView)convertView.findViewById(R.id.news_content)).setText(data.News);

			ImageView view=(ImageView)convertView.findViewById(R.id.newsBullet);

			TypedArray array=getResources().obtainTypedArray(R.array.ModernColor);

			Drawable drawable= ResourcesCompat.getDrawable(getResources(), R.drawable.bullet_icon, null);
			DrawableCompat.setTint(DrawableCompat.wrap(drawable), array.getColor(position%array.length(),0));
			view.setImageDrawable(drawable);

			Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/Font1.ttf");
			(( TextView)convertView.findViewById(R.id.news_Title)).setTypeface(font);

			font = Typeface.createFromAsset(getContext().getAssets(),"fonts/Font2.ttf");
			(( TextView)convertView.findViewById(R.id.news_content)).setTypeface(font);
			(( TextView)convertView.findViewById(R.id.news_date)).setTypeface(font);

			return convertView;
		}
	}
}
