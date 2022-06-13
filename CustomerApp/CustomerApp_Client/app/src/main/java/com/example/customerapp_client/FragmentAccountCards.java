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
    Button act_account_cardsF_addCard_button;
    Button act_account_cardsF_addFunds_button;
    TextView act_account_cardsF_balance_TW;

    View view;
    ArrayList<DataCard> cardsList;
    float balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_cards, container, false);

        getActivityElements();
        setListeners();

        if(GlobalManager.getPayMethod(getContext()) == null)
            GlobalManager.setPayMethod(getContext(), "Wallet");

        cardsList = new ArrayList<>();
        addCardsList();
        getBalance();

        return view;
    }

    @Override
    public void getActivityElements()
    {
        act_account_cardsF_radioGroup = view.findViewById(R.id.act_account_cardsF_radioGroup);
        act_account_cardsF_addCard_button = view.findViewById(R.id.act_account_cardsF_addCard_button);
        act_account_cardsF_balance_TW = view.findViewById(R.id.act_account_cardsF_balance_TW);
        act_account_cardsF_addFunds_button = view.findViewById(R.id.act_account_cardsF_addFunds_button);
    }

    @Override
    public void setListeners()
    {
        act_account_cardsF_addCard_button_onClick();
        act_account_cardsF_addFunds_button_onClick();
        act_account_cardsF_radioGroup_onCheckedChange();
    }

    private void act_account_cardsF_addCard_button_onClick()
    {
        act_account_cardsF_addCard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddCardFragment();
            }
        });
    }

    private void act_account_cardsF_addFunds_button_onClick()
    {
        act_account_cardsF_addFunds_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddFundsFragment();
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
                DataCard dataCard = cardsList.get(i);
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText("**** " + dataCard.getCardNumber().substring(12, 16));

                radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setTextColor(Color.parseColor("#000000"));
                if(i != cardsListSize - 1)
                    radioButton.setBackgroundResource(R.drawable.test_border_3);

                act_account_cardsF_radioGroup.addView(radioButton);
                if(GlobalManager.getPayMethod(getContext()).equals("Card") && dataCard.getCardNumber().equals(GlobalManager.getCardNumber(getContext())) &&
                        dataCard.getExpirationDate().equals(GlobalManager.getExpirationDate(getContext())) && dataCard.getCvv().equals(GlobalManager.getCvv(getContext())))
                {
                    act_account_cardsF_radioGroup.check(act_account_cardsF_radioGroup.getChildAt(i + 1).getId());
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(GlobalManager.getPayMethod(getContext()).equals("Wallet"))
            act_account_cardsF_radioGroup.check(act_account_cardsF_radioGroup.getChildAt(0).getId());
    }

    private void getBalance()
    {
        HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/balance");
        Thread connectionThread = new Thread(httpRequestsAccount);
        connectionThread.start();

        try {
            connectionThread.join();
            balance = Float.parseFloat(httpRequestsAccount.getBalance());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            balance = -1;
        }

        if(balance == -1)
            act_account_cardsF_balance_TW.setText("ERROR");
        else if(balance == Math.round(balance))  //if the balance is an int it will be displayed as an int
                act_account_cardsF_balance_TW.setText("RON " + Math.round(balance));
        else
            act_account_cardsF_balance_TW.setText("RON " + balance);
    }

    private void  act_account_cardsF_radioGroup_onCheckedChange()
    {
        act_account_cardsF_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               int checkedButtonId = act_account_cardsF_radioGroup.getCheckedRadioButtonId();
               int checkedButtonIndexInGroup = act_account_cardsF_radioGroup.indexOfChild(view.findViewById(checkedButtonId));

                if(checkedButtonIndexInGroup == 0)
                    GlobalManager.setPayMethod(getContext(), "Wallet");
                else
                {
                    GlobalManager.setPayMethod(getContext(), "Card");
                    DataCard selectedCard = cardsList.get(checkedButtonIndexInGroup - 1);
                    GlobalManager.setCard(getContext(), selectedCard.getCardNumber(), selectedCard.getExpirationDate(), selectedCard.getCvv());
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setAddCardFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddCard());
        fragmentTransaction.commit();
    }

    private void setAddFundsFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_account_frameLayout, new FragmentAccountAddFunds());
        fragmentTransaction.commit();
    }
}