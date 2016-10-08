package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.util.Log;

public class eventData
{
	public int eventID;
	public int Category;
	public String Duration;
	public String eventName;
	private boolean bookmark;
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

	boolean isResultDeclared()
	{
		return (!Result.equals("") || Result.length()>10 || Result==null);
	}

	boolean isBookmarked()
	{
		return bookmark;
	}

	int getBookmark()
	{
		return (bookmark)?1:0;
	}

	void setBookmark(int bookmark)
	{
		this.bookmark=(bookmark==1);
	}

	boolean updateBookmark(Context c,boolean bookmarked)
	{

		if(bookmark==bookmarked)
			return false;

		Log.d("bookmarks", bookmark+" and "+bookmarked);

		bookmark=bookmarked;

		dbHelper helper=new dbHelper(c);
		helper.updateBookmarkStatus(helper.getReadableDatabase(),getBookmark(),this.eventID);
		helper.close();

		return true;
	}

	public int getImageResourceID()
	{
		int resID;
		try
		{
			resID=Integer.parseInt(ImageID);
		}
		catch (Exception e)
		{
			return -1;
		}
		return resID;
	}
}