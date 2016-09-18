package com.nitkkr.gawds.ts16;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class upcomingFragment extends Fragment
{
	private ArrayList<eventData> eventDataList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
		dbHelper helper=new dbHelper(getContext());
		eventDataList=helper.GetUpcomingEvents(helper.getReadableDatabase());
		helper.close();
		if (eventDataList.size() == 0)
		{
			view.findViewById(R.id.NoUpcoming).setVisibility(View.VISIBLE);
		}
		else
		{
			view.findViewById(R.id.NoUpcoming).setVisibility(View.INVISIBLE);
			ListView listView = (ListView) view.findViewById(R.id.upcomingList);

			listView.setAdapter(new eventItemAdapter(eventDataList, getContext(), true));

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					int EventID = eventDataList.get(position).eventID;
					Intent intent = new Intent(getContext(), eventDetail.class);
					intent.putExtra(getString(R.string.EventID), EventID);
					intent.putExtra(getString(R.string.TabID), 0);
					startActivity(intent);
				}
			});
		}
		return view;
	}
}
