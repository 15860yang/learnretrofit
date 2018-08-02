package com.example.learnretrofit.LearnMoreProcess.BinderPool;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.learnretrofit.R;

public class BinderPoolActivity extends AppCompatActivity {

    private final static String TAG = "BinderPoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doThings();
            }
        }).start();

    }

    private void doThings(){
        Log.d(TAG, "doThings: 准备获取binder单例对象");
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);

        Log.d(TAG, "doThings: 获取学习Binder对象");
        IBinder studyBinder = binderPool.queryBinder(BinderPool.BINDER_STUDY);
        Log.d(TAG, "doThings: 包装studyBinder 跨进程传输对象");
        Study study = StudyImpl.asInterface(studyBinder);
        String bookName = "《鬼吹灯》";
        try {
            study.readBook(bookName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        IBinder playBinder = binderPool.queryBinder(BinderPool.BINDER_PLAY);
        Play play = PlayImpl.asInterface(playBinder);
        try {
            play.playGames("率土之滨");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
