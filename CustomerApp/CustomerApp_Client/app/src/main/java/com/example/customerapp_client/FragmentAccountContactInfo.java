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

public class FragmentAccountContactInfo extends Fragment implements ActivityBasics{

    private EditText act_account_contactInfoF_firstName_ET;
    private EditText act_account_contactInfoF_lastName_ET;
    private EditText act_account_contactInfoF_email_ET;
    private EditText act_account_contactInfoF_phone_ET;
    private Button act_account_contactInfoF_save_button;
    private TextView act_account_contactInfoF_warning_TW;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_contact_info, container, false);

        getActivityElements();
        setListeners();
        loadContactInfo();

        return view;
    }

    @Override
    public void getActivityElements()
    {
        act_account_contactInfoF_firstName_ET = view.findViewById(R.id.act_account_contactInfoF_firstName_ET);
        act_account_contactInfoF_lastName_ET = view.findViewById(R.id.act_account_contactInfoF_lastName_ET);
        act_account_contactInfoF_email_ET = view.findViewById(R.id.act_account_contactInfoF_email_ET);
        act_account_contactInfoF_phone_ET = view.findViewById(R.id.act_account_contactInfoF_phone_ET);
        act_account_contactInfoF_save_button = view.findViewById(R.id.act_account_contactInfoF_save_button);
        act_account_contactInfoF_warning_TW = view.findViewById(R.id.act_account_contactInfoF_warning_TW);
    }

    @Override
    public void setListeners()
    {
        act_account_contactInfoF_save_button_onClick();
        act_account_contactInfoF_email_ET_onTextChanged();
        act_account_contactInfoF_phone_ET_onTextChanged();
    }

    private void loadContactInfo()
    {
        HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/contact");
        Thread connectionThread = new Thread(httpRequestsAccount);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsAccount.getStatus();

            if(status.equals("Failed"))
                act_account_contactInfoF_warning_TW.setText("Internal error!");
            else
            {
                DataClientInfo dataClientInfo = httpRequestsAccount.getDataClientInfo();
                act_account_contactInfoF_firstName_ET.setText(dataClientInfo.getFirstName());
                act_account_contactInfoF_lastName_ET.setText(dataClientInfo.getLastName());
                act_account_contactInfoF_email_ET.setText(dataClientInfo.getEmail());
                act_account_contactInfoF_phone_ET.setText(dataClientInfo.getPhone());
            }
        }
        catch (InterruptedException e) {
            System.out.println("COULDN'T SET CONTACT INFO");
            act_account_contactInfoF_warning_TW.setText("Internal error!");
        }
    }

    private  void  act_account_contactInfoF_save_button_onClick()
    {
        act_account_contactInfoF_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = act_account_contactInfoF_firstName_ET.getText().toString().trim();
                String lastName = act_account_contactInfoF_lastName_ET.getText().toString().trim();
                String email = act_account_contactInfoF_email_ET.getText().toString().trim();
                String phone = act_account_contactInfoF_phone_ET.getText().toString().trim();

                boolean preCheck = false;

                if(email.isEmpty())
                {
                    act_account_contactInfoF_warning_TW.setText("Complete email!");
                    preCheck = true;
                }

                if(!phone.matches("^[0-9]+$"))
                {
                    act_account_contactInfoF_warning_TW.setText("Phone number invalid format!");
                    preCheck = true;
                }

                if(preCheck)
                    return;

                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/contact/change", firstName, lastName, email, phone, 1);
                Thread connectionThread = new Thread(httpRequestsAccount);
                connectionThread.start();

                try {
                    connectionThread.join();
                    String status = httpRequestsAccount.getStatus();

                    switch (status){
                        case "-2":
                            act_account_contactInfoF_warning_TW.setText("Internal error!");
                            break;
                        case "-1":
                            act_account_contactInfoF_warning_TW.setText("Email already used by another user!");
                            break;
                        default:
                            GlobalManager.saveEmailOrUsername(getContext(), email);
                            act_account_contactInfoF_warning_TW.setText("Contact info successfully changed!");
                    }

                }
                catch (InterruptedException e) {
                    System.out.println("COULDN'T CHANGE CONTACT INFO");
                    act_account_contactInfoF_warning_TW.setText("Internal error!");
                }
            }
        });
    }

    private void  act_account_contactInfoF_phone_ET_onTextChanged()
    {
        act_account_contactInfoF_phone_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = act_account_contactInfoF_phone_ET.getText().toString().trim();
                if(phone.length() != 0 && !phone.matches("^[0-9]+$"))
                    act_account_contactInfoF_phone_ET.setBackgroundResource(R.drawable.test_border_5);
                else
                    act_account_contactInfoF_phone_ET.setBackgroundResource(R.drawable.test_border_6);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void  act_account_contactInfoF_email_ET_onTextChanged()
    {
        act_account_contactInfoF_email_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(act_account_contactInfoF_email_ET.getText().toString().trim().length() == 0)
                    act_account_contactInfoF_email_ET.setBackgroundResource(R.drawable.test_border_5);
                else
                    act_account_contactInfoF_email_ET.setBackgroundResource(R.drawable.test_border_6);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}