package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class eventsListPage extends AppCompatActivity
{
	ListView eventListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_list_page);

		Intent i=getIntent();
		Bundle b=i.getExtras();
		int CategoryId=b.getInt(getString(R.string.CategoryID));
		eventListView =(ListView) findViewById(R.id.eventList);
		dbHelper helper=new dbHelper(this);
		final ArrayList<eventData> list=helper.ReadDatabaseEvents(helper.getReadableDatabase(),CategoryId);
		helper.close();

		String title=b.getString("CategoryName");
		if(!title.equals("All Events"))
			title+=" Events";

		setTitle(title);
		if(list.size()==0)
		{
			findViewById(R.id.noEvent).setVisibility(View.VISIBLE);
		}
		else
		{
			findViewById(R.id.noEvent).setVisibility(View.INVISIBLE);
			eventListView.setAdapter(new eventItemAdapter(list, getApplicationContext(), true));
		}
	}
}