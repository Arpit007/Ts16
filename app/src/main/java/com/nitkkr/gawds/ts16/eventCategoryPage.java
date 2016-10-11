package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class eventCategoryPage extends AppCompatActivity
{
	ListView categoryList;
	ArrayList<eventCategory> list=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_category_page);

		setTitle("Events");

		categoryList = (ListView) findViewById(R.id.eventCategoryList);

		CategoriesDbHelper helper = new CategoriesDbHelper(getBaseContext());
		list = helper.ReadDatabaseCategory(helper.getWritableDatabase());
		helper.close();
		if(list.size()==0)
		{

			Toast.makeText(this,"Please wait until we load data!\nMake Sure you have a working Internet Connection!",Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this,mainActivity.class));
		}
		Collections.sort(list, new Comparator<eventCategory>() {
			@Override
			public int compare(eventCategory eventCategory, eventCategory t1) {
				return eventCategory.category.compareToIgnoreCase(t1.category);
			}
		});
		categoryList.setAdapter(new eventCategoryAdapter(list, getBaseContext()));

		categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent i=new Intent(getBaseContext(),eventsListPage.class);
				i.putExtra("CategoryId",list.get(position).id);
				i.putExtra("CategoryName",list.get(position).category);
				eventCategoryPage.this.startActivity(i);
//				overridePendingTransition(R.anim.anim_left_out,R.anim.anim_right_in);
			}
		});
		overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
	}

	class eventCategoryAdapter extends BaseAdapter
	{

		ArrayList<eventCategory> list=null;
		Context context;

		eventCategoryAdapter(ArrayList<eventCategory> list, Context context)
		{
			this.context=context;
			this.list=list;
		}

		@Override
		public int getCount()
		{
			return list.size();
		}

		@Override
		public Object getItem(int position)
		{
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				LayoutInflater inflater=LayoutInflater.from(context);
				convertView=inflater.inflate(R.layout.category_item_layout,parent,false);
			}

			final eventCategory thisCategory=list.get(position);

			((TextView)convertView.findViewById(R.id.category_name)).setText(thisCategory.category);
			Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Font1.ttf");
			(( TextView)convertView.findViewById(R.id.category_name)).setTypeface(font);
			ImageView view=(ImageView)convertView.findViewById(R.id.categoryBullet);

			TypedArray array=context.getResources().obtainTypedArray(R.array.ModernColor);

			Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), R.drawable.bullet_icon, null);
			DrawableCompat.setTint(DrawableCompat.wrap(drawable), array.getColor(position%array.length(),0));
			view.setImageDrawable(drawable);


			return convertView;
		}
	}
}

