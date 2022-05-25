package com.example.customerapp_client;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentAccountMain extends Fragment implements ActivityBasics{

    LinearLayout act_account_mainF_addresses_layout;

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
    }

    @Override
    public void setListeners()
    {
        act_account_mainF_addresses_layout_listeners();
    }

    private void act_account_mainF_addresses_layout_listeners()
    {
        act_account_mainF_addresses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddressesFragment();
            }
        });

//        act_account_mainF_addresses_layout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN)
//                    act_account_mainF_addresses_layout.setBackgroundColor(colorOnTouch);
//                else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
//                    act_account_mainF_addresses_layout.setBackgroundResource(R.drawable.test_border_2);
//                return true;
//            }
//        });

    }

    private void setAddressesFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddresses());
        fragmentTransaction.commit();
    }
}