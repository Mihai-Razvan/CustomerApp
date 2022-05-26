package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentAccountAddAddress extends Fragment implements ActivityBasics {

    private Button act_account_addAddressesF_add_button;
    private Button act_account_addAddressesF_logOut_button;
    private EditText act_account_addAddressesF_city_ET;
    private EditText act_account_addAddressesF_street_ET;
    private EditText act_account_addAddressesF_number_ET;
    private EditText act_account_addAddressesF_details_ET;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_add_address, container, false);
        getActivityElements();
        setListeners();

        return view;
    }


    @Override
    public void getActivityElements() {
        act_account_addAddressesF_add_button = view.findViewById(R.id.act_account_addAddressesF_add_button);
        act_account_addAddressesF_logOut_button = view.findViewById(R.id.act_account_addAddressesF_logOut_button);
        act_account_addAddressesF_city_ET = view.findViewById(R.id.act_account_addAddressesF_city_ET);
        act_account_addAddressesF_street_ET = view.findViewById(R.id.act_account_addAddressesF_street_ET);
        act_account_addAddressesF_number_ET = view.findViewById(R.id.act_account_addAddressesF_number_ET);
        act_account_addAddressesF_details_ET = view.findViewById(R.id.act_account_addAddressesF_details_ET);
    }

    @Override
    public void setListeners()
    {
        act_account_addAddressesF_add_button_onClick();
        act_account_addAddressesF_logOut_button_onClick();
    }

    private void act_account_addAddressesF_add_button_onClick()
    {
        act_account_addAddressesF_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = act_account_addAddressesF_city_ET.getText().toString().trim();
                act_account_addAddressesF_city_ET.getText().clear();

                String street = act_account_addAddressesF_street_ET.getText().toString().trim();
                act_account_addAddressesF_street_ET.getText().clear();

                String number = act_account_addAddressesF_number_ET.getText().toString().trim();
                act_account_addAddressesF_number_ET.getText().clear();

                String details = act_account_addAddressesF_details_ET.getText().toString().trim();
                act_account_addAddressesF_details_ET.getText().clear();

                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/addresses/new", city, street, number, details);
                Thread connectionThread = new Thread(httpRequestsAccount);
                connectionThread.start();
            }
        });
    }

    private void act_account_addAddressesF_logOut_button_onClick()
    {
        act_account_addAddressesF_logOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalManager.saveEmailOrUsername(getActivity(), null);
                GlobalManager.savePassword(getActivity(), null);

                startActivity(new Intent(getActivity(), ActivityLogin.class));
            }
        });
    }
}