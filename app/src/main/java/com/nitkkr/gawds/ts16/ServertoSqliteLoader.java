package com.nitkkr.gawds.ts16;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SAHIL SINGLA on 01-09-2016.
 */
public class ServertoSqliteLoader extends Service {
    private static final String id="id";
    private static final String name="name";
    private static final String category="category";
    private static final String venue="venue";
    private static final String date="date";
    private static final String status="status";
    private static final String scheduled_start="scheduled_start";
    private static final String scheduled_end="scheduled_end";
    private static final String duration="duration";
    private static final String delay_status="delay_status";
    private static final String poster_name="poster_name";
    private static final String description="description";
    private static final String rules="rules";
    private static final String event_coordinator="event_coordinator";
    private static final String special="special";
    private static final String result="result";
    private static final String last_updated="last_updated";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                httpRequest rh=new httpRequest();
                String EventJsonString;
                EventJsonString=rh.SendGetRequest(getString(R.string.EventsByCategory));
                Log.d("EventJSon ", EventJsonString );
                try {

                    JSONObject EventsObject = new JSONObject(EventJsonString);
                    JSONArray EventsArray = EventsObject.getJSONArray(getString(R.string.EventsJSONArray));
                    int length=EventsArray.length();
                    dbHelper helper=new dbHelper(getBaseContext());
                    for(int i=0;i<length;i++)
                    {
                        JSONObject object=EventsArray.getJSONObject(i);
                        eventData item=new eventData();
                        item.eventID=object.getInt(id);
                        item.eventName=object.getString(name);
                        item.Category=object.getInt(category);
                        item.Venue=object.getString(venue);
                        item.Day=object.getString(date);
                        item.Status=object.getInt(status);
                        item.Time=object.getString(scheduled_start);
                        item.EndTime=object.getString(scheduled_end);
                        item.Duration=object.getString(duration);
                        item.Status=object.getInt(delay_status);
                        item.ImageID=object.getString(poster_name);
                        item.Description=object.getString(description);
                        item.EventCoordinators=object.getString(event_coordinator);
                        item.bookmark=object.getInt(special);
                        item.Result=object.getString(result);
                        item.Rules=object.getString(rules);
                        item.TimeStamp=object.getLong(last_updated);
                        helper.addEvent(item);
                    }
                    Thread.sleep(10000);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
