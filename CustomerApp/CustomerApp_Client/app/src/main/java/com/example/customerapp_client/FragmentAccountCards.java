package com.example.customerapp_client;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

public class FragmentAccountCards extends Fragment implements ActivityBasics{

    RadioGroup act_account_cardsF_radioGroup;
    Button act_account_cardsF_addMethod_button;
    TextView act_account_cardsF_balance_TW;

    View view;
    ArrayList<String> cardsList;   //it contains the last 4 digits for every card number
    int balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_cards, container, false);

        getActivityElements();
        setListeners();

        cardsList = new ArrayList<>();
        addCardsList();
        getBalance();

        return view;
    }

    @Override
    public void getActivityElements()
    {
        act_account_cardsF_radioGroup = view.findViewById(R.id.act_account_cardsF_radioGroup);
        act_account_cardsF_addMethod_button = view.findViewById(R.id.act_account_cardsF_addMethod_button);
        act_account_cardsF_balance_TW = view.findViewById(R.id.act_account_cardsF_balance_TW);
    }

    @Override
    public void setListeners()
    {
        act_account_cardsF_addMethod_button_onClick();
    }

    private void act_account_cardsF_addMethod_button_onClick()
    {
        act_account_cardsF_addMethod_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddCardFragment();
            }
        });
    }

    private void addCardsList()
    {
        HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/cards");
        Thread connectionThread = new Thread(httpRequestsAccount);
        connectionThread.start();

        try {
            connectionThread.join();
            cardsList = httpRequestsAccount.getCardsList();

            int cardsListSize = cardsList.size();
            for(int i = 0 ; i < cardsListSize; i++)
            {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText("**** " + cardsList.get(i));

                radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setTextColor(Color.BLACK);
                if(i != cardsListSize - 1)
                    radioButton.setBackgroundResource(R.drawable.test_border_3);

                act_account_cardsF_radioGroup.addView(radioButton);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getBalance()
    {
        HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/balance");
        Thread connectionThread = new Thread(httpRequestsAccount);
        connectionThread.start();

        try {
            connectionThread.join();
            balance = Integer.parseInt(httpRequestsAccount.getBalance());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            balance = -1;
        }

        if(balance == -1)
            act_account_cardsF_balance_TW.setText("ERROR");
        else
            act_account_cardsF_balance_TW.setText("RON " + balance);
    }

    private void setAddCardFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddCard());
        fragmentTransaction.commit();
    }
}