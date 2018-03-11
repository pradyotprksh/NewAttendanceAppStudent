package com.application.pradyotprakash.newattendanceapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by pradyotprakash on 03/03/18.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String message_title = remoteMessage.getNotification().getTitle();
        String message_body = remoteMessage.getNotification().getBody();
        String click_action = remoteMessage.getNotification().getClickAction();
        String dataMessage = remoteMessage.getData().get("message");
        String dataFrom = remoteMessage.getData().get("from_user_id");
        String dataFromDesignation = remoteMessage.getData().get("from_designation");
        String dataFromOn = remoteMessage.getData().get("message_on");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(message_title)
                        .setContentText(message_body);

        Intent intent = new Intent(click_action);
        intent.putExtra("message", dataMessage);
        intent.putExtra("from_user_id", dataFrom);
        intent.putExtra("from_designation", dataFromDesignation);
        intent.putExtra("message_on", dataFromOn);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
