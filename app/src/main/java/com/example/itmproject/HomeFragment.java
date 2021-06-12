package com.example.itmproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserWithCategories;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView _categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<ProfileAd> arrayList = new ArrayList<ProfileAd>();
        ProfileAdAdapter profileAdAdapter = new ProfileAdAdapter(getActivity().getApplicationContext(), arrayList);

        List<User> users = AppDatabase.getInstance(requireActivity()).userDao().getAllUsers();
        populateUsers(view, profileAdAdapter, users);

        _categoryList = (ListView) view.findViewById(R.id.categories_search_list);

        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        List<Category> categories = AppDatabase.getInstance(requireActivity()).categoryDao().getAll();

        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getActivity().getApplicationContext(), categoryArrayList);
        _categoryList.setAdapter(categoryListAdapter);
        categoryListAdapter.addAll(categories);

        _categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Populate users list with the correct users calling populateUsers method
            }
        });


        return view;
    }

    private void populateUsers(View view, ProfileAdAdapter profileAdAdapter, List<User> users) {
        ListView listView = (ListView) view.findViewById(R.id.mainMenu);
        listView.setAdapter(profileAdAdapter);
        int i = 0;
        ProfileAd profileAd;
        for(User u : users){
            profileAd = new ProfileAd(R.drawable.blank_profile_picture, u.getName(), u.getDescription());
            profileAdAdapter.add(profileAd);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(requireActivity(), UserProfile.class);
                    intent.putExtra("USER_ID", u.getUserId());
                    startActivity(intent);
                }
            });

        }
    }


}
