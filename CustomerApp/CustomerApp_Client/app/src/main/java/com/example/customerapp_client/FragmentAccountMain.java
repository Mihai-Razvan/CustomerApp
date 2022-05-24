package com.example.customerapp_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentAccountMain extends Fragment implements ActivityBasics{

    LinearLayout act_account_mainF_addresses_layout;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_main, container, false);
        getActivityElements();
        setListeners();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_account_mainF_addresses_layout = view.findViewById(R.id.act_account_mainF_addresses_layout);
    }

    @Override
    public void setListeners()
    {
        act_account_mainF_addresses_layout_onClick();
    }

    private void act_account_mainF_addresses_layout_onClick()
    {
        act_account_mainF_addresses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddressesFragment();
            }
        });
    }

    private void setAddressesFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddresses());
        fragmentTransaction.commit();
    }
}