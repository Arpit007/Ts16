package com.nitkkr.gawds.ts16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class schedule extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		setTitle("Schedule");
		ImageView image=(ImageView) findViewById(R.id.schedule);
		Glide.with(this).load("http://www.almerston.com/nitkkr2110/images/schedule/schedule.jpg").crossFade().into(image);
	}
}
