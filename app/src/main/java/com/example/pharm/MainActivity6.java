package com.example.pharm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        OrderList[] myList = new OrderList[] {
                new OrderList("Acetaminophen", R.drawable.acetam),
                new OrderList("Adderall",R.drawable.adderall),
                new OrderList("Amitriptyline", R.drawable.amitrip)};

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        OrderAdapter adapter = new OrderAdapter(myList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
