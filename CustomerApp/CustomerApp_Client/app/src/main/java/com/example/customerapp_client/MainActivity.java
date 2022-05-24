package com.example.customerapp_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;

import android.widget.ImageButton;

import com.example.customerapp_client.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ActivityBasics {

    private ImageButton act_main_test_button;
    private ImageButton act_main_bills_button;
    private ImageButton act_main_account_button;
    private ImageButton act_main_login_button;
    private ImageButton act_main_index_button;
    private ImageButton act_main_contact_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();
    }

    @Override
    public void getActivityElements() {
        act_main_test_button = findViewById(R.id.act_main_test_button);
        act_main_bills_button = findViewById(R.id.act_main_bills_button);
        act_main_account_button = findViewById(R.id.act_main_account_button);
        act_main_login_button = findViewById(R.id.act_main_login_button);
        act_main_index_button = findViewById(R.id.act_main_index_button);
        act_main_contact_button = findViewById(R.id.act_main_contact_button);
    }

    @Override
    public void setListeners()
    {
        act_main_test_button_onClick();
        act_main_bills_button_onClick();
        act_main_account_button_onClick();
        act_main_login_button_onClick();
        act_main_contact_button_onClick();
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

    private void act_main_index_button_onClick() {
        act_main_index_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IndexActivity.class));
            }
        });
    }

    private void act_main_contact_button_onClick()
    {
        act_main_contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(MainActivity.this, ContactActivity.class));
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "075-303-0215"));

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                }
                else
                {
                    try {
                        startActivity(callIntent);
                    }
                    catch(SecurityException e) {
                        System.out.println("COULDN'T MAKE PHONE CALL: " + e.getMessage());
                    }
                }
            }
        });
    }
}