package com.nitkkr.gawds.ts16;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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

		new parallelDataSetup().execute(this.getBaseContext());

		//startAlarm(this);
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

	class parallelDataSetup extends AsyncTask<Context,Void,Void>
	{
		long oldTime, currentTime;

		@Override
		protected Void doInBackground(Context... params)
		{
			oldTime = System.currentTimeMillis();

			if(!ServertoSqliteLoader.ServiceRunning)
				serviceStartBroadcast.startServices(params[0]);

			try
			{
				new MessageDbHelper(params[0]);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				new CategoriesDbHelper(params[0]);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				dbHelper helper1 = new dbHelper(params[0]);
				helper1.onCreate(helper1.getWritableDatabase());;
				helper1.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid)
		{
			super.onPostExecute(aVoid);
			currentTime= System.currentTimeMillis();
			long milliSeconds=(currentTime-oldTime);
			long duration=getResources().getInteger(R.integer.splashDuration);
			if(duration>milliSeconds)
			{
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
				}, duration);
			}
			else
			{
				Intent intent=new Intent(Splash.this,mainActivity.class);
				startActivity(intent);
			}
		}
	}

}
