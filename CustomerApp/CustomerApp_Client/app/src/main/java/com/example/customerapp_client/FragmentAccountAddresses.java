package com.example.customerapp_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

public class FragmentAccountAddresses extends Fragment implements ActivityBasics{

    RecyclerView act_account_addressesF_recycleView;

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
    }

    @Override
    public void setListeners()
    {

    }


}