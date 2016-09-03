package com.nitkkr.gawds.ts16;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class eventCategoryPage extends AppCompatActivity
{
	RecyclerView EventsRecyclerView;
	CategoryAdapter categoryAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_category_page);

		EventsRecyclerView =(RecyclerView) findViewById(R.id.category_recycler);
		EventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		CategoryJSON();
	}

	public void CategoryJSON()
	{
		class GetCategories extends AsyncTask<Void, Void,String>
		{
			ProgressDialog dialog;
			@Override
			protected void onPreExecute() {
				dialog= new ProgressDialog(getBaseContext()); // this = YourActivity
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setMessage("Loading, Please wait...");
				dialog.setIndeterminate(true);
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);

				try {
					loadArrays();
				} catch (Exception e) {
					e.printStackTrace();
				}
				dialog.dismiss();

			}

			private void loadArrays()  {
				CategoriesDbHelper helper=new CategoriesDbHelper(getBaseContext());
				ArrayList<EventCategory> list=helper.ReadDatabaseCategory();
				categoryAdapter=new CategoryAdapter(getBaseContext(),list);
				EventsRecyclerView.setAdapter(categoryAdapter);
			}

			@Override
			protected String doInBackground(Void... voids) {
				return null;
			}
		}
		GetCategories gc=new GetCategories();
		gc.execute();
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
		return 0;
	}
	public class ViewHolder extends RecyclerView.ViewHolder{
		ImageView CategoryImage;
		TextView CategoryName;
		LinearLayout CategoryRecyclerItem;
		public ViewHolder(View itemView) {
			super(itemView);
			CategoryImage=(ImageView) itemView.findViewById(R.id.category_image);
			CategoryName=(TextView) itemView.findViewById(R.id.category_name);
			CategoryRecyclerItem=(LinearLayout) itemView.findViewById(R.id.category_recycler);
		}
	}
}