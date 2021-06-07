package com.example.itmproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.User;

import java.util.List;

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

        List<Category> categories = AppDatabase.getInstance(UserProfile.this).categoryDao().getAll();
        for(Category c : categories){
            description.setText(description.getText().toString() + " " + c.getName());
        }

        btnCall = findViewById(R.id.callButton);

        btnCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0)
            {
                callPhoneNumber();
            }
        });
    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults)
    {
        if(requestCode == 101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhoneNumber();
            }
            else
            {
                Log.e("nesto", "Permission not Granted");
            }
        }
    }
}