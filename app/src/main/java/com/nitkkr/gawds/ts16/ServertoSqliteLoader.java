package com.nitkkr.gawds.ts16;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ServertoSqliteLoader extends IntentService
{
    private static final String id="id";
    private static final String name="name";
    private static final String category="category";
    private static final String venue="venue";
    private static final String date="date";
    private static final String status="status";
    private static final String scheduled_start="scheduled_start";
    private static final String scheduled_end="scheduled_end";
    private static final String duration="duration";
    private static final String contact="event_coordinator";
    private static final String poster_name="poster_name";
    private static final String description="description";
    private static final String rules="rules";
    private static final String special="special";
    private static final String result="result";

    public ServertoSqliteLoader() {
        super("ServertoSqliteLoader Service");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d("ServiceTag","Thread Run");
        httpRequest rh=new httpRequest();
        String EventJsonString;
        EventJsonString=rh.SendGetRequest("http://www.almerston.com/nitkkr2110/TS/events.php?category=0");
        Log.d("EventJSon ", EventJsonString );
        try
        {
            JSONObject EventsObject = new JSONObject(EventJsonString);
            JSONArray EventsArray = EventsObject.getJSONArray(getString(R.string.EventsJSONArray));
            int length = EventsArray.length();

            dbHelper helper = new dbHelper(getBaseContext());
            for (int i = 0; i < length; i++)
            {
                JSONObject object = EventsArray.getJSONObject(i);
                eventData item = new eventData();
                item.eventID = Integer.parseInt(object.getString(id));
                item.eventName = object.getString(name);
                item.Category = Integer.parseInt(object.getString(category));
                item.Venue = object.getString(venue);
                item.Day = object.getString(date);
                item.Status = Integer.parseInt(object.getString(status));
                item.Time = object.getString(scheduled_start);
                item.EndTime = object.getString(scheduled_end);
                item.Duration = object.getString(duration);
                item.ImageID = object.getString(poster_name);
                item.Description = object.getString(description);
                item.setBookmark(Integer.parseInt(object.getString(special)));
                item.Result = object.getString(result);
                item.Rules = object.getString(rules);
                item.Contact = object.getString(contact);

                helper.addEvent(helper.getWritableDatabase(), item);
            }

            helper.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            // Category
            String CategoryJson = rh.SendGetRequest(getString(R.string.Categories));
            Log.d("CategoryJson", CategoryJson);
            JSONObject CategoriesJson = new JSONObject(CategoryJson);
            JSONArray CategoryArray = CategoriesJson.getJSONArray("cats");
            int length2 = CategoryArray.length();
            for (int i = 0; i < length2; i++)
            {
                CategoriesDbHelper categoriesDbHelper = new CategoriesDbHelper(getBaseContext());
                JSONObject object = CategoryArray.getJSONObject(i);
                eventCategory category = new eventCategory();
                category.id = object.getInt(id);
                category.category = object.getString(name);
                categoriesDbHelper.addCategory(categoriesDbHelper.getWritableDatabase(), category);
                categoriesDbHelper.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            // Notification
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final Calendar calendar = Calendar.getInstance();
            dbHelper NotificationDbHelper = new dbHelper(getBaseContext());
            ArrayList<eventData> list = NotificationDbHelper.GetUpcomingEvents(NotificationDbHelper.getReadableDatabase());
            NotificationDbHelper.close();
            SharedPreferences upcomingnotificationPreferences = getSharedPreferences("upcomingPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor upcomingEditor = upcomingnotificationPreferences.edit();
            int length3 = list.size();
            for (int i = 0; i < length3; i++)
            {
                Log.d("upcoming here", "in upcomin");
                eventData item = list.get(i);
                Date eventDate = simpleDateFormat.parse(item.Day + " " + item.Time);
                Long eventTimeStamp = ( eventDate.getTime() ) / 1000;
                Long currentTimeStamp = ( calendar.getTimeInMillis() ) / 1000;
                boolean notificationGenerated = upcomingnotificationPreferences.contains("" + item.eventID);
                if (item.isBookmarked() && currentTimeStamp + 1800 >= eventTimeStamp && !notificationGenerated)
                {
                    Log.d("Notification Released", "notified" + item.eventID);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
                    builder.setContentTitle(item.eventName + " Beginning Soon");
                    builder.setContentText(item.eventName + " is beginning in about 30 minutes from now.\n" + item.Venue);
                    builder.setTicker(item.eventName + " is beginning in about 30 minutes from now.\n" + item.Venue);
                    Intent resultIntent = new Intent(getBaseContext(), eventDetail.class);
                    resultIntent.putExtra(getBaseContext().getString(R.string.TabID), 0);
                    resultIntent.putExtra(getBaseContext().getString(R.string.EventID), item.eventID);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
                    stackBuilder.addNextIntentWithParentStack(new Intent(getBaseContext(), mainActivity.class));
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    builder.setOnlyAlertOnce(true);
                    builder.setVibrate(new long[]{ 1000, 1000, 1000, 1000, 1000 });
                    builder.setLights(Color.RED, 3000, 3000);
                    builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                    builder.setSmallIcon(R.drawable.events_icon);
                    builder.setAutoCancel(true);
                    upcomingEditor.putInt("" + item.eventID, item.eventID);
                    upcomingEditor.apply();
                    NotificationManager notification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notification.notify("UpcomingEventNotification", 100 + item.eventID, builder.build());
                }
            }
            NotificationDbHelper.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
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
                int x=0;
                if(i==length4-1)
                    x=1;
                MessageHelper.addMessage(MessageHelper.getWritableDatabase(), object.getString("message"),object.getInt("id"),object.getString("date"),object.getString("title"),x);
            }
            MessageHelper.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
