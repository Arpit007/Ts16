package com.nitkkr.gawds.ts16;

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


public class eventDescTab extends Fragment
{
    eventData data;
    View view;

    public void setUpFragment(eventData event)
    {
        data=event;
        if(data==null)
            data=new eventData();
        eventUpdated();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_event_desc_tab, container, false);
        eventUpdated();
        return view;
    }

    public void eventUpdated()
    {
        if(view== null)
            return;

        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
            Date date=simpleDateFormat.parse(data.Duration);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int hours = cal.get(Calendar.HOUR_OF_DAY), minutes=cal.get(Calendar.MINUTE);

            String time=Integer.toString(hours);

            if(minutes!=0)
                time+="."+minutes+" hours";
            else if(hours==1)
                time+=" hour";
            else time+=" hours";
            if(hours==0)
            {
                view.findViewById(R.id.eventDescriptionDuration).setVisibility(View.INVISIBLE);
            }
            else
            {
                view.findViewById(R.id.eventDescriptionDuration).setVisibility(View.VISIBLE);
            }
            ((TextView) view.findViewById(R.id.eventDescriptionDuration)).setText(time);
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        }

        ((TextView)(view.findViewById(R.id.eventDescriptionText))).setText(data.Description);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Font2.ttf");
        (( TextView)view.findViewById(R.id.eventDescriptionDuration)).setTypeface(font);
            (( TextView)view.findViewById(R.id.eventDescriptionText)).setTypeface(font);

    }
}
