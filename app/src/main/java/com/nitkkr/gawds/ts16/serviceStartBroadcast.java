package com.nitkkr.gawds.ts16;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Home Laptop on 09-Sep-16.
 */
public class serviceStartBroadcast extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		startServices(context);
//		startAlarm(context);
	}

	public static void startServices(Context context)
	{
		Log.d("Service call","star");
			context.startService(new Intent(context, ServertoSqliteLoader.class));
	}


}
