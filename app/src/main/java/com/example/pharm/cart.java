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

import java.util.ArrayList;
import java.util.Random;

public class cart extends AppCompatActivity {
    ListView cart;
    ImageButton back;
    TextView total;
    Button conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        String item = getIntent().getStringExtra("itemName");
        ArrayList<String> cartitems = new ArrayList<>();
        cartitems.add(item);
        back = findViewById(R.id.imageButton8);
        cart = findViewById(R.id.listview);
        total=findViewById(R.id.totalprice);
        conf = findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.pharm.cart.this,MainActivity3.class);
                startActivity(i);
            }
        });

        ArrayAdapter<String> arr = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,cartitems);
        cart.setAdapter(arr);
        int[] numbers = {10, 20, 30, 50, 150};
        Random random = new Random();
        int randomIndex = random.nextInt(numbers.length);
        int randomNum = numbers[randomIndex];
        total.setText(""+cartitems.size()*randomNum);

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.pharm.cart.this,MyOrders.class);
                i.putExtra("items",cartitems);
                startActivity(i);
                cart.setVisibility(View.INVISIBLE);

            }
        });
    }
}