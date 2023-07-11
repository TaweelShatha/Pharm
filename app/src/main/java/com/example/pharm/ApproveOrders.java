package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ApproveOrders extends AppCompatActivity {
    ListView orders;
    ImageButton back;
    public ArrayList<String> orderslist = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        db.collection("Ordered")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String drugName = document.getData().values().toString();
                                orderslist.add(drugName);
                            }

                            ArrayAdapter<String> arr = new ArrayAdapter<>(ApproveOrders.this,
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                    orderslist);
                            orders.setAdapter(arr);
                        } else {
                            // Error occurred while fetching data from Firestore
                        }
                    }
                });
        orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ApproveOrders.this, OrderDetails.class);
                String drugname = orders.getItemAtPosition(position).toString();
                i.putExtra("drugName",drugname);
                startActivity(i);
            }
        });



    }
}