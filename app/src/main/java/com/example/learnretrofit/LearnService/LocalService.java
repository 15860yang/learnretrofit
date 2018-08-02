package com.example.learnretrofit.LearnService;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.learnretrofit.R;

/**
 * Created by 杨豪 on 2018/7/27.
 */

public class LocalService extends Service {

    private final static String TAG = "MyService";
    private NotificationManager mNM;

    private int NOTIFICATION = R.string.local_service_started;

    public class LocalBinder extends Binder {
        LocalService getService(){
            return LocalService.this;
        }

        public void doOurWantTodoThings(){
            Log.d(TAG, "doOurWantTodoThings: 在这里可以调用我们在服务里面写好的方法哟");
        }

    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.d(TAG, "onCreate: 服务所在的线程为 == " + Thread.currentThread().getName());
    }


    private final IBinder mBinder = new LocalBinder();
    /**
     * onBind方法用来返回一个实例类，这个会用在以后对服务的操作上
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: 服务所在的线程为 == " + Thread.currentThread().getName());
        return mBinder;
    }

    /**
     * 在启动服务的时候启动，那么如果你有什么是要在启动服务之后立即就做的事情，可以放在这个方法中
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService-onStartCd", "Received start id " + startId + ": " + intent);
        Log.d(TAG, "onStartCommand: 服务所在的线程为 == " + Thread.currentThread().getName());
        return START_NOT_STICKY;//非粘性启动
    }

    @Override
    public void onDestroy() {
        mNM.cancel(NOTIFICATION);
        Log.d(TAG, "onDestroy: 服务销毁");
    }

}
