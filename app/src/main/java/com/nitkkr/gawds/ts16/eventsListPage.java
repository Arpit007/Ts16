package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

		eventListView.setAdapter(new eventItemAdapter(list, getApplicationContext(), true));

		eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent i=new Intent(getBaseContext(),eventDetail.class);
				i.putExtra(getBaseContext().getString(R.string.EventID),list.get(position).eventID);
				i.putExtra(getBaseContext().getString(R.string.TabID),0);

				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getBaseContext().startActivity(i);
			}
		});
	}
}