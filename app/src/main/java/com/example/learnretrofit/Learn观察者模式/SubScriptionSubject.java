package com.example.learnretrofit.Learn观察者模式;

/**
 * Created by 杨豪 on 2018/7/22.
 */

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 具体被观察者
 *
 */

public class SubScriptionSubject implements Subject {

    private List<Observer> mObserverList = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        mObserverList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        mObserverList.remove(observer);
    }

    @Override
    public void notify(String message) {
        for (Observer observer : mObserverList){
            observer.update(message);
        }
    }
}
