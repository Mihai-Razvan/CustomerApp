package com.example.customerapp_client;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp_client.databinding.ActivityIndexBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;


public class IndexActivity extends AppCompatActivity  implements ActivityBasics{

    private Button act_index_send_category_button;
    private Button act_index_history_category_button;
    private Button act_index_compare_category_button;
    private ConstraintLayout act_index_send_category_layout;
    private ConstraintLayout act_index_history_category_layout;
    private ConstraintLayout act_index_compare_category_layout;

    private Spinner act_index_send_spinner;
    private TextView act_index_send_category_OldIndex_TW;
    private EditText act_index_send_category_NewIndex_ET;
    private Button act_index_send_category_send_button;
    private TextView act_index_send_category_status_TW;

    private RecyclerView act_index_history_category_recycleView;
    private IndexesAdapter indexesAdapter;

    private RecyclerView act_index_compare_category_recycleView;
    private IndexesDifferenceAdapter indexesDifferenceAdapter;

    ArrayList<String> addressesList;
    ArrayList<IndexData> indexesList;
    ArrayList<IndexData> usedIndexesList;     //the indexes which have the same address ass the selected address, also this list is used by the adapter in history
    ArrayList<IndexData> indexesDifferenceList;   //pseudo-indexes created for compare list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIndexBinding binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addressesList = new ArrayList<>();
        indexesList = new ArrayList<>();
        usedIndexesList = new ArrayList<>();
        indexesDifferenceList = new ArrayList<>();
        getIndexes();
        getActivityElements();
        setListeners();
        setAddressesDropdown();
        setIndexesAdapter();
        setIndexesDifferenceAdapter();

        act_index_history_category_layout.setVisibility(View.INVISIBLE);
        act_index_send_category_layout.setVisibility(View.VISIBLE);
        act_index_compare_category_layout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getActivityElements() {
        act_index_send_category_button = findViewById(R.id.act_index_send_category_button);
        act_index_history_category_button = findViewById(R.id.act_index_history_category_button);
        act_index_compare_category_button = findViewById((R.id.act_index_compare_category_button));
        act_index_send_category_layout = findViewById(R.id.act_index_send_category_layout);
        act_index_history_category_layout = findViewById(R.id.act_index_history_category_layout);
        act_index_compare_category_layout = findViewById(R.id.act_index_compare_category_layout);

        act_index_send_category_status_TW = findViewById(R.id.act_index_send_category_status_TW);
        act_index_send_spinner = findViewById(R.id.act_index_spinner);
        act_index_send_category_OldIndex_TW = findViewById(R.id.act_index_send_category_OldIndex_TW);
        act_index_send_category_NewIndex_ET = findViewById(R.id.act_index_send_category_NewIndex_ET);
        act_index_send_category_send_button = findViewById(R.id.act_index_send_category_send_button);
        act_index_send_category_status_TW = findViewById(R.id.act_index_send_category_status_TW);
        act_index_history_category_recycleView = findViewById(R.id.act_index_history_category_recycleView);
        act_index_compare_category_recycleView = findViewById(R.id.act_index_compare_category_recycleView);
    }

    @Override
    public void setListeners() {
        act_index_send_category_send_button_onClick();
        act_index_send_spinner_onItemSelected();
        act_index_send_category_button_onClick();
        act_index_history_category_button_onClick();
        act_index_compare_category_button_onClick();
    }

    private void act_index_send_category_button_onClick()
    {
        act_index_send_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_index_history_category_layout.setVisibility(View.INVISIBLE);
                act_index_compare_category_layout.setVisibility(View.INVISIBLE);
                act_index_send_category_layout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void act_index_history_category_button_onClick()
    {
        act_index_history_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_index_send_category_layout.setVisibility(View.INVISIBLE);
                act_index_send_category_status_TW.setText("");
                act_index_compare_category_layout.setVisibility(View.INVISIBLE);
                act_index_history_category_layout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void act_index_compare_category_button_onClick()
    {
        act_index_compare_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_index_send_category_layout.setVisibility(View.INVISIBLE);
                act_index_send_category_status_TW.setText("");
                act_index_history_category_layout.setVisibility(View.INVISIBLE);
                act_index_compare_category_layout.setVisibility(View.VISIBLE);
            }
        });
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

    private void act_index_send_spinner_onItemSelected()      //old index will be change with the address
    {
        act_index_send_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //this is called on the initialisation too
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //i is the position of the selected item in the adapter, so the position in the sendCategoryAddressList
               setUsedIndexesList();
               orderUsedIndexesList();
               setIndexesDifferenceList();
               indexesAdapter.notifyDataSetChanged();
               indexesDifferenceAdapter.notifyDataSetChanged();
               String oldIndexString;
               int oldIndexValue = getPreviousIndex().getValue();
               String  sendDate = getPreviousIndex().getSendDate();
               if(sendDate.equals("nullDate"))
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

    private void setIndexesAdapter()
    {
        indexesAdapter = new IndexesAdapter(usedIndexesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        act_index_history_category_recycleView.setLayoutManager(layoutManager);
        act_index_history_category_recycleView.setItemAnimator(new DefaultItemAnimator());
        act_index_history_category_recycleView.setAdapter(indexesAdapter);
    }

    private void setIndexesDifferenceAdapter()
    {
        setIndexesDifferenceList();
        indexesDifferenceAdapter = new IndexesDifferenceAdapter(indexesDifferenceList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        act_index_compare_category_recycleView.setLayoutManager(layoutManager);
        act_index_compare_category_recycleView.setItemAnimator(new DefaultItemAnimator());
        act_index_compare_category_recycleView.setAdapter(indexesDifferenceAdapter);
    }

    private void setUsedIndexesList()         //set the used indexes (the ones that got the same address as the selected address)
    {
        String selectedAddress = act_index_send_spinner.getSelectedItem().toString().trim();
        int numOfIndexes = indexesList.size();
        usedIndexesList.clear();

        for(int i = 0; i < numOfIndexes; i++)
            if(indexesList.get(i).getAddressName().equals(selectedAddress))
                usedIndexesList.add(indexesList.get(i));

        orderUsedIndexesList();
    }

    private void orderUsedIndexesList()  //we need this method so the indexes in history will be ordered
    {
        //now we will order this list, the last index will be the first element usedIndexesList(0)

        int numOfUsedIndexes = usedIndexesList.size();
        for(int i = 0; i < numOfUsedIndexes - 1; i++)
            for(int j = i + 1; j < numOfUsedIndexes; j++)
                if(usedIndexesList.get(j).getValue() > usedIndexesList.get(i).getValue() ||
                        (usedIndexesList.get(j).getValue() == usedIndexesList.get(i).getValue()) &&
                                LocalDate.parse(usedIndexesList.get(j).getSendDate()).isAfter(LocalDate.parse(usedIndexesList.get(i).getSendDate())))
                    Collections.swap(usedIndexesList, i, j);
    }

    private IndexData getPreviousIndex()       //searches into the usedIndexes to find the one with the latest sendDate
    {
        int numOfUsedIndexes = usedIndexesList.size();

        if(numOfUsedIndexes == 0)     //there is no used index, so there is no index on this address, so the last index is 0
            return new IndexData(0, 0, "nullDate", "nullDate", "nullAddress");
        //we tried to put value - 1 if there is no previous index but it will break some part of the code so if we need to know if
        //there is any previousIndex we check sendDate for the previous and see if it is "nullDate"

        return usedIndexesList.get(0);   //the lsi tis ordered
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setAddressesDropdown()   //the addresses spinner in the send category
    {
        HttpRequestsIndex httpRequestsIndex = new HttpRequestsIndex("/index/addresses");
        Thread connectionThread = new Thread(httpRequestsIndex);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsIndex.getStatus();

            if(status.equals("Successful"))
            {
                addressesList.addAll(httpRequestsIndex.getAddressesList());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.address_dropdown_item, addressesList);
                act_index_send_spinner.setAdapter(adapter);
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

        String addressName = act_index_send_spinner .getSelectedItem().toString().trim();

        HttpRequestsIndex httpRequestsIndex = new HttpRequestsIndex("/index/new", newIndexValue, addressName);
        Thread connectionThread = new Thread(httpRequestsIndex);
        connectionThread.start();

        try {
            connectionThread.join();
            String status = httpRequestsIndex.getStatus();

            if(status.equals("Successful"))
            {
                IndexData newIndex = new IndexData(newIndexValue, newIndexValue - getPreviousIndex().getValue(),
                        LocalDate.now().toString(), getPreviousIndex().getSendDate(), addressName);
                indexesList.add(newIndex);
                usedIndexesList.add(newIndex);
                orderUsedIndexesList();
                setIndexesDifferenceList();
                indexesAdapter.notifyDataSetChanged();  //we cant use notifyItemInserted because we will reorder the list so most item will have another index
                indexesDifferenceAdapter.notifyDataSetChanged();
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

    private void setIndexesDifferenceList()
    {
        LocalDate lastDate;

        if(usedIndexesList.size() != 0) {
            IndexData lastIndexInMonth = usedIndexesList.get(0);
            lastDate = LocalDate.parse(lastIndexInMonth.getSendDate());
            int lastMonth = lastDate.getMonthValue();


            for (int i = 0; i <= usedIndexesList.size() - 1; i++) {
                LocalDate actualDate = LocalDate.parse(usedIndexesList.get(i).getSendDate());
                int actualMonth = actualDate.getMonthValue();

                if (actualMonth != lastMonth) {
                    IndexData indexData = usedIndexesList.get(i);
                    IndexData newIndexData = new IndexData(lastIndexInMonth.getValue(), lastIndexInMonth.getValue() - indexData.getValue(),
                            lastIndexInMonth.getSendDate(), indexData.getSendDate(), lastIndexInMonth.getAddressName());

                    indexesDifferenceList.add(newIndexData);
                    lastIndexInMonth = indexData;
                    lastDate = LocalDate.parse(lastIndexInMonth.getSendDate());
                    lastMonth = lastDate.getMonthValue();
                }
            }

            indexesDifferenceList.add(lastIndexInMonth);
        }
    }
}