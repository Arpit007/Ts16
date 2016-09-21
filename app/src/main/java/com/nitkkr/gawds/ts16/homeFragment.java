package com.nitkkr.gawds.ts16;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class homeFragment extends Fragment
{
	AppCompatActivity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		Typeface font = Typeface.createFromAsset(activity.getAssets(),
				"fonts/Font1.ttf");

		View view = inflater.inflate(R.layout.fragment_home, container, false);

		Button button=(Button)view.findViewById(R.id.newsButton);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(1);
			}
		});
		button.setTypeface(font);

		button=(Button)view.findViewById(R.id.liveButton);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(2);
			}
		});
		button.setTypeface(font);

		button=(Button)view.findViewById(R.id.upcomingButton);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(3);
			}
		});
		button.setTypeface(font);

		button=(Button)view.findViewById(R.id.scheduleButton);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent=new Intent(homeFragment.this.getContext(),schedule.class);
				startActivity(intent);
			}
		});
		button.setTypeface(font);
		return view;
	}

	public void setActivity(AppCompatActivity activity)
	{
		this.activity=activity;
	}

}
