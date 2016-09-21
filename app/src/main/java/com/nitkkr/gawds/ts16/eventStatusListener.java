package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Home Laptop on 04-Sep-16.
 */
public class eventStatusListener implements TextWatcher
{
	private TextView textView;
	ImageView bullet;
	private Context context;

	public eventStatusListener(TextView textView, ImageView Bullet, Context context)
	{
		bullet=Bullet;
		this.context=context;
		this.textView=textView;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		String[] array=context.getResources().getStringArray(R.array.EventStatus);
		int color=0;
		int[] colorArray=context.getResources().getIntArray(R.array.EventStateColors);
		if(s.equals(array[0]))
		{
			color=colorArray[0];
		}
		else if(s.equals(array[1]))
		{
			color=colorArray[1];
		}
		else if(s.equals(array[2]))
		{
			color=colorArray[2];
		}
		else if(s.equals(array[3]))
		{
			bullet.setVisibility(View.INVISIBLE);
			textView.setVisibility(View.INVISIBLE);
			return;
		}

		bullet.setVisibility(View.VISIBLE);
		textView.setVisibility(View.VISIBLE);
		Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), R.drawable.bullet_icon, null);
		DrawableCompat.setTint(DrawableCompat.wrap(drawable), color);
		bullet.setImageDrawable(drawable);
	}

	@Override
	public void afterTextChanged(Editable s)
	{

	}
}
