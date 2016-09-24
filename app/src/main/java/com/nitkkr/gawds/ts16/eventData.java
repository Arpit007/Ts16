package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class eventData
{
	public int eventID;
	public int Category;
	public String Duration;
	public String eventName;
	public int bookmark;
	public String Day;
	public String Time;
	public String EndTime;
	public int Status;
	public eventStatusListener.StatusCode code= eventStatusListener.StatusCode.None;
	public String Venue;
	public String Description;
	public String Rules;
	public String Result;
	public String Contact;
	public String ImageID;
	public Boolean notificationGenerated;

	boolean isResultDeclared()
	{
		return (Result.equals("") || Result==null)? false:true;
	}

	boolean isBookmarked()
	{
		return (bookmark==1)? true:false;
	}

	void updateBookmark(Context c,boolean bookmarked)
	{
		int bookmarkX=(bookmarked)?1:0;

		if(bookmark==bookmarkX)
			return;

		Log.d("bookmarks", bookmarked+" and "+bookmark);

		dbHelper helper=new dbHelper(c);
		helper.updateBookmarkStatus(helper.getReadableDatabase(),bookmark,this.eventID);
		helper.close();

		UpdateEvent();
	}

	public interface eventDataListener
	{
		void eventUpdated(eventData event);
	}

	private ArrayList<eventDataListener> eventListenerList;

	public eventData()
	{
		eventListenerList=new ArrayList<>();
		this.notificationGenerated = false;
	}

	public void addEventDataListener(eventDataListener dataListener)
	{
		if(!eventListenerList.contains(dataListener))
			eventListenerList.add(dataListener);
	}

	public void removeDataListener(eventDataListener dataListener)
	{
		eventListenerList.remove(dataListener);
	}

	public void UpdateEvent()
	{
		for (eventDataListener listener : eventListenerList)
			try
			{
					if(listener!=null)
						listener.eventUpdated(this);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
	}

	public int getImageResourceID()
	{
		int resID=-1;
		try
		{
			resID=Integer.parseInt(ImageID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -2;//resID;
	}
}
