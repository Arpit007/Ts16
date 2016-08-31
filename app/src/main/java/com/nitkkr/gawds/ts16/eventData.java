package com.nitkkr.gawds.ts16;

import java.util.ArrayList;

public class eventData
{
	public int eventID;
	public String eventName;
	public int bookmark;
	public String Day;
	public String Time;
	public int Status;
	public String Location;
	public String Description;
	public String Rules;
	public String Result;
	public boolean ResultDeclared;
	public String Contact;
	public int ImageID;
	private int TimeStamp;


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

	public void UpdateEvent()
	{
		for(eventDataListener listener:eventListenerList)
			listener.eventUpdated(this);
	}

	public void UpdateEvent(eventData data)
	{
		eventID=data.eventID;
		eventName=data.eventName;
		bookmark=data.bookmark;
		Day=data.Day;
		Time=data.Time;
		Status= data.Status;
		Location=data.Location;
		Description=data.Description;
		Rules=data.Rules;
		Result=data.Result;
		ResultDeclared=data.ResultDeclared;
		Contact=data.Contact;
		ImageID=data.ImageID;
		TimeStamp=data.TimeStamp;
		UpdateEvent();
	}

}
