package com.example.learnretrofit.Learn观察者模式;

/**
 * Created by 杨豪 on 2018/7/22.
 */

public interface Subject {

    /**
     *
     * 增加被观察者
     *
     */

    public void attach (Observer observer);

    /*

    删除被观察者
     */
    public void detach(Observer observer);

    /**
     * 通知订阅者更新消息
     * @param message
     */
    public void notify(String message);
}
