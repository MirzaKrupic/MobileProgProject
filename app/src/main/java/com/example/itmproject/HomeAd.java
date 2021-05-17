package com.example.itmproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class HomeAd extends Fragment {
/*
    public static HomeAd newInstance(int imageId, String title, String desc){
        HomeAd homeAd= new HomeAd();

        Bundle args = new Bundle();
        args.putInt("id", imageId);
        args.putString("title", title);
        args.putString("desc", desc);

        return homeAd;
    } */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_list_view_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
        TextView main = (TextView) view.findViewById(R.id.item_title);
        TextView desc = (TextView) view.findViewById(R.id.item_description);

        assert getArguments() != null;
        main.setText("Testiranje");
        desc.setText("diskripsn");

        return view;
    }

}
