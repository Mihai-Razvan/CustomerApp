package com.example.customerapp_client;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp_client.databinding.ActivityIndexBinding;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import kotlin.ranges.IntRange;


public class IndexActivity extends AppCompatActivity  implements ActivityBasics{

    private Button act_index_send_category_button;
    private Button act_index_history_category_button;

    private Spinner act_index_send_category_spinner;
    private TextView act_index_send_category_OldIndex_TW;
    private EditText act_index_send_category_NewIndex_ET;
    private Button act_index_send_category_send_button;
    private TextView act_index_send_category_status_TW;

    ArrayList<String> sendCategoryAddressesList;
    ArrayList<IndexData> indexesList;
    ArrayList<IndexData> usedIndexesList;     //the indexes which have the same address ass the selected address

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIndexBinding binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sendCategoryAddressesList = new ArrayList<>();
        indexesList = new ArrayList<>();
        usedIndexesList = new ArrayList<>();
        getIndexes();
        getActivityElements();
        setListeners();
        setSendCategoryDropdown();
    }

    @Override
    public void getActivityElements() {
        act_index_send_category_button = findViewById(R.id.act_index_send_category_button);
        act_index_history_category_button = findViewById(R.id.act_index_history_category_button);
        act_index_send_category_spinner = findViewById(R.id.act_index_send_category_spinner);
        act_index_send_category_OldIndex_TW = findViewById(R.id.act_index_send_category_OldIndex_TW);
        act_index_send_category_NewIndex_ET = findViewById(R.id.act_index_send_category_NewIndex_ET);
        act_index_send_category_send_button = findViewById(R.id.act_index_send_category_send_button);
        act_index_send_category_status_TW = findViewById(R.id.act_index_send_category_status_TW);
    }

    @Override
    public void setListeners() {
        act_index_send_category_send_button_onClick();
        act_index_send_category_spinner_onItemSelected();
    }

    private void act_index_send_category_send_button_onClick()
    {
        act_index_send_category_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIndex();
            }
        });
    }

    private void act_index_send_category_spinner_onItemSelected()      //old index will be change with the address
    {
        act_index_send_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //this is called on the initialisation too
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //i is the position of the selected item in the adapter, so the position in the sendCategoryAddressList
               setUsedIndexesList();
               String oldIndexString;
               int oldIndexValue = getPreviousIndex().getValue();
               if(oldIndexValue == -1)
                   oldIndexString = "Old index: Nonexistent";
               else
               oldIndexString = "Old index: " + oldIndexValue;
               act_index_send_category_OldIndex_TW.setText(oldIndexString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setUsedIndexesList()         //set the used indexes (the ones that got the same address as the selected address)
    {
        String selectedAddress = act_index_send_category_spinner.getSelectedItem().toString().trim();
        int numOfIndexes = indexesList.size();
        usedIndexesList.clear();

        for(int i = 0; i < numOfIndexes; i++)
            if(indexesList.get(i).getAddressName().equals(selectedAddress))
                usedIndexesList.add(indexesList.get(i));
    }

    private IndexData getPreviousIndex()       //searches into the usedIndexes to find the one with the latest sendDate
    {
        int numOfUsedIndexes = usedIndexesList.size();

        if(numOfUsedIndexes == 0)     //there is no used index, so there is no index on this address, so the last index is 0
            return new IndexData(-1, null, null, null);  //-1 means there is no previous index on this address

        //an user can send a new index with the value equal to the last index value. Also he can send multiple indexes the same day
        //so we need to order by value and sendDate also
        String previousDate = usedIndexesList.get(0).getSendDate();
        int previousValue = usedIndexesList.get(0).getValue();
        IndexData previousIndex = usedIndexesList.get(0);

        for(int i = 1; i < numOfUsedIndexes; i++)
            if(usedIndexesList.get(i).getValue() > previousValue ||
                    (usedIndexesList.get(i).getValue() == previousValue && LocalDate.parse(usedIndexesList.get(i).getSendDate()).isAfter(LocalDate.parse(previousDate))))
            {
                previousDate = usedIndexesList.get(i).getSendDate();
                previousValue = usedIndexesList.get(i).getValue();
                previousIndex = usedIndexesList.get(i);
            }

        return  previousIndex;
    }

    ////////////////////////////////////////////////////////////////////

    private void setSendCategoryDropdown()   //the addresses spinner in the send category
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

    private void getIndexes()
    {
        HttpRequestsIndex httpRequestsIndex = new HttpRequestsIndex("/index/indexes");
        Thread connectionThread = new Thread(httpRequestsIndex);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsIndex.getStatus();

            if(status.equals("Successful"))
            {
                indexesList = httpRequestsIndex.getIndexesList();
            }
            else
                throw new InterruptedException();
        }
        catch (InterruptedException e) {
            System.out.println("COULDN'T SHOW OLD INDEX");
        }
    }

    private void sendIndex()
    {
        int newIndexValue;
        try {
            newIndexValue = Integer.parseInt(act_index_send_category_NewIndex_ET.getText().toString().trim());
            act_index_send_category_NewIndex_ET.getText().clear();
            if(newIndexValue < 0)
            {
                act_index_send_category_status_TW.setText("New index can't be lower than 0");
                return;
            }
        }
        catch (NumberFormatException e) {
            act_index_send_category_status_TW.setText("New index should contain only digits");
            act_index_send_category_NewIndex_ET.getText().clear();
            return;
        }

        if(newIndexValue < getPreviousIndex().getValue())
        {
            act_index_send_category_status_TW.setText("New index value can't be lower than last index's value");
            return;
        }

        String addressName = act_index_send_category_spinner.getSelectedItem().toString().trim();

        HttpRequestsIndex httpRequestsIndex = new HttpRequestsIndex("/index/new", newIndexValue, addressName);
        Thread connectionThread = new Thread(httpRequestsIndex);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsIndex.getStatus();

            if(status.equals("Successful"))
            {
                IndexData newIndex = new IndexData(newIndexValue, LocalDate.now().toString(), getPreviousIndex().getSendDate(), addressName);
                indexesList.add(newIndex);
                usedIndexesList.add(newIndex);
                String oldIndexString = "Old index: " + newIndexValue;
                act_index_send_category_OldIndex_TW.setText(oldIndexString);  //the new index becomes the latest index, so old index value is taken from the new index
                act_index_send_category_status_TW.setText("Successfully added index");
            }
            else
                throw new InterruptedException();
        }
        catch (InterruptedException e) {
            act_index_send_category_status_TW.setText("Couldn't add index");
            System.out.println("COULDN'T ADD INDEX");
        }
    }
}