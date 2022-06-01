package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentAccountMain extends Fragment implements ActivityBasics{

    LinearLayout act_account_mainF_addresses_layout;
    LinearLayout act_account_mainF_deleteAccount_layout;

    View view;

    int colorOnTouch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_main, container, false);

        colorOnTouch =  ContextCompat.getColor(getContext(), R.color.testColor5);

        getActivityElements();
        setListeners();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_account_mainF_addresses_layout = view.findViewById(R.id.act_account_mainF_addresses_layout);
        act_account_mainF_deleteAccount_layout = view.findViewById(R.id.act_account_mainF_deleteAccount_layout);
    }

    @Override
    public void setListeners()
    {
        act_account_mainF_addresses_layout_listeners();
    }

    private void act_account_mainF_addresses_layout_listeners()
    {
        act_account_mainF_addresses_layout_onClick();
        act_account_mainF_deleteAccount_layout_onClick();
    }

    public void act_account_mainF_addresses_layout_onClick() {

        act_account_mainF_addresses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddressesFragment();
            }
        });
    }

    public void act_account_mainF_deleteAccount_layout_onClick() {

        act_account_mainF_deleteAccount_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/delete");
                Thread connectionThread = new Thread(httpRequestsAccount);
                connectionThread.start();

                try {
                    connectionThread.join();
                    String status = httpRequestsAccount.getStatus();

                    if(status.equals("Success"))
                    {
                        System.out.println("SUCCESSFULLY DELETED ACCOUNT");
                        startActivity(new Intent(getActivity(), ActivityLogin.class));
                    }
                    else
                        System.out.println("COULDN'T DELETE ACCOUNT");
                }
                catch (InterruptedException e) {
                    System.out.println("COULDN'T DELETE ACCOUNT");
                }
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