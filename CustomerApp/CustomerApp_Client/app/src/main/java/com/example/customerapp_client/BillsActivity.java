package com.example.customerapp_client;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.customerapp_client.databinding.ActivityBillsBinding;

public class BillsActivity extends AppCompatActivity {

    private ActivityBillsBinding binding;
    private TextView act_bills_bill_id_TW;
    private TextView act_bills_bill_price_TW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // getActivityElements();
    }

    private void getActivityElements()
    {
     //   act_bills_bill_id_TW = findViewById(R.id.act_bills_bill_id_TW);
      //  act_bills_bill_id_TW = findViewById(R.id.act_bills_bill_price_TW);
    }
}