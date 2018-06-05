package com.example.learnretrofit.LearnOtto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnretrofit.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class LearnOtto extends AppCompatActivity {

    private Button bt_jump;
    private TextView tv_message;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnotto);

        tv_message = findViewById(R.id.myOtto_Tv);
        bt_jump = findViewById(R.id.myOtto_Bt);
        bt_jump.setText("跳转到下一个活动");
        bt_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnOtto.this,Otto2Activity.class));
            }
        });
        bus = MyOttoBus.getInstance();
        bus.register(this);
    }

    @Subscribe
    public void setContent(OttoMessage message){
        tv_message.setText(message.getMessage());
    }


    @Produce
    public OttoMessage lalalala(){
        return new OttoMessage("我是消息");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
