package com.example.learnretrofit.LearnRemoteView;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.learnretrofit.R;

/**
 * Created by 杨豪 on 2018/7/20.
 */

public class MyAppWidthProvider extends AppWidgetProvider {

    public static final String TAG = "MyAppWidthProvider";
    public static final String CLICK_ACTION = "com.example.learnretrofit.MyAppWidthProvider";

    public MyAppWidthProvider(){
        super();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive: action = "+intent.getAction());

        if(intent.getAction().equals(CLICK_ACTION)){
            Toast.makeText(context,"clicked it",Toast.LENGTH_SHORT).show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.remoteviewicon);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for(int i = 0;i < 37 ;i++){
                        float drgree = (i * 10 ) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
                        remoteViews.setImageViewBitmap(R.id.widgetImageView1,rotateBitmap(context,b,drgree));
                        Intent intentClick = new Intent();
                        intentClick.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
                        remoteViews.setOnClickPendingIntent(R.id.widgetImageView1,pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidthProvider.class),remoteViews);
                        SystemClock.sleep(30);
                    }

                }
            }).start();
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.i(TAG, "onUpdate");
        final int counter = appWidgetIds.length;
        Log.i(TAG, "onUpdate: counter = "+counter);
        for(int i = 0 ; i < counter;i++){
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context,appWidgetManager,appWidgetId);
        }

    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(TAG, "onWidgetUpdate: appwidgetId = "+ appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);

        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
        remoteViews.setOnClickPendingIntent(R.id.widgetImageView1,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);

    }

    private Bitmap rotateBitmap(Context context, Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap b = Bitmap.createBitmap(bitmap,0,0,
                bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return b;
    }
}
