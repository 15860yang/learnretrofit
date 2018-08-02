// IBookManager.aidl
package com.example.learnretrofit.LearnMoreProcess;

import com.example.learnretrofit.LearnMoreProcess.Apple;
import com.example.learnretrofit.LearnMoreProcess.InewDataListener;


interface IAppleManager {

    List<Apple> getAppleList();
    void addApple(in Apple apple);

    void registerListener(InewDataListener listener);
    void unRegisterListener(InewDataListener listener);

}
