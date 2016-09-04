package com.nitkkr.gawds.ts16;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SAHIL SINGLA on 03-09-2016.
 */
public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Context c=this;
        final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final Calendar calendar=Calendar.getInstance();
        Thread upcomingNotification=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ArrayList<eventData> list=dbHelper.DbHelper.GetUpcominEvents();
                    int length=list.size();
                    for(int i=0;i<length;i++)
                    {
                        eventData item=list.get(i);
                        Date eventDate=simpleDateFormat.parse(item.Day+" "+item.Time);
                        Long eventTimeStamp=(eventDate.getTime())/1000;
                        Long currentTimeStamp=(calendar.getTimeInMillis())/1000;
                        if(item.bookmark==1 && item.notificationGenerated==false && currentTimeStamp+1800<=eventTimeStamp)
                        {
                            item.notificationGenerated=true;
                            NotificationCompat.Builder builder=new NotificationCompat.Builder(c);
                            builder.setContentTitle(item.eventName+" Beginning Soon");
                            builder.setContentText(item.eventName+" is beginning in about 30 minutes from now.\n"+item.Venue);
                            builder.setTicker(item.eventName+" is beginning in about 30 minutes from now.\n"+item.Venue);
                            Intent resultIntent=new Intent(c,eventDetail.class);
                            TaskStackBuilder stackBuilder=TaskStackBuilder.create(c);
                            stackBuilder.addParentStack(eventDetail.class);
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notification=(NotificationManager )getSystemService(Context.NOTIFICATION_SERVICE);
                            notification.notify("UpcomingEventNotification",100,builder.build());
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread messageNotification=new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        messageNotification.start();
        upcomingNotification.start();

        return Service.START_NOT_STICKY;
    }

}
