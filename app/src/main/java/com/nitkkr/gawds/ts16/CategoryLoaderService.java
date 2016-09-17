package com.nitkkr.gawds.ts16;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by SAHIL SINGLA on 02-09-2016.
 */
public class CategoryLoaderService extends Service{
    private static final String id="id";
    private static final String name="name";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        httpRequest rh=new httpRequest();
        String CategoryJson=rh.SendGetRequest(getString(R.string.Categories));
        Log.d("CategoryJson",CategoryJson);
        try {
            JSONObject CategoriesJson = new JSONObject(CategoryJson);
            JSONArray CategoryArray = CategoriesJson.getJSONArray("cats");
            int length=CategoryArray.length();
            CategoriesDbHelper categoriesDbHelper=new CategoriesDbHelper(getBaseContext());
            for (int i=0;i<length;i++)
            {
                JSONObject object=CategoryArray.getJSONObject(i);
                EventCategory category=new EventCategory();
                category.id=object.getInt(id);
                category.category=object.getString(name);
                categoriesDbHelper.addCategory(category);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
