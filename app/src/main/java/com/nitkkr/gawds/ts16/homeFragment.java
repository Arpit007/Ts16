package com.nitkkr.gawds.ts16;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment
{
	AppCompatActivity activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		view.findViewById(R.id.newsButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(1);
			}
		});
		view.findViewById(R.id.liveButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(2);
			}
		});
		view.findViewById(R.id.upcomingButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(3);
			}
		});
		view.findViewById(R.id.scheduleButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(4);
			}
		});
		return view;
	}

	public void setActivity(AppCompatActivity activity)
	{
		this.activity=activity;
	}

}
