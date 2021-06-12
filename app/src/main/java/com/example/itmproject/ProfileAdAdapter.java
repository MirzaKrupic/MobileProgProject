package com.example.itmproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProfileAdAdapter extends ArrayAdapter<ProfileAd> {
    public ProfileAdAdapter(Context context, ArrayList<ProfileAd> ads){
        super(context, 0, ads);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProfileAd profileAd = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_image);
        TextView title = (TextView) convertView.findViewById(R.id.item_title);
        TextView description = (TextView)  convertView.findViewById(R.id.item_description);

        imageView.setImageResource(profileAd.getImageResourceId());
        title.setText(profileAd.getTitle());
        description.setText(profileAd.getDescription());

        return convertView;
    }

}
