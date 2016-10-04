package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class eventStatusListener
{

	private TextView textView;
	private ImageView bullet;
	private Context context;

	public enum StatusCode {
		None(0), Upcoming(1), Ongoing(2), Over(3);
		private int value;

		StatusCode(int value) {
			this.value = value;
		}

	}

	public static StatusCode getStatusCode(eventData data, Context context)
	{
		StatusCode code=StatusCode.None;

		try
		{

			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			long BeginTime=(format.parse(data.Day+" "+ data.Time).getTime())/1000;
			long EndTime=(format.parse(data.Day+" "+ data.EndTime).getTime())/1000;
			long currentTimeStamp=Calendar.getInstance().getTimeInMillis()/1000;
			long lapse = TimeUnit.HOURS.toMillis(context.getResources().getInteger(R.integer.upcomingDuration));

			if(currentTimeStamp>=BeginTime && currentTimeStamp<EndTime)
				code=StatusCode.Ongoing;
			else if (currentTimeStamp < BeginTime && currentTimeStamp + lapse > BeginTime)
				code=StatusCode.Upcoming;
			else if(currentTimeStamp>EndTime)
				code=StatusCode.Over;
			else code=StatusCode.None;
		}
		catch (Exception e)
		{
//			e.printStackTrace();
		}

		return code;
	}

	public void setStatusCode(eventData data)
	{
		data.code=getStatusCode(data,context);

		int[] colorArray=context.getResources().getIntArray(R.array.EventStateColors);

		int color=0;
		String Text=null;

		if(data.code==StatusCode.None)
		{
			bullet.setVisibility(View.INVISIBLE);
			textView.setVisibility(View.INVISIBLE);
			return;
		}
		else if(data.code==StatusCode.Upcoming)
		{
			color=colorArray[1];
			Text="Upcoming";
		}
		else if(data.code==StatusCode.Ongoing)
		{
			color=colorArray[2];
			Text="Live";
		}
		else if(data.code==StatusCode.Over)
		{
			color=colorArray[3];
			Text="Over";
		}

		bullet.setVisibility(View.VISIBLE);
		textView.setVisibility(View.VISIBLE);

		textView.setText(Text);

		Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), R.drawable.bullet_icon, null);
		DrawableCompat.setTint(DrawableCompat.wrap(drawable), color);
		bullet.setImageDrawable(drawable);
	}

	public static void setEventStatusCode(eventData data, Context context)
	{
		data.code=getStatusCode(data,context);
	}

	public eventStatusListener(TextView textView, ImageView Bullet, Context context)
	{
		bullet=Bullet;
		this.context=context;
		this.textView=textView;
	}
}
