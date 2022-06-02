package com.example.customerapp_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;


public class FragmentAccountPassword extends Fragment implements ActivityBasics{

    private EditText act_account_passwordF_actualPass_ET;
    private EditText act_account_passwordF_newPass_ET;
    private EditText act_account_passwordF_newPassConfirm_ET;
    private Button act_account_passwordF_change_button;
    private TextView act_account_passwordF_warning_TW;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_password, container, false);

        getActivityElements();
        setListeners();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_account_passwordF_actualPass_ET = view.findViewById(R.id.act_account_passwordF_actualPass_ET);
        act_account_passwordF_newPass_ET = view.findViewById(R.id.act_account_passwordF_newPass_ET);
        act_account_passwordF_newPassConfirm_ET = view.findViewById(R.id.act_account_passwordF_newPassConfirm_ET);
        act_account_passwordF_change_button = view.findViewById(R.id.act_account_passwordF_change_button);
        act_account_passwordF_warning_TW = view.findViewById(R.id.act_account_passwordF_warning_TW);
    }

    @Override
    public void setListeners() {
        act_account_passwordF_change_button_onClick();
        act_account_passwordF_actualPass_ET_onTextChanged();
        act_account_passwordF_newPass_ET_onTextChanged();
        act_account_passwordF_newPassConfirm_ET_onTextChanged();
    }

    private  void  act_account_passwordF_change_button_onClick()
    {
        act_account_passwordF_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = act_account_passwordF_actualPass_ET.getText().toString().trim();
                String newPassword = act_account_passwordF_newPass_ET.getText().toString().trim();
                String confirmPassword = act_account_passwordF_newPassConfirm_ET.getText().toString().trim();
                boolean preCheck = false;

                if(currentPassword.isEmpty())
                {
                    act_account_passwordF_actualPass_ET.setBackgroundResource(R.drawable.test_border_5);
                    preCheck = true;
                }

                if(newPassword.isEmpty())
                {
                    act_account_passwordF_newPass_ET.setBackgroundResource(R.drawable.test_border_5);
                    preCheck = true;
                }
                if(confirmPassword.isEmpty())
                {
                    act_account_passwordF_newPassConfirm_ET.setBackgroundResource(R.drawable.test_border_5);
                    preCheck = true;
                }

                if(!newPassword.equals(confirmPassword))
                {
                    act_account_passwordF_warning_TW.setText("The passwords you introduced doesn't match!");
                    act_account_passwordF_newPass_ET.getText().clear();
                    act_account_passwordF_newPassConfirm_ET.getText().clear();
                    preCheck = true;
                }

                if(preCheck)
                    return;

                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/password/change", currentPassword, newPassword);
                Thread connectionThread = new Thread(httpRequestsAccount);
                connectionThread.start();

                try {
                    connectionThread.join();
                    String status = httpRequestsAccount.getStatus();

                    switch (status) {
                        case "-2":
                            act_account_passwordF_warning_TW.setText("Internal error!");
                            break;
                        case  "-1":
                            act_account_passwordF_warning_TW.setText("The current password you introduced is wrong!");
                            act_account_passwordF_actualPass_ET.getText().clear();
                            break;
                        default:
                            GlobalManager.savePassword(getContext(), newPassword);
                            act_account_passwordF_warning_TW.setText("Password successfully changed");
                            act_account_passwordF_actualPass_ET.getText().clear();
                    }

                    act_account_passwordF_newPass_ET.getText().clear();
                    act_account_passwordF_newPassConfirm_ET.getText().clear();
                }
                catch (InterruptedException e) {
                    System.out.println("COULDN'T CHANGE PASSWORD");
                    act_account_passwordF_warning_TW.setText("Internal error!");
                    act_account_passwordF_newPass_ET.getText().clear();
                    act_account_passwordF_newPassConfirm_ET.getText().clear();
                }
            }
        });
    }

    private void act_account_passwordF_actualPass_ET_onTextChanged()
    {
        act_account_passwordF_actualPass_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(act_account_passwordF_actualPass_ET.getText().toString().trim().length() != 0)
                    act_account_passwordF_actualPass_ET.setBackgroundResource(R.drawable.test_border_6);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void act_account_passwordF_newPass_ET_onTextChanged()
    {
        act_account_passwordF_newPass_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(act_account_passwordF_newPass_ET.getText().toString().trim().length() != 0)
                    act_account_passwordF_newPass_ET.setBackgroundResource(R.drawable.test_border_6);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void act_account_passwordF_newPassConfirm_ET_onTextChanged()
    {
        act_account_passwordF_newPassConfirm_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(act_account_passwordF_newPassConfirm_ET.getText().toString().trim().length() != 0)
                    act_account_passwordF_newPassConfirm_ET.setBackgroundResource(R.drawable.test_border_6);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}