package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Home Laptop on 04-Sep-16.
 */
public class eventStatusListener implements TextWatcher
{
	private TextView textView;
	private Context context;

	public eventStatusListener(TextView textView, Context context)
	{
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
			color=colorArray[3];
		}
		textView.setTextColor(color);
	}

	@Override
	public void afterTextChanged(Editable s)
	{

	}
}
