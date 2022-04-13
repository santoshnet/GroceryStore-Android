package com.quintus.labs.grocerystore.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessaging;

import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.activity.MainActivity;
import com.quintus.labs.grocerystore.util.NotificationHelper;
import com.quintus.labs.grocerystore.util.NotificationUtils;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    LocalStorage localStorage;
    Intent intent;
    private NotificationUtils notificationUtils;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload1: " + remoteMessage.getData().toString());

            try {
                JSONObject jsonObj = new JSONObject(remoteMessage.getData().toString());
                Log.e(TAG, "Data Payload2: " + jsonObj);
                sendNotification(jsonObj.getJSONObject("data").getString("title"),jsonObj.getJSONObject("data").getString("message"));


            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        localStorage = new LocalStorage(getApplicationContext());
        FirebaseMessaging.getInstance().subscribeToTopic("global");
        localStorage.setFirebaseToken(token);



    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param title
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "1000000000000";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
