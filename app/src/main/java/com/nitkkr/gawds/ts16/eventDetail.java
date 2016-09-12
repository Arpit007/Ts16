package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class eventDetail extends AppCompatActivity implements eventData.eventDataListener
{
	private int EventId, TabID;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.eventTabLayout);
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.eventTab1)));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.eventTab2)));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.eventTab3)));
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

		EventId=getIntent().getIntExtra(getString(R.string.EventID),0);
		TabID=getIntent().getIntExtra(getString(R.string.TabID),0);

		final ViewPager viewPager = (ViewPager) findViewById(R.id.eventPager);
		final PagerAdapter adapter = new PagerAdapter
				(getSupportFragmentManager(), tabLayout.getTabCount(), EventId, this);
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		viewPager.setCurrentItem(TabID);

		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		setTitle("Event");

		ActionBar bar=getSupportActionBar();
		if(bar!=null)
			bar.setDisplayHomeAsUpEnabled(true);

		eventDatabase.Database.getEventData(EventId).addEventDataListener(this);
		eventUpdated(eventDatabase.Database.getEventData(EventId));
	}


	@Override
	protected void onStop()
	{
		super.onStop();
		eventDatabase.Database.getEventData(EventId).removeDataListener(this);
	}

	@Override
	public void eventUpdated(eventData event)
	{
		((ImageView)findViewById(R.id.eventDetailImage)).setImageResource(event.eventID);
		((TextView)findViewById(R.id.eventDetailName)).setText(event.eventName);
		((TextView)findViewById(R.id.eventDetailStatus)).setText(event.Status);
		((CheckBox)findViewById(R.id.eventDetailNotify)).setChecked(event.isBookmarked());
		((TextView)findViewById(R.id.eventDetailLocation)).setText(event.Venue);
		((TextView)findViewById(R.id.eventDetailDate)).setText(event.Day);
		((TextView)findViewById(R.id.eventDetailTime)).setText(event.Time);
		((CheckBox)findViewById(R.id.eventDetailNotify)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				eventDatabase.Database.getEventData(EventId).updateBookmark(isChecked);
			}
		});
		((TextView)findViewById(R.id.eventDetailStatus)).addTextChangedListener(new eventStatusListener(((TextView)findViewById(R.id.eventDetailStatus)),this));
		int ImageId=event.getImageResourceID();
		if(ImageId!=-1)
			((ImageView)findViewById(R.id.eventDetailImage)).setImageResource(ImageId);
		else
			((ImageView)findViewById(R.id.eventDetailImage)).setImageURI(Uri.parse(event.ImageID));
	}

	public static class PagerAdapter extends FragmentStatePagerAdapter
	{
		int mNumOfTabs;
		Context context;
		int EventID;

		public PagerAdapter(FragmentManager fm, int NumOfTabs, int eventID, Context context) {
			super(fm);
			EventID=eventID;
			this.context=context;
			this.mNumOfTabs = NumOfTabs;
		}

		@Override
		public Fragment getItem(int position) {

			Bundle bundle=new Bundle();
			bundle.putInt(context.getString(R.string.EventID),EventID);
			switch (position) {
				case 0: return eventDescTab.CreateFragment(bundle);
				case 1: return eventRuleTab.CreateFragment(bundle);
				case 2: return eventResultTab.CreateFragment(bundle);
				default:
					return null;
			}
		}

		@Override
		public int getCount() {
			return mNumOfTabs;
		}
	}
}