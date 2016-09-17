package com.nitkkr.gawds.ts16;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class eventCategoryPage extends AppCompatActivity {
	RecyclerView EventsRecyclerView;
	CategoryAdapter categoryAdapter;
	Context c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_category_page);
		c = this;
		EventsRecyclerView = (RecyclerView) findViewById(R.id.category_recycler);
		EventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		CategoriesDbHelper helper = new CategoriesDbHelper(getBaseContext());
		ArrayList<EventCategory> list = helper.ReadDatabaseCategory(helper.getWritableDatabase());
		helper.close();
		for (int i = 0; i < list.size(); i++) {
			Log.d("Category:: ", list.get(i).category);
		}
		categoryAdapter = new CategoryAdapter(c, list);
		EventsRecyclerView.setAdapter(categoryAdapter);
	}

}

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
	Context context;
	ArrayList<EventCategory> categories;

	public CategoryAdapter(Context context,ArrayList<EventCategory> categories)
	{
		this.context = context;
		this.categories=categories;
	}

	@Override
	public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler_item,parent,false);
		CategoryAdapter.ViewHolder holder=new ViewHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
		final EventCategory thisCategory=categories.get(position);
		Log.d("Category",thisCategory.category );
		holder.CategoryName.setText(thisCategory.category);
//        Glide.with(context).load(context.getString(R.string.CategoryImagePath)+thisCategory.getImage()).crossFade().placeholder(R.drawable.ic_menu_gallery).into(holder.CategoryImage);
		holder.CategoryRecyclerItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i=new Intent(context,eventsListPage.class);
				i.putExtra("CategoryId",thisCategory.id);
				context.startActivity(i);
			}
		});
	}

	@Override
	public int getItemCount() {
		return categories.size();
	}
	class ViewHolder extends RecyclerView.ViewHolder{
		TextView CategoryName;
		LinearLayout CategoryRecyclerItem;
		public ViewHolder(View itemView) {
			super(itemView);
			CategoryName=(TextView) itemView.findViewById(R.id.category_name);
			CategoryRecyclerItem=(LinearLayout) itemView.findViewById(R.id.category_recycler);
		}
	}
}
