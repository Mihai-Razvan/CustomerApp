package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.ImageButton;

import com.example.customerapp_client.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ActivityBasics {

    private ActivityMainBinding binding;
    private ImageButton act_main_test_button;
    private ImageButton act_main_bills_button;
    private ImageButton act_main_account_button;
    private ImageButton act_main_login_button;

    private int clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getExtras();
        getActivityElements();
        setListeners();
    }

    @Override
    public void getActivityElements() {
        act_main_test_button = findViewById(R.id.act_main_test_button);
        act_main_bills_button = findViewById(R.id.act_main_bills_button);
        act_main_account_button = findViewById(R.id.act_main_account_button);
        act_main_login_button = findViewById(R.id.act_main_login_button);
    }

    @Override
    public void setListeners()
    {
        act_main_test_button_onClick();
        act_main_bills_button_onClick();
        act_main_account_button_onClick();
        act_main_login_button_onClick();
    }

    @Override
    public void getExtras()
    {
        clientId = getIntent().getIntExtra("clientId", 0);
        System.out.println("CLIENT ID: " + clientId);
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

    private void act_main_login_button_onClick() {
        act_main_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}