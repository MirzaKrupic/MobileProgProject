package com.example.itmproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {
    private EditText _email;
    private EditText _username;
    private EditText _password;
    private Button _registerButton;
    private TextView _signInText;
    private AppDatabase _db;
    private CategoryFragment cat;
    private ListView _categoryList;

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
        _signInText.setOnClickListener(this::switchToLogin);
        _registerButton.setOnClickListener(this::register);
        _categoryList = (ListView) view.findViewById(R.id.categoryList);
        _db = AppDatabase.getInstance(requireActivity());

        ArrayList<Category> arrayList = new ArrayList<Category>();
        List<Category> categories = _db.categoryDao().getAll();
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getActivity().getApplicationContext(), arrayList);
        _categoryList.setAdapter(categoryListAdapter);
        for(Category c : categories){
            categoryListAdapter.add(c);
        }

    }

    public void register(View view){
        String username = _username.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();

        List<Category> categories = new ArrayList<Category>();

        for(int i=0; i< _categoryList.getCount(); i++){
            String cat = _categoryList.getItemAtPosition(i).toString();
            categories.add(_db.categoryDao().getByName(cat));
        }



        if(_db.userDao().getUserByUsername(username) != null)
        {
            Toast.makeText(requireActivity(), "User with the same username already exists", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(email, username, password);
        _db.userDao().add(user);
        Toast.makeText(requireActivity(), "Successful registration", Toast.LENGTH_SHORT).show();

        ((MainActivity)getActivity()).userId = _db.userDao().loginUser(username, password);

        for(Category c : categories){

        }

        ((MainActivity)getActivity()).bar.setItemSelected(R.id.nav_home, true);
    }

    public void switchToLogin(View view){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        ft.replace(R.id.fragment_container, new LoginFragment(), null);
        ft.commit();
    }
}
