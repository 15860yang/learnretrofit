package com.example.learnretrofit.LearnRemoteView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.learnretrofit.R;

public class RemoteViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view);
        CreatNotificationwithRemoteView();
    }

    void CreatNotification(){
        Intent intent = new Intent(this,RemoteView_goActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("主题")
                .setContentText("内容")
                .setSmallIcon(R.drawable.remoteviewicon)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);
    }

    void CreatNotificationwithRemoteView(){
        Intent intent = new Intent(this,RemoteView_goActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.remoteviewlayout);
        remoteViews.setTextViewText(R.id.remoteViewText,"这是文字");
        remoteViews.setOnClickPendingIntent(R.id.remoteViewBT1,pendingIntent);
        remoteViews.setImageViewResource(R.id.remoteViewIV,R.drawable.remoteviewicon);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.remoteviewicon)
                .build();
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(2,notification);
    }

}
