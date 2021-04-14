package com.evilgeniusesapps.recyclerview.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniusesapps.recyclerview.R;
import com.evilgeniusesapps.recyclerview.adapters.SimpleAdapter;
import com.evilgeniusesapps.recyclerview.databases.TinyDB;
import com.evilgeniusesapps.recyclerview.models.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Customer> customers;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    TinyDB tinyDB;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customers = new ArrayList();
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        tinyDB = new TinyDB(this);


        //Get saved data
        if(tinyDB.getListObject("arrayCustomers", Customer.class) != null){
            ArrayList<Object> playerObjects = tinyDB.getListObject("arrayCustomers", Customer.class);
            for(Object objs : playerObjects){
                customers.add((Customer) objs);
            }
        }

        simpleAdapter = new SimpleAdapter(this, customers);
        recyclerView.setAdapter(simpleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customers.add(new Customer("Customer " + simpleAdapter.getItemCount()));
                simpleAdapter.notifyItemInserted(simpleAdapter.getItemCount() - 1);
                recyclerView.scrollToPosition(simpleAdapter.getItemCount() - 1);

                // Save data
                ArrayList<Object> playerObjects = new ArrayList<>();
                for(Customer a : customers){
                    playerObjects.add((Object)a);
                }
                tinyDB.putListObject("arrayPersons", playerObjects);
            }
        });



    }
}