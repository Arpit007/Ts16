package com.nitkkr.gawds.ts16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class organizerPage extends AppCompatActivity
{
	private ArrayList<organizerData> organizerList;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		organizerList=new ArrayList<>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_page);
		setUpOrganizerData();
		setTitle("Organizers");
		(( ListView)findViewById(R.id.organizerList)).setAdapter(new organizerAdapter(organizerList, this));
	}

	public void setUpOrganizerData()
	{
		String Data="Failed to Fetch Data";
		try
		{
			InputStream inputStream = getAssets().open(getString(R.string.organizerFileName));
			int size = inputStream.available();
			byte[] buffer = new byte[size];
			inputStream.read(buffer);
			inputStream.close();
			Data = new String(buffer, "UTF-8");
			JSONObject Root=new JSONObject(Data);
			JSONArray array=Root.getJSONArray("Organizers");

			for(int i=0; i < array.length(); i++){
				JSONObject item = array.getJSONObject(i);

				organizerData data=new organizerData();
				data.Name=item.optString("Name").toString();
				data.Phone=item.optString("Phone").toString();
				data.Detail=item.optString("Detail").toString();
				organizerList.add(data);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private class organizerData
	{
		public String Name;
		public String Detail;
		public String Phone;
	}

	class organizerAdapter extends BaseAdapter
	{
		private ArrayList<organizerData> dataList;
		Context context;

		public organizerAdapter(ArrayList<organizerData> dataList, Context context)
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
				convertView=inflater.inflate(R.layout.organizer_item,parent,false);
			}
			((TextView)convertView.findViewById(R.id.organizerName)).setText(dataList.get(position).Name);
			((TextView)convertView.findViewById(R.id.organizerDetail)).setText(dataList.get(position).Detail);
			convertView.findViewById(R.id.organizerCall).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+dataList.get(position).Phone));
					startActivity(intent);
				}
			});
			return convertView;
		}
	}


}
