package com.example.learnretrofit.LearnMoreProcess.BinderPool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 杨豪 on 2018/8/1.
 */

public class BinderPool {

    private static final String TAG = "BinderPool";
    public static final int BINDER_NONE = -1;
    public static final int BINDER_STUDY = 0;
    public static final int BINDER_PLAY = 1;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;


    private BinderPool(Context context){
        mContext = context.getApplicationContext();
        connectBinderService();
    }

    public static BinderPool getInstance(Context context) {
        if(sInstance == null){
            synchronized (BinderPool.class){
                if(sInstance == null){
                    Log.d(TAG, "getInstance: 实例化单例对象");
                    sInstance = new BinderPool(context);
                    Log.d(TAG, "getInstance: 准备返回到客户端");
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext,BinderPoolService.class);
        Log.d(TAG, "connectBinderService: 绑定服务");
        mContext.bindService(service,mBinderPoolConnection,Context.BIND_AUTO_CREATE);
        try {
            Log.d(TAG, "connectBinderService: mConnectBinderPoolCountDownLatch.await()");
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "connectBinderService: 绑定方法执行完毕");
    }

    public IBinder queryBinder(int binderCode){
        IBinder binder = null;

        try{
            if(mBinderPool != null){
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到跨进程操作BinderPool的对象
            Log.d(TAG, "onServiceConnected: 拿到跨进程操作BinderPool的对象");
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                Log.d(TAG, "connectBinderService: 绑定死亡监听");
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipent,0);
            } catch (RemoteException e) {
                Log.d(TAG, "onServiceConnected: 错误了");
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected: 连接完毕");
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipent = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipent,0);
            mBinderPool = null;
            connectBinderService();
        }
    };


    public static class BinderPoolImpl extends IBinderPool.Stub{


        public BinderPoolImpl() {
            super();
        }

        @Override
        public IBinder queryBinder(int BinderCode) throws RemoteException {
            IBinder binder = null;
            switch (BinderCode){
                case BINDER_STUDY:
                    //
                    Log.d(TAG, "queryBinder: 实例化学习功能的Binder");
                    binder = new StudyImpl();
                    break;
                case BINDER_PLAY:{
                    binder = new PlayImpl();
                    break;
                }
            }
            return binder;
        }
    }

}
