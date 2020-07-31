package com.el.mkoba.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.el.mkoba.R;
import com.el.mkoba.TransViewActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessaging extends FirebaseMessagingService {



    String click_ation;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getTitle();
        String  title2 = remoteMessage.getData().get("title");
        String  message = remoteMessage.getData().get("message");
        String  ord_id = remoteMessage.getData().get("Doc_id");

        String click_ation = remoteMessage.getNotification().getClickAction();


        if (remoteMessage.getData().isEmpty()){

            ShowNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),click_ation,ord_id);

        }else {

           ShowNotification(remoteMessage.getData(),click_ation,ord_id);

        }

    }


    private void ShowNotification(Map<String, String> data, String click_ation, String ord_id) {

        String title = data.get("title").toString();
        String message = data.get("message").toString();


        Uri defaultSoundUri= Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.mkoba);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.mkobawhite)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setSound(defaultSoundUri)
                .setColor(getResources().getColor(R.color.colorPrimary));
        int notificationId = (int) System.currentTimeMillis();



        Intent resultintent = new Intent(click_ation);
        resultintent.putExtra("Doc_id",ord_id);
        PendingIntent  resultPendingintent =  PendingIntent.getActivity(
                this,
                0,
                resultintent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingintent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId,mBuilder.build());




        if (notificationManager != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id),
                        "El_Tech_CH_01",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setShowBadge(true);
                channel.enableLights(true);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{0,100});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                notificationManager.createNotificationChannel(channel);

            }

            notificationManager.notify(notificationId, mBuilder.build());
        }



    }

    private void ShowNotification(String title, String body,String click_ation,String ord_id) {

        Uri defaultSoundUri= Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.mkoba);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.mkobawhite)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setSound(defaultSoundUri)
                .setColor(getResources().getColor(R.color.colorPrimary));
        int notificationId = (int) System.currentTimeMillis();



        Intent resultintent = new Intent(click_ation);
        resultintent.putExtra("Doc_id",ord_id);
        PendingIntent  resultPendingintent =  PendingIntent.getActivity(
                this,
                0,
                resultintent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingintent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId,mBuilder.build());



    }





}
