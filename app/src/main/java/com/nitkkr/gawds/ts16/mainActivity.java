package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class mainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.homeTabLayout);
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_icon));
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.news_icon));
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ongoing_icon));
		tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.upcoming_icon));
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

		final ViewPager viewPager = (ViewPager) findViewById(R.id.homePager);
		viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),this));
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
		navigateToTab(getIntent().getIntExtra(getString(R.string.TabID),0));
	}

	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
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
