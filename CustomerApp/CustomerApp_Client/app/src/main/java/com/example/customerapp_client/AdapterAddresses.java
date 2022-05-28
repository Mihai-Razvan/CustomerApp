package com.example.customerapp_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class AdapterAddresses extends  RecyclerView.Adapter<AdapterAddresses.MyViewHolder>{

    private final ArrayList<String> addressesList;

    public AdapterAddresses(ArrayList<String> addressesList)
    {
        this.addressesList = addressesList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_city;
        private final TextView card_street;
        private final TextView card_number;
        private final TextView card_details;
        private final ImageButton card_delete_button;

        public MyViewHolder(final View view) {
            super(view);
            card_city = view.findViewById(R.id.address_card_city_TW);
            card_street = view.findViewById(R.id.address_card_street_TW);
            card_number = view.findViewById(R.id.address_card_number_TW);
            card_details = view.findViewById(R.id.address_card_details_TW);
            card_delete_button = view.findViewById(R.id.address_card_delete_button);
        }
    }

    @NonNull
    @Override
    public AdapterAddresses.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View address_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_address, parent, false);
        return new AdapterAddresses.MyViewHolder(address_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddresses.MyViewHolder holder, int position) {

        ArrayList<String> fullAddressSplit = splitFullAddress(addressesList.get(position));

        holder.card_city.setText(fullAddressSplit.get(0));
        holder.card_street.setText(fullAddressSplit.get(1));
        holder.card_number.setText(fullAddressSplit.get(2));
        holder.card_details.setText(fullAddressSplit.get(3));

        holder.card_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = fullAddressSplit.get(0);
                String street = fullAddressSplit.get(1);
                String number = fullAddressSplit.get(2);
                String details = fullAddressSplit.get(3);

                HttpRequestsAccount httpRequestsAccount = new HttpRequestsAccount("/account/addresses/delete", city, street, number, details);
                Thread connectionThread = new Thread(httpRequestsAccount);
                connectionThread.start();

                try {
                    connectionThread.join();
                    String status = httpRequestsAccount.getStatus();

                    if(status.equals("Success"))
                    {
                        addressesList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        System.out.println("SUCCESSFULLY DELETED ADDRESS");
                    }
                    else
                        System.out.println("COULDN'T DELETE ADDRESS");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("COULDN'T DELETE ADDRESS");
                }
            }
        });
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
