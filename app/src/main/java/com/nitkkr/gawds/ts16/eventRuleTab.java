package com.nitkkr.gawds.ts16;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class eventRuleTab extends Fragment implements eventData.eventDataListener
{
	private int EventID;
	static  eventData data;
	public static eventRuleTab CreateFragment(Bundle bundle)
	{
		eventRuleTab tab = new eventRuleTab();
		tab.EventID=bundle.getInt(tab.getString(R.string.EventID));
		dbHelper helper=new dbHelper(tab.getContext());
		data = helper.GetEventById(helper.getReadableDatabase(),tab.EventID);
		helper.close();
		data.addEventDataListener(tab);
		return tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eventUpdated(data);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_event_rule_tab, container, false);
	}

	@Override
	public void eventUpdated(eventData event)
	{
		EventID=event.eventID;
		View view=getView();
		((TextView)(view.findViewById(R.id.eventRuleText))).setText(data.Rules);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		data.removeDataListener(this);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		data.removeDataListener(this);
	}
}
