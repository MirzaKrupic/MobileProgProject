package com.example.itmproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserCategoryCrossref;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {
    private EditText _email;
    private EditText _username;
    private EditText _password;
    private EditText _fullName;
    private EditText _phone;
    private EditText _location;
    private Button _registerButton;
    private Button _categoriesButton;
    private TextView _signInText;
    private AppDatabase _db;
    private CategoryFragment cat;
    private ListView _categoryList;
    private ArrayList<Integer> _checkedCategories;

    public RegisterFragment(){
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        _db = AppDatabase.getInstance(requireActivity());
        _email = (EditText) view.findViewById(R.id.emal_input);
        _username = (EditText) view.findViewById(R.id.username_input);
        _password = (EditText) view.findViewById(R.id.password_input);
        //_fullName = (EditText) view.findViewById(R.id.full_name_input);
        //_phone = (EditText) view.findViewById(R.id.phone_input);
        //_location = (EditText) view.findViewById(R.id.location_input);
        _registerButton = (Button) view.findViewById(R.id.register_button);
        //_categoriesButton = (Button) view.findViewById(R.id.categories_button);
        _signInText = (TextView) view.findViewById(R.id.sign_in_text);
        _signInText.setOnClickListener(this::switchToLogin);
        _registerButton.setOnClickListener(this::register);
        _categoryList = (ListView) view.findViewById(R.id.categoryList);
        _checkedCategories = new ArrayList<>();


        ArrayList<Category> arrayList = new ArrayList<Category>();
        List<Category> categories = _db.categoryDao().getAll();
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getActivity().getApplicationContext(), arrayList);
        _categoryList.setAdapter(categoryListAdapter);

        _categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _checkedCategories.add(position);
            }
        });

        categoryListAdapter.addAll(categories);
    }

    public void register(View view){
        String username = _username.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        //String fullName = _fullName.getText().toString();
        //String phone = _phone.getText().toString();
        //String location = _location.getText().toString();

        if(_db.userDao().getUserByUsername(username) != null)
        {
            Toast.makeText(requireActivity(), "User with the same username already exists", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(email, username, password);

        for (int i = 0; i < _checkedCategories.size(); i++) {
            _db.userCategoryDao().add(new UserCategoryCrossref(user.getUserId(), (long)i));
        }

        _db.userDao().add(user);
        Toast.makeText(requireActivity(), "Successful registration", Toast.LENGTH_SHORT).show();

        ((MainActivity)getActivity()).userId = _db.userDao().loginUser(username, password);

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
