package com.example.customerapp_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBills extends RecyclerView.Adapter<AdapterBills.MyViewHolder> {

    private final ArrayList<DataBill> billsList;

    public AdapterBills(ArrayList<DataBill> billsList)
    {
        this.billsList = billsList;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_releaseDate;
        private final TextView card_total;
        private final TextView card_status;
        private final TextView card_payDate;

        public MyViewHolder(final View view) {
            super(view);
            card_releaseDate = view.findViewById(R.id.bill_card_releaseDate_TW);
            card_total = view.findViewById(R.id.bill_card_total_TW);
            card_status = view.findViewById(R.id.bill_card_status_TW);
            card_payDate = view.findViewById(R.id.bill_card_payDate_TW);
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
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }
}
