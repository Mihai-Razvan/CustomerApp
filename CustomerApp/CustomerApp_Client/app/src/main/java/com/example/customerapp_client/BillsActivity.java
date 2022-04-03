package com.example.customerapp_client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp_client.databinding.ActivityBillsBinding;

import java.util.ArrayList;

public class BillsActivity extends AppCompatActivity implements ActivityBasics {

    private ActivityBillsBinding binding;
    private Button act_bills_add_button;
    private RecyclerView act_bills_recyclerView;
    private Spinner act_bills_locations_spinner;

    private ArrayList<Bill> allBillsList;      //the list which contains all bills, no matter the address
    private ArrayList<Bill> usedBillsList;     //the list which used for the adapter, which contains only the bills matching the selected address
    private BillsAdapter billsAdapter;
    private ArrayList<String> addressList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();

        allBillsList= new ArrayList<>();
        usedBillsList = new ArrayList<>();
        addressList = new ArrayList<>();
        setInitialBills();
        setBillsAdapter();
    }

    @Override
    public void getActivityElements()
    {
        act_bills_recyclerView = findViewById(R.id.act_bills_recyclerView);
        act_bills_add_button = findViewById(R.id.act_bills_add_button);
        act_bills_locations_spinner = findViewById(R.id.act_bills_locations_spinner);
    }

    @Override
    public void setListeners() {
        act_bills_add_button_onClick();
        act_bills_locations_spinner_OnItemSelected();
    }

    private void act_bills_add_button_onClick()
    {
        act_bills_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ADD BILL BUTTON PRESSED");
            }
        });
    }

    private void act_bills_locations_spinner_OnItemSelected()
    {
        act_bills_locations_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //i is the position of the selected item in the adapter, so the position in the addressList
                String newAddress = addressList.get(i);
                if(!newAddress.equals("All"))
                {
                    //delete bills for actual address
                    int numOfBills = usedBillsList.size();
                    usedBillsList.clear();
                    billsAdapter.notifyItemRangeRemoved(0, numOfBills);

                    //add bills for newly selected address
                    numOfBills = allBillsList.size();
                    for(int j = 0; j < numOfBills; j++)
                        if(allBillsList.get(j).getAddress().equals(newAddress))
                        {
                            usedBillsList.add(allBillsList.get(j));
                            billsAdapter.notifyItemInserted(usedBillsList.size() - 1);
                        }
                }
                else
                {
                    int numOfBills = usedBillsList.size();
                    usedBillsList.clear();
                    billsAdapter.notifyItemRangeRemoved(0, numOfBills);
                    usedBillsList.addAll(allBillsList);
                    billsAdapter.notifyItemRangeInserted(0, usedBillsList.size());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////

    private void setBillsAdapter()
    {
        billsAdapter = new BillsAdapter(usedBillsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        act_bills_recyclerView.setLayoutManager(layoutManager);
        act_bills_recyclerView.setItemAnimator(new DefaultItemAnimator());
        act_bills_recyclerView.setAdapter(billsAdapter);
    }

    private void setDropdown()
    {
        addressList.add("All");
        for(int i = 0; i < allBillsList.size(); i++)
        {
            String address = allBillsList.get(i).getAddress();
            if(!addressList.contains(address))
                addressList.add(address);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.address_dropdown_item, addressList);
        act_bills_locations_spinner.setAdapter(adapter);
    }

    private void setInitialBills()
    {
        String name = "";
        String total = "";
        String status = "";
        String connectionStatus = "Failed";

        HttpRequestsBills httpRequestsBills = new HttpRequestsBills("/bills");
        Thread connectionThread = new Thread(httpRequestsBills);
        connectionThread.start();

        try {
            connectionThread.join();
            connectionStatus = httpRequestsBills.getConnectionStatus();

            if(connectionStatus.equals("Successful"))
            {
                allBillsList.addAll(httpRequestsBills.getBillList());
                usedBillsList.addAll(allBillsList);
                setDropdown();
            }
            else
                System.out.println("COULDN'T ADD CARD");
        }
        catch (InterruptedException e) {
            System.out.println("COULDN'T ADD CARD");
        }
    }

}