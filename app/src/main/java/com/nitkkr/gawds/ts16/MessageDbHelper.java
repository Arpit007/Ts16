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
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="ts16.db";
    private static final String TABLE_MESSAGES="messages";
    private static final String id="id";
    private static final String name="message";
    Context context;
    public MessageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="Create table "+TABLE_MESSAGES+" (" + id
                +" INTEGER PRIMARY KEY,"+name
                +" TEXT;";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_MESSAGES);
    }
    public void addMessage(String message,int MessageId)
    {
        ContentValues messageValues=new ContentValues();
        messageValues.put(id,MessageId);
        messageValues.put(name,message);
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
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
                NotificationManager notification=(NotificationManager ) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notification.notify("MessageNotification",120,builder.build());
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d("addMessage ", "Error while trying to add message");
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }
    public ArrayList<String> ReadDatabaseMessage()
    {
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        ArrayList<String> list=new ArrayList<>();
        try
        {
            String query;
            query="Select * from "+TABLE_MESSAGES+";";
            Cursor categoryCursor=db.rawQuery(query,null);
            if(categoryCursor.moveToFirst())
            {
                list.add(categoryCursor.getString(categoryCursor.getColumnIndex(name)));
                Log.d("Mesage ",categoryCursor.getString(categoryCursor.getColumnIndex(name)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
        return list;
    }
}


