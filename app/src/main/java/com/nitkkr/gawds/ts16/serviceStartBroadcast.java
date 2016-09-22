package com.nitkkr.gawds.ts16;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class serviceStartBroadcast extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		startServices(context);
	}

	public static void startServices(Context context)
	{
		context.startService(new Intent(context, ServertoSqliteLoader.class));
	}
}
