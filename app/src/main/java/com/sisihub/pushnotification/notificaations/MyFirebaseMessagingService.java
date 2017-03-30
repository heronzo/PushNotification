package com.sisihub.pushnotification.notificaations;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sisihub.pushnotification.R;
import com.sisihub.pushnotification.notificaations.MainActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by HERONS on 8/10/2016.
 * making use of the FirebaseMessagingService service incorporate firebase gradle services
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String mesg;
    private int number = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification( remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        EventBus.getDefault().post(remoteMessage.getNotification().getBody());

    }
/**
*The method implementing push notification's appearing at the notification area
*/
    private void showNotification(String messageTitle, String message) {

        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("Message", message);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this);
        notificationCompat.setContentTitle(messageTitle);
        notificationCompat.setContentText(message);
        notificationCompat.setTicker("New Push Notification");
        notificationCompat.setNumber(++number);
        notificationCompat.setAutoCancel(true);
        notificationCompat.setSound(defaultSoundUri);
        notificationCompat.setSmallIcon(R.drawable.custom_logo);
        notificationCompat.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationCompat.build());

    }

    

}
