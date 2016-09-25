package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class aboutUs extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);

		setTitle(getString(R.string.AboutUs));

		LinearLayout layout=(LinearLayout)findViewById(R.id.aboutDev1);
		layout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String uri = getString(R.string.Dev1Profile);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});

		layout=(LinearLayout)findViewById(R.id.aboutDev2);
		layout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String uri = getString(R.string.Dev2Profile);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});

		Typeface font = Typeface.createFromAsset(getBaseContext().getAssets(),
				"fonts/Free.ttf");
		((TextView)findViewById(R.id.dev1)).setTypeface(font);
		((TextView)findViewById(R.id.dev2)).setTypeface(font);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
