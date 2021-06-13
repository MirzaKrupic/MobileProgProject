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
import android.widget.AdapterView;
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
    private ArrayList<String> cats;

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
        for(Category c : categories){
            categoryListAdapter.add(c);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void register(View view){
        String username = _username.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        //String fullName = _fullName.getText().toString();
        //String phone = _phone.getText().toString();
        //String location = _location.getText().toString();
        View v;
        CheckBox checkBox;
        cats = new ArrayList<String>();
        int checker = 0;
        for (int i = 0; i < _categoryList.getCount(); i++) {
            if((CheckBox)_categoryList.getChildAt(i).findViewById(R.id.categoryBox) != null){
                CheckBox cBox=(CheckBox)_categoryList.getChildAt(i).findViewById(R.id.categoryBox);
                if(cBox.isChecked()){
                    checker = 1;
                    cats.add(cBox.getText().toString());
                }
            }

        }

        if(checker == 0)
        {
            Toast.makeText(requireActivity(), "Please select at least one category", Toast.LENGTH_LONG).show();
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

        ((MainActivity)getActivity()).userId = _db.userDao().loginUser(username, password);
        Long category;
        for(String s : cats){
             category = _db.categoryDao().getByName(s);
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
