package com.nitkkr.gawds.ts16;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class dbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="ts16.db";
    private static final String TABLE_EVENTS="events";
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

    Context context;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="Create table IF NOT EXISTS "+TABLE_EVENTS+" (" + id
                +" INTEGER PRIMARY KEY,"+name
                +" TEXT,"+category
                +" INTEGER,"+venue
                +" TEXT,"+date
                +" TEXT,"+status
                +" INTEGER,"+scheduled_start
                +" TEXT,"+scheduled_end
                +" TEXT,"+duration
                +" TEXT,"+delay_status
                +" INTEGER,"+poster_name
                +" TEXT,"+description
                +" TEXT,"+event_coordinator
                +" TEXT,"+special
                +" INTEGER,"+rules
                +" TEXT,"+result
                +" TEXT,"+last_updated
                +" TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS);
        onCreate(sqLiteDatabase);
    }

    public void addEvent(SQLiteDatabase sqLiteDatabase,eventData Event)
    {
        try
        {
            addorUpdateEvent(sqLiteDatabase,Event);
        }
        catch (Exception e)
        {
//            Log.d("addEventExceptionCatch" ,"Error occurred while adding or updating event\n");
//            e.printStackTrace();
        }
    }

    private void addorUpdateEvent(SQLiteDatabase db,eventData event) {
        try {
            ContentValues eventValues = new ContentValues();

            String query="Select * from "+TABLE_EVENTS+" where "+id+"="+event.eventID+";";
            Cursor c=db.rawQuery(query,null);
            int count=c.getCount();
            if(count!=0)
            {
                c.moveToFirst();
                event.setBookmark(c.getInt(c.getColumnIndex(special)));
                eventValues=setEventContentValues(eventValues, event);

//                Log.d("Updating ",event.eventID+"");
                if((c.getString(c.getColumnIndex(result))).compareToIgnoreCase(event.Result)!=0 && event.isBookmarked())
                {
                    generate(event,"Result Declared");
                }

                db.update(TABLE_EVENTS,eventValues,"id = "+event.eventID,null);
                c.close();
//                Cursor cu=db.rawQuery("Select status from "+TABLE_EVENTS+" where id="+event.eventID+";",null);
//                cu.moveToFirst();
//                Log.d("Updating ",event.eventID+" "+eventValues.get("status")+" "+cu.getString(cu.getColumnIndex(status)));
//                cu.close();
            }
            else
            {
                eventValues=setEventContentValues(eventValues, event);
                try {
                    db.insertOrThrow(TABLE_EVENTS, null, eventValues);
                }
                catch (Exception e)
                {
//                    e.printStackTrace();
                }
            }
            c.close();
        } catch (Exception e) {

//            Log.d("addorUpdateEvent error ", "Error while trying to add or update event");
//            e.printStackTrace();
        } finally {
        }
    }
public  void generate(eventData event,String message)
{

    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
    builder.setContentTitle(event.eventName + " "+message);

    builder.setSmallIcon(R.drawable.events_icon);

    Intent i = new Intent(context, eventDetail.class);
    i.putExtra(context.getString(R.string.TabID), 2);
    i.putExtra(context.getString(R.string.EventID), event.eventID);

    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

    stackBuilder.addNextIntentWithParentStack(new Intent(context,mainActivity.class));
    stackBuilder.addNextIntent(i);
    builder.setVibrate(new long[] { 1000, 500});
    builder.setLights(Color.RED, 3000, 3000);
    builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
    builder.setAutoCancel(true);
    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(pendingIntent);
    Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.logots_16);
    builder.setLargeIcon(bitmap);
    NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notification.notify("ResultNotification", 2140+event.eventID, builder.build());
}
    public ArrayList<eventData> ReadDatabaseEvents( SQLiteDatabase db,int category)
    {

        ArrayList<eventData> list=new ArrayList<>();
        try
        {
            String query;
            if(category>0)
                query="Select * from "+TABLE_EVENTS+" where category = "+category+";";
            else
                query="Select * from "+TABLE_EVENTS+";";
            Cursor object=db.rawQuery(query,null);
            if(object.getCount()>0)
            {
                object.moveToFirst();
                do{
                    eventData item=new eventData();
                    item.eventID=object.getInt(object.getColumnIndex(id));
                    item.eventName=object.getString(object.getColumnIndex(name));
                    item.Category=object.getInt(object.getColumnIndex("category"));
                    item.Venue=object.getString(object.getColumnIndex(venue));
                    item.Day=object.getString(object.getColumnIndex(date));
                    item.Status=object.getInt(object.getColumnIndex(status));
                    item.Time=object.getString(object.getColumnIndex(scheduled_start));
                    item.EndTime=object.getString(object.getColumnIndex(scheduled_end));
                    item.Duration=object.getString(object.getColumnIndex(duration));
                    item.ImageID=object.getString(object.getColumnIndex(poster_name));
                    item.Description=object.getString(object.getColumnIndex(description));
                    item.setBookmark(object.getInt(object.getColumnIndex(special)));
                    item.Result=object.getString(object.getColumnIndex(result));
                    item.Rules=object.getString(object.getColumnIndex(rules));
                    item.Contact=object.getString(object.getColumnIndex(event_coordinator));
                    list.add(item);
                }while(object.moveToNext());
                object.close();
            }
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        }
        Collections.sort(list, new Comparator<eventData>() {
            @Override
            public int compare(eventData ob1, eventData ob2) {
                return 0;
            }
        });
        return list;
    }

    public ArrayList<eventData> GetUpcomingEvents(SQLiteDatabase db)
    {
        ArrayList<eventData> eventDataArrayList = this.ReadDatabaseEvents(db, 0);
        ArrayList<eventData> upcoming = new ArrayList<>();

        for (eventData data : eventDataArrayList)
        {
            try
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Log.d("timing ",data.Day + " " + data.Time);
                long BeginTime = ( format.parse(data.Day + " " + data.Time).getTime() );
                long currentTimeStamp = Calendar.getInstance().getTimeInMillis();
//                Log.d("times ",""+BeginTime+" "+currentTimeStamp);
                long lapse = TimeUnit.HOURS.toMillis(context.getResources().getInteger(R.integer.upcomingDuration));

                if (currentTimeStamp < BeginTime && currentTimeStamp + lapse > BeginTime && data.Status==0)
                {
                    if (data.code != eventStatusListener.StatusCode.Upcoming)
                        data.code = eventStatusListener.StatusCode.Upcoming;
                    upcoming.add(data);
                }
            }
            catch (Exception e)
            {
//                e.printStackTrace();
            }
        }
        return upcoming;
    }

    public ArrayList<eventData> GetOngoingEvents(SQLiteDatabase db)
    {
        ArrayList<eventData> eventDataArrayList=this.ReadDatabaseEvents(db,0);
        ArrayList<eventData> ongoing=new ArrayList<>();

        for(eventData data: eventDataArrayList)
        {
            try
            {
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                long BeginTime=(format.parse(data.Day+" "+ data.Time).getTime())/1000;
//                long EndTime=(format.parse(data.Day+" "+ data.EndTime).getTime())/1000;
                long currentTimeStamp=Calendar.getInstance().getTimeInMillis()/1000;

                if(currentTimeStamp<BeginTime+(3600*3) && data.Status==1)
                {
                    if(data.code!= eventStatusListener.StatusCode.Ongoing)
                        data.code= eventStatusListener.StatusCode.Ongoing;
                    ongoing.add(data);
                }
            }
            catch (Exception e)
            {
//                e.printStackTrace();
            }
        }
        return ongoing;
    }

    private ContentValues setEventContentValues(ContentValues eventValues,eventData event)
    {
        eventValues.put(dbHelper.id,event.eventID);
        eventValues.put(dbHelper.name,event.eventName);
        eventValues.put(dbHelper.category,event.Category);
        eventValues.put(dbHelper.venue,event.Venue);
        eventValues.put(dbHelper.date,event.Day);
        eventValues.put(dbHelper.status,event.Status);
        eventValues.put(dbHelper.scheduled_start,event.Time);
        eventValues.put(dbHelper.scheduled_end,event.EndTime);
        eventValues.put(dbHelper.event_coordinator,event.Contact);
        eventValues.put(dbHelper.duration,event.Duration);
        eventValues.put(dbHelper.poster_name,event.ImageID);
        eventValues.put(dbHelper.description,event.Description);
        eventValues.put(dbHelper.special,event.getBookmark());
        eventValues.put(dbHelper.rules,event.Rules);
        eventValues.put(dbHelper.result,event.Result);
        return eventValues;
    }

    public boolean updateBookmarkStatus(SQLiteDatabase db,int status,int ids)
    {
        String query="Update "+TABLE_EVENTS+" set "+special+" = "+status+" where "+id+"="+ids;
        try {
            db.execSQL(query);
            return true;
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        }
        db.close();
        return false;
    }

    public eventData GetEventById(SQLiteDatabase db, int eventID) {

        eventData item=new eventData();
        try
        {
            String query="Select * from "+TABLE_EVENTS+" where id= "+eventID+";";
            Cursor object=db.rawQuery(query,null);

            if(object.getCount()>0)
            {
                object.moveToFirst();
                do{
                    item.eventID=object.getInt(object.getColumnIndex(id));
                    item.eventName=object.getString(object.getColumnIndex(name));
                    item.Category=object.getInt(object.getColumnIndex("category"));
                    item.Venue=object.getString(object.getColumnIndex(venue));
                    item.Day=object.getString(object.getColumnIndex(date));
                    item.Status=object.getInt(object.getColumnIndex(status));
                    item.Time=object.getString(object.getColumnIndex(scheduled_start));
                    item.EndTime=object.getString(object.getColumnIndex(scheduled_end));
                    item.Duration=object.getString(object.getColumnIndex(duration));
                    item.ImageID=object.getString(object.getColumnIndex(poster_name));
                    item.Description=object.getString(object.getColumnIndex(description));
                    item.setBookmark(object.getInt(object.getColumnIndex(special)));
                    item.Contact=object.getString(object.getColumnIndex(event_coordinator));
                    item.Result=object.getString(object.getColumnIndex(result));
                    item.Rules=object.getString(object.getColumnIndex(rules));
                }while(object.moveToNext());
                object.close();
            }
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        }

        return item;

    }
}
