package com.example.customerapp_client;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.customerapp_client.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements ActivityBasics {

    private ActivityLoginBinding binding;

    private EditText act_login_email_sau_nume;
    private EditText act_login_password;
    private Button act_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();
    }


    @Override
    public void getActivityElements() {
        act_login_email_sau_nume = findViewById(R.id.act_login_email_sau_nume);
        act_login_password = findViewById(R.id.act_login_password);
        act_login_button = findViewById(R.id.act_login_button);
    }

    @Override
    public void setListeners() {
        act_login_button_onClick();
    }

    private void act_login_button_onClick()
    {
        act_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailOrUsername = act_login_email_sau_nume.getText().toString().trim();
                String password = act_login_password.getText().toString().trim();
                act_login_email_sau_nume.getText().clear();
                act_login_password.getText().clear();

                HttpRequestsAuthentication httpRequestsAuthentication = new HttpRequestsAuthentication("/authentication/login", emailOrUsername, password);
                Thread connectionThread = new Thread(httpRequestsAuthentication);
                connectionThread.start();
            }
        });
    }
}