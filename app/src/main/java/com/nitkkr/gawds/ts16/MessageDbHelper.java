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

import java.util.ArrayList;

/**
 * Created by SAHIL SINGLA on 03-09-2016.
 */
class MessageDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="ts16.db";
    private static final String TABLE_MESSAGES="messages";
    private static final String id="id";
    private static final String Date="date";
    private static final String Title="title";
    private static final String News="message";
    Context context;
    public MessageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="Create table IF NOT EXISTS "+TABLE_MESSAGES+" (" + id
                +" INTEGER PRIMARY KEY,"+News
                +" TEXT," +Title+
                " TEXT," +Date+
                " TEXT);";
        try {
            sqLiteDatabase.execSQL(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_MESSAGES);
    }

    public void addMessage(SQLiteDatabase db,String message,int MessageId,String MDateTime,String MTitle)
    {
        ContentValues messageValues=new ContentValues();
        messageValues.put(id,MessageId);
        messageValues.put(News,message);
        messageValues.put(Date,MDateTime);
        messageValues.put(Title,MTitle);
        try
        {
            int rows=db.update(TABLE_MESSAGES,messageValues,id+"="+MessageId,null);
            if(rows<1) {
                db.insertOrThrow(TABLE_MESSAGES, null, messageValues);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
                builder.setContentTitle("Talent Show Notification");
                builder.setContentText(message);
                builder.setTicker(message);
//                builder.setSmallIcon()
                Intent i=new Intent(context,mainActivity.class);
                i.putExtra(context.getString(R.string.TabID),1);
                TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
                stackBuilder.addParentStack(mainActivity.class);
                stackBuilder.addNextIntent(i);
                PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setSmallIcon(R.drawable.events_icon);
                NotificationManager notification=(NotificationManager ) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notification.notify("MessageNotification",120,builder.build());
            }

        }
        catch (Exception e)
        {
            Log.d("addMessage ", "Error while trying to add message");
            e.printStackTrace();
        }

    }

    public ArrayList<MessageData> ReadDatabaseMessage(SQLiteDatabase db)
    {
        ArrayList<MessageData> list=new ArrayList<>();
        try
        {
            String query;
            query="Select * from "+TABLE_MESSAGES+";";
            Cursor categoryCursor=db.rawQuery(query,null);
            if(categoryCursor.getCount()>0)
            {
                categoryCursor.moveToFirst();
                do {
                    MessageData data=new MessageData();
                    data.Date=categoryCursor.getString(categoryCursor.getColumnIndex(Date));
                    data.Title=categoryCursor.getString(categoryCursor.getColumnIndex(Title));
                    data.News=categoryCursor.getString(categoryCursor.getColumnIndex(News));
                    list.add(data);
                    Log.d("Mesage ", categoryCursor.getString(categoryCursor.getColumnIndex(News)));
                }while(categoryCursor.moveToNext());
            }
            categoryCursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

        }
        return list;
    }

    class MessageData
    {
        public String Date;
        public String Title;
        public String News;
    }
}


