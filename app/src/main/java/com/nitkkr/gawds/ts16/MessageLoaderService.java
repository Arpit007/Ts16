package com.nitkkr.gawds.ts16;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SAHIL SINGLA on 03-09-2016.
 */


public class MessageLoaderService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final MessageDbHelper messageDbHelper=new MessageDbHelper(this);

        try {
            httpRequest rh=new httpRequest();
            String MessageJson=rh.SendGetRequest("http://192.168.43.210/TS/messages.php");
            JSONObject jsonObject=new JSONObject(MessageJson);
            Log.d("MESSAGEJSON ", MessageJson);
            JSONArray messages=jsonObject.getJSONArray("messages");
            int length=messages.length();
            for(int i=0;i<length;i++)
            {
                JSONObject object=messages.getJSONObject(i);
                messageDbHelper.addMessage(object.getString("message"),object.getInt("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Service.START_STICKY;
    }
}
