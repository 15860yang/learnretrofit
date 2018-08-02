package com.example.learnretrofit.LearnMoreProcess.SocketTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learnretrofit.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SocketActivity extends AppCompatActivity {

    private final static String TAG = "SocketActivity";

    Socket socket = null;

    private TextView mTextView = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        initView();
        startService(new Intent(this,SocketService.class));
        //开始一个线程去尝试连接服务端
        new Thread(new ClientThread()).start();


    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //接收到消息
                    mTextView.setText(mTextView.getText() + "\n" + (String)msg.obj);
                    break;
            }
            super.handleMessage(msg);

        }
    };


    private class ClientThread implements Runnable{

        @Override
        public void run() {
            while (socket == null) {
                try {
                    socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 30000);
                    //连接服务端请求
                }catch (UnknownHostException e) {
                    Log.d(TAG, "run: 连接主机时出错");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Log.d(TAG, "run: 连接成功 socket " + socket.getPort() + "," + socket.getLocalSocketAddress());
                //连接成功之后发送一条消息
                OutputStream ss = socket.getOutputStream();
                ss.write("我来啦".getBytes());
                ss.flush();

                InputStream in = socket.getInputStream();
                byte[] b = new byte[1024];
                int i = -1;
                while (true) {
                    if ((i = in.read(b)) > 0) {
                        String s = new String(b, 0, i);
                        mHandler.obtainMessage(1,s).sendToTarget();
                        Log.d(TAG, "客户端接收: " + s);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                Log.d(TAG, "run: 终止");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initView() {
        final EditText editText = findViewById(R.id.SocketActivityET);
        findViewById(R.id.SocketActivitySendMessageBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = editText.getText().toString();
                if(s != null){
                    Log.d(TAG, "onClick: 给服务器发送 ： " + s);
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                OutputStream os = socket.getOutputStream();
                                os.write(s.getBytes());
                                os.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }else {
                    Log.d(TAG, "onClick: 文本不能为空");
                }

            }
        });
        mTextView = findViewById(R.id.SocketActivityShowMessage);
    }
}
