package com.example.itmproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.itmproject.Entities.Categorized;
import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {
    private AppDatabase _db;
    private EditText _email;
    private EditText _username;
    private EditText _password;
    private ListView _categoryList;

    public RegisterFragment(){
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        _db = AppDatabase.getInstance(requireActivity());
        _email = (EditText) view.findViewById(R.id.emal_input);
        _username = (EditText) view.findViewById(R.id.username_input);
        _password = (EditText) view.findViewById(R.id.password_input);
        _categoryList = (ListView) view.findViewById(R.id.categoryList);

        Button registerButton = (Button) view.findViewById(R.id.register_button);
        TextView signInText = (TextView) view.findViewById(R.id.sign_in_text);
        signInText.setOnClickListener(this::switchToLogin);
        registerButton.setOnClickListener(this::register);

        ArrayList<Category> arrayList = new ArrayList<>();
        List<Category> categories = _db.categoryDao().getAll();
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getActivity().getApplicationContext(), arrayList);
        _categoryList.setAdapter(categoryListAdapter);

        for(Category c : categories){
            categoryListAdapter.add(c);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void register(View view){
        String username = _username.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        InputValidationHelper inputValidationHelper = new InputValidationHelper();

        ArrayList<String> categories = new ArrayList<>();
        for (int i = 0; i < _categoryList.getCount() - 1; i++) {
            CheckBox checkBox = (CheckBox) _categoryList.getChildAt(i).findViewById(R.id.categoryBox);
            if(checkBox != null && checkBox.isChecked()){
                categories.add(checkBox.getText().toString());
            }
        }

        if(categories.size() == 0)
        {
            Toast.makeText(requireActivity(), "Please select at least one category", Toast.LENGTH_LONG).show();
            return;
        }

        if(!inputValidationHelper.isValidEmail(email))
        {
            Toast.makeText(requireActivity(), "Please insert valid email address", Toast.LENGTH_LONG).show();
            return;
        }

        if(!inputValidationHelper.isValidPassword(password))
        {
            Toast.makeText(requireActivity(), "Password should be at least 6 characters long", Toast.LENGTH_LONG).show();
            return;
        }

        if(username.length() < 2)
        {
            Toast.makeText(requireActivity(), "Username should be at least 3 characters long", Toast.LENGTH_LONG).show();
            return;
        }

        if(_db.userDao().getUserByUsername(username) != null)
        {
            Toast.makeText(requireActivity(), "User with the same username already exists", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(email, username, password);

        _db.userDao().add(user);
        Toast.makeText(requireActivity(), "Successful registration", Toast.LENGTH_SHORT).show();

        ((MainActivity)getActivity()).userId = _db.userDao().getUserByUsername(username).getUserId();
        Long category;
        for(String categoryName : categories){
             category = _db.categoryDao().getByName(categoryName);
             Categorized categorized = new Categorized(((MainActivity)getActivity()).userId, category );
             Log.e("User ID:", ""+categorized.getUserId());
            Log.e("Category ID:", ""+categorized.getCategoryId());
            _db.categorizedDaoDao().addCategorized(categorized);
        }

        show_Notification();

        ((MainActivity)getActivity()).bar.setItemSelected(R.id.nav_home, true);
    }

    public void switchToLogin(View view){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        ft.replace(R.id.fragment_container, new LoginFragment(), null);
        ft.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(){

        Intent intent=new Intent(requireActivity(),MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(requireActivity(),1,intent,0);
        Notification notification=new Notification.Builder(requireActivity(),CHANNEL_ID)
                .setContentText("Please fill your profile in order to appear on main list")
                .setContentTitle("Attention needed")
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Profile needed",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) requireActivity().getSystemService(requireActivity().NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }
}
