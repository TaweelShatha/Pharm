package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrders extends AppCompatActivity {
    ListView orders;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        back = findViewById(R.id.imageButton8);
        orders = findViewById(R.id.listview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyOrders.this,MainActivity3.class);
                startActivity(i);
            }
        });
        Bundle extras = getIntent().getExtras();
        ArrayList<String> items = extras.getStringArrayList("items");
        ArrayAdapter<String> arr = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items);
        orders.setAdapter(arr);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userEmail = "";
        if (currentUser != null) {
             userEmail = currentUser.getEmail();
        }
        Map<String, String> orderedItems = new HashMap<>();
        for (String item : items) {
            orderedItems.put(userEmail, item + "\n" + userEmail);
        }

        db.collection("Ordered")
                .document()
                .set(orderedItems, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Data successfully added to Firestore
                        } else {
                            // Error occurred while adding data to Firestore
                        }
                    }
                });









    }
}