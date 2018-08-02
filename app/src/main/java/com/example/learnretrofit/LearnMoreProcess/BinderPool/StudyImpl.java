package com.example.learnretrofit.LearnMoreProcess.BinderPool;

import android.os.RemoteException;
import android.util.Log;

/**
 * Created by 杨豪 on 2018/8/1.
 */

public class StudyImpl extends Study.Stub {
    
    private static final String TAG = "studyImpl";
    
   
    
    @Override
    public String readBook(String bookName) throws RemoteException {
        Log.d(TAG, "readBook: 来到这里读书了");
        return "读 " + bookName + " 这本书真是太充实了！";
    }
}
