package com.nitkkr.gawds.ts16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class CategoriesDbHelper  extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="ts16.db";
    private static final String TABLE_CATEGORIES="categories";
    private static final String id="id";
    private static final String name="name";

    public CategoriesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
        this.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="Create table IF NOT EXISTS "+TABLE_CATEGORIES+" (" + id
                +" INTEGER PRIMARY KEY,"+name
                +" TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
    }

    public void addCategory(SQLiteDatabase db,eventCategory Category)
    {
        try{
            addorUpdateCategory(db,Category);
        }
        catch (Exception e)
        {
//            Log.d("addEventExceptionCatch" ,"Error occurred while adding or updating event\n");
//            e.printStackTrace();
        }

    }

    private void addorUpdateCategory(SQLiteDatabase db,eventCategory category) {

        try{
            ContentValues categoryValues=new ContentValues();
            categoryValues.put(id,category.id);
            categoryValues.put(name,category.category);
            int affectedRows=db.update(TABLE_CATEGORIES,categoryValues,id+" = "+ category.id,null);
            if(affectedRows<1)
            {
                db.insertOrThrow(TABLE_CATEGORIES,null,categoryValues);
            }
        }
        catch (Exception e)
        {
//            Log.d("addorUpdateCategory", "Error while trying to add or update category");
//            e.printStackTrace();
        }
    }

    public ArrayList<eventCategory> ReadDatabaseCategory(SQLiteDatabase db)
    {

        ArrayList<eventCategory> list=new ArrayList<>();
        try
        {
            String query;

            query="Select * from "+TABLE_CATEGORIES+";";

            Cursor categoryCursor=db.rawQuery(query,null);
            if(categoryCursor.getCount()>0) {
                categoryCursor.moveToFirst();
                do {
                    eventCategory item = new eventCategory();
                    item.id = (categoryCursor.getInt(categoryCursor.getColumnIndex(id)));
                    item.category = (categoryCursor.getString(categoryCursor.getColumnIndex(name)));
                    Log.d("category", item.category);
                    list.add(item);
                } while (categoryCursor.moveToNext());
            }
            categoryCursor.close();
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        }
        return list;
    }


}
