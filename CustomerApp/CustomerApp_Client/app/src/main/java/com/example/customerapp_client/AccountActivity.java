package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.customerapp_client.databinding.ActivityAccountBinding;
import com.google.android.material.textfield.TextInputEditText;

public class AccountActivity extends AppCompatActivity implements ActivityBasics {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAccountBinding binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();

        setMainFragment();
    }


    @Override
    public void getActivityElements() {

    }

    @Override
    public void setListeners()
    {

    }

    private void setMainFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountMain());
        fragmentTransaction.commit();
    }


}