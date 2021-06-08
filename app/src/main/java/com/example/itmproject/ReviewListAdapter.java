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

import com.example.itmproject.Entities.Review;

import java.util.ArrayList;

public class ReviewListAdapter extends ArrayAdapter<Review> {
    public ReviewListAdapter(Context context, ArrayList<Review> ads){
        super(context, 0, ads);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Review profileAd = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view_rating, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.item_title);
        TextView description = (TextView)  convertView.findViewById(R.id.item_description);

        title.setText(profileAd.getComment());
        //description.setText(String.valueOf(profileAd.getGrade()));
        int grade = profileAd.getGrade();
        ImageView star1 = convertView.findViewById(R.id.star1);
        ImageView star2 = convertView.findViewById(R.id.star2);
        ImageView star3 = convertView.findViewById(R.id.star3);
        ImageView star4 = convertView.findViewById(R.id.star4);
        ImageView star5 = convertView.findViewById(R.id.star5);

        if(grade == 1){
            star4.setVisibility(View.GONE);
            star3.setVisibility(View.GONE);
            star2.setVisibility(View.GONE);
            star1.setVisibility(View.GONE);
        }else if(grade == 2){
            star3.setVisibility(View.GONE);
            star2.setVisibility(View.GONE);
            star1.setVisibility(View.GONE);
        }else if(grade == 3){
            star2.setVisibility(View.GONE);
            star1.setVisibility(View.GONE);
        }else if(grade == 4){
            star1.setVisibility(View.GONE);
        }


        return convertView;
    }
}
