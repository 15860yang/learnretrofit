package com.example.learnretrofit.LearnEventBus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnretrofit.R;

import org.greenrobot.eventbus.EventBus;

public class EventBusTestActivity02 extends AppCompatActivity {

    private Button bt_message;
    private TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test02);
        initView();

    }

    private void initView() {
        tv_message = findViewById(R.id.event_Bus02_Tv);
        tv_message.setText("SecondActivity");
        bt_message = findViewById(R.id.event_Bus02_Bt);
        bt_message.setText("发送事件");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus eventBus = EventBus.getDefault();
                eventBus.post(new MyEvent("我是消息"));
                eventBus.postSticky(new MyEvent("00000000000000"));

                finish();
            }
        });
    }
}
