package com.example.learnretrofit.LearnMoreProcess;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnretrofit.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LearnMoreProcess2Activity extends AppCompatActivity {


    private static final String TAG = "LearnMoreProcess2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more_process2);

    }


    void initView(){
        findViewById(R.id.toActivity3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnMoreProcess2Activity.this,LearnMoreProcess2Activity.class));
            }
        });

        findViewById(R.id.process2GetData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = null;
                try {
                    ObjectInputStream in = new ObjectInputStream(
                            new FileInputStream("/storage/emulated/0/1/sina/sdadas.txt"));
                    data = (Data) in.readObject();
                }catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(data != null){
                    Log.d(TAG, "onClick: 读取成功 ， data.id = "  + data.id + "data.name = " + data.name);
                }else {
                    Log.d(TAG, "onClick: 错误");
                }
            }
        });
    }
    private Messenger mService;
    void bindMyMessengerService(){
        ServiceConnection mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = new Messenger(service);
                sendData();
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        };
        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    private Messenger mCilentMessenger = new Messenger(new ClientMessenger());
    private static class ClientMessenger extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d(TAG, "handleMessage: 接收到服务端的消息  = " +msg.getData().getString("reply"));
                    break;
            }

            super.handleMessage(msg);
        }
    }
    void sendData(){
        Message msg = Message.obtain(null,1);
        msg.replyTo = mCilentMessenger;
        Bundle data = new Bundle();
        data.putString("msg","这是客户端的消息");
        msg.setData(data);
        try{
            mService.send(msg);
        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }




}
