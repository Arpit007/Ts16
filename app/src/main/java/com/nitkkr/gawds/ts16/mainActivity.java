package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class mainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener
{
	NavigationView navigationView;
    int TabID;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Typeface font;
		try
		{
			Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
			setSupportActionBar(toolbar);
			font = Typeface.createFromAsset(getAssets(), "fonts/Free.ttf");
			((TextView)toolbar.findViewById(R.id.mainLogo)).setTypeface(font);
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
					this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
			drawer.setDrawerListener(toggle);
			toggle.syncState();
		}
		catch (Exception e)
		{
//			e.printStackTrace();
		}
		navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.setCheckedItem(R.id.nav_home);
		navigationView.setItemIconTintList(null);
        TabID=getIntent().getIntExtra(String.valueOf(R.string.TabID),0);
		font = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Free.ttf");
		((TextView)navigationView.getHeaderView(0).findViewById(R.id.headerLogo)).setTypeface(font);
		Log.d("Tabid", String.valueOf(TabID));
		TabLayout tabLayout = (TabLayout) findViewById(R.id.homeTabLayout);
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_icon));
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.events_icon));
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ongoing_icon));
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.upcoming_icon));
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		final ViewPager viewPager = (ViewPager) findViewById(R.id.homePager);
		viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),this));
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		viewPager.setCurrentItem(TabID);
        navigateToTab(TabID);
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
		overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
		if(savedInstanceState!=null)
			onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else if(((TabLayout) findViewById(R.id.homeTabLayout)).getSelectedTabPosition()>0)
		{
			navigateToTab(0);
		}
		else
		{
			super.onBackPressed();
		}
	}

	public void navigateToTab(int Index)
	{
		TabLayout tabLayout = (TabLayout) findViewById(R.id.homeTabLayout);

		if(Index<tabLayout.getTabCount())
		{
			tabLayout.getTabAt(Index).select();
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		navigateToTab(savedInstanceState.getInt(getString(R.string.TabID)));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt(getString(R.string.TabID),((TabLayout) findViewById(R.id.homeTabLayout)).getSelectedTabPosition());
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.nav_home)
		{
			TabLayout tabLayout = (TabLayout) findViewById(R.id.homeTabLayout);
			tabLayout.getTabAt(0).select();
		}
		else if (id == R.id.nav_events)
		{
			Intent intent=new Intent(this,eventCategoryPage.class);
			startActivity(intent);
			}
		else if (id == R.id.nav_schedule)
		{
			Intent intent=new Intent(this,schedule.class);
			startActivity(intent);
		}
		else if (id == R.id.nav_starred)
		{
			Intent intent=new Intent(this,starredEvents.class);
			startActivity(intent);
		}
		else if (id == R.id.nav_results)
		{
			Intent intent=new Intent(this,eventResult.class);
			startActivity(intent);
		}
		else if (id == R.id.nav_organizers)
		{
			Intent intent=new Intent(this,organizerPage.class);
			startActivity(intent);
		}
		else if (id == R.id.nav_sponsor)
		{
			Intent intent=new Intent(this,sponsors.class);
			startActivity(intent);
		}
		else if (id == R.id.nav_dev)
		{
			Intent intent=new Intent(this,aboutUs.class);
			startActivity(intent);
		}
		else if(id==R.id.nav_rate)
		{
			Intent intent=new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id="+getBaseContext().getPackageName()));
			startActivity(intent);
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		navigationView.setCheckedItem(R.id.nav_home);
	}

	public static class PagerAdapter extends FragmentStatePagerAdapter
	{
		int mNumOfTabs;

		AppCompatActivity activity;

		public PagerAdapter(FragmentManager fm, int NumOfTabs, AppCompatActivity activity) {
			super(fm);
			this.mNumOfTabs = NumOfTabs;
			this.activity=activity;
		}

		@Override
		public Fragment getItem(int position) {

			switch (position) {
				case 0: homeFragment fragment=new homeFragment();
						fragment.setActivity(activity);
					return fragment;
				case 1: return new newsListFragment();
				case 2: return new ongoingFragment();
				case 3: return new upcomingFragment();
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
