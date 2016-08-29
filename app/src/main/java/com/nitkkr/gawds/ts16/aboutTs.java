package com.nitkkr.gawds.ts16;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.security.KeyStore;

import javax.crypto.NullCipher;

public class aboutTs extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_ts);
		setTitle(getString(R.string.AboutTs));
	}

}
