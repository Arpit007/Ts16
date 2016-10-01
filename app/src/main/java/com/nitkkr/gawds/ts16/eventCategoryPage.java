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

import java.util.ArrayList;

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

			ImageView view=(ImageView)convertView.findViewById(R.id.categoryBullet);

			TypedArray array=context.getResources().obtainTypedArray(R.array.ModernColor);

			Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), R.drawable.bullet_icon, null);
			DrawableCompat.setTint(DrawableCompat.wrap(drawable), array.getColor(position%array.length(),0));
			view.setImageDrawable(drawable);

			Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Font1.ttf");
			(( TextView)convertView.findViewById(R.id.category_name)).setTypeface(font);

			return convertView;
		}
	}
}

