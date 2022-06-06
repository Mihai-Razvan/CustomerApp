package com.example.customerapp_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;

import android.widget.ImageButton;

import com.example.customerapp_client.databinding.ActivityMainBinding;

public class ActivityMain extends AppCompatActivity implements ActivityBasics {

    private ImageButton act_main_test_button;
    private ImageButton act_main_bills_button;
    private ImageButton act_main_account_button;
    private ImageButton act_main_deals_button;
    private ImageButton act_main_index_button;
    private ImageButton act_main_contact_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getActivityElements();
        setListeners();
    }

    @Override
    public void getActivityElements() {
        act_main_test_button = findViewById(R.id.act_main_test_button);
        act_main_bills_button = findViewById(R.id.act_main_bills_button);
        act_main_account_button = findViewById(R.id.act_main_account_button);
        act_main_deals_button = findViewById(R.id.act_main_deals_button);
        act_main_index_button = findViewById(R.id.act_main_index_button);
        act_main_contact_button = findViewById(R.id.act_main_contact_button);
    }

    @Override
    public void setListeners()
    {
        act_main_test_button_onClick();
        act_main_bills_button_onClick();
        act_main_account_button_onClick();
        act_main_deals_button_onClick();
        act_main_index_button_onClick();
        act_main_contact_button_onClick();
    }

    private void act_main_test_button_onClick() {
        act_main_test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("TEST ACTIVITY");

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
//                    NotificationManager manager = activity.getSystemService(NotificationManager.class);
//                    manager.createNotificationChannel(channel);
//                }
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, "My notification");
//                builder.setContentTitle("Title");
//                builder.setContentText("This is a notification");
//                builder.setSmallIcon(R.mipmap.place_holder_logo_foreground);
//                builder.setAutoCancel(true);
//
//                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(activity);
//
//                Intent indexIntent = new Intent(activity, ActivityIndex.class);
//                Intent mainIntent = new Intent(activity, ActivityMain.class);
//                TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
//                stackBuilder.addParentStack(ActivityMain.class);
//                stackBuilder.addNextIntent(mainIntent);
//                stackBuilder.addNextIntent(indexIntent);
//
//                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//                builder.setContentIntent(resultPendingIntent);
//
//                managerCompat.notify(1, builder.build());
            }
        });
    }

    private void act_main_bills_button_onClick() {
        act_main_bills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivityBills.class));
            }
        });
    }

    private void act_main_account_button_onClick() {
        act_main_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivityAccount.class));
            }
        });
    }

    private void act_main_deals_button_onClick() {
        act_main_deals_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.enel.ro/enel-muntenia/ro/clienti-rezidentiali/ofertele-tale.html"));
                startActivity(browserIntent);
            }
        });
    }

    private void act_main_index_button_onClick() {
        act_main_index_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivityIndex.class));
            }
        });
    }

    private void act_main_contact_button_onClick()
    {
        act_main_contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
    }

    private void call()    //makes a phone call for the contact category
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Constants.getPhoneNumber()));

        if (ContextCompat.checkSelfPermission(ActivityMain.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ActivityMain.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

        }
        else
        {
            try {
                startActivity(callIntent);
            }
            catch(SecurityException e) {
                System.out.println("COULDN'T MAKE PHONE CALL: " + e.getMessage());
            }
        }
    }
}