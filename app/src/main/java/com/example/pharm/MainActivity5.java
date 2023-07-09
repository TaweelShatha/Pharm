package com.example.pharm;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity5 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String storagepath = "Users_Profile_Cover_image/";
    String uid;
    ImageView set;
    TextView profilepic, editname, editpassword;
    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;
    String profileOrCoverPhoto;
    FirebaseUser user;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_main5);
        editpassword = findViewById(R.id.changepassword);
        profilepic = findViewById(R.id.profilepic);
        set = findViewById(R.id.setting_profile_image);
        storageReference = FirebaseStorage.getInstance().getReference("UsersPics");
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersPics");



        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        profilepic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            mImageUri = data.getData();
          Picasso.get().load(mImageUri).into(set);
          UploadImage();

        }
    }

    public  void UploadImage()
    {
        if (mImageUri!=null)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            StorageReference fileRef = storageReference.child(user.getEmail()
                    +"."+getFileExtension(mImageUri));

        }
        else
        {
            Toast.makeText(this,"NO Image Selected" ,Toast.LENGTH_SHORT);
        }
    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}