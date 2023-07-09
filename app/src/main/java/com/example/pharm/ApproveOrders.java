package com.example.pharm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ApproveOrders extends AppCompatActivity {
    ListView orders;
    ImageButton back;
    String[] orderslist={"Trufen","Alcamol","Vintolin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        back = findViewById(R.id.imageButton8);
        orders = findViewById(R.id.listview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ApproveOrders.this,MainActivity7.class);
                startActivity(i);
            }
        });
        ArrayAdapter<String> arr = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,orderslist);
        orders.setAdapter(arr);
        for(int n =0; n<orders.getCount();n++){
            orders.getChildAt(n).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ApproveOrders.this,MainActivity7.class);
                    startActivity(i);
                }
            });
        }



    }
}