package com.nitkkr.gawds.ts16;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Splash extends AppCompatActivity
{
	int connection=1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		if(this.getSupportActionBar()!=null)
		{
			this.getSupportActionBar().hide();
		}
		Toast.makeText(this,"Loading...Ensure a working Data Connection!",Toast.LENGTH_SHORT).show();
		new parallelDataSetup().execute(this.getBaseContext());
	}

	public  void startAlarm(Context c)
	{
		Intent intent=new Intent(c, serviceStartBroadcast.class);
		final PendingIntent pendingIntent=PendingIntent.getBroadcast(c,12345,intent,PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarm = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP,1000,60000,pendingIntent);
	}

	class parallelDataSetup extends AsyncTask<Context,Void,Void>
	{
		long oldTime, currentTime;

		@Override
		protected Void doInBackground(Context... params)
		{
			oldTime = System.currentTimeMillis();
//			startAlarm(params[0]);
			try
			{
				new MessageDbHelper(params[0]);
			}
			catch (Exception e)
			{
//				e.printStackTrace();
			}
			try
			{
				new CategoriesDbHelper(params[0]);
			}
			catch (Exception e)
			{
//				e.printStackTrace();
			}
			try
			{
				dbHelper helper1 = new dbHelper(params[0]);
				helper1.onCreate(helper1.getWritableDatabase());
				helper1.close();
			}
			catch (Exception e)
			{
//				e.printStackTrace();
			}
			startAlarm(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid)
		{
			long duration=getResources().getInteger(R.integer.splashDuration);
			if(connection==0)
			{
				Toast.makeText(getBaseContext(),"No Internet Connection!",Toast.LENGTH_SHORT).show();
			}
			Handler handler=new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					Intent intent=new Intent(Splash.this,mainActivity.class);
					startActivity(intent);finish();
				}
			}, duration);
		}
	}
}
