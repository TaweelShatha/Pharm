package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditStock extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;

    ImageButton back;
    de.hdodenhof.circleimageview.CircleImageView drugimg;
    EditText drugname;
    EditText quan;
    Button save;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock);
        back = findViewById(R.id.back);
        drugimg = findViewById(R.id.medimg);
        drugname = findViewById(R.id.medname);
        quan = findViewById(R.id.quan);
        save = findViewById(R.id.save);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditStock.this, MainActivity7.class);
                startActivity(i);
            }
        });

        drugimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchImageSelection();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = drugname.getText().toString();
                String qq = quan.getText().toString();
                saveData(name, qq);
            }
        });
    }

    private void launchImageSelection() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            drugimg.setImageURI(imageUri);
            drugimg.setTag(imageUri);
        }
    }

    private void saveData(String name, String qq) {
        Uri imageUri = getImageUri();

        if (imageUri != null) {
            StorageReference imageRef = storageRef.child("MedPics/drugImage.jpg");
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(EditStock.this, "Image uploaded successfully.", Toast.LENGTH_SHORT).show();
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            Drug newd = new Drug(name, imageUrl, Integer.parseInt(qq.trim()));
                            db.collection("Drug")
                                    .document()
                                    .set(newd, SetOptions.merge())
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            // Data successfully added to Firestore
                                            Toast.makeText(EditStock.this, "Data added to Firestore.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Error occurred while adding data to Firestore
                                            Toast.makeText(EditStock.this, "Failed to add data to Firestore.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Image upload failed
                        Toast.makeText(EditStock.this, "Failed to upload image.", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private Uri getImageUri() {
        return (Uri) drugimg.getTag();
    }
}
