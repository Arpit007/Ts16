package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		if(this.getSupportActionBar()!=null)
		{
			this.getSupportActionBar().hide();
		}
		try {
			MessageDbHelper helper = new MessageDbHelper(this);
			helper.onCreate(helper.getWritableDatabase());
			helper.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try {
			CategoriesDbHelper helper2 = new CategoriesDbHelper(this);
			helper2.onCreate(helper2.getWritableDatabase());
			helper2.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		try{
			dbHelper helper1 = new dbHelper(this);
			helper1.onCreate(helper1.getWritableDatabase());;
			helper1.close();
		}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	serviceStartBroadcast.startServices(this);
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		Handler handler=new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				Intent intent=new Intent(Splash.this,mainActivity.class);
				startActivity(intent);
				finish();
			}
		}, getResources().getInteger(R.integer.splashDuration));
	}
}
