package com.example.itmproject;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.itmproject.Entities.Categorized;
import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserWithCategories;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView _categoryList;
    private Spinner categoryFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        _categoryList = view.findViewById(R.id.mainMenu);
        ArrayList<ProfileAd> arrayList = new ArrayList<ProfileAd>();
        ProfileAdAdapter profileAdAdapter = new ProfileAdAdapter(getActivity().getApplicationContext(), arrayList);

        List<User> users = AppDatabase.getInstance(requireActivity()).userDao().getAllUsers();
        populateUsers(view, profileAdAdapter, users);


        categoryFilter = view.findViewById(R.id.categoryFilter);
        // Array of choices
        List<String> categories = AppDatabase.getInstance(requireActivity()).categoryDao().getAllCategoryNames();
        String categoryArray[] = new String[categories.size() + 1];
        categoryArray[0] = "Filter by categories";
        int i = 1;
        for(String category : categories){
            categoryArray[i] = category;
            i++;
        }

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(requireActivity(),   android.R.layout.simple_spinner_item, categoryArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        categoryFilter.setAdapter(spinnerArrayAdapter);

        categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(!categoryFilter.getSelectedItem().toString().equals("Filter by categories")) {
                   Long catId = AppDatabase.getInstance(requireActivity()).categoryDao().getByName(categoryFilter.getSelectedItem().toString());
                   List<Categorized> categorizeds = AppDatabase.getInstance(requireActivity()).categorizedDaoDao().getCategorizedByCategoryId(catId);
                   users.clear();
                   _categoryList.setAdapter(null);
                   for (Categorized c : categorizeds) {
                       users.add(AppDatabase.getInstance(requireActivity()).userDao().getUserById(c.getUserId()));
                   }
                   arrayList.clear();
                   ProfileAdAdapter profileAdAdapter = new ProfileAdAdapter(getActivity().getApplicationContext(), arrayList);

                   populateUsers(view, profileAdAdapter, users);
               }else{
                   ArrayList<ProfileAd> arrayList = new ArrayList<ProfileAd>();
                   ProfileAdAdapter profileAdAdapter = new ProfileAdAdapter(getActivity().getApplicationContext(), arrayList);

                   List<User> users = AppDatabase.getInstance(requireActivity()).userDao().getAllUsers();
                   populateUsers(view, profileAdAdapter, users);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    private void populateUsers(View view, ProfileAdAdapter profileAdAdapter, List<User> users) {
        _categoryList.setAdapter(profileAdAdapter);
        int i = 0;
        ProfileAd profileAd;
        List<Long> ids = new ArrayList<Long>();

        for(User u : users) {
            if (u.getName() != null && !u.getName().equals("") && u.getDescription() != null && !u.getDescription().equals("")) {
                profileAd = new ProfileAd(R.drawable.blank_profile_picture, u.getName(), u.getDescription());
                profileAdAdapter.add(profileAd);
                ids.add(u.getUserId());

            }
        }
        _categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfileAd item = (ProfileAd)_categoryList.getAdapter().getItem(position);
                Intent intent = new Intent(requireActivity(), UserProfile.class);
                intent.putExtra("USER_ID", ids.get(position));
                startActivity(intent);
            }
        });
    }


}
