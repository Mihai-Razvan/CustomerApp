package com.example.customerapp_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FragmentWalletAddFunds extends Fragment implements ActivityBasics{

    EditText act_wallet_addFundsF_number_ET;
    EditText act_wallet_addFundsF_expiration_ET;
    EditText act_wallet_addFundsF_cvv_ET;
    EditText act_wallet_addFundsF_amount_ET;
    Button act_wallet_addFundsF_add_button;
    TextView act_wallet_addFundsF_warning_TW;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wallet_add_funds, container, false);

        getActivityElements();
        setListeners();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_wallet_addFundsF_number_ET = view.findViewById(R.id.act_wallet_addFundsF_number_ET);
        act_wallet_addFundsF_expiration_ET = view.findViewById(R.id.act_wallet_addFundsF_expiration_ET);
        act_wallet_addFundsF_cvv_ET = view.findViewById(R.id.act_wallet_addFundsF_cvv_ET);
        act_wallet_addFundsF_amount_ET = view.findViewById(R.id.act_wallet_addFundsF_amount_ET);
        act_wallet_addFundsF_add_button = view.findViewById(R.id.act_wallet_addFundsF_add_button);
        act_wallet_addFundsF_warning_TW = view.findViewById(R.id.act_wallet_addFundsF_warning_TW);
    }

    @Override
    public void setListeners() {

        act_wallet_addFundsF_add_button_onClick();
        act_wallet_addFundsF_number_ET_onTextChanged();
        act_wallet_addFundsF_expiration_ET_onTextChanged();
        act_wallet_addFundsF_cvv_ET_onTextChanged();
    }

    private void act_wallet_addFundsF_add_button_onClick() {
        act_wallet_addFundsF_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = act_wallet_addFundsF_number_ET.getText().toString().trim().replace(" ", "");
                String expirationDate = act_wallet_addFundsF_expiration_ET.getText().toString().trim();
                String cvv = act_wallet_addFundsF_cvv_ET.getText().toString().trim();
                String amount = act_wallet_addFundsF_amount_ET.getText().toString().trim();

                boolean checked = false;

                if(cardNumber.length() != 16)
                {
                    act_wallet_addFundsF_number_ET.setBackgroundResource(R.drawable.test_border_5);
                    checked = true;
                }
                if(expirationDate.length() != 7)
                {
                    act_wallet_addFundsF_expiration_ET.setBackgroundResource(R.drawable.test_border_5);
                    checked = true;
                }
                if(cvv.length() != 3)
                {
                    act_wallet_addFundsF_cvv_ET.setBackgroundResource(R.drawable.test_border_5);
                    checked = true;
                }

                if(checked)
                    return;

                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/balance/add", cardNumber, expirationDate, cvv, Float.parseFloat(amount));
                Thread connectionThread = new Thread(httpRequestsAccount);
                connectionThread.start();

                try {
                    connectionThread.join();
                    String status = httpRequestsAccount.getStatus();

                    switch (status) {
                        case "-3":
                            act_wallet_addFundsF_warning_TW.setText("Internal error!");
                            break;
                        case "-2":
                            act_wallet_addFundsF_warning_TW.setText("Invalid card!");
                            break;
                        case "-1":
                            act_wallet_addFundsF_warning_TW.setText("Not enought funds!");
                            break;
                        case "1":
                            act_wallet_addFundsF_warning_TW.setText("Funds successfully added!");
                            break;
                    }
                }
                catch (InterruptedException e) {
                    System.out.println("COULDN'T ADD CARD");
                    act_wallet_addFundsF_warning_TW.setText("Internal error!");
                }
            }
        });
    }

    private void act_wallet_addFundsF_number_ET_onTextChanged() {

        act_wallet_addFundsF_number_ET.addTextChangedListener(new TextWatcher() {
            int initialTextLength;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initialTextLength = act_wallet_addFundsF_number_ET.getText().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String cardNumber = act_wallet_addFundsF_number_ET.getText().toString();


                if((cardNumber.length() == 4 && initialTextLength == 3) || (cardNumber.length() == 9 && initialTextLength == 8)
                        || (cardNumber.length() == 14 && initialTextLength == 13))
                {
                    act_wallet_addFundsF_number_ET.setText(cardNumber + " ");
                    act_wallet_addFundsF_number_ET.setSelection(initialTextLength + 1);
                }
                else if((cardNumber.length() == 15 && initialTextLength == 16) || (cardNumber.length() == 10 && initialTextLength == 11)
                        || (cardNumber.length() == 5 && initialTextLength == 6))
                {
                    act_wallet_addFundsF_number_ET.setText(cardNumber.substring(0, cardNumber.length() - 1));
                    act_wallet_addFundsF_number_ET.setSelection(initialTextLength - 1);
                }

                if(cardNumber.length() == 19)
                    act_wallet_addFundsF_number_ET.setBackgroundResource(R.drawable.test_border_8);
                else
                    act_wallet_addFundsF_number_ET.setBackgroundResource(R.drawable.test_border_5);
            }
        });
    }

    private void act_wallet_addFundsF_expiration_ET_onTextChanged()
    {
        act_wallet_addFundsF_expiration_ET.addTextChangedListener(new TextWatcher() {
            int initialTextLength;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initialTextLength = act_wallet_addFundsF_expiration_ET.getText().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String expirationDate = act_wallet_addFundsF_expiration_ET.getText().toString();

                if(expirationDate.length() == 1 && initialTextLength == 0 && Integer.parseInt(expirationDate) > 1)
                {
                    act_wallet_addFundsF_expiration_ET.setText("0" + expirationDate + " / ");
                    act_wallet_addFundsF_expiration_ET.setSelection(5);
                }
                else if(expirationDate.equals("00")){
                    act_wallet_addFundsF_expiration_ET.setText("0");
                    act_wallet_addFundsF_expiration_ET.setSelection(1);
                }
                else if(expirationDate.length() == 2 && initialTextLength == 1)
                {
                    act_wallet_addFundsF_expiration_ET.setText(expirationDate + " / ");
                    act_wallet_addFundsF_expiration_ET.setSelection(5);
                }
                else if(expirationDate.length() == 5 && initialTextLength == 6)
                {
                    act_wallet_addFundsF_expiration_ET.setText(expirationDate.substring(0, 2));
                    act_wallet_addFundsF_expiration_ET.setSelection(2);
                }

                if(expirationDate.length() == 7)
                    act_wallet_addFundsF_expiration_ET.setBackgroundResource(R.drawable.test_border_8);
                else
                    act_wallet_addFundsF_expiration_ET.setBackgroundResource(R.drawable.test_border_5);
            }
        });
    }

    private void act_wallet_addFundsF_cvv_ET_onTextChanged()
    {
        act_wallet_addFundsF_cvv_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(act_wallet_addFundsF_cvv_ET.getText().toString().length() == 3)
                    act_wallet_addFundsF_cvv_ET.setBackgroundResource(R.drawable.test_border_8);
                else
                    act_wallet_addFundsF_cvv_ET.setBackgroundResource(R.drawable.test_border_5);
            }
        });
    }
}