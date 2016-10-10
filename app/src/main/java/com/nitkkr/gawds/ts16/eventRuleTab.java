package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class eventRuleTab extends Fragment
{
	eventData data;
	View view;

	public void setUpFragment(eventData event)
	{
		data=event;
		if(data==null)
			data=new eventData();
		eventUpdated();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		view= inflater.inflate(R.layout.fragment_event_rule_tab, container, false);
		eventUpdated();
		return view;
	}

	public void eventUpdated()
	{
		if (view == null)
			return;

		Typeface font = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/Font2.ttf");
		if(data.Status!=-1)
			( (TextView) view.findViewById(R.id.eventRuleText) ).setTypeface(font);
		String string = data.Rules.toString();
		( (TextView) ( view.findViewById(R.id.eventRuleText) ) ).setText(string);
		( (TextView) view.findViewById(R.id.eventCall) ).setTypeface(font);

		view.findViewById(R.id.eventCall).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.Contact));
				startActivity(intent);
			}
		});
	}
}
