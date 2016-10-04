package com.nitkkr.gawds.ts16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class starredEvents extends AppCompatActivity implements eventItemAdapter.BookmarkListener
{
	ArrayList<eventData> eventDataList=null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starred_events);
		setTitle("Starred Events");
		overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
		BookmarkChanged();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		BookmarkChanged();
	}

	@Override
	public void BookmarkChanged()
	{
		if(eventDataList!=null)
			eventDataList.clear();

		eventDataList=new ArrayList<>();

		dbHelper helper=new dbHelper(this);
		for(eventData data:helper.ReadDatabaseEvents(helper.getReadableDatabase(),0))
		{
			if(data.isBookmarked())
			{
				eventDataList.add(data);
			}
		}
//		Collections.sort(eventDataList, new Comparator<eventData>() {
//			@Override
//			public int compare(eventData eventData, eventData t1) {
//				return eventData.eventName.compareToIgnoreCase(t1.eventName);
//			}
//
//		});

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
			adapter.listener=this;
			adapter.setForcedBookmark();
			listView.setAdapter(adapter);
		}
	}
}

