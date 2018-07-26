package com.example.learnretrofit.Learn观察者模式;

import android.util.Log;

/**
 * Created by 杨豪 on 2018/7/22.
 */

/***
 * 具体观察者1
 *
 */
public class ObServer1 implements Observer {
    private String name;
    private String TAG = "Observer1";
    public ObServer1(String name){
        this.name = name;
    }

    @Override
    public void update(String message) {
        Log.d(TAG, "update: name = " + name);
    }
}
