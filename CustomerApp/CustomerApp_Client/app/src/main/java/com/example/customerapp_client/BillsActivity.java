package com.example.customerapp_client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp_client.databinding.ActivityBillsBinding;

import java.util.ArrayList;

public class BillsActivity extends AppCompatActivity {

    private ActivityBillsBinding binding;
    private ConstraintLayout act_bills_root_layout;
    private Button act_bills_add_button;
    private RecyclerView act_bills_recyclerView;

    private ArrayList<Bill> billsList;
    private BillsAdapter billsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        act_bills_add_button_onClick();

        billsList = new ArrayList<>();
        setBillsInfo();
        setAdapter();
    }

    private void getActivityElements()
    {
        act_bills_root_layout = findViewById(R.id.act_bill_root_layout);
        act_bills_recyclerView = findViewById(R.id.act_bills_recyclerView);
        act_bills_add_button = findViewById(R.id.act_bills_add_button);
    }

    private void act_bills_add_button_onClick()
    {
        act_bills_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBillCard();
            }
        });
    }

    private void setAdapter()
    {
        billsAdapter = new BillsAdapter(billsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        act_bills_recyclerView.setLayoutManager(layoutManager);
        act_bills_recyclerView.setItemAnimator(new DefaultItemAnimator());
        act_bills_recyclerView.setAdapter(billsAdapter);
    }

    private void setBillsInfo()
    {
        for(int i = 1; i <= 50; i++)
            billsList.add(new Bill("John", Integer.toString(100 * i), "PAIED"));

    }

    private void addBillCard()
    {
        billsList.add(new Bill("Andrew", "20000", "PENDING"));
        billsAdapter.notifyItemInserted(billsList.size() - 1);
    }
}