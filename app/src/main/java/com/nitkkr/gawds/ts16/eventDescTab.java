package com.nitkkr.gawds.ts16;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
            Date date=simpleDateFormat.parse(event.Duration);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            int minutes=cal.get(Calendar.MINUTE);
            String time=Integer.toString(hours);
            if(minutes!=0)
                time+="."+minutes+" hours";
            else if(hours==1)
                time+=" hour";
            else time+=" hours";

            ((TextView)view.findViewById(R.id.eventDescriptionDuration)).setText(time);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ((TextView)(view.findViewById(R.id.eventDescriptionText))).setText(event.Description);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Font2.ttf");
        (( TextView)view.findViewById(R.id.eventDescriptionDuration)).setTypeface(font);
        font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Font1.ttf");
        (( TextView)view.findViewById(R.id.eventDescriptionText)).setTypeface(font);
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
