package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.customerapp_client.databinding.ActivityAccountBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AccountActivity extends AppCompatActivity implements ActivityBasics {

    private TextInputEditText act_account_location_TI_edit;
    private Button act_account_send_button;
    private Button act_account_logOut_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAccountBinding binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();
    }


    @Override
    public void getActivityElements() {
        act_account_location_TI_edit = findViewById(R.id.act_account_location_TI_edit);
        act_account_send_button = findViewById(R.id.act_account_send_button);
        act_account_logOut_button = findViewById(R.id.act_account_logOut_button);
    }

    @Override
    public void setListeners()
    {
        act_account_send_button_onClick();
        act_account_logOut_button_onClick();
    }

    private void act_account_send_button_onClick()
    {
        act_account_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = act_account_location_TI_edit.getText().toString().trim();
                act_account_location_TI_edit.getText().clear();

                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/locations/new", location);
                Thread connectionThread = new Thread(httpRequestsAccount);
                connectionThread.start();
            }
        });
    }

    private void act_account_logOut_button_onClick()
    {
        act_account_logOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalManager.saveEmailOrUsername(AccountActivity.this, null);
                GlobalManager.savePassword(AccountActivity.this, null);

                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });
    }
}