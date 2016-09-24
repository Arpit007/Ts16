package com.nitkkr.gawds.ts16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class sponsors extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sponsors);

		setTitle("Sponsors");

		GridView gridView=(GridView)findViewById(R.id.sponsorGrid);
		gridView.setAdapter(new sponsorListAdapter(this));

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				sponsorListAdapter adapter=(sponsorListAdapter)parent.getAdapter();
				String url = adapter.sponsorList.get(position).url;
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class sponsorListAdapter extends BaseAdapter
	{
		public class sponsorData
		{
			public int imageId;
			public String label;
			public String url;

			public sponsorData(int ImageId, String Label, String Url)
			{
				imageId=ImageId;
				label=Label;
				url=Url;
			}
		}

		Context context;
		ArrayList<sponsorData> sponsorList=null;

		public sponsorListAdapter(Context c)
		{
			context=c;
			if(sponsorList==null)
				AppendData();
		}

		@Override
		public int getCount()
		{
			return sponsorList.size();
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
				LayoutInflater inflater=(( Activity)context).getLayoutInflater();
				convertView=inflater.inflate(R.layout.sponsor_item,parent,false);
			}

			if(sponsorList.get(position).imageId!=-1)
				((ImageView)convertView.findViewById(R.id.sponsorItemImage)).setImageResource(sponsorList.get(position).imageId);

			(( TextView)convertView.findViewById(R.id.sponsorItemLabel)).setText(sponsorList.get(position).label);

			Typeface font = Typeface.createFromAsset(context.getAssets(),
					"fonts/Font1.ttf");
			(( TextView)convertView.findViewById(R.id.sponsorItemLabel)).setTypeface(font);

			convertView.setMinimumWidth(parent.getWidth()/2);
			convertView.setMinimumHeight(parent.getWidth()/2);

			return convertView;
		}

		private void AppendData()
		{
			String[] sponsorLabelList=getResources().getStringArray(R.array.sponsorLabelList);
			String[] sponsorUrlList=getResources().getStringArray(R.array.sponsorUrlList);
			TypedArray sponsorImageList = getResources().obtainTypedArray(R.array.sponsorImageIDList);

			sponsorList=new ArrayList<>();

			for(int a=0;a<sponsorLabelList.length;a++)
			{
				sponsorList.add(new sponsorData(sponsorImageList.getResourceId(a,-1),sponsorLabelList[a],sponsorUrlList[a]));
			}
		}
	}
}
