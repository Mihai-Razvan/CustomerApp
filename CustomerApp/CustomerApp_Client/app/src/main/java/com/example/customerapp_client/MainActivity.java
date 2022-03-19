package com.example.customerapp_client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.customerapp_client.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button act_main_home_button;
    private Button act_main_request_button;
    private Button act_main_test_button;
    private TextView act_main_tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        act_main_test_button_onClick();
        setAct_main_request_button_onClick();
    }

    private void getActivityElements() {
        act_main_home_button = findViewById(R.id.act_main_home_button);
        act_main_request_button = findViewById(R.id.act_main_request_button);
        act_main_tw = findViewById(R.id.act_main_tw);
        act_main_test_button = findViewById(R.id.act_main_test_button);
    }

    private void act_main_test_button_onClick() {
        act_main_test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_main_tw.setText("Test");
            }
        });
    }

    private void setAct_main_request_button_onClick() {
        act_main_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_main_tw.setText("Request");
                Connection connection = new Connection();
                Thread connectionThread = new Thread(connection);
                connectionThread.start();
             //   Connection.connect();
            }
        });
    }
}