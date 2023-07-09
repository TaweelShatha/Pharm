package com.example.pharm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView forgot;
    TextView register;
    Button login;
    FirebaseFirestore firestore;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgot = findViewById(R.id.forgot);
        register = findViewById(R.id.textView3);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MainActivity3.class);
                if(String.valueOf(email.getText()).isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(String.valueOf(email.getText())).matches())
                {
                    return;  // make error on layout wrong email
                }
                if(String.valueOf(password.getText()).isEmpty())
                {
                    return;      // make error on layout wrong password
                }

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        firestore.collection("Users").document(user.getEmail().toString())
                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        if (documentSnapshot.exists()) {
                                                            String role = documentSnapshot.getString("role");
                                                            if (role != null) {
                                                                if (role.equals("Admin")) {
                                                                    Intent intent = new Intent(MainActivity.this, MainActivity7.class);
                                                                    startActivity(intent);
                                                                } else if (role.equals("User")) {
                                                                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                                                                    startActivity(intent);
                                                                } else {
                                                                    return;
                                                                }
                                                            } else {
                                                                return;                                                            }
                                                        } else {
                                                           Toast.makeText(MainActivity.this , "No user registered with this email",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Error occurred while retrieving the document, handle accordingly
                                                    }
                                                });
                                    } else {
                                        // User is null, handle accordingly
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MainActivity4.class);
                startActivity(i);
            }
        });
    }
}