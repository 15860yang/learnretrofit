package com.example.learnretrofit.LearnOtto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.learnretrofit.R;

public class Otto2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otto2);

        Button bt = findViewById(R.id.otto2Bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOttoBus.getInstance().post(new OttoMessage("这是消息"));
                finish();
            }
        });
    }
}
