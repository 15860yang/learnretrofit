package com.example.learnretrofit.LearnEventBus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnretrofit.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class learnEventBus extends AppCompatActivity {

    private TextView tv_message,tv1_message;
    private Button bt_message,bt_subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        tv_message = findViewById(R.id.main_Iv);
        tv_message.setText("MainActivity");
        tv1_message = findViewById(R.id.main_tv1);

        bt_message = findViewById(R.id.main_bt1);
        bt_message.setText("跳转到SecondActivity");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(learnEventBus.this,EventBusTestActivity02.class));
            }
        });

        bt_subscription = findViewById(R.id.main_bt2);
        bt_subscription.setText("注册事件");
        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册事件
                EventBus eventBus = EventBus.getDefault();
                eventBus.register(learnEventBus.this);
            }
        });
    }

    //事件处理函数，接收普通事件和粘性事件
    //定义线程模型为所有事件都会在UI线程处理
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMoonEvent(MyEvent event){
        tv_message.setText(event.getMessage());
    }

    //接收普通事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent1(MyEvent event){
        tv1_message.setText(event.getMessage());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(learnEventBus.class);
    }
}
