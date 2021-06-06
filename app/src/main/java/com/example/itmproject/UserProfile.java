package com.example.itmproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {

    TextView name, mail, phone, location, description;
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.userName);
        mail = findViewById(R.id.userEmail);
        phone = findViewById(R.id.userPhone);
        location = findViewById(R.id.userLocation);
        description = findViewById(R.id.userDescription);

        Long userId = getIntent().getLongExtra("USER_ID", -1);
        User user = AppDatabase.getInstance(this).userDao().getUserById(userId);

        name.setText(user.getName());
        mail.setText(user.getEmail());
        phone.setText(user.getPhone());
        location.setText(user.getLocation());
        description.setText(user.getDescription());

        btnCall = findViewById(R.id.callButton);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + user.getPhone()));
                    startActivity(intent);
                }else{
                    requestCallPermission();
                }
            }
        });
    }

    private boolean isPermissionGranted(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return false;
        }else {
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCallPermission(){
        if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
            Toast.makeText(this, "This is some custom explanation", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission is granted, you can read the storage!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();

            }
        }
    }
}