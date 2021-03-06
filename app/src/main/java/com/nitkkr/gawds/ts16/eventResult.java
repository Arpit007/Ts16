package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class eventResult extends AppCompatActivity
{
	ArrayList<eventData> resultList=null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_result);

		if(resultList!= null)
			resultList.clear();

		resultList=new ArrayList<>();

		setTitle("Results");

		dbHelper helper=new dbHelper(this);
		for(eventData data:helper.ReadDatabaseEvents(helper.getReadableDatabase(),0))
			if(data.isResultDeclared())
				resultList.add(data);

		helper.close();
		Collections.sort(resultList, new Comparator<eventData>() {
			@Override
			public int compare(eventData eventData, eventData t1) {
				return eventData.eventName.compareToIgnoreCase(t1.eventName);
			}
		});

		if(resultList.size()==0)
		{
			findViewById(R.id.NoResult).setVisibility(View.VISIBLE);
			findViewById(R.id.resultList).setVisibility(View.INVISIBLE);
		}
		else
		{
			findViewById(R.id.NoResult).setVisibility(View.INVISIBLE);
			findViewById(R.id.resultList).setVisibility(View.VISIBLE);

			ListView listView=(ListView)findViewById(R.id.resultList);

			listView.setAdapter(new eventResultAdapter(resultList,getBaseContext()));
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					Intent i=new Intent(getBaseContext(),eventDetail.class);
					i.putExtra(getBaseContext().getString(R.string.EventID),resultList.get(position).eventID);
					i.putExtra(getBaseContext().getString(R.string.TabID),2);

					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getBaseContext().startActivity(i);
				}
			});
		}
	}

	class eventResultAdapter extends BaseAdapter
	{
		ArrayList<eventData> list;
		Context context;

		eventResultAdapter(ArrayList<eventData> list, Context context)
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
				LayoutInflater inflater=LayoutInflater.from(context);
				convertView=inflater.inflate(R.layout.result_item_layout,parent,false);
			}

			final eventData data=list.get(position);
			((TextView)convertView.findViewById(R.id.resultName)).setText(data.eventName);


			ImageView view=(ImageView)convertView.findViewById(R.id.resultBullet);

			TypedArray array=context.getResources().obtainTypedArray(R.array.ModernColor);

			Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), R.drawable.bullet_icon, null);
			DrawableCompat.setTint(DrawableCompat.wrap(drawable), array.getColor(position%array.length(),0));
			view.setImageDrawable(drawable);
			array.recycle();

			Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Font1.ttf");
			(( TextView)convertView.findViewById(R.id.resultName)).setTypeface(font);

			return convertView;
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		int scrollPosition=savedInstanceState.getInt("Scroll",0);
		findViewById(R.id.resultList).setVerticalScrollbarPosition(scrollPosition);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		int scrollPosition=findViewById(R.id.resultList).getVerticalScrollbarPosition();
		outState.putInt("Scroll",scrollPosition);
	}
}