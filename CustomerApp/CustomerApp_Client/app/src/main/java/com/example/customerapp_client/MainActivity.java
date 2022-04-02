package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.customerapp_client.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ActivityBasics {

    private ActivityMainBinding binding;
    private ImageButton act_main_test_button;
    private ImageButton act_main_bills_button;
    private ImageButton act_main_account_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();
    }

    @Override
    public void getActivityElements() {
        act_main_test_button = findViewById(R.id.act_main_test_button);
        act_main_bills_button = findViewById(R.id.act_main_bills_button);
        act_main_account_button = findViewById(R.id.act_main_account_button);
    }

    @Override
    public void setListeners()
    {
        act_main_test_button_onClick();
        act_main_bills_button_onClick();
        act_main_account_button_onClick();
    }

    private void act_main_test_button_onClick() {
        act_main_test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("TEST BUTTON PRESSED");
            }
        });
    }

    private void act_main_bills_button_onClick() {
        act_main_bills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BillsActivity.class));
            }
        });
    }

    private void act_main_account_button_onClick() {
        act_main_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }
        });
    }
}