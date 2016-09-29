package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ongoingFragment extends Fragment
{
	private ArrayList<eventData> eventDataList=null;
	View view;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_ongoing, container, false);
		Update();
		return view;
	}

	@Override
	public void setMenuVisibility(boolean menuVisible)
	{
		super.setMenuVisibility(menuVisible);
		if(menuVisible)
			Update();
	}

	public void Update()
	{
		if (view==null)
			return;

		if(eventDataList!= null)
			eventDataList.clear();

		dbHelper DbHelper=new dbHelper(getContext());
		eventDataList=DbHelper.GetOngoingEvents(DbHelper.getReadableDatabase());
		DbHelper.close();

		if (eventDataList.size() == 0)
		{
			view.findViewById(R.id.NoOngoing).setVisibility(View.VISIBLE);
		}
		else
		{
			view.findViewById(R.id.NoOngoing).setVisibility(View.INVISIBLE);

			ListView listView = (ListView) view.findViewById(R.id.OngoingList);
			listView.setAdapter(new eventItemAdapter(eventDataList, getContext(), false));

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
					getActivity().overridePendingTransition(R.anim.anim_left_out,R.anim.anim_right_in);
				}
			});

		}
	}
}
