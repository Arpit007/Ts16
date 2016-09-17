package com.nitkkr.gawds.ts16;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class eventListExperimental extends AppCompatActivity
{
	ExpandableListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list_experimental);
		listView=(ExpandableListView)findViewById(R.id.event_list);
	}

	public void setData()
	{
		CategoriesDbHelper helper=new CategoriesDbHelper(getBaseContext());
		ArrayList<EventCategory> list=helper.ReadDatabaseCategory(helper.getWritableDatabase());
		helper.close();
	}

}

class EventListAdapter implements ExpandableListAdapter
{
	@Override
	public boolean areAllItemsEnabled()
	{
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{

	}

	@Override
	public int getGroupCount()
	{
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return null;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		return null;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return false;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public void onGroupExpanded(int groupPosition)
	{

	}

	@Override
	public void onGroupCollapsed(int groupPosition)
	{

	}

	@Override
	public long getCombinedChildId(long groupId, long childId)
	{
		return 0;
	}

	@Override
	public long getCombinedGroupId(long groupId)
	{
		return 0;
	}
}