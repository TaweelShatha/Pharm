package com.example.pharm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity7 extends AppCompatActivity {
    ImageButton approveorders;
    ImageButton editstock;
    ImageButton logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        approveorders = findViewById(R.id.imageButton5);
        editstock = findViewById(R.id.imageButton6);
        logout = findViewById(R.id.logout);

        approveorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity7.this,ApproveOrders.class);
                startActivity(i);
            }
        });
        editstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity7.this, EditStock.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity7.this, MainActivity.class);
                startActivity(i);
            }
        });




    }
}