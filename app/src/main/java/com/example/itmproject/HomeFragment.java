package com.example.itmproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<ProfileAd> arrayList = new ArrayList<ProfileAd>();
        ProfileAdAdapter profileAdAdapter = new ProfileAdAdapter(getActivity().getApplicationContext(), arrayList);

        ListView listView = (ListView) view.findViewById(R.id.mainMenu);
        listView.setAdapter(profileAdAdapter);

        ProfileAd profileAd;
        List<User> users = AppDatabase.getInstance(requireActivity()).userDao().getAllUsers();
        for(User u : users){
            profileAd = new ProfileAd(R.drawable.blank_profile_picture, u.getName(), u.getDescription());
            profileAdAdapter.add(profileAd);
        }


        return view;
    }

}
