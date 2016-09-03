package com.nitkkr.gawds.ts16;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class eventResultTab extends Fragment implements eventData.eventDataListener
{
	private int EventID;
	private eventData data;

	public static eventResultTab CreateFragment(Bundle bundle)
	{
		eventResultTab tab = new eventResultTab();
		tab.EventID=bundle.getInt(tab.getString(R.string.EventID));
		tab.data = eventDatabase.Database.getEventData(tab.EventID);
		eventDatabase.Database.eventDataList.get(tab.EventID).addEventDataListener(tab);
		return tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Populate();
	}

	private void Populate()
	{
		View view=getView();
		if(!data.isResultDeclared())
		{
			(view.findViewById(R.id.NoResult)).setVisibility(View.VISIBLE);
		}
		else
		{
			view.findViewById(R.id.NoResult).setVisibility(View.INVISIBLE);
			( (TextView) ( view.findViewById(R.id.resultText) ) ).setText(data.Result);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_event_result_tab, container, false);
	}

	@Override
	public void eventUpdated(eventData event)
	{
		EventID=event.eventID;
		Populate();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		eventDatabase.Database.eventDataList.get(EventID).removeDataListener(this);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		eventDatabase.Database.eventDataList.get(EventID).removeDataListener(this);
	}
}