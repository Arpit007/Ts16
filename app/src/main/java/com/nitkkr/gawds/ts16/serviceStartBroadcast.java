package com.nitkkr.gawds.ts16;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Home Laptop on 09-Sep-16.
 */
public class serviceStartBroadcast extends BroadcastReceiver
{
	public static boolean ServiceRunning=false;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		startServices(context);
	}

	public static void startServices(Context context)
	{
		if(!ServiceRunning)
		{

			context.startService(new Intent(context, ServertoSqliteLoader.class));
			ServiceRunning = true;
		}
	}
}
