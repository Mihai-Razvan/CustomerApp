package com.example.customerapp_client;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBills extends RecyclerView.Adapter<AdapterBills.MyViewHolder> {

    private final ArrayList<DataBill> billsList;
    private Context context;

    public AdapterBills(ArrayList<DataBill> billsList, Context context)
    {
        this.billsList = billsList;
        this.context = context;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_releaseDate;
        private final TextView card_total;
        private final TextView card_status;
        private final TextView card_payDate;
        private final Button card_button;

        public MyViewHolder(final View view) {
            super(view);
            card_releaseDate = view.findViewById(R.id.bill_card_releaseDate_TW);
            card_total = view.findViewById(R.id.bill_card_total_TW);
            card_status = view.findViewById(R.id.bill_card_status_TW);
            card_payDate = view.findViewById(R.id.bill_card_payDate_TW);
            card_button = view.findViewById(R.id.bills_act_pay_bill_button);
        }
    }

    @NonNull
    @Override
    public AdapterBills.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bill_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_bill, parent, false);
        return new MyViewHolder(bill_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBills.MyViewHolder holder, int position) {
        String releaseDate = "Release date: " + billsList.get(position).getReleaseDate();
        releaseDate = releaseDate.replace("-",  ".");
        holder.card_releaseDate.setText(releaseDate);

        String total = "Total: " + billsList.get(position).getTotal() + " RON";
        holder.card_total.setText(total);

        String status = billsList.get(position).getStatus();
        holder.card_status.setText(status);

        String payDate = "Pay date: " + billsList.get(position).getPayDate();
        payDate = payDate.replace("-",  ".");
        holder.card_payDate.setText(payDate);

        if(status.equals("Paid"))
        {
            holder.card_button.setBackgroundColor(Color.GRAY);
            holder.card_button.setEnabled(false);
        }
        else
        {
            holder.card_button.setBackgroundColor(Color.parseColor("#03DFFC"));   //test_color3
            holder.card_button.setEnabled(true);
        }

        holder.card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBill bill = billsList.get(holder.getAdapterPosition());
                payBill(bill.getIndexId(), Float.parseFloat(bill.getTotal()), holder.card_button, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void payBill(String indexId, float total, Button button, int posInList)
    {
        boolean moneyPaid = false;

        if(GlobalManager.getPayMethod(context).equals("Wallet"))
        {
            if(getWalletAmount() < total)
                System.out.println("NOT ENOUGHT FUNDS IN YOU WALLET!");
            else
            {
                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/balance/reduce", total);
                Thread accountConnectionThread = new Thread(httpRequestsAccount);
                accountConnectionThread.start();

                try {
                    accountConnectionThread.join();
                    String accountStatus = httpRequestsAccount.getStatus();

                    if(accountStatus.equals("1"))
                    {
                        System.out.println("REDUCED FUNDS");
                        moneyPaid = true;
                    }
                    else
                        System.out.println("SERVER ERROR");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("SERVER ERROR");
                }
            }
        }
        else
        {
            String cardNumber = GlobalManager.getCardNumber(context);
            String expirationDate = GlobalManager.getExpirationDate(context);
            String cvv = GlobalManager.getCvv(context);

            HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/cards/spend", cardNumber, expirationDate, cvv, total);
            Thread accountConnectionThread = new Thread(httpRequestsAccount);
            accountConnectionThread.start();

            try {
                accountConnectionThread.join();
                String accountStatus = httpRequestsAccount.getStatus();

                switch (accountStatus) {
                    case "-2":
                        System.out.println("SERVER ERROR");
                        break;
                    case "-1":
                        System.out.println("NOT ENOUGHT FUNDS ON CARD");
                        break;
                    default:
                        System.out.println("SUCCESS REDUCED FUNDS FROM CARD");
                        moneyPaid = true;
                        break;
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("SERVER ERROR");
            }
        }

        //////////////////////////////////////

        if(!moneyPaid)
        {
            System.out.println("MONEY WERE NOT PAID");
            button.setBackgroundColor(Color.RED);
            return;
        }

        HttpRequestsBills httpRequestsBills = new HttpRequestsBills("/bills/pay", indexId);
        Thread connectionThread = new Thread(httpRequestsBills);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsBills.getStatus();

            if(status.equals("1"))
            {
                billsList.get(posInList).setStatus("Paid");
                button.setBackgroundColor(Color.GRAY);
                button.setEnabled(false);
            }
            else
                button.setBackgroundColor(Color.RED);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            button.setBackgroundColor(Color.RED);
        }
    }

    private float getWalletAmount()  //returns the balance or -1 if error
    {
        float balance;
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

        return balance;
    }
}
