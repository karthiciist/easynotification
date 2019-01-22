package com.tag.localnotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private int currentNotificationID = 0;
    private EditText etMainNotificationText, etMainNotificationTitle;
    private Button btnMainSendNotificationActionBtn;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private String notificationTitle;
    private String notificationText;
    private Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring views
        getViews();

        btnMainSendNotificationActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotificationData();
                setDataForNotificationWithActionButton();
            }
        });
    }

    private void getViews() {
        etMainNotificationText = (EditText) findViewById(R.id.etMainNotificationText);
        etMainNotificationTitle = (EditText) findViewById(R.id.etMainNotificationTitle);
        btnMainSendNotificationActionBtn = (Button) findViewById(R.id.btnMainSendNotificationActionBtn);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;

        notificationManager.notify(notificationId, notification);
    }


    private void setNotificationData() {
        notificationTitle = this.getString(R.string.app_name);
        notificationText = "Hello..This is a Notification Test";
        if (!etMainNotificationText.getText().toString().equals("")) {
            notificationText = etMainNotificationText.getText().toString();
        }
        if (!etMainNotificationTitle.getText().toString().equals("")) {
            notificationTitle = etMainNotificationTitle.getText().toString();
        }
    }

    private void setDataForNotificationWithActionButton() {

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setContentText(notificationText);

        Intent answerIntent = new Intent(this, AnswerReceiveActivity.class);

        // Declare positive button if any
       /* answerIntent.setAction("Yes");
        PendingIntent pendingIntentYes = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.thumbs_up, "Yes", pendingIntentYes);*/

       // Declare negative button
        answerIntent.setAction("STOP BOOKING");
        PendingIntent pendingIntentNo = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.thumbs_down, "STOP BOOKING", pendingIntentNo);

        // We can add any number of buttons here to be shown in notification

        sendNotification();
    }

}
