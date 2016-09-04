package com.nitkkr.gawds.ts16;

import java.util.ArrayList;

public class eventDatabase
{
	private ArrayList<eventData> eventDataList;

	public static eventDatabase Database =new eventDatabase();

	private eventDatabase()
	{
		eventDataList=new ArrayList<>();
	}

	public eventData getEventData(int eventId)
	{
		for(eventData data: eventDataList)
			if(data.eventID==eventId)
				return data;
		return new eventData();
	}

	private eventDatabase addEvent(eventData data)
	{
		eventDataList.add(data);
		return this;
	}

}
