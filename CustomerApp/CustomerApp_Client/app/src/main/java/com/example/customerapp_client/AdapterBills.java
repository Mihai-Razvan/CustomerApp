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
        private final TextView card_name;
        private final TextView card_total;
        private final TextView card_status;
        private final TextView card_dueDate;

        public MyViewHolder(final View view) {
            super(view);
            card_name = view.findViewById(R.id.bill_card_name_TW);
            card_total = view.findViewById(R.id.bill_card_total_TW);
            card_status = view.findViewById(R.id.bill_card_status_TW);
            card_dueDate = view.findViewById(R.id.bill_card_dueDate_TW);
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
        String name = "Name: " + billsList.get(position).getName();
        holder.card_name.setText(name);

        String total = "Total: " + billsList.get(position).getTotal();
        holder.card_total.setText(total);

        String status = "Status: " + billsList.get(position).getStatus();
        holder.card_status.setText(status);

        String dueDate = "Pay date: " + billsList.get(position).getPayDate();
        holder.card_dueDate.setText(dueDate);
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }
}