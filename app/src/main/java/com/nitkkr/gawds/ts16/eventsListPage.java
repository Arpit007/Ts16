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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class eventsListPage extends AppCompatActivity
{ RecyclerView EventsRecyclerView;
	EventAdapter eventAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_list_page);

		Intent i=getIntent();
		Bundle b=i.getExtras();
		int CategoryId=b.getInt(getString(R.string.CategoryID));
		EventsRecyclerView=(RecyclerView) findViewById(R.id.event_recycler);
		EventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		LoadEvents(CategoryId);



	}
	public void LoadEvents(final int CategoryId)
	{
		class EventLoader extends AsyncTask<Void,Void, String>
		{
			ProgressDialog dialog;
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
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

			private void loadArrays() {
				dbHelper helper=new dbHelper(getBaseContext());
				ArrayList<eventData> list=helper.ReadDatabaseEvents(CategoryId);

				eventAdapter=new EventAdapter(getBaseContext(),list);
				EventsRecyclerView.setAdapter(eventAdapter);
			}

			@Override
			protected String doInBackground(Void... voids) {
				return null;
			}
		}
		EventLoader ob=new EventLoader();
		ob.execute();
	}

}

class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
	Context context;
	ArrayList<eventData> eventItems;
	public EventAdapter(Context c, ArrayList<eventData> list) {
		this.context = c;
		this.eventItems=list;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_recycler_item,parent,false);
		EventAdapter.ViewHolder viewHolder=new ViewHolder(v);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		final eventData item=eventItems.get(position);
		holder.EventName.setText(item.eventName);
		holder.Time.setText(item.Time);
		holder.Day.setText(item.Day);
		holder.ID.setText(item.eventID);
		holder.Bookmark.setChecked(((item.bookmark==1)?true:false));
		holder.EventRecyclerItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i=new Intent(context,eventDetail.class);
				i.putExtra("EventId",item.eventID);
				context.startActivity(i);
			}
		});
		holder.Bookmark.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//SharedPreferences to be edited
			}
		});
	}

	@Override
	public int getItemCount() {
		return 0;
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		TextView EventName,Day,Time,ID;
		CheckBox Bookmark;
		LinearLayout EventRecyclerItem;
		public ViewHolder(View itemView) {
			super(itemView);
			EventRecyclerItem=(LinearLayout) itemView.findViewById(R.id.event_recycler_item);
			EventName=(TextView) itemView.findViewById(R.id.event_name);
			Day=(TextView) itemView.findViewById(R.id.recycler_event_date);
			Time=(TextView)itemView.findViewById(R.id.recycler_event_time);
			Bookmark=(CheckBox) itemView.findViewById(R.id.recycler_bookmark_image);
			ID=(TextView) itemView.findViewById(R.id.recycler_event_id);
		}
	}
}