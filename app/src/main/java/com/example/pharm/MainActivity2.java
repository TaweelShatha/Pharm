package com.example.pharm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {
    EditText email;
    TextView send;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        email = findViewById(R.id.editTextTextEmailAddress2);
        send = findViewById(R.id.sendemail);
        back = findViewById(R.id.back);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString());
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);

        }

    });
}
}