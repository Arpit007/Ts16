package com.nitkkr.gawds.ts16;

import android.util.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class eventData
{
	public int eventID;
	public int Category;
	public String Duration;
	public String EventCoordinators;
	public String eventName;
	public int bookmark;
	public String Day;
	public String Time;
	public String EndTime;
	public int Status;
	public String Venue;
	public String Description;
	public String Rules;
	public String Result;
	public String Contact;
	public String ImageID;
	public long TimeStamp;

	boolean isResultDeclared()
	{
		return (Result.equals(""))? true:false;
	}

	public interface eventDataListener
	{
		void eventUpdated(eventData event);
	}

	private ArrayList<eventDataListener> eventListenerList;

	public eventData()
	{
		eventListenerList=new ArrayList<>();
	}

	public void addEventDataListener(eventDataListener dataListener)
	{
		eventListenerList.add(dataListener);
	}

	public void removeDataListener(eventDataListener dataListener)
	{
		eventListenerList.remove(dataListener);
	}

	public void UpdateEvent()
	{
		for(eventDataListener listener:eventListenerList)
			listener.eventUpdated(this);
	}

	public void UpdateEvent(eventData data)
	{
		EndTime=data.EndTime;
		eventID=data.eventID;
		Category=data.Category;
		Duration=data.Duration;
		EventCoordinators=data.EventCoordinators;
		eventName=data.eventName;
		bookmark=data.bookmark;
		Day=data.Day;
		Time=data.Time;
		Status= data.Status;
		Venue=data.Venue;
		Description=data.Description;
		Rules=data.Rules;
		Result=data.Result;
		Contact=data.Contact;
		ImageID=data.ImageID;
		TimeStamp=data.TimeStamp;
		TimeStamp=data.TimeStamp;
		UpdateEvent();
	}

}