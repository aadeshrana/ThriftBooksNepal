package com.example.thearbiter.thriftbooksnepal.ExtraClasses;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FcmMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMMessagingService";

    static final String GROUP_IDD = "group";
    public static int check = 0;

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

        handleMessage(remoteMessage);
    }

    private void handleMessage(RemoteMessage remoteMessage) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        String already = pref.getString("messageFrom", "");
        //////eta log check garna baki cha hai.
        Log.d("eta", "koValue1" + already);
        String message = remoteMessage.getNotification().getBody();
        String[] a = message.split(":");
        if (already.equals("")) {
            editor.putString("messageFrom", a[0]);
            editor.apply();
        } else {
            String newOne = already + "***" + a[0];
            editor.putString("messageFrom", newOne);
            editor.apply();
        }
        if (check == 0) {
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction());
            check = 1;
        }
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

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder2 = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title).setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentText(messageBody).setSound(defaultSoundUri)
                .setGroup(GROUP_IDD).setGroupSummary(true)
                .setContentIntent(pendingIntent);

        Notification notification = notificationBuilder2.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, notification);

    }

}
