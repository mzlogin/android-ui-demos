package org.mazhuang.androiduidemos.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import org.mazhuang.androiduidemos.MainActivity;
import org.mazhuang.androiduidemos.R;

public class NotificationDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SIMPLE_NOTIFICATION_ID = 0;
    private static final int CUSTOM_LAYOUT_NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_demo);

        Button simpleNotificationBtn = (Button) findViewById(R.id.simpleNotification);
        if (simpleNotificationBtn != null) {
            simpleNotificationBtn.setOnClickListener(this);
        }

        Button customLayoutNotificationBtn = (Button)findViewById(R.id.customLayoutNotification);
        if (customLayoutNotificationBtn != null) {
            customLayoutNotificationBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simpleNotification:
                sendSimpleNotification();
                break;
            case R.id.customLayoutNotification:
                sendCustomLayoutNotification();
                break;
        }
    }

    private void sendCustomLayoutNotification() {
        RemoteViews contentViews = new RemoteViews(getPackageName(), R.layout.notification_custom);
        contentViews.setImageViewResource(R.id.bigPicture,
                android.R.drawable.star_big_on);
        contentViews.setTextViewText(R.id.notificationText,
                getString(R.string.custom_layout_notification));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.custom_layout_notification))
                .setAutoCancel(true);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        Notification notification = builder.build();

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
            notification.bigContentView = contentViews;
        }
        notification.contentView = contentViews;
        // builder.setContent(contentViews);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(CUSTOM_LAYOUT_NOTIFICATION_ID, notification);
        // notificationManager.notify(CUSTOM_LAYOUT_NOTIFICATION_ID, builder.build());
    }

    private void sendSimpleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.simple_notification))
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(SIMPLE_NOTIFICATION_ID, builder.build());
    }
}
