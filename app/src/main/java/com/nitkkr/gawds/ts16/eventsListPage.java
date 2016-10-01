package com.nitkkr.gawds.ts16;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


public class eventsListPage extends AppCompatActivity implements eventItemAdapter.BookmarkListener
{
	ListView eventListView;
	ArrayList<eventData> list=null;
	int CategoryID=0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_list_page);
		overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);

		Bundle b=getIntent().getExtras();

		String string = getString(R.string.CategoryID);

		if(b!=null)
			CategoryID=b.getInt(string);

		String title=null;
		if(b!=null)
			title=b.getString("CategoryName");

		try
		{
			if (!title.equals("All Events"))
				title += " Events";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		setTitle(title);
		BookmarkChanged();
//		overridePendingTransition(R.anim.anim_left_out,R.anim.anim_right_in);
	}

	@Override
	public void BookmarkChanged()
	{
		eventListView =(ListView) findViewById(R.id.eventList);

		if(list!= null)
			list.clear();

		dbHelper helper=new dbHelper(this);
		list=helper.ReadDatabaseEvents(helper.getReadableDatabase(),CategoryID);
		helper.close();


		if(list.size()==0)
		{
			findViewById(R.id.noEvent).setVisibility(View.VISIBLE);
		}
		else
		{
			findViewById(R.id.noEvent).setVisibility(View.INVISIBLE);
			eventItemAdapter adapter=new eventItemAdapter(list, getApplicationContext(), true);
			adapter.listener=this;
			eventListView.setAdapter(adapter);
		}

		TypedArray array=getBaseContext().getResources().obtainTypedArray(R.array.Category_Image_Map);

		try
		{
			(( ImageView)findViewById(R.id.bg)).setImageResource(array.getResourceId(CategoryID,0));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
