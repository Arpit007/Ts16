package com.nitkkr.gawds.ts16;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class eventRuleTab extends Fragment implements eventData.eventDataListener
{
	eventData data;
	View view;

	public void setUpFragment(int eventID, Context context)
	{
		dbHelper helper=new dbHelper(context);
		data=helper.GetEventById(helper.getReadableDatabase(),eventID);
		helper.close();

		data.addEventDataListener(this);
		eventUpdated(data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		view= inflater.inflate(R.layout.fragment_event_rule_tab, container, false);
		eventUpdated(data);
		return view;
	}

	@Override
	public void eventUpdated(eventData event)
	{
		if(view== null)
			return;

		Typeface font = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/Font1.ttf");
		(( TextView)view.findViewById(R.id.eventRuleText)).setTypeface(font);
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
