package com.nitkkr.gawds.ts16;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class eventRuleTab extends Fragment
{
	public static eventRuleTab CreateFragment(Bundle bundle)
	{
		return new eventRuleTab();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_event_rule_tab, container, false);
	}
}
