package com.example.learnretrofit.Learn观察者模式;

/**
 * Created by 杨豪 on 2018/7/22.
 */

public class Client {

    public  void  aaa(){

        SubScriptionSubject subScriptionSubject = new SubScriptionSubject();

        ObServer1 obServer1 = new ObServer1("111");

        subScriptionSubject.attach(obServer1);

        subScriptionSubject.notify("sadsafasga");
    }

}
