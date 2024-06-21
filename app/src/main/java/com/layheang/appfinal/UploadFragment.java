package com.layheang.appfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso; // Import Picasso

public class UploadFragment extends Fragment {

    private Button chooseImageButton;
    private Button uploadButton;
    private ImageView selectedImageView;
    private ProgressBar progressBar;
    private Uri imageUri;
    private static final int REQUEST_CODE = 101;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        chooseImageButton = view.findViewById(R.id.button_choose_image);
        uploadButton = view.findViewById(R.id.button_upload);
        selectedImageView = view.findViewById(R.id.imageView_selected_image);
        progressBar = view.findViewById(R.id.progress_bar);

        // Initialize Firebase Auth and Firebase Storage
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Set click listener for the Choose Image button
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Set click listener for the Upload button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() != null) {
                    uploadImage();
                } else {
                    Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
                    // Optionally, you could redirect to a login screen here
                }
            }
        });

        // Check if there's already an image URL stored in Firebase Storage
        // For example, if you want to retrieve and display an image after uploading
        retrieveImageFromStorage();

        return view;
    }

    // Method to open image chooser
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    // Handle result from image chooser
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            selectedImageView.setImageURI(imageUri);
            selectedImageView.setVisibility(View.VISIBLE);
        }
    }

    // Method to upload image to Firebase Storage
    private void uploadImage() {
        if (imageUri != null) {
            progressBar.setVisibility(View.VISIBLE);

            StorageReference ref = storageReference.child("uploads/" + System.currentTimeMillis() + ".jpg");

            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Upload successful!", Toast.LENGTH_SHORT).show();
                            retrieveImageFromStorage(); // After upload, retrieve and display the image
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to retrieve image URL from Firebase Storage and display in ImageView
    private void retrieveImageFromStorage() {
        // Example path to retrieve image (change this to your actual storage path)
        String imagePath = "uploads/example.jpg";
        storageReference.child(imagePath).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Use Picasso to load image into ImageView
                        Picasso.get().load(uri).into(selectedImageView);
                        selectedImageView.setVisibility(View.VISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors
                        Toast.makeText(getContext(), "Failed to retrieve image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
