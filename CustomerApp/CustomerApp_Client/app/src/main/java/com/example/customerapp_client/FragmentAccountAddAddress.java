package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class FragmentAccountAddAddress extends Fragment implements ActivityBasics {

    private TextInputEditText act_account_addAddressesF_location_TI_edit;
    private Button act_account_addAddressesF_send_button;
    private Button act_account_addAddressesF_logOut_button;

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
        act_account_addAddressesF_location_TI_edit = view.findViewById(R.id.act_account_addAddressesF_location_TI_edit);
        act_account_addAddressesF_send_button = view.findViewById(R.id.act_account_addAddressesF_send_button);
        act_account_addAddressesF_logOut_button = view.findViewById(R.id.act_account_addAddressesF_logOut_button);
    }

    @Override
    public void setListeners()
    {
        act_account_addAddressesF_send_button_onClick();
        act_account_addAddressesF_logOut_button_onClick();
    }

    private void act_account_addAddressesF_send_button_onClick()
    {
        act_account_addAddressesF_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = act_account_addAddressesF_location_TI_edit.getText().toString().trim();
                act_account_addAddressesF_location_TI_edit.getText().clear();

                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/locations/new", location);
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

                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}