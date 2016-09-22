package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

		public int getValue()
		{
			return this.value;
		}
	}

	public void setStatusCode(StatusCode code)
	{
		int[] colorArray=context.getResources().getIntArray(R.array.EventStateColors);

		int color=0;
		String Text=null;

		if(code==StatusCode.None)
		{
			bullet.setVisibility(View.INVISIBLE);
			textView.setVisibility(View.INVISIBLE);
			return;
		}
		else if(code==StatusCode.Upcoming)
		{
			color=colorArray[1];
			Text="Upcoming";
		}
		else if(code==StatusCode.Ongoing)
		{
			color=colorArray[2];
			Text="Live";
		}
		else if(code==StatusCode.Over)
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

	public eventStatusListener(TextView textView, ImageView Bullet, Context context)
	{
		bullet=Bullet;
		this.context=context;
		this.textView=textView;
	}
}
