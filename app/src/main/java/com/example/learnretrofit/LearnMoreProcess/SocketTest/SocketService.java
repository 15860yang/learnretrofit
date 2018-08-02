package com.example.learnretrofit.LearnMoreProcess.SocketTest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telecom.Call;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SocketService extends Service {



    private final static String TAG = "SocketService";

    private List<Socket> mSockets = new ArrayList<>();
    private ServerSocket mServerSocket = null;

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private class TcpServer implements Runnable{

        @Override
        public void run() {
            //先启动服务端的30000端口号，如果不能启动就直接退出
            try {
                mServerSocket = new ServerSocket(30000);
                Log.d(TAG, "run: 服务端启动成功");
            } catch (IOException e) {
                Log.d(TAG, "run: 不能使用30000端口");
                e.printStackTrace();
                return;
            }
            //创建完成服务端之后就等着客户端来请求连接了
            while (!mServerSocket.isClosed()){
                try {
                    final Socket socket = mServerSocket.accept();
                    Log.d(TAG, "run: 收到了一个客户端");
                    //将服务器这边的Socket添加到集合
                    mSockets.add(socket);
                    //当来一个客户端连接成功，就会启动一个线程来监听客户端发送消息
                    new Thread(new myThread(socket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //监听客户端发消息的线程
    private class myThread implements Runnable{

        Socket mSocket = null;

        public myThread(Socket socket) {
            mSocket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream in = mSocket.getInputStream();

                byte[] b = new byte[1024];
                int i = -1;
                while (true) {
                    if ((i = in.read(b)) > 0) {
                        String s = new String(b, 0, i);
                        Log.d(TAG, "服务端接收: " + s);
                        //一旦收到某个客户端发送消息，就将这条消息发送到所有的客户端，以达到多人聊天的操作
                        for (Socket socket : mSockets){
                            OutputStream out = socket.getOutputStream();
                            out.write(("你发来了 "+ s+"\n 我给你回个消息吧，免得你尴尬").getBytes());
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
