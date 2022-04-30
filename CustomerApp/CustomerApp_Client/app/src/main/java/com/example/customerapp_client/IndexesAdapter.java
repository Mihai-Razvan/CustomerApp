package com.example.customerapp_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IndexesAdapter  extends RecyclerView.Adapter<IndexesAdapter.MyViewHolder> {

    private final ArrayList<IndexData> indexesList;

    public IndexesAdapter(ArrayList<IndexData> indexesList)
    {
        this.indexesList = indexesList;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_sendDate;
        private final TextView card_previousDate;
        private final TextView card_newIndex;
        private final TextView card_oldIndex;
        private final TextView card_consumption;

        public MyViewHolder(final View view) {
            super(view);
            card_sendDate = view.findViewById(R.id.index_card_sendDate_TW);
            card_previousDate = view.findViewById(R.id.index_card_previousDate_TW);
            card_newIndex = view.findViewById(R.id.index_card_newIndex_TW);
            card_oldIndex = view.findViewById(R.id.index_card_oldIndex_TW);
            card_consumption = view.findViewById(R.id.index_card_consumption_TW);
        }
    }

    @NonNull
    @Override
    public IndexesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View index_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_card, parent, false);
        return new IndexesAdapter.MyViewHolder(index_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndexesAdapter.MyViewHolder holder, int position) {
        String sendDate = indexesList.get(position).getSendDate();
        sendDate = sendDate.replace("-",  ".");
        holder.card_sendDate.setText(sendDate);

        String previousDate = indexesList.get(position).getPreviousDate();

        if(previousDate.equals("nullDate"))   //previousDate could be "nullDate" for the indexes in the database where previous_index_id is null
            holder.card_previousDate.setText("");
        else
        {
            previousDate = previousDate.replace("-",  ".");
            holder.card_previousDate.setText(previousDate);
        }

        int newIndex = indexesList.get(position).getValue();
        holder.card_newIndex.setText(Integer.toString(newIndex));

        int consumption = indexesList.get(position).getConsumption();
        holder.card_consumption.setText(Integer.toString(consumption));

        int oldIndex = newIndex - consumption;
        holder.card_oldIndex.setText(Integer.toString(oldIndex));
    }

    @Override
    public int getItemCount() {
        return indexesList.size();
    }
}

