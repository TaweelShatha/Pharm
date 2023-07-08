package com.example.pharm;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button back;
    Button next;
    ImageView form1;
    ImageView form2;
    LinearLayout personal;
    EditText name;
    EditText DOB;
    Spinner citymenu;
    Spinner gendermenu;
    Spinner statusmenu;
    LinearLayout emailandpass;
    EditText getemail;
    EditText getpass;
    EditText conpass;
    Button submit;
    String[] cities = {"Bethlehem", "Hebron", "Jenin", "Jericho & Al Aghwar", "Jerusalem", "Nablus", "Qalqiliya", "Ramallah & Al-Bireh", "Salfit", "Tubas", "Tulkarm"};
    String[] gender = {"Female", "Male"};
    String[] status = {"Single", "Married", "Divorced", "Widowed"};
    User user = new User();
    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main4);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        form1 = findViewById(R.id.imageView2);
        form2 = findViewById(R.id.imageView3);
        personal = findViewById(R.id.form1);
        name = findViewById(R.id.getname);
        DOB = findViewById(R.id.getdob);
        citymenu = findViewById(R.id.citymenu);
        gendermenu = findViewById(R.id.gendermenu);
        statusmenu = findViewById(R.id.statusmenu);
        emailandpass = findViewById(R.id.form2);
        getemail = findViewById(R.id.getemail);
        getpass = findViewById(R.id.getpass);
        conpass = findViewById(R.id.getreppass);
        submit = findViewById(R.id.submit);
        ArrayAdapter fillcity = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        ArrayAdapter fillgender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        ArrayAdapter fillstatus = new ArrayAdapter(this, android.R.layout.simple_spinner_item, status);
        fillcity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fillgender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fillstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citymenu.setAdapter(fillcity);
        gendermenu.setAdapter(fillgender);
        statusmenu.setAdapter(fillstatus);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(name.getText().toString());
                int n = citymenu.getSelectedItemPosition();
                user.setCity(cities[n]);
                user.setDOB(DOB.getText().toString());
                n = gendermenu.getSelectedItemPosition();
                user.setGender(gender[n]);
                n = statusmenu.getSelectedItemPosition();
                user.setSStatus(status[n]);
                form1.setVisibility(View.INVISIBLE);
                form2.setVisibility(View.VISIBLE);
                personal.setVisibility(View.INVISIBLE);
                emailandpass.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.VISIBLE);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        user.setEmail(getemail.getText().toString());
                        if(getemail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(getemail.getText().toString()).matches())
                        {
                            return;  // make error on layout wrong email
                        }
                        if((getpass.getText().toString()).isEmpty() || getpass.getText().toString().length() < 8 || !conpass.getText().toString().equals(getpass.getText().toString()))
                        {
                            return;      // make error on layout wrong password and password confirmation
                        }

                        mAuth.createUserWithEmailAndPassword(user.email, getpass.getText().toString())
                                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            firestore.collection("Users")
                                                    .document(user.email)
                                                    .set(user);
                                            Intent i = new Intent(MainActivity4.this,MainActivity.class);
                                            startActivity(i);

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(MainActivity4.this, "failed to register.",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                        Intent i = new Intent(MainActivity4.this,MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(), cities[position], Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), gender[position], Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), status[position], Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}