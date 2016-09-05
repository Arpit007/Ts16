package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class starredEvents extends AppCompatActivity
{

	ArrayList<eventData> eventDataList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starred_events);

		setTitle("Starred");

		eventDataList=new ArrayList<>();
		for(eventData data:eventDatabase.Database.getEventList())
		{
			if(data.isBookmarked())
				eventDataList.add(data);
		}

		if(eventDataList.size()==0)
		{
			findViewById(R.id.NoStar).setVisibility(View.VISIBLE);
		}
		else
		{
			findViewById(R.id.NoStar).setVisibility(View.INVISIBLE);
			ListView listView=(ListView)findViewById(R.id.starList);

			listView.setAdapter(new eventItemAdapter(eventDataList, this));

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					int EventID=eventDataList.get(position).eventID;
					Intent intent=new Intent(getBaseContext(),eventDetail.class);
					intent.putExtra(getString(R.string.EventID),EventID);
					intent.putExtra(getString(R.string.TabID),0);
					startActivity(intent);
				}
			});
		}
	}
}

