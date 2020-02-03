package com.custom.rxbus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.custom.rxbus.R;
import com.custom.rxbus.core.RxBus;
import com.custom.rxbus.core.Subscribe;
import com.custom.rxbus.core.ThreadMode;
import com.custom.rxbus.model.RxBusTestModel;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxBus.getInstance().register(this);
        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rxbusTest(RxBusTestModel model) {
        tvName.setText(String.format("这个是名字 :%s", model.getName()));
        tvAge.setText(String.format("这个是年龄 :%s", model.getAge()));
    }


    public void jump(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        RxBus.getInstance().unRegister(this);
        super.onDestroy();
    }
}
