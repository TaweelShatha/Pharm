package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {
    private EditText Password , PasswordConfirmation ;
    private Button ChangePass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_password);
        Password = findViewById(R.id.oldpasslog);
        PasswordConfirmation = findViewById(R.id.newpasslog);
        ChangePass = findViewById(R.id.updatepass);

        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Password.getText().toString().equals(PasswordConfirmation.getText().toString()))
                {
                    Toast.makeText(UpdatePassword.this , "Password dont match the confirmation", Toast.LENGTH_LONG).show();
                    return;
                }
                if(Password.getText().toString().length() < 8)
                {
                    Toast.makeText(UpdatePassword.this , "Password should be 8 characters or longer ", Toast.LENGTH_LONG).show();
                    return;
                }
                String newPassword = Password.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdatePassword.this , "Password updated ", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(UpdatePassword.this , MainActivity3.class);
                                    startActivity(i);
                                }
                            }
                        });


            }
        });
    }
}