package com.example.customerapp_client;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp_client.databinding.ActivityBillsBinding;

public class BillsActivity extends AppCompatActivity {

    private ActivityBillsBinding binding;
    private ConstraintLayout act_bills_root_layout;
    private RecyclerView act_bills_recyclerView;
    private Button act_bills_add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        act_bills_add_button_onClick();
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

    private void addBillCard()
    {
        View view = getLayoutInflater().inflate(R.layout.bill_card, null);

        act_bills_recyclerView.addView(view);
    }
}