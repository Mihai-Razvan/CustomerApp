package com.example.customerapp_client;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.customerapp_client.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button act_main_home_button;
    private Button act_main_request_button;
    private Button act_main_test_button;
    private TextView act_main_tw;

   // SQLiteDatabase database = openOrCreateDatabase("Users", MODE_PRIVATE, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        act_main_test_button_onClick();
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
}