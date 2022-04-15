package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.customerapp_client.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements ActivityBasics {

    private EditText act_login_email_or_username_editText;
    private EditText act_login_password_editText;
    private Button act_login_signIn_button;
    private Button act_login_toRegister_button;
    private TextView act_logIn_status_TW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();
    }


    @Override
    public void getActivityElements() {
        act_login_email_or_username_editText = findViewById(R.id.act_register_email_editText);
        act_login_password_editText = findViewById(R.id.act_register_username_editText);
        act_login_signIn_button = findViewById(R.id.act_login_signIn_button);
        act_login_toRegister_button = findViewById(R.id.act_login_toRegister_button);
        act_logIn_status_TW = findViewById(R.id.act_register_status_TW);
    }

    @Override
    public void setListeners() {

        act_login_signIn_button_onClick();
        act_login_toRegister_button_onClick();
    }

    private void act_login_signIn_button_onClick()
    {
        act_login_signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailOrUsername = act_login_email_or_username_editText.getText().toString().trim();
                String password = act_login_password_editText.getText().toString().trim();

                HttpRequestsAuthentication httpRequestsAuthentication = new HttpRequestsAuthentication("/authentication/login", emailOrUsername, password);
                Thread connectionThread = new Thread(httpRequestsAuthentication);
                connectionThread.start();

                try {
                    connectionThread.join();
                    int logInResponseCode = httpRequestsAuthentication.getLoginResponseCode();
                    switch (logInResponseCode) {
                        case -3:
                            act_logIn_status_TW.setText("User does not exist!");
                            act_login_email_or_username_editText.getText().clear();
                            act_login_password_editText.getText().clear();
                            break;
                        case -2:
                            act_logIn_status_TW.setText("Wrong password!");
                            act_login_password_editText.getText().clear();
                            break;
                        case -1:
                            act_logIn_status_TW.setText("Failed to connect to database! Please try again!");
                            break;
                        case 0:
                            act_logIn_status_TW.setText("Error trying to connect to server! Please try again!");
                            break;
                        default:
                            GlobalManager.setClientId(logInResponseCode);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
                catch (InterruptedException e) {
                    System.out.println("COULDN'T LOG IN USER" + e.getMessage());
                }
            }
        });
    }

    private void act_login_toRegister_button_onClick()
    {
        act_login_toRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

}