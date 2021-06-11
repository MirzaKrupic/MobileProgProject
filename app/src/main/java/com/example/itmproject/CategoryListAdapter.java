package com.example.itmproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.Review;

import java.util.ArrayList;

public class CategoryListAdapter extends ArrayAdapter<Category> {
    public CategoryListAdapter(Context context, ArrayList<Category> ads){
        super(context, 0, ads);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Category profileAd = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view_category, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.categoryBox);

        checkBox.setText(profileAd.getName());


        return convertView;
    }
}
