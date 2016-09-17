package com.nitkkr.gawds.ts16;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class newsListFragment extends Fragment
{
	ArrayList<MessageDbHelper.MessageData> eventDataList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.fragment_news_list, container, false);
		MessageDbHelper helper=new MessageDbHelper(getContext());
		eventDataList=helper.ReadDatabaseMessage(helper.getReadableDatabase());
//		helper.close();
//		if (eventDataList.size() == 0)
//		{
//			view.findViewById(R.id.NoNews).setVisibility(View.VISIBLE);
//		}
//		else
//		{
//			view.findViewById(R.id.NoNews).setVisibility(View.INVISIBLE);
//			ListView listView = (ListView) view.findViewById(R.id.NewsList);
//			listView.setAdapter(new eventItemAdapter(eventDataList, getContext(), false));
//			listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//			{
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//				{
//					int EventID = eventDataList.get(position).eventID;
//					Intent intent = new Intent(getContext(), eventDetail.class);
//					intent.putExtra(getString(R.string.EventID), EventID);
//					intent.putExtra(getString(R.string.TabID), 0);
//					startActivity(intent);
//				}
//			});
//		}
		return view;
	}
}
