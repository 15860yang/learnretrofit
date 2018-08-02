package com.example.learnretrofit.LearnService;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.learnretrofit.R;

public class ServiceActivity extends AppCompatActivity {

    private final static String TAG = "ServiceActivity";
    private boolean mShouldUnbind;

    private LocalService mBoundService;
    private LocalService.LocalBinder mLocalBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        doBindService();
//        mLocalBinder.doOurWantTodoThings();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mShouldUnbind);
                mLocalBinder.doOurWantTodoThings();
            }
        }).start();
    }

    //绑定服务
    void doBindService(){
        ComponentName s = startService(new Intent(this,LocalService.class));
//
//        Log.d(TAG, "doBindService: start方式启动成功 s = " + s);
//        if(s != null){
//            return;
//        }

        if(bindService(new Intent(this, LocalService.class),mConnection, Context.BIND_AUTO_CREATE)){
            Log.d(TAG, "doBindService: 启动成功");

        }else {
            Log.d(TAG, "doBindService: 绑定失败");
        }
    }
    //取消绑定
    void doUnbindService() {
        if (mShouldUnbind) {
            unbindService(mConnection);
            mShouldUnbind = false;
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        //当绑定服务的时候调用，带着被绑定的服务引用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mShouldUnbind = true;
            mLocalBinder = (LocalService.LocalBinder) service;
            mBoundService = ((LocalService.LocalBinder)service).getService();
            Log.d(TAG, "onServiceConnected: 服务已经连接 ComponentName = " + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            Log.d(TAG, "onServiceDisconnected: 服务被取消连接 ComponentName = " + name );
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
}

