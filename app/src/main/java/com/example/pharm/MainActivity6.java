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
        ShopList[] myList = new ShopList[] {
                new ShopList("Acetaminophen", R.drawable.acetam),
                new ShopList("Adderall",R.drawable.adderall),
                new ShopList("Amitriptyline", R.drawable.amitrip)};

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ShopAdapter adapter = new ShopAdapter(myList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
