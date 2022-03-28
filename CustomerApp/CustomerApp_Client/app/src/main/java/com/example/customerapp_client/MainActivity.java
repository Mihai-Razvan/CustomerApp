package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.customerapp_client.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button act_main_home_button;
    private Button act_main_test_button;
    private Button act_main_bills_button;
    private TextView act_main_tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        act_main_test_button_onClick();
        act_main_bills_button_onClick();
    }

    private void getActivityElements() {
        act_main_home_button = findViewById(R.id.act_main_home_button);
        act_main_tw = findViewById(R.id.act_main_tw);
        act_main_test_button = findViewById(R.id.act_main_test_button);
        act_main_bills_button = findViewById(R.id.act_main_bills_button);
    }

    private void act_main_test_button_onClick() {
        act_main_test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_main_tw.setText("Test");
                HttpRequestsTest httpRequestsTest = new HttpRequestsTest("/test");
                Thread connectionThread = new Thread(httpRequestsTest);
                connectionThread.start();
            }
        });
    }


    private void act_main_bills_button_onClick() {
        act_main_bills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BillsActivity.class));
//                HttpRequestsBills httpRequestsBills = new HttpRequestsBills("/bills");
//                Thread connectionThread = new Thread(httpRequestsBills);
//                connectionThread.start();
            }
        });
    }
}