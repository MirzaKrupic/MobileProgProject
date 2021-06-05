package com.example.itmproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RegisterFragment extends Fragment {
    private EditText _email;
    private EditText _username;
    private EditText _password;
    private Button _registerButton;
    private TextView _registerText;
    private TextView _signInText;
    private Button _signInButton;
    private AppDatabase _db;

    public RegisterFragment(){
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        _email = (EditText) view.findViewById(R.id.emal_input);
        _username = (EditText) view.findViewById(R.id.username_input);
        _password = (EditText) view.findViewById(R.id.password_input);
        _registerButton = (Button) view.findViewById(R.id.register_button);
        _signInText = (TextView) view.findViewById(R.id.sign_in_text);
        _signInButton = (Button) view.findViewById(R.id.sign_in_button);
        _registerText = (TextView) view.findViewById(R.id.register_text);

        _registerText.setVisibility(View.INVISIBLE);
        _signInButton.setVisibility(View.INVISIBLE);

        _signInText.setOnClickListener(this::switchToLogin);
        _registerText.setOnClickListener(this::switchToRegister);
        _signInButton.setOnClickListener(this::login);
        _registerButton.setOnClickListener(this::register);

        _db = AppDatabase.getInstance(requireActivity());
    }

    public void register(View view){
        String username = _username.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();

        if(_db.userDao().getUserByUsername(username) != null)
        {
            Toast.makeText(requireActivity(), "User with the same username already exists", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(email, username, password);
        _db.userDao().add(user);
        Toast.makeText(requireActivity(), "Successful registration", Toast.LENGTH_SHORT).show();

        _email.setVisibility(View.INVISIBLE);
        _signInButton.setVisibility(View.VISIBLE);
        _registerText.setVisibility(View.VISIBLE);
        _signInText.setVisibility(View.INVISIBLE);
        _registerButton.setVisibility(View.INVISIBLE);
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        ft.replace(R.id.fragment_container, new DashboardFragment(), null);
        ft.commit();*/
    }

    public void login(View view){
        String username = _username.getText().toString();
        String password = _password.getText().toString();

        User dbUser = _db.userDao().getUserByUsername(username);

        if(dbUser == null)
        {
            Toast.makeText(requireActivity(), "User with the provided username doesn't exist", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!dbUser.getPassword().equals(password))
        {
            Toast.makeText(requireActivity(), "Invalid password", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(requireActivity(), "Successfully logged in", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).userId = _db.userDao().loginUser(username, password);

        ((MainActivity)getActivity()).bar.setItemSelected(R.id.nav_home, true);
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        ft.replace(R.id.fragment_container, new DashboardFragment(), null);
        ft.commit();*/
    }

    public void switchToRegister(View view){
        _email.setVisibility(View.VISIBLE);
        _signInButton.setVisibility(View.INVISIBLE);
        _registerText.setVisibility(View.INVISIBLE);
        _signInText.setVisibility(View.VISIBLE);
        _registerButton.setVisibility(View.VISIBLE);
    }

    public void switchToLogin(View view){
        _email.setVisibility(View.INVISIBLE);
        _signInButton.setVisibility(View.VISIBLE);
        _registerText.setVisibility(View.VISIBLE);
        _signInText.setVisibility(View.INVISIBLE);
        _registerButton.setVisibility(View.INVISIBLE);
    }
}
