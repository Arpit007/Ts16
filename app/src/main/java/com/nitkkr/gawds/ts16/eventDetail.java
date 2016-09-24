package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.graphics.Typeface;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class eventDetail extends AppCompatActivity implements eventData.eventDataListener
{
	eventData data;
	int selectedtabID;

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

		dbHelper helper=new dbHelper(this);
		data=helper.GetEventById(helper.getReadableDatabase(),getIntent().getIntExtra(getString(R.string.EventID),0));
		helper.close();

		selectedtabID=getIntent().getIntExtra(getString(R.string.TabID),0);
		tabLayout.getTabAt(selectedtabID).select();
		Log.d("MyEventId", String.valueOf(data.eventID));

		final ViewPager viewPager = (ViewPager) findViewById(R.id.eventPager);
		final PagerAdapter adapter = new PagerAdapter
				(getSupportFragmentManager(), tabLayout.getTabCount(), data, this);
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		viewPager.setCurrentItem(selectedtabID);

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

		data.addEventDataListener(this);
		eventUpdated(data);


		Typeface font = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/Font2.ttf");
		(( TextView)findViewById(R.id.eventDetailStatus)).setTypeface(font);
		((TextView)findViewById(R.id.eventDetailDate)).setTypeface(font);
		((TextView)findViewById(R.id.eventDetailTime)).setTypeface(font);
		((TextView)findViewById(R.id.eventDetailLocation)).setTypeface(font);
	}

	@Override
	public void eventUpdated(final eventData event)
	{
		try
		{
			int ImageId=event.getImageResourceID();
			if(ImageId==-2);
			else
			if(ImageId!=-1)
				((ImageView)findViewById(R.id.eventDetailImage)).setImageResource(ImageId);
			else
				((ImageView)findViewById(R.id.eventDetailImage)).setImageURI(Uri.parse(event.ImageID));
		}
		catch (Exception e)
		{
			Log.d("EventDetail","Image Error");
		}

		setTitle(event.eventName);


		((TextView)findViewById(R.id.eventDetailLocation)).setText(event.Venue);

		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date=simpleDateFormat.parse(event.Day);
			simpleDateFormat.applyPattern("dd MMM yyyy");
			((TextView)findViewById(R.id.eventDetailDate)).setText(simpleDateFormat.format(date));

			simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
			date=simpleDateFormat.parse(event.Time);
			simpleDateFormat.applyPattern("hh:mm a");
			((TextView)findViewById(R.id.eventDetailTime)).setText(simpleDateFormat.format(date));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		((CheckBox)findViewById(R.id.eventDetailNotify)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				event.updateBookmark(getBaseContext(),isChecked);
			}
		});
		((CheckBox)findViewById(R.id.eventDetailNotify)).setChecked(event.isBookmarked());

		eventStatusListener listener=new eventStatusListener((TextView)findViewById(R.id.eventDetailStatus),(ImageView) findViewById(R.id.eventStatusBullet),this);
		listener.setStatusCode(event);

		switch (event.code)
		{
			case None:
			case Upcoming:
				findViewById(R.id.eventDetailNotify).setVisibility(View.VISIBLE);
				break;
			case Ongoing:
			case Over:
				findViewById(R.id.eventDetailNotify).setVisibility(View.INVISIBLE);
				break;
		}
		if(event.isBookmarked())
			findViewById(R.id.eventDetailNotify).setVisibility(View.VISIBLE);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void finalize() throws Throwable
	{
		if(data!=null)
			data.removeDataListener(this);

		super.finalize();
	}

	public static class PagerAdapter extends FragmentStatePagerAdapter
	{
		int mNumOfTabs;
		Context context;
		eventData event;

		public PagerAdapter(FragmentManager fm, int NumOfTabs, eventData Event, Context context) {
			super(fm);
			event=Event;
			this.context=context;
			this.mNumOfTabs = NumOfTabs;
		}

		@Override
		public Fragment getItem(int position)
		{

			switch (position) {
				case 0: eventDescTab tab=new eventDescTab();
					tab.setUpFragment(event);return tab;
				case 1: eventRuleTab tab1=new eventRuleTab();
					tab1.setUpFragment(event);return tab1;
				case 2: eventResultTab tab2=new eventResultTab();
					tab2.setUpFragment(event);return tab2;
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