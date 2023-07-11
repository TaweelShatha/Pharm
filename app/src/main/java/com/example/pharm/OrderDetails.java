package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class OrderDetails extends AppCompatActivity {
    TextView details;
    ImageButton back;
    Button approve;
    Button decline;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        details = findViewById(R.id.textView6);
        back = findViewById(R.id.imageback);
        approve = findViewById(R.id.approve);
        decline = findViewById(R.id.decline);
        Bundle bundle = getIntent().getExtras();
        String e  = bundle.getString("drugName");
        details.setText(e);
        db.collection("Ordered")
                .document(e)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                    }
                });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderDetails.this, ApproveOrders.class);
                startActivity(i);
            }
        });
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Order Approved ", Toast.LENGTH_SHORT).show();
                db.collection("Ordered")
                        .document(e)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Item successfully deleted
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while deleting item
                            }
                        });
                Intent i = new Intent(OrderDetails.this, ApproveOrders.class);
                startActivity(i);


            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Order Declined ", Toast.LENGTH_SHORT).show();
                db.collection("Ordered")
                        .document(e)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.println(Log.WARN,"s","Deleted");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.println(Log.ASSERT,"s","Not");
                            }
                        });

                Intent i = new Intent(OrderDetails.this, ApproveOrders.class);
                startActivity(i);

            }
        });
    }
}