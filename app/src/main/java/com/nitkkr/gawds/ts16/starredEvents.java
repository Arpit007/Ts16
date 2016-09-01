package com.nitkkr.gawds.ts16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class starredEvents extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starred_events);

		setTitle("Starred");

		ArrayList<eventData> eventDatas=new ArrayList<>();
		for(eventData data:eventDatabase.Database.eventDataList)
		{
			if(data.bookmark == 1)
				eventDatas.add(data);
		}
	}
}
