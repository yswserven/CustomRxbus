package com.custom.rxbus.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.custom.rxbus.R;
import com.custom.rxbus.core.Hermes;
import com.custom.rxbus.model.HermesTestInterface;
import com.custom.rxbus.service.HermesService;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Hermes.getInstance().connect(this, HermesService.class);

    }

    public void send(View view) {
//        RxBus.getInstance().post(new RxBusTestModel("杨胜文", "30"));
        HermesTestInterface anInterface = Hermes.getInstance().getInstance(HermesTestInterface.class);
        Toast.makeText(this, "名字 = " + anInterface.getName() + " 年龄 = " + anInterface.getAge(), Toast.LENGTH_SHORT).show();
    }
}
