package com.lixd.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lixd.example.mvc.MvcActivity;
import com.lixd.example.mvp.MvpActivity;
import com.lixd.example.mvvm.MvvmActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_mvc_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(MvcActivity.class);
            }
        });

        findViewById(R.id.btn_mvp_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(MvpActivity.class);
            }
        });

        findViewById(R.id.btn_mvvm_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(MvvmActivity.class);
            }
        });
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
