package com.nitkkr.gawds.ts16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    public MessageDbHelper(Context context) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="Create table "+TABLE_MESSAGES+" (" + id
                +" INTEGER PRIMARY KEY,"+name
                +" TEXT;";
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
            int rows=db.update(TABLE_MESSAGES,messageValues,null,null);
            db.insertOrThrow(TABLE_MESSAGES,name,messageValues);
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


