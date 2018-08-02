package com.example.learnretrofit.LearnMoreProcess.BinderPool;

import android.os.RemoteException;
import android.util.Log;

/**
 * Created by 杨豪 on 2018/8/1.
 */

public class PlayImpl extends Play.Stub {

    public static final int PlayVeryGood = 1;
    private static final String TAG = "playImpl";


    @Override
    public int playGames(String gameName) throws RemoteException {

        Log.d(TAG, "playGames: 玩 " + gameName + " 很爽！");


        return PlayVeryGood;
    }
}
