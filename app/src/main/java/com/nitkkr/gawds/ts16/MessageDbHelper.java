package com.nitkkr.gawds.ts16;

import android.app.Notification;
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

import java.util.ArrayList;


class MessageDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="ts16.db";
    private static final String TABLE_MESSAGES="messages";
    private static final String id="id";
    private static final String Date="date";
    private static final String Title="title";
    private static final String News="message";

    private static ArrayList<MessageData> messageDataArrayList;
    private static MessageDbHelper messageDbHelper;
    Context context;
    private boolean Updated=true;

    public MessageDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

        if(messageDataArrayList==null)
        {
            messageDataArrayList=ReadDatabaseMessage(getWritableDatabase());
            close();
        }
        messageDbHelper=this;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String query="Create table IF NOT EXISTS "+TABLE_MESSAGES+" (" + id
                +" INTEGER PRIMARY KEY,"+News
                +" TEXT," +Title+
                " TEXT," +Date+
                " TEXT);";
        try
        {
            sqLiteDatabase.execSQL(query);
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_MESSAGES);
    }

    public void addMessage(SQLiteDatabase db,String message,int MessageId,String MDateTime,String MTitle,int generate_notif)
    {
        ContentValues messageValues=new ContentValues();
        messageValues.put(id,MessageId);
        messageValues.put(News,message);
        messageValues.put(Date,MDateTime);
        messageValues.put(Title,MTitle);

        try
        {
            int rows=db.update(TABLE_MESSAGES,messageValues,id+"="+MessageId,null);
            if(rows<1)
            {
                db.insertOrThrow(TABLE_MESSAGES, null, messageValues);
                if(generate_notif==1) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                    builder.setContentTitle("TS\' 16: New Notification");
                    builder.setContentText(message);
                    builder.setTicker(message);

                    Intent i = new Intent(context, mainActivity.class);
                    i.putExtra(context.getString(R.string.TabID), 1);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                    stackBuilder.addParentStack(mainActivity.class);
                    stackBuilder.addNextIntent(i);
                    builder.setAutoCancel(true);
                    builder.setVibrate(new long[]{1000,500});
                    builder.setLights(Color.GREEN, 3000, 3000);
                    builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    builder.setSmallIcon(R.drawable.events_icon);
                    Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.logots_16);
                    builder.setLargeIcon(bitmap);
                    NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notification.notify("MessageNotification", 120, builder.build());
                }
                Updated=true;
            }

        }
        catch (Exception e)
        {
//            Log.d("addMessage ", "Error while trying to add message");
//            e.printStackTrace();
        }
    }

    public ArrayList<MessageData> ReadDatabaseMessage(SQLiteDatabase db)
    {
        ArrayList<MessageData> list=new ArrayList<>();
        try
        {
            String query="Select * from "+TABLE_MESSAGES+";";
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
//            e.printStackTrace();
        }
        return list;
    }

    static public ArrayList<MessageData> getMessageList(Context context)
    {
        if(messageDataArrayList==null)
            new MessageDbHelper(context);

        messageDbHelper.Updated=false;

        return (ArrayList<MessageData>)messageDbHelper.messageDataArrayList.clone();
    }

    public static boolean isUpdated(Context context)
    {
        if(messageDbHelper==null)
            new MessageDbHelper(context);

        return messageDbHelper.Updated;
    }

    class MessageData
    {
        public String Date;
        public String Title;
        public String News;
    }
}


