package com.example.learnretrofit.LearnOtto;

import com.squareup.otto.Bus;

/**
 * Created by 杨豪 on 2018/6/5.
 */

public class MyOttoBus extends Bus {

    private volatile static MyOttoBus ottoBus;
    private MyOttoBus(){}
    public static MyOttoBus getInstance(){
        if(ottoBus == null){
            synchronized (MyOttoBus.class){
                if(ottoBus == null){
                    ottoBus = new MyOttoBus();
                }
            }
        }
        return ottoBus;
    }
}
