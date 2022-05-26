package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.customerapp_client.databinding.ActivityLoginBinding;


public class ActivityLogin extends AppCompatActivity implements ActivityBasics {

    private ConstraintLayout act_login_main_container;
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

        if(checkLoggedIn()) //user is logged in
            userLoggedIn();
        else
            act_login_main_container.setVisibility(View.VISIBLE); //it is initially invisible //so the login ui won't be visible for a brief moment before switching to mainAct when logged in success
    }


    @Override
    public void getActivityElements() {
        act_login_main_container = findViewById(R.id.act_login_main_container);
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
                            logInSuccess(logInResponseCode, emailOrUsername, password);
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
                startActivity(new Intent(ActivityLogin.this, ActivityRegister.class));
            }
        });
    }

    private void logInSuccess(int clientId, String emailOrUsername, String password)     //happens when log in is successful
    {
        GlobalManager.setClientId(clientId);
        GlobalManager.saveEmailOrUsername(ActivityLogin.this, emailOrUsername);
        GlobalManager.savePassword(ActivityLogin.this, password);
        startActivity(new Intent(ActivityLogin.this, ActivityMain.class));
    }

    private boolean checkLoggedIn()  //return true if savedEmailOrUsername an savedPassword are not null, so the user is logged in
    {
        return GlobalManager.getSavedEmailOrUsername(ActivityLogin.this) != null && GlobalManager.getSavedPassword(ActivityLogin.this) != null;
    }

    private void userLoggedIn() //if user is logged in (has its username and pass in savedpreferences this happens
    {
        String emailOrUsername = GlobalManager.getSavedEmailOrUsername(ActivityLogin.this);
        String password = GlobalManager.getSavedPassword(ActivityLogin.this);

        HttpRequestsAuthentication httpRequestsAuthentication = new HttpRequestsAuthentication("/authentication/login", emailOrUsername, password);
        Thread connectionThread = new Thread(httpRequestsAuthentication);
        connectionThread.start();

        try {
            connectionThread.join();
            //even if we got user and pass saved in preferences we still need to send a request to get his id, because we don t save id
            //in preferences. If the response is <= 0 it means there was an error on the server side; it can't be a failed log in because
            //of wrong user or pass cuz if user changes his username ot pass he does it inside the app so the new username/pass will also
            //be changed in preferences so they will be right
            int logInResponseCode = httpRequestsAuthentication.getLoginResponseCode();
            if(logInResponseCode > 0)
                logInSuccess(logInResponseCode, emailOrUsername, password);
            else
            {
                act_login_main_container.setVisibility(View.VISIBLE);
                act_logIn_status_TW.setText("Something went wrong with auto logging!");
            }
        }
        catch (InterruptedException e) {
            System.out.println("COULDN'T LOG IN USER" + e.getMessage());
            act_login_main_container.setVisibility(View.VISIBLE);
        }
    }
}