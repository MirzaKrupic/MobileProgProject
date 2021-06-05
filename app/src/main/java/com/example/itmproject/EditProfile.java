package com.example.itmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    private TextView _name, _email, _phone, _description;
    private Spinner _location;
    private Button _btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        _name = findViewById(R.id.editName);
        _email = findViewById(R.id.editEmail);
        _phone = findViewById(R.id.editPhone);
        _description = findViewById(R.id.editDescription);
        _location = findViewById(R.id.editLocation);

        _location.setSelection(0);

        _btnUpdate = findViewById(R.id.updateButton);

        Long userId = getIntent().getLongExtra("USER_ID", -1);


        User user = AppDatabase.getInstance(this).userDao().getUserById(userId);

        String[] stringArray = getResources().getStringArray(R.array.locations);

        for(int i = 1; i<stringArray.length; i++){
            if(stringArray[i].equals(user.getLocation())){
                _location.setSelection(i);
                break;
            }

        }

        _name.setText(user.getName());
        _email.setText(user.getEmail());
        _phone.setText(user.getPhone());
        _description.setText(user.getDescription());

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userByEmail = AppDatabase.getInstance(EditProfile.this).userDao().getUserByEmail(_email.getText().toString());
                if(_location.getSelectedItem().equals("City")){
                    Toast.makeText(EditProfile.this, "Please select city", Toast.LENGTH_SHORT).show();
                }else if(AppDatabase.getInstance(EditProfile.this).userDao().getUserByEmail(_email.getText().toString())!=null && !_email.getText().toString().equals(user.getEmail())){
                    Toast.makeText(EditProfile.this, "This mail already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    AppDatabase.getInstance(EditProfile.this).userDao().updateUser(_name.getText().toString(), _phone.getText().toString(), _description.getText().toString(), _email.getText().toString(), _location.getSelectedItem().toString(), userId);
                    finish();
                }
            }
        });
    }
}