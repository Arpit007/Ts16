package com.nitkkr.gawds.ts16;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class eventDescTab extends Fragment implements eventData.eventDataListener
{
    private int EventID;
    static eventData data;
    public static eventDescTab CreateFragment(Bundle bundle)
    {
        eventDescTab tab = new eventDescTab();
        tab.EventID=bundle.getInt(tab.getString(R.string.EventID));
        dbHelper helper=new dbHelper(tab.getContext());
        data=helper.GetEventById(helper.getReadableDatabase(),tab.EventID);
        helper.close();
//      eventData data = eventDatabase.Database.getEventData(tab.EventID);
        data.addEventDataListener(tab);
        return tab;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventUpdated(data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_desc_tab, container, false);
    }

    @Override
    public void eventUpdated(eventData event)
    {
        EventID=event.eventID;
        View view=getView();
        ((TextView)(view.findViewById(R.id.eventDescriptionDuration))).setText(event.Duration);
        ((TextView)(view.findViewById(R.id.eventDescriptionText))).setText(event.Description);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        data.removeDataListener(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        data.removeDataListener(this);
//        eventDatabase.Database.getEventData(EventID).removeDataListener(this);
    }
}
