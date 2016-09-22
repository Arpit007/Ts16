package com.nitkkr.gawds.ts16;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
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
		if(!ServertoSqliteLoader.ServiceRunning)
			serviceStartBroadcast.startServices(this);
		startAlarm(this);
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
	public  void startAlarm(Context c)
	{
		Intent intent=new Intent(c, serviceStartBroadcast.class);
		final PendingIntent pendingIntent=PendingIntent.getBroadcast(c,12345,intent,PendingIntent.FLAG_UPDATE_CURRENT);
		long firstMillis = System.currentTimeMillis();
		AlarmManager alarm = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
//		alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,firstMillis,100,pendingIntent);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP,firstMillis,1000,pendingIntent);
	}
}
