package com.example.pharm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity6 extends AppCompatActivity {

    ArrayList<Drug> myList;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        back = findViewById(R.id.back);
        myList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        db.collection("Drug")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String name = documentSnapshot.getString("Name");
                        String imagePath = documentSnapshot.getString("ImageUrl");
                        String qnum = documentSnapshot.getString("Quant");
                        int q = 0;
                        try {
                            q = Integer.parseInt(qnum);
                        } catch (NumberFormatException e) {
                            q = 0; // Set a default value or handle the error as needed
                            e.printStackTrace();
                        }
                        StorageReference storageRef = storage.getReferenceFromUrl(imagePath);
                        int finalQ = q;
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            myList.add(new Drug(name, imageUrl, finalQ));

                            RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            ShopAdapter adapter = new ShopAdapter(myList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity6.this));
                            recyclerView.setAdapter(adapter);
                        }).addOnFailureListener(exception -> {
                            // Failed to retrieve the download URL
                            exception.printStackTrace();
                        });
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity6.this, MainActivity3.class);
                startActivity(i);
            }
        });



    }
}
