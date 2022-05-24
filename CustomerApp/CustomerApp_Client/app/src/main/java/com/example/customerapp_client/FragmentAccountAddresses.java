package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

public class FragmentAccountAddresses extends Fragment implements ActivityBasics{

    RecyclerView act_account_addressesF_recycleView;
    Button act_account_addressesF_addAddress_button;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_addresses, container, false);
        getActivityElements();
        setListeners();

        return view;
    }


    @Override
    public void getActivityElements() {
        act_account_addressesF_recycleView = view.findViewById(R.id.act_account_addressesF_recycleView);
        act_account_addressesF_addAddress_button = view.findViewById(R.id.act_account_addressesF_addAddress_button);
    }

    @Override
    public void setListeners()
    {
        act_account_addressesF_addAddress_button_onClick();
    }

    private void act_account_addressesF_addAddress_button_onClick()
    {
        act_account_addressesF_addAddress_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddAddressFragment();
            }
        });
    }

    private void setAddAddressFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddAddress());
        fragmentTransaction.commit();
    }

}