package com.nitkkr.gawds.ts16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class starredEvents extends AppCompatActivity
{

	ArrayList<eventData> eventDataList=null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starred_events);
		setTitle("Starred Events");
		bookMarkChanged();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		bookMarkChanged();
	}

	public void bookMarkChanged()
	{
		if(eventDataList!=null)
			eventDataList.clear();

		eventDataList=new ArrayList<>();

		dbHelper helper=new dbHelper(this);
		for(eventData data:helper.ReadDatabaseEvents(helper.getReadableDatabase(),0))
		{
			if(data.isBookmarked())
				eventDataList.add(data);
		}

		helper.close();

		if(eventDataList.size()==0)
		{
			findViewById(R.id.NoStar).setVisibility(View.VISIBLE);
		}
		else
		{
			findViewById(R.id.NoStar).setVisibility(View.INVISIBLE);
			ListView listView=(ListView)findViewById(R.id.starList);
			eventItemAdapter adapter=new eventItemAdapter(eventDataList, this, true);
			listView.setAdapter(adapter);
		}
	}
}

