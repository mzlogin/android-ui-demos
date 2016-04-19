package org.mazhuang.androiduidemos.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.mazhuang.androiduidemos.MainActivity;
import org.mazhuang.androiduidemos.R;

public class NotificationDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SIMPLE_NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_demo);

        Button simpleNotificationBtn = (Button) findViewById(R.id.simpleNotification);
        if (simpleNotificationBtn != null) {
            simpleNotificationBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simpleNotification:
                sendSimpleNotification();
                break;
        }
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
