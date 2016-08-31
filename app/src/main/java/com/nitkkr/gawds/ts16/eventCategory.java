package com.nitkkr.gawds.ts16;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class eventCategory extends AppCompatActivity
{
	public static class eventCategoryData
	{
		public int id;
		public Uri imageUri;
		public String category;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_category);
	}
}
