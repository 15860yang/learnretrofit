package com.example.learnretrofit.LearnMoreProcess;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 杨豪 on 2018/7/31.
 */

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static class Messengerhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d(TAG, "handleMessage: receive msg from Client " + msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message clientData = Message.obtain(null,1);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","嗯，我收到了你的消息，我是服务端");
                    clientData.setData(bundle);
                    try {
                        client.send(clientData);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private final Messenger mMessenger = new Messenger(new Messengerhandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
