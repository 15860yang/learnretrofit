package com.example.learnretrofit.LearnMoreProcess;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.Permission;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 杨豪 on 2018/7/31.
 */

public class AppleManagerService extends Service {

    private static final String TAG = "AppleManagerService";

    private AtomicBoolean mAtomicBoolean = new AtomicBoolean(false);


    private CopyOnWriteArrayList<Apple> mAppleList = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<InewDataListener> mCallbackList = new RemoteCallbackList<>();

//    private CopyOnWriteArrayList<InewDataListener> mListeners = new CopyOnWriteArrayList<>();


    private Binder mBinder = new IAppleManager.Stub() {
        @Override
        public List<Apple> getAppleList() throws RemoteException {
            return mAppleList;
        }

        @Override
        public void addApple(Apple apple) throws RemoteException {
            mAppleList.add(apple);
        }

        @Override
        public void registerListener(InewDataListener listener) throws RemoteException {
//            if(!mListeners.contains(listener)){
//                mListeners.add(listener);
//            }

            mCallbackList.register(listener);
        }

        @Override
        public void unRegisterListener(InewDataListener listener) throws RemoteException {
//            if(mListeners.contains(listener)){
//                mListeners.remove(listener);
//            }
            Log.d(TAG, "unRegisterListener: 取消之前的数量是 " + mCallbackList.beginBroadcast());;
            mCallbackList.finishBroadcast();
            mCallbackList.unregister(listener);
            Log.d(TAG, "unRegisterListener: 取消之后的数量是 " + mCallbackList.beginBroadcast());;
            mCallbackList.finishBroadcast();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mAppleList.add(new Apple(10,"红富士"));
        mAppleList.add(new Apple(10,"早熟苹果"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.example.learnretrofit.LearnMoreProcess.permission.ACCESS_APPLE_SERVICE");
        if(check == PackageManager.PERMISSION_DENIED){
            return null;
        }
        return mBinder;
    }


    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (!mAtomicBoolean.get()){
                try{
                    Thread.sleep(5000);
                    int appleId = mAppleList.size() + 1;
                    Apple apple = new Apple(appleId,"苹果 " + appleId + " 号");

                    addNewApple(apple);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addNewApple(Apple apple) {
        mAppleList.add(apple);
//        for(int i = 0;i < mListeners.size() ; i ++) {
//            InewDataListener listener = mListeners.get(i);
//            try {
//                listener.DataUpdata(apple);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
        final int N = mCallbackList.beginBroadcast();
        for (int i = 0;i < N;i++){
            InewDataListener listener = mCallbackList.getBroadcastItem(i);
            if(listener != null){
                try{
                    listener.DataUpdata(apple);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mCallbackList.finishBroadcast();

    }
}
