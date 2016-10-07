package com.nitkkr.gawds.ts16;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class schedule extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		setTitle("Schedule");
		Button schedule=(Button) findViewById(R.id.scheduleButton);
		schedule.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String uri = getString(R.string.schedule_page);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});
	}
}
