package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homePage extends AppCompatActivity
{

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.homeTabLayout);
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.homeTab1)).setIcon(R.drawable.home_icon));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.homeTab2)).setIcon(R.drawable.news_icon));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.homeTab3)).setIcon(R.drawable.ongoing_icon));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.homeTab4)).setIcon(R.drawable.upcoming_icon));
		tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.homeTab5)).setIcon(R.drawable.schedule_icon));
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

		final ViewPager viewPager = (ViewPager) findViewById(R.id.homePager);
		final PagerAdapter adapter = new PagerAdapter
				(getSupportFragmentManager(), tabLayout.getTabCount());
		viewPager.setAdapter(adapter);
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
	}


	public static class PagerAdapter extends FragmentStatePagerAdapter
	{
		int mNumOfTabs;

		public PagerAdapter(FragmentManager fm, int NumOfTabs) {
			super(fm);
			this.mNumOfTabs = NumOfTabs;
		}

		@Override
		public Fragment getItem(int position) {

			switch (position) {
				case 0: return new homeFragment();
				case 1: return new newsListFragment();
				case 2: return new ongoingFragment();
				case 3: return new upcomingFragment();
				case 4: return new scheduleFragment();
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
