package com.custom.rxbus.ui;

import android.os.Bundle;
import android.view.View;

import com.custom.rxbus.R;
import com.custom.rxbus.core.RxBus;
import com.custom.rxbus.model.RxBusTestModel;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    public void send(View view) {
        RxBus.getInstance().post(new RxBusTestModel("杨胜文", "30"));
    }
}
