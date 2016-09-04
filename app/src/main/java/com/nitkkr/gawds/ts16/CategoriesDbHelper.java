package com.nitkkr.gawds.ts16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by SAHIL SINGLA on 01-09-2016.
 */
public class CategoriesDbHelper  extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="ts16.db";
    private static final String TABLE_CATEGORIES="categories";
    private static final String id="id";
    private static final String name="name";
    public CategoriesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="Create table "+TABLE_CATEGORIES+" (" + id
                +" INTEGER PRIMARY KEY,"+name
                +" TEXT;";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
    }

    public void addCategory(EventCategory Category)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.beginTransaction();
        try{
            addorUpdateCategory(Category);
        }
        catch (Exception e)
        {
            Log.d("addEventExceptionCatch" ,"Error occurred while adding or updating event\n");
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }

    private void addorUpdateCategory(EventCategory category) {

        SQLiteDatabase db=getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues categoryValues=new ContentValues();
            categoryValues.put(id,category.id);
            categoryValues.put(name,category.category);
            int affectedRows=db.update(TABLE_CATEGORIES,categoryValues,id+" = "+ category.id,null);
            if(affectedRows!=1)
            {
                db.insertOrThrow(TABLE_CATEGORIES,null,categoryValues);
                db.setTransactionSuccessful();
            }
        }
        catch (Exception e)
        {
            Log.d("addorUpdateCategory", "Error while trying to add or update category");
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }
    public ArrayList<EventCategory> ReadDatabaseCategory()
    {
        SQLiteDatabase db=getReadableDatabase();
        db.beginTransaction();
        ArrayList<EventCategory> list=new ArrayList<>();
        try
        {
            String query;

                query="Select * from "+TABLE_CATEGORIES+";";

            Cursor categoryCursor=db.rawQuery(query,null);
            if(categoryCursor.moveToFirst())
            {
                EventCategory item=new EventCategory();
                item.id=(categoryCursor.getInt(categoryCursor.getColumnIndex(id)));
                item.category=(categoryCursor.getString(categoryCursor.getColumnIndex(name)));
                list.add(item);
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
