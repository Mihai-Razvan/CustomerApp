package com.example.customerapp_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.MyViewHolder> {

    private final ArrayList<Bill> billsList;

    public BillsAdapter(ArrayList<Bill> billsList)
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
    public BillsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bill_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_card, parent, false);
        return new MyViewHolder(bill_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillsAdapter.MyViewHolder holder, int position) {
        String name = billsList.get(position).getDisplayName();
        holder.card_name.setText(name);

        String total = billsList.get(position).getDisplayTotal();
        holder.card_total.setText(total);

        String status = billsList.get(position).getDisplayStatus();
        holder.card_status.setText(status);

        String dueDate = billsList.get(position).getDisplayDueDate();
        holder.card_dueDate.setText(dueDate);
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }
}
