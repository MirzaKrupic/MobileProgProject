package com.example.itmproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {
    private EditText username, emailId, password;
    private Button btnChoose, btnUpload, btnSignUp, btnSignOut;
    private ImageView imageView;
    private TextView changer;
    private FirebaseAuth mFirebaseAuth;
    private int perspective = 0;
    private Uri filePath;
    private Long checkUser;

    private final int PICK_IMAGE_REQUEST = 71;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        btnChoose = (Button) view.findViewById(R.id.btnChoose);
        btnUpload = (Button) view.findViewById(R.id.btnUpload);
        imageView = (ImageView) view.findViewById(R.id.imgView);

        username = view.findViewById(R.id.username);
        emailId = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        btnSignUp = view.findViewById(R.id.signup);
        btnSignOut = view.findViewById(R.id.signout);

        changer = view.findViewById(R.id.showOther);

        btnSignUp.setOnClickListener(this::signUp);

        changer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(perspective == 0){
                    emailId.setVisibility(View.VISIBLE);
                    btnSignUp.setText("Sign up");
                    changer.setText("Already have account?");
                    btnSignUp.setOnClickListener(DashboardFragment.this::signUp);
                    perspective = 1;
                }else if(perspective==2){
                    emailId.setVisibility(View.INVISIBLE);
                    btnSignUp.setText("Sign in");
                    changer.setText("Register here");
                    btnSignUp.setOnClickListener(DashboardFragment.this::signIn);
                    perspective = 0;
                }else{
                    emailId.setVisibility(View.GONE);
                    username.setVisibility(View.GONE);
                    btnSignUp.setVisibility(View.GONE);
                    changer.setVisibility(View.GONE);

                    btnSignOut.setVisibility(View.VISIBLE);
                    btnChoose.setVisibility(View.VISIBLE);
                    btnUpload.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        return view;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(requireActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(requireActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(requireActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void signUp(View view){
        User user = new User(emailId.getText().toString(), username.getText().toString(), password.getText().toString());
        AppDatabase.getInstance(requireActivity()).userDao().add(user);
        perspective = 1;
        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
    }

    private void signIn(View view){
        checkUser = AppDatabase.getInstance(requireActivity()).userDao().loginUser(username.getText().toString(), password.getText().toString());
        if(checkUser != null){
            Toast.makeText(requireActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            perspective = 2;
        }else{
            Toast.makeText(requireActivity(), "Invalid credidentials", Toast.LENGTH_SHORT).show();
        }
    }

}
