package com.nitkkr.gawds.ts16;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
                //event
                httpRequest rh=new httpRequest();
                String EventJsonString;
                EventJsonString=rh.SendGetRequest("http://www.almerston.com/nitkkr2110/TS/events.php?category=0");
                Log.d("EventJSon ", EventJsonString );
                try {

                    JSONObject EventsObject = new JSONObject(EventJsonString);
                    JSONArray EventsArray = EventsObject.getJSONArray(getString(R.string.EventsJSONArray));
                    int length=EventsArray.length();

                    for(int i=0;i<length;i++)
                    {
                        dbHelper helper=new dbHelper(getBaseContext());
                        JSONObject object=EventsArray.getJSONObject(i);
                        eventData item=new eventData();
                        item.eventID=Integer.parseInt(object.getString(id));
                        item.eventName=object.getString(name);
                        item.Category=Integer.parseInt(object.getString(category));
                        item.Venue=object.getString(venue);
                        item.Day=object.getString(date);
                        item.Status=Integer.parseInt(object.getString(status));
                        item.Time=object.getString(scheduled_start);
                        item.EndTime=object.getString(scheduled_end);
                        item.Duration=object.getString(duration);
                        item.DelayStatus=object.getInt(delay_status);
                        item.ImageID=object.getString(poster_name);
                        item.Description=object.getString(description);
                        item.EventCoordinators=object.getString(event_coordinator);
                        item.bookmark=Integer.parseInt(object.getString(special));
                        item.Result=object.getString(result);
                        item.Rules=object.getString(rules);
                        item.TimeStamp=object.getString(last_updated);
                        helper.addEvent(helper.getWritableDatabase(),item);
                        helper.close();
                    }
                    // Category
                    String CategoryJson=rh.SendGetRequest(getString(R.string.Categories));
                    Log.d("CategoryJson",CategoryJson);
                    JSONObject CategoriesJson = new JSONObject(CategoryJson);
                    JSONArray CategoryArray = CategoriesJson.getJSONArray("cats");
                    int length2=CategoryArray.length();
                    for (int i=0;i<length2;i++)
                    {
                        CategoriesDbHelper categoriesDbHelper=new CategoriesDbHelper(getBaseContext());
                        JSONObject object=CategoryArray.getJSONObject(i);
                        eventCategory category=new eventCategory();
                        category.id=object.getInt(id);
                        category.category=object.getString(name);
                        categoriesDbHelper.addCategory(categoriesDbHelper.getWritableDatabase(),category);
                        categoriesDbHelper.close();
                    }
                    // Notification
                    final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    final Calendar calendar=Calendar.getInstance();
                    dbHelper NotificationDbHelper=new dbHelper(getBaseContext());
                    ArrayList<eventData> list=NotificationDbHelper.GetUpcomingEvents(NotificationDbHelper.getReadableDatabase());
                    NotificationDbHelper.close();
                    int length3=list.size();
                    for(int i=0;i<length3;i++)
                    {
                        eventData item=list.get(i);
                        Date eventDate=simpleDateFormat.parse(item.Day+" "+item.Time);
                        Long eventTimeStamp=(eventDate.getTime())/1000;
                        Long currentTimeStamp=(calendar.getTimeInMillis())/1000;
                        if(item.bookmark==1 && item.notificationGenerated==false && currentTimeStamp+1800<=eventTimeStamp)
                        {
                            item.notificationGenerated=true;
                            NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());
                            builder.setContentTitle(item.eventName+" Beginning Soon");
                            builder.setContentText(item.eventName+" is beginning in about 30 minutes from now.\n"+item.Venue);
                            builder.setTicker(item.eventName+" is beginning in about 30 minutes from now.\n"+item.Venue);
                            Intent resultIntent=new Intent(getBaseContext(),eventDetail.class);
                            TaskStackBuilder stackBuilder=TaskStackBuilder.create(getBaseContext());
                            stackBuilder.addParentStack(eventDetail.class);
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notification=(NotificationManager )getSystemService(Context.NOTIFICATION_SERVICE);
                            notification.notify("UpcomingEventNotification",100,builder.build());
                        }
                    }
                    NotificationDbHelper.close();
                    //messages
                    MessageDbHelper MessageHelper= new MessageDbHelper(getBaseContext());
                    String MessageJson=rh.SendGetRequest("http://www.almerston.com/nitkkr2110/TS/messages.php");
                    JSONObject jsonObject=new JSONObject(MessageJson);
                    Log.d("MESSAGEJSON ", MessageJson);
                    JSONArray messages=jsonObject.getJSONArray("messages");
                    int length4=messages.length();
                    for(int i=0;i<length4;i++)
                    {
                        JSONObject object=messages.getJSONObject(i);
                        MessageHelper.addMessage(MessageHelper.getWritableDatabase(), object.getString("message"),object.getInt("id"),object.getString("date"),object.getString("title"));
                    }
                    MessageHelper.close();
                }
            catch (Exception e)
            {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * A simple {@link Fragment} subclass.
     */

}
