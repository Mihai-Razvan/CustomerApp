package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.customerapp_client.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements ActivityBasics {

    private ActivityRegisterBinding binding;
    private EditText act_register_email_editText;
    private EditText act_register_username_editText;
    private EditText act_register_password_editText;
    private Button act_register_backToLogIn_button;
    private Button act_register_register_button;
    private TextView act_register_status_TW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getExtras();
        getActivityElements();
        setListeners();
    }

    @Override
    public void getActivityElements() {
        act_register_email_editText = findViewById(R.id.act_register_email_editText);
        act_register_username_editText = findViewById(R.id.act_register_username_editText);
        act_register_password_editText = findViewById(R.id.act_register_password_editText);
        act_register_backToLogIn_button = findViewById(R.id.act_register_backToLogIn_button);
        act_register_register_button = findViewById(R.id.act_register_register_button);
        act_register_status_TW = findViewById(R.id.act_register_status_TW);
    }

    @Override
    public void setListeners() {
        act_register_backToLogIn_button_onClick();
        act_register_register_button_onClick();
    }

    @Override
    public void getExtras() {

    }

    private void act_register_backToLogIn_button_onClick()
    {
        act_register_backToLogIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void act_register_register_button_onClick()
    {
        act_register_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = act_register_email_editText.getText().toString().trim();
                String username = act_register_username_editText.getText().toString().trim();
                String password = act_register_password_editText.getText().toString().trim();

                HttpRequestsAuthentication httpRequestsAuthentication = new HttpRequestsAuthentication("/authentication/register", email, username, password);
                Thread connectionThread = new Thread(httpRequestsAuthentication);
                connectionThread.start();

                try {
                    connectionThread.join();
                    int registerResponseCode = httpRequestsAuthentication.getRegisterResponseCode();
                    switch (registerResponseCode) {
                        case -3:
                            act_register_status_TW.setText("User already exists");
                            act_register_password_editText.getText().clear();
                            break;
                        case -2:
                            act_register_status_TW.setText("This email is already used!");
                            act_register_password_editText.getText().clear();
                            break;
                        case -1:
                            act_register_status_TW.setText("Failed to connect to database! Please try again!");
                            break;
                        case 0:
                            act_register_status_TW.setText("Error trying to connect to server! Please try again!");
                            break;
                        default:
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("clientId", registerResponseCode);
                            startActivity(intent);
                    }
                }
                catch (InterruptedException e) {
                    System.out.println("COULDN'T LOG IN USER" + e.getMessage());
                }
            }
        });
    }
}