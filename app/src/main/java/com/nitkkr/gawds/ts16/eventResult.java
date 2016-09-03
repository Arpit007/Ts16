package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class eventResult extends AppCompatActivity
{
	ArrayList<eventData> datas;
	int ScrollPosition=0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_result);
		datas=new ArrayList<>();

		setTitle("Results");

		for(eventData data:eventDatabase.Database.eventDataList)
			if(data.isResultDeclared())
				datas.add(data);

		if(datas.size()==0)
		{
			findViewById(R.id.NoResult).setVisibility(View.VISIBLE);
		}
		else
		{
			findViewById(R.id.NoResult).setVisibility(View.INVISIBLE);
			ListView listView=(ListView)findViewById(R.id.resultList);

			ArrayList<String> eventName=new ArrayList<>();
			for(eventData data:datas)
				eventName.add(data.eventName);

			listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,eventName));
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					int EventID=datas.get(position).eventID;
					Intent intent=new Intent(getBaseContext(),eventDetail.class);
					intent.putExtra(getString(R.string.EventID),EventID);
					intent.putExtra(getString(R.string.TabID),2);
					startActivity(intent);
				}
			});
		}
	}

}
