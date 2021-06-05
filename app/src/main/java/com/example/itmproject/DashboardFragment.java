package com.example.itmproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {
    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private TextView changer;
    private FirebaseAuth mFirebaseAuth;
    private int perspective = 0;
    private Uri filePath;
    private Long checkUser;
    private AppDatabase _db;
    private TextView _name, _email, _location, _phone, _description;
    private FloatingActionButton openOptions;

    private final int PICK_IMAGE_REQUEST = 71;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        _db = AppDatabase.getInstance(requireActivity());
        User loggedUser = _db.userDao().getUserById(((MainActivity) Objects.requireNonNull(getActivity())).userId);
        _description = view.findViewById(R.id.description_text);
        _email = view.findViewById(R.id.email_text);
        _name = view.findViewById(R.id.name_text);
        _phone = view.findViewById(R.id.phone_text);
        _location = view.findViewById(R.id.location_text);
        fillTextFields(loggedUser);

        openOptions = view.findViewById(R.id.openOptions);
        openOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), EditProfile.class);
                intent.putExtra("USER_ID", ((MainActivity) Objects.requireNonNull(getActivity())).userId);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        User loggedUser = _db.userDao().getUserById(((MainActivity) Objects.requireNonNull(getActivity())).userId);
        fillTextFields(loggedUser);
    }

    private void fillTextFields(User loggedUser){
        if(loggedUser.getName() == null || loggedUser.getName().equals("")) _name.setText("Name not set");
        else _name.setText(loggedUser.getName());
        if(loggedUser.getDescription() == null || loggedUser.getDescription().equals("")) _description.setText("Description not set");
        else _description.setText(loggedUser.getDescription());
        if(loggedUser.getEmail() == null || loggedUser.getEmail().equals("")) _email.setText("Email not set");
        else _email.setText(loggedUser.getEmail());
        if(loggedUser.getLocation() == null || loggedUser.getLocation().equals("")) _location.setText("Location not set");
        else _location.setText(loggedUser.getLocation());
        if(loggedUser.getPhone() == null || loggedUser.getPhone().equals("")) _phone.setText("Phone not set");
        else _phone.setText(loggedUser.getPhone());
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
/*
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
*/
}
