package com.example.learnretrofit.LearnService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by 杨豪 on 2018/7/27.
 */

public class MyLocalService extends Service {

    //当Activity或者其他组件调用StartService或者bindService时，如果服务还没有创建，就调用这个方法
    //如果已经创建，就不调用
    @Override
    public void onCreate() {
        super.onCreate();
    }

    class MyBinder extends Binder{

    }
    private final IBinder mBinder = new MyBinder();
    //绑定的时候会通过一系列的回调到此方法，他的作用是我们可以在我们的组件Activity或者其他组件绑定服务的同时拿到操作服务的引用
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //在Activity或者其他组件调用bindService的时候调用，在这个方法里面我们可以做一些绑定刚完成之后的初始化工作
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    //最后释放资源
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
