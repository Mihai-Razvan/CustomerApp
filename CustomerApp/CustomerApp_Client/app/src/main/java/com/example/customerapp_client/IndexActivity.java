package com.example.customerapp_client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp_client.databinding.ActivityIndexBinding;

public class IndexActivity extends AppCompatActivity  implements ActivityBasics{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIndexBinding binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    public void getActivityElements() {

    }

    @Override
    public void setListeners() {

    }

}