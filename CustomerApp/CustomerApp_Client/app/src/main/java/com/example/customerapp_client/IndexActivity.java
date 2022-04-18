package com.example.customerapp_client;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp_client.databinding.ActivityIndexBinding;

import java.util.ArrayList;


public class IndexActivity extends AppCompatActivity  implements ActivityBasics{

    Button act_index_send_category_button;
    Button act_index_history_category_button;
    Spinner act_index_send_category_spinner;

    ArrayList<String> sendCategoryAddressesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIndexBinding binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();
        setDropdown();

        sendCategoryAddressesList = new ArrayList<>();
    }

    @Override
    public void getActivityElements() {
        act_index_send_category_button = findViewById(R.id.act_index_send_category_button);
        act_index_history_category_button = findViewById(R.id.act_index_history_category_button);
        act_index_send_category_spinner = findViewById(R.id.act_index_send_category_spinner);
    }

    @Override
    public void setListeners() {

    }

    private void setDropdown()
    {
        HttpRequestsIndex httpRequestsIndex = new HttpRequestsIndex("/index/addresses");
        Thread connectionThread = new Thread(httpRequestsIndex);
        connectionThread.start();
    }
}