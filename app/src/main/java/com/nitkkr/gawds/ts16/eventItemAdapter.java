package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class eventItemAdapter extends BaseAdapter implements eventData.eventDataListener
{
	public interface BookmarkListener
	{
		void BookmarkChanged();
	}

	private ArrayList<eventData> dataList=null;
	Context context;
	boolean showBookmark, forced=false;
	BookmarkListener listener;

	public eventItemAdapter(ArrayList<eventData> dataList, Context context, boolean showBookmark)
	{
		this.showBookmark=showBookmark;
		this.context=context;
		this.dataList=dataList;
	}


	public void setForcedBookmark()
	{
		forced=true;
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
			LayoutInflater inflater=LayoutInflater.from(context);
			convertView=inflater.inflate(R.layout.event_item_layout,parent,false);
		}

		final eventData data=dataList.get(position);

		((TextView)convertView.findViewById(R.id.event_name)).setText(data.eventName);

		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date=simpleDateFormat.parse(data.Day+" "+data.Time);
			simpleDateFormat.applyPattern("hh:mm a");
			(( TextView)convertView.findViewById(R.id.recycler_event_time)).setText(simpleDateFormat.format(date));
			simpleDateFormat.applyPattern("dd MMM yyyy");
			(( TextView)convertView.findViewById(R.id.recycler_event_date)).setText(simpleDateFormat.format(date));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		eventStatusListener.setEventStatusCode(data,context);

		if(forced || data.isBookmarked() || (showBookmark && (data.code== eventStatusListener.StatusCode.None || data.code== eventStatusListener.StatusCode.Upcoming)))
		{
			convertView.findViewById(R.id.starrred).setVisibility(View.VISIBLE);
		}
		else
			convertView.findViewById(R.id.starrred).setVisibility(View.INVISIBLE);

		((CheckBox) convertView.findViewById(R.id.starrred) ).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{

				boolean check=data.updateBookmark(context,isChecked);
				if(check && listener!=null)
					listener.BookmarkChanged();
			}
		});
		((CheckBox)convertView.findViewById(R.id.starrred)).setChecked(data.isBookmarked());

		Typeface font = Typeface.createFromAsset(context.getAssets(),
				"fonts/Font1.ttf");
		(( TextView)convertView.findViewById(R.id.event_name)).setTypeface(font);

		font = Typeface.createFromAsset(context.getAssets(),
				"fonts/Font2.ttf");
		(( TextView)convertView.findViewById(R.id.recycler_event_date)).setTypeface(font);
		(( TextView)convertView.findViewById(R.id.recycler_event_time)).setTypeface(font);

		TypedArray array=context.getResources().obtainTypedArray(R.array.ModernColor);

		Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), R.drawable.bullet_icon, null);
		DrawableCompat.setTint(DrawableCompat.wrap(drawable), array.getColor(position%array.length(),0));
		((ImageView)convertView.findViewById(R.id.eventBullet)).setImageDrawable(drawable);

		if(showBookmark)
			convertView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent i=new Intent(context,eventDetail.class);
					i.putExtra(context.getString(R.string.EventID),data.eventID);
					i.putExtra(context.getString(R.string.TabID),0);

					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				}
			});

		return convertView;
	}

	@Override
	public void eventUpdated(eventData event)
	{
		try
		{
			if(listener!=null)
				listener.BookmarkChanged();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
