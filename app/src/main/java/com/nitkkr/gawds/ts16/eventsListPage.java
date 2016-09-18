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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
		dbHelper helper=new dbHelper(this);
		ArrayList<eventData> list=helper.ReadDatabaseEvents(helper.getReadableDatabase(),CategoryId);
		helper.close();
		eventAdapter=new EventAdapter(getBaseContext(),list);
		EventsRecyclerView.setAdapter(eventAdapter);


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
		Log.d("tag id", String.valueOf(item.eventID));
		holder.Bookmark.setChecked(((item.bookmark==1)?true:false));
		holder.EventRecyclerItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i=new Intent(context,eventDetail.class);
				i.putExtra(context.getString(R.string.EventID),item.eventID);
				i.putExtra(context.getString(R.string.TabID),0);

				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
		});
//		final dbHelper helper=new dbHelper(context);

		holder.Bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				item.updateBookmark(context,b);
			}
		});
	}

	@Override
	public int getItemCount() {
		return eventItems.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		TextView EventName,Day,Time;
		CheckBox Bookmark;
		LinearLayout EventRecyclerItem;
		public ViewHolder(View itemView) {
			super(itemView);
			EventRecyclerItem=(LinearLayout) itemView.findViewById(R.id.event_recycler_item);
			EventName=(TextView) itemView.findViewById(R.id.event_name);
			Day=(TextView) itemView.findViewById(R.id.recycler_event_date);
			Time=(TextView)itemView.findViewById(R.id.recycler_event_time);
			Bookmark=(CheckBox) itemView.findViewById(R.id.starrred);

		}
	}
}