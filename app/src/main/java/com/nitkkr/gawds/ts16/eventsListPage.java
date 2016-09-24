package com.nitkkr.gawds.ts16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class eventsListPage extends AppCompatActivity
{
	ListView eventListView;
	ArrayList<eventData> list=null;
	int CategoryID=0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_list_page);

		Bundle b=getIntent().getExtras();

		String string = getString(R.string.CategoryID);

		if(b!=null)
			CategoryID=b.getInt(string);

		eventListView =(ListView) findViewById(R.id.eventList);

		if(list!= null)
			list.clear();

		dbHelper helper=new dbHelper(this);
		list=helper.ReadDatabaseEvents(helper.getReadableDatabase(),CategoryID);
		helper.close();

		String title=null;
		if(b!=null)
			title=b.getString("CategoryName");

		try
		{
			if (!title.equals("All Events"))
				title += " Events";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

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
