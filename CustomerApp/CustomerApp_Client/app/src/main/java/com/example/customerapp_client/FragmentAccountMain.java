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
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;

public class FragmentAccountMain extends Fragment implements ActivityBasics{

    LinearLayout act_account_mainF_options_layout;  //main layout of this fragment

    LinearLayout act_account_mainF_contactInfo_layout;
    LinearLayout act_account_mainF_changePassword_layout;
    LinearLayout act_account_mainF_addresses_layout;
    LinearLayout act_account_mainF_deleteAccount_layout;

    LinearLayout act_account_mainF_warning_layout;
    Button act_account_mainF_warningYes_layout;
    Button act_account_mainF_warningNo_layout;

    Button act_account_mainF_logOut_button;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_main, container, false);

        getActivityElements();
        act_account_mainF_warning_layout.setVisibility(View.INVISIBLE);
        setListeners();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_account_mainF_options_layout = view.findViewById(R.id.act_account_mainF_options_layout);

        act_account_mainF_contactInfo_layout = view.findViewById(R.id.act_account_mainF_contactInfo_layout);
        act_account_mainF_changePassword_layout = view.findViewById(R.id.act_account_mainF_changePassword_layout);
        act_account_mainF_addresses_layout = view.findViewById(R.id.act_account_mainF_addresses_layout);
        act_account_mainF_deleteAccount_layout = view.findViewById(R.id.act_account_mainF_deleteAccount_layout);

        act_account_mainF_warning_layout = view.findViewById(R.id.act_account_mainF_warning_layout);
        act_account_mainF_warningYes_layout = view.findViewById(R.id.act_account_mainF_warningYes_layout);
        act_account_mainF_warningNo_layout = view.findViewById(R.id.act_account_mainF_warningNo_layout);

        act_account_mainF_logOut_button = view.findViewById(R.id.act_account_mainF_logOut_button);
    }

    @Override
    public void setListeners()
    {
        act_account_mainF_addresses_layout_listeners();
    }

    private void act_account_mainF_addresses_layout_listeners()
    {
        act_account_mainF_warningYes_layout_onClick();
        act_account_mainF_warningNo_layout_onClick();
        act_account_mainF_contactInfo_layout_onClick();
        act_account_mainF_addresses_layout_onClick();
        act_account_mainF_deleteAccount_layout_onClick();
        act_account_mainF_changePassword_layout_onClick();
        act_account_mainF_logOut_button_onClick();
    }

    private void act_account_mainF_warningYes_layout_onClick()
    {
        act_account_mainF_warningYes_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
            }
        });
    }

    private void act_account_mainF_warningNo_layout_onClick()
    {
        act_account_mainF_warningNo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_account_mainF_warning_layout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void act_account_mainF_addresses_layout_onClick() {

        act_account_mainF_addresses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddressesFragment();
            }
        });
    }


    private void act_account_mainF_deleteAccount_layout_onClick() {

        act_account_mainF_deleteAccount_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_account_mainF_warning_layout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void act_account_mainF_changePassword_layout_onClick() {

        act_account_mainF_changePassword_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPasswordFragment();
            }
        });
    }

    private void act_account_mainF_contactInfo_layout_onClick()
    {
        act_account_mainF_contactInfo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContactInfoFragment();
            }
        });
    }

    private void deleteAccount()
    {
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

    private void act_account_mainF_logOut_button_onClick()
    {
        act_account_mainF_logOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalManager.saveEmailOrUsername(getActivity(), null);
                GlobalManager.savePassword(getActivity(), null);

                startActivity(new Intent(getActivity(), ActivityLogin.class));
            }
        });
    }

    //////////////////////////////////////////////////////////

    private void setContactInfoFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountContactInfo());
        fragmentTransaction.commit();
    }

    private void setAddressesFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddresses());
        fragmentTransaction.commit();
    }

    private void setCardsFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountCards());
        fragmentTransaction.commit();
    }

    private void setPasswordFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountPassword());
        fragmentTransaction.commit();
    }
}