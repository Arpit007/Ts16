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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class eventDetail extends AppCompatActivity
{
	eventData data;
	int selectedtabID;
	ImageView eventDetailImage;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.eventTabLayout);
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.eventTab1)));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.eventTab2)));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.eventTab3)));
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		eventDetailImage=(ImageView) findViewById(R.id.eventDetailImage);
		Glide.with(eventDetail.this).load(R.drawable.logots_16).into(eventDetailImage);
		try
			{
				Glide.with(eventDetail.this).load("http://www.almerston.com/images/"+data.ImageID).diskCacheStrategy(DiskCacheStrategy.RESULT).crossFade().placeholder(R.drawable.logots_16).into(eventDetailImage);
			}
		catch (Exception e)
		{

		}
			dbHelper helper=new dbHelper(this);
		data=helper.GetEventById(helper.getReadableDatabase(),getIntent().getIntExtra(getString(R.string.EventID),0));
		helper.close();

		if(data==null)
			data=new eventData();

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

		eventUpdated();


		Typeface font = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/Font2.ttf");
		(( TextView)findViewById(R.id.eventDetailStatus)).setTypeface(font);
		((TextView)findViewById(R.id.eventDetailDate)).setTypeface(font);
		((TextView)findViewById(R.id.eventDetailTime)).setTypeface(font);
		((TextView)findViewById(R.id.eventDetailLocation)).setTypeface(font);
	}

	public void eventUpdated()
	{
		setTitle(data.eventName);
		((TextView)findViewById(R.id.eventDetailLocation)).setText(data.Venue);
		boolean check1=false;
		boolean check2=false;
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date=simpleDateFormat.parse(data.Day);
			simpleDateFormat.applyPattern("dd MMM yyyy");
			((TextView)findViewById(R.id.eventDetailDate)).setText(simpleDateFormat.format(date));
			check1=true;
			simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
			date=simpleDateFormat.parse(data.Time);
			simpleDateFormat.applyPattern("hh:mm a");
			((TextView)findViewById(R.id.eventDetailTime)).setText(simpleDateFormat.format(date));
			check2=true;
		}
		catch (Exception e)
		{
//			e.printStackTrace();
		}
		if(!check1)
		{
			((TextView)findViewById(R.id.eventDetailDate)).setText("NA");
		}
		if(!check2)
		{
			((TextView)findViewById(R.id.eventDetailTime)).setText("NA");
		}


		eventStatusListener listener=new eventStatusListener((TextView)findViewById(R.id.eventDetailStatus),(ImageView) findViewById(R.id.eventStatusBullet),this);
		listener.setStatusCode(data);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
	}

}