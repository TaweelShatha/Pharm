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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity5 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    DocumentReference docRef;
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
        firestore = FirebaseFirestore.getInstance();
        editname = findViewById(R.id.editname);
        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity5.this, UpdatePassword.class);
                startActivity(i);
            }
        });
        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity5.this, UpdateName.class);
                startActivity(i);

            }
        });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        // Check if imageUri field is empty
        String userEmail = user.getEmail();
        docRef = firestore.collection("Users").document(userEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String imageUri = document.getString("imageUri");
                        if (imageUri == null || imageUri.isEmpty()) {
                            // Field value is empty
                            // Add your logic or code here when imageUri is empty
                        } else {
                            // Field value is not empty
                            // Add your logic or code here when imageUri is not empty
                            // For example, you can load the image using Picasso
                            storageReference.child(imageUri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Picasso.get().load(uri).into(set);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity5.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                                }
                            });                        }
                    } else {
                        // Document does not exist
                        Toast.makeText(MainActivity5.this, "Document not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error occurred
                    Toast.makeText(MainActivity5.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
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
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(set);
            UploadImage();
        }
    }

    public void UploadImage() {
        if (mImageUri != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String userEmail = user.getEmail();
            final String newImageFileName = userEmail + "." + getFileExtension(mImageUri);
            final StorageReference newFileRef = storageReference.child(newImageFileName);

            // Delete the previous image from Firebase Storage
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String prevImagePath = documentSnapshot.getString("imagePath");
                    if (prevImagePath != null && !prevImagePath.isEmpty()) {
                        StorageReference prevFileRef = FirebaseStorage.getInstance().getReference().child(prevImagePath);
                        prevFileRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Previous image deleted successfully
                                // Now upload the new image
                                uploadNewImage(newFileRef);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // An error occurred while deleting the previous image
                                Toast.makeText(MainActivity5.this, "Failed to delete previous image", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // No previous image to delete
                        // Just upload the new image
                        uploadNewImage(newFileRef);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error occurred while retrieving the document
                    Toast.makeText(MainActivity5.this, "Failed to retrieve document", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "NO Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadNewImage(StorageReference fileRef) {
        fileRef.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // New image uploaded successfully
                        // Get the download URL of the new image
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String newImageUri = uri.toString();
                                final String newImageFileName = user.getEmail() + "." + getFileExtension(mImageUri);

                                // Update the imageUri field in Firestore
                                docRef.update("imagePath", newImageFileName)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(MainActivity5.this, "Upload Successful", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity5.this, "Failed to update image path", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity5.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // An error occurred while uploading the new image
                        Toast.makeText(MainActivity5.this, "Check Your Connection", Toast.LENGTH_LONG).show();
                    }
                });
    }



    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
