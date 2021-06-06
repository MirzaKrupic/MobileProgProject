package com.example.itmproject;

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

import com.example.itmproject.Entities.User;

public class LoginFragment extends Fragment {
    private EditText _username;
    private EditText _password;
    private TextView _registerText;
    private Button _signInButton;
    private AppDatabase _db;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        _username = (EditText) view.findViewById(R.id.login_username_input);
        _password = (EditText) view.findViewById(R.id.login_username_password);
        _signInButton = (Button) view.findViewById(R.id.sign_in_button);
        _registerText = (TextView) view.findViewById(R.id.register_text);

        _registerText.setOnClickListener(this::switchToRegister);
        _signInButton.setOnClickListener(this::login);

        _db = AppDatabase.getInstance(requireActivity());
    }

    public void switchToRegister(View view){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        ft.replace(R.id.fragment_container, new RegisterFragment(), null);
        ft.commit();
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

}
