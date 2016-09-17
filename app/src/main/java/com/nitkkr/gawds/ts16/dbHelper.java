package com.nitkkr.gawds.ts16;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SAHIL SINGLA on 01-09-2016.
 */
    public class dbHelper extends SQLiteOpenHelper{

    public static dbHelper DbHelper;
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
        DbHelper=this;
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
        try{
            addorUpdateEvent(sqLiteDatabase,Event);
        }
        catch (Exception e)
        {
            Log.d("addEventExceptionCatch" ,"Error occurred while adding or updating event\n");
            e.printStackTrace();
        }
    }

    private void addorUpdateEvent(SQLiteDatabase db,eventData event) {
        try {
            ContentValues eventValues = new ContentValues();
            eventValues=setEventContentValues(eventValues, event);
            String query="Select * from "+TABLE_EVENTS+" where "+id+"="+event.eventID+";";
            Cursor c=db.rawQuery(query,null);
            int count=c.getCount();
            if(count!=0)
            {
                c.moveToFirst();
                if(((c.getString(c.getColumnIndex(venue))).compareToIgnoreCase(event.Venue)!=0 ||
                        (c.getString(c.getColumnIndex(scheduled_start))).compareToIgnoreCase(event.Time)!=0)
                        && (c.getInt(c.getColumnIndex(special))==1))
                {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentTitle(event.eventName);
                    builder.setContentText(event.eventName+" has been updated! Check Here.");
                    Intent i = new Intent(context, eventDetail.class);
                    i.putExtra(context.getString(R.string.TabID), 2);
                    i.putExtra(context.getString(R.string.EventID), event.eventID);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                    stackBuilder.addParentStack(eventDetail.class);
                    stackBuilder.addNextIntent(i);
                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notification.notify("ResultNotification", 140, builder.build());
                }
                if((c.getString(c.getColumnIndex(result))).compareToIgnoreCase(event.Result)!=0)
                {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                        builder.setContentTitle("Results for " + event.eventName + " declared");
                        Intent i = new Intent(context, eventDetail.class);
                        i.putExtra(context.getString(R.string.TabID), 2);
                        i.putExtra(context.getString(R.string.EventID), event.eventID);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                        stackBuilder.addParentStack(eventDetail.class);
                        stackBuilder.addNextIntent(i);
                        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);
                        NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notification.notify("ResultNotification", 140, builder.build());
                }
                db.update(TABLE_EVENTS,eventValues,"id = "+event.eventID,null);
            }
            else
            {
                try {
                    db.insertOrThrow(TABLE_EVENTS, null, eventValues);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            c.close();
        } catch (Exception e) {
            Log.d("addorUpdateEvent error ", "Error while trying to add or update event");
            e.printStackTrace();
        } finally {
        }
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

            Log.d("cnt", String.valueOf(object.getCount()));

            Log.d("cnt", "sas,");
            Log.d("cnt", String.valueOf(object.getCount()));
            Log.d("cnt", String.valueOf(object.getCount()));

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
                    item.Status=object.getInt(object.getColumnIndex(delay_status));
                    item.ImageID=object.getString(object.getColumnIndex(poster_name));
                    item.Description=object.getString(object.getColumnIndex(description));
                    item.EventCoordinators=object.getString(object.getColumnIndex(event_coordinator));
                    item.bookmark=object.getInt(object.getColumnIndex(special));
                    item.Result=object.getString(object.getColumnIndex(result));
                    item.Rules=object.getString(object.getColumnIndex(rules));
                    item.TimeStamp=object.getString(object.getColumnIndex(last_updated));
                    list.add(item);
                    Log.d("categoryf  ", String.valueOf(item.eventID));
                }while(object.moveToNext());
                object.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<eventData> GetUpcominEvents(SQLiteDatabase db)
    {
        ArrayList<eventData> all=this.ReadDatabaseEvents(db,0);
        ArrayList<eventData> upcoming=new ArrayList<>();
        int length=all.size();
        for(int i=0;i<length;i++)
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar=Calendar.getInstance();
            eventData item=all.get(i);
            try {
                Date eventDate=simpleDateFormat.parse(item.Day+" "+item.Time);
                Long eventTimeStamp=(eventDate.getTime())/1000;
                Long currentTimeStamp=(calendar.getTimeInMillis())/1000;
                if(eventTimeStamp<=currentTimeStamp+10800)
                {
                    upcoming.add(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return upcoming;

    }




    public ArrayList<eventData> GetOngoingEvents(SQLiteDatabase db)
    {
        ArrayList<eventData> all=this.ReadDatabaseEvents(db,0);
        ArrayList<eventData> ongoing=new ArrayList<>();
        int length=all.size();
        for(int i=0;i<length;i++)
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar=Calendar.getInstance();
            eventData item=all.get(i);
            try {
                Date eventDate=simpleDateFormat.parse(item.Day+" "+item.EndTime);
                Long eventTimeStamp=(eventDate.getTime())/1000;
                Long currentTimeStamp=(calendar.getTimeInMillis())/1000;
                if(eventTimeStamp>=currentTimeStamp)
                {
                    ongoing.add(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ongoing;
    }

    private ContentValues setEventContentValues(ContentValues eventValues,eventData event) {
        eventValues.put(dbHelper.id,event.eventID);
        eventValues.put(dbHelper.name,event.eventName);
        eventValues.put(dbHelper.category,event.Category);
        eventValues.put(dbHelper.venue,event.Venue);
        eventValues.put(dbHelper.date,event.Day);
        eventValues.put(dbHelper.status,event.Status);
        eventValues.put(dbHelper.scheduled_start,event.Time);
        eventValues.put(dbHelper.scheduled_end,event.EndTime);
        eventValues.put(dbHelper.duration,event.Duration);
        eventValues.put(dbHelper.delay_status,event.Status);
        eventValues.put(dbHelper.poster_name,event.ImageID);
        eventValues.put(dbHelper.description,event.Description);
        eventValues.put(dbHelper.event_coordinator,event.EventCoordinators);
        eventValues.put(dbHelper.special,event.bookmark);
        eventValues.put(dbHelper.rules,event.Rules);
        eventValues.put(dbHelper.result,event.Result);
        eventValues.put(dbHelper.last_updated,event.TimeStamp);
        return eventValues;
    }

    public void updateBookmarkStatus(int status,int ids)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query="Update "+TABLE_EVENTS+" set "+special+" = "+status+" where "+id+"="+ids;
        try {
            db.execSQL(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        db.close();
    }
}
