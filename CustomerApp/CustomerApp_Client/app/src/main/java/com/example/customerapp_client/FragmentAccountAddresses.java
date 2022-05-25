package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class FragmentAccountAddresses extends Fragment implements ActivityBasics{

    RecyclerView act_account_addressesF_recycleView;
    Button act_account_addressesF_addAddress_button;

    private View view;
    private AddressesAdapter addressesAdapter;
    private ArrayList<String> addressesList;  //these are in fullAddress form


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_addresses, container, false);
        addressesList = new ArrayList<>();
        getActivityElements();
        setListeners();
        getAddresses();
        setAddressesAdapter();

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

    private void getAddresses()         //it gets addresses in fullAddress form
    {
        HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/addresses");
        Thread connectionThread = new Thread(httpRequestsAccount);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsAccount.getStatus();

            if(status.equals("Successful"))
            {
                addressesList.addAll(httpRequestsAccount.getAddressesList());
            }
            else
                System.out.println("COULDN'T ADD ADDRESS");
        }
        catch (InterruptedException e) {
            System.out.println("COULDN'T ADD ADDRESS");
        }
    }

    private void setAddressesAdapter()
    {
        addressesAdapter = new AddressesAdapter(addressesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        act_account_addressesF_recycleView.setLayoutManager(layoutManager);
        act_account_addressesF_recycleView.setItemAnimator(new DefaultItemAnimator());
        act_account_addressesF_recycleView.setAdapter(addressesAdapter);
    }



    /////////////////////////////////////////////////////////////////

    private void setAddAddressFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddAddress());
        fragmentTransaction.commit();
    }
}