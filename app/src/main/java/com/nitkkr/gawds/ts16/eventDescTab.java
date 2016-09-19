package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.crypto.NullCipher;

public class eventDescTab extends Fragment implements eventData.eventDataListener
{
    private int EventID;
    static eventData data;
    View view;
    public void setUpFragment(int eventID, Context context)
    {
        EventID=eventID;
        dbHelper helper=new dbHelper(context);
        data=helper.GetEventById(helper.getReadableDatabase(),EventID);
        helper.close();
        data.addEventDataListener(this);
        eventUpdated(data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_event_desc_tab, container, false);
        eventUpdated(data);
        return view;
    }

    @Override
    public void eventUpdated(eventData event)
    {
        EventID=event.eventID;
        if(view== null)
            return;
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
    }
}
