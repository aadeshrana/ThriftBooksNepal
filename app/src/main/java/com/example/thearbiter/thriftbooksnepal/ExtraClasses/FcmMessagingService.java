package com.example.thearbiter.thriftbooksnepal.ExtraClasses;
//
///**
// * Created by Gaurav Jayasawal on 1/18/2017.
// */
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//
//import com.example.thearbiter.thriftbooksnepal.R;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
///**
// * Created by Gaurav Jayasawal on 1/15/2017.
// */
//
//public class FcmMessagingService extends FirebaseMessagingService {
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String title = remoteMessage.getNotification().getTitle();
//        String message = remoteMessage.getNotification().getBody();
//        String click_action = remoteMessage.getNotification().getClickAction();
//        int numMessages = 0;
//
//        Intent intent = new Intent(click_action);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        int notifyId = 1;
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder.setContentTitle(title);
//        notificationBuilder.setContentText(message);
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notificationBuilder.setSound(uri);
//        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        notificationBuilder.setAutoCancel(true);
//        notificationBuilder.setContentIntent(pendingIntent);
//        Notification notification = notificationBuilder.build();
//        notificationManager.notify(notifyId, notification);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setContentText(message)
//                .setContentTitle(title)
//                .setSound(uri)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setAutoCancel(false)
//                .setOnlyAlertOnce(true)
//                .setContentIntent(
//                        PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_ONE_SHOT));
//        notificationManager.notify(1, builder.build());
//
//    }
//}

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.atomic.AtomicInteger;


public class FcmMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMMessagingService";
    private static AtomicInteger notificationCounter;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        handleMessage(remoteMessage);
    }

    private void handleMessage(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getClickAction());
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody, String click_act) {

        Intent intent = new Intent(click_act);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
//
        Notification notification = null;

        int notificationNumber = notificationCounter.incrementAndGet();
        //
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setNumber(notificationNumber)
                .setTicker("ok");

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancel(notificationNumber - 1);

        notificationManager.notify(notificationNumber, notificationBuilder.build());
    }
}