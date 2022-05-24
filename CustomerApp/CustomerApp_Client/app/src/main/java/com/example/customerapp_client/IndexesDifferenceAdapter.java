package com.example.customerapp_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class IndexesDifferenceAdapter extends RecyclerView.Adapter<IndexesDifferenceAdapter.MyViewHolder> {

    private final ArrayList<IndexData> indexesList;

    public IndexesDifferenceAdapter(ArrayList<IndexData> indexesList) {
        this.indexesList = indexesList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_year;
        private final TextView card_month;
        private final TextView card_difference;

        public MyViewHolder(final View view) {
            super(view);
            card_year = view.findViewById(R.id.address_city_TW);
            card_month = view.findViewById(R.id.address_street_TW);
            card_difference = view.findViewById(R.id.address_number_TW);
        }
    }

    @NonNull
    @Override
    public IndexesDifferenceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View index_difference_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_difference_card, parent, false);
        return new IndexesDifferenceAdapter.MyViewHolder(index_difference_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndexesDifferenceAdapter.MyViewHolder holder, int position) {
        LocalDate date = LocalDate.parse(indexesList.get(position).getSendDate());

        holder.card_year.setText(Integer.toString(date.getYear()));
        holder.card_month.setText(date.getMonth().toString());

        holder.card_difference.setText(Integer.toString(indexesList.get(position).getConsumption()));
    }

    @Override
    public int getItemCount() {
        return indexesList.size();
    }
}