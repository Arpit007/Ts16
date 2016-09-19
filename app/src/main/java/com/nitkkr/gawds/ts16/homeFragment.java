package com.nitkkr.gawds.ts16;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment
{
	AppCompatActivity activity;
	ImageView posters;
	Thread t;
	boolean running;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		view.findViewById(R.id.newsButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(1);
			}
		});
		view.findViewById(R.id.liveButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(2);
			}
		});
		view.findViewById(R.id.upcomingButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((mainActivity)activity).navigateToTab(3);
			}
		});
		view.findViewById(R.id.scheduleButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent=new Intent(homeFragment.this.getContext(),schedule.class);
				startActivity(intent);
			}
		});
		posters=(ImageView) view.findViewById(R.id.home_fragment_img);
		final Handler handler=new Handler();
		running=true;
		Thread t=new Thread(new Runnable() {
			int images[]={R.drawable.ab,R.drawable.splash,R.drawable.fresher};
			int i=0;
			@Override
			public void run() {
if(running) {

	i++;
	if (i == 3) {
		i = 0;
	}
	handler.postDelayed(this, 5000);
//	animateout();
	animatein(images[i]);
	Log.d("handler", "image changing " + i);
}
			}
		});
		t.start();
		return view;
	}

	public void setActivity(AppCompatActivity activity)
	{
		this.activity=activity;
	}

	@Override
	public void onPause() {
		super.onPause();
//		running=false;
	}

	@Override
	public void onResume() {
		super.onResume();
//		running=true;
	}

	void animatein(int imageid)
	{

		Animation fadein=new AlphaAnimation(0,1);
		fadein.setInterpolator(new AccelerateInterpolator());
		fadein.setDuration(800);
		posters.startAnimation(fadein);
		posters.setImageResource(imageid);
	}
	void animateout()
	{

		Animation fadeout=new AlphaAnimation(1,0);
		fadeout.setInterpolator(new AccelerateInterpolator());
		fadeout.setDuration(1000);
		Log.d("animate","out ");
		posters.startAnimation(fadeout);
	}


}
