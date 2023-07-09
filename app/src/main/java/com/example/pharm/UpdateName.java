package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateName extends AppCompatActivity {
    private EditText newNameEditText;
    private Button updateNameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        newNameEditText = findViewById(R.id.newName);
        updateNameButton = findViewById(R.id.updatename);
        updateNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String newNameValue = newNameEditText.getText().toString();

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firestore.collection("Users").document(userEmail);

                docRef.update("name", newNameValue)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateName.this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UpdateName.this,MainActivity3.class);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateName.this, "Failed to update name", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}
