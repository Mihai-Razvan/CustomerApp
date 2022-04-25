package com.example.customerapp_client;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp_client.databinding.ActivityIndexBinding;

import java.util.ArrayList;
import java.util.Locale;

import kotlin.ranges.IntRange;


public class IndexActivity extends AppCompatActivity  implements ActivityBasics{

    Button act_index_send_category_button;
    Button act_index_history_category_button;

    Spinner act_index_send_category_spinner;
    TextView act_index_send_category_OldIndex_TW;
    EditText act_index_send_category_NewIndex_ET;

    ArrayList<String> sendCategoryAddressesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIndexBinding binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sendCategoryAddressesList = new ArrayList<>();
        getActivityElements();
        setListeners();
        setSendCategoryDropdown();
        setOldIndex();
    }

    @Override
    public void getActivityElements() {
        act_index_send_category_button = findViewById(R.id.act_index_send_category_button);
        act_index_history_category_button = findViewById(R.id.act_index_history_category_button);
        act_index_send_category_spinner = findViewById(R.id.act_index_send_category_spinner);
        act_index_send_category_OldIndex_TW = findViewById(R.id.act_index_send_category_OldIndex_TW);
        act_index_send_category_NewIndex_ET = findViewById(R.id.act_index_send_category_NewIndex_ET);
    }

    @Override
    public void setListeners() {

    }

    private void setSendCategoryDropdown()
    {
        HttpRequestsIndex httpRequestsIndex = new HttpRequestsIndex("/index/addresses");
        Thread connectionThread = new Thread(httpRequestsIndex);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsIndex.getStatus();

            if(status.equals("Successful"))
            {
                sendCategoryAddressesList.addAll(httpRequestsIndex.getAddressesList());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.address_dropdown_item, sendCategoryAddressesList);
                act_index_send_category_spinner.setAdapter(adapter);
            }
            else
                System.out.println("COULDN'T ADD ADDRESS");
        }
        catch (InterruptedException e) {
            System.out.println("COULDN'T ADD ADDRESS");
        }
    }

    private void setOldIndex()
    {
        HttpRequestsIndex httpRequestsIndex = new HttpRequestsIndex("/index/indexes");
        Thread connectionThread = new Thread(httpRequestsIndex);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsIndex.getStatus();

            if(status.equals("Successful"))
            {
                act_index_send_category_OldIndex_TW.setText("Old index: " + httpRequestsIndex.getOldIndexValue());
            }
            else
                throw new InterruptedException();
        }
        catch (InterruptedException e) {
            System.out.println("COULDN'T SHOW OLD INDEX");
        }
    }
}