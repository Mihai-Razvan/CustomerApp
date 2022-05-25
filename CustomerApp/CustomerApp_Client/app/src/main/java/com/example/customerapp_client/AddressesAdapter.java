package com.example.customerapp_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddressesAdapter extends  RecyclerView.Adapter<AddressesAdapter.MyViewHolder>{

    private final ArrayList<String> addressesList;

    public AddressesAdapter(ArrayList<String> addressesList)
    {
        this.addressesList = addressesList;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_city;
        private final TextView card_street;
        private final TextView card_number;
        private final TextView card_details;

        public MyViewHolder(final View view) {
            super(view);
            card_city = view.findViewById(R.id.address_card_city_TW);
            card_street = view.findViewById(R.id.address_card_street_TW);
            card_number = view.findViewById(R.id.address_card_number_TW);
            card_details = view.findViewById(R.id.address_card_details_TW);
        }
    }

    @NonNull
    @Override
    public AddressesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View address_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_card, parent, false);
        return new AddressesAdapter.MyViewHolder(address_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.MyViewHolder holder, int position) {

        ArrayList<String> fullAddressSplit = splitFullAddress(addressesList.get(position));

        holder.card_city.setText(fullAddressSplit.get(0));
        holder.card_street.setText(fullAddressSplit.get(1));
        holder.card_number.setText(fullAddressSplit.get(2));
        holder.card_details.setText(fullAddressSplit.get(3));
    }

    @Override
    public int getItemCount() {
        return addressesList.size();
    }

    public static ArrayList<String> splitFullAddress(String fullAddress)
    {
        String[] fullAddressSplit = fullAddress.split(", ");
        String details = fullAddressSplit[3];

        for(int i = 4; i < fullAddressSplit.length; i++)
            details = details + ", " + fullAddressSplit[i];

        fullAddressSplit[3] = details;

        return new ArrayList<String>(Arrays.asList(fullAddressSplit));
    }
}
