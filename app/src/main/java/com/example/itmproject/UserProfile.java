package com.example.itmproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itmproject.Entities.CategoriyAndCategorized;
import com.example.itmproject.Entities.Categorized;
import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.CategoryAndUser;
import com.example.itmproject.Entities.Review;
import com.example.itmproject.Entities.ReviewAndUser;
import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserAndReview;

import java.util.ArrayList;
import java.util.List;

import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserWithCategories;

public class UserProfile extends AppCompatActivity {

    TextView name, mail, phone, location, description, categories;
    Button btnCall, btnSubmitReview;
    EditText comment;
    Spinner grade;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.userName);
        mail = findViewById(R.id.userEmail);
        phone = findViewById(R.id.userPhone);
        location = findViewById(R.id.userLocation);
        description = findViewById(R.id.userDescription);
        categories = findViewById(R.id.userCategories);

        Long userId = getIntent().getLongExtra("USER_ID", -1);
        User user = AppDatabase.getInstance(this).userDao().getUserById(userId);

        if(user.getName() != null && !user.getName().equals("")) name.setText(user.getName());
        else name.setText("Name not set");
        if(user.getEmail() != null && !user.getEmail().equals("")) mail.setText(user.getEmail());
        else mail.setText("E-mail not set");
        if(user.getPhone() != null && !user.getPhone().equals("")) phone.setText(user.getPhone());
        else phone.setText("Phone number not set");
        if(user.getLocation() != null && !user.getLocation().equals("")) location.setText(user.getLocation());
        else location.setText("Location not set");
        if(user.getDescription() != null && !user.getDescription().equals("")) description.setText(user.getDescription());
        else description.setText("Description not set");

        btnCall = findViewById(R.id.callButton);

        btnCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0)
            {
                callPhoneNumber();
            }
        });

        btnSubmitReview = findViewById(R.id.submitReview);
        grade = findViewById(R.id.reviewGrade);
        comment = findViewById(R.id.reviewComent);

        List<Categorized> categorizeds = AppDatabase.getInstance(UserProfile.this).categorizedDaoDao().getCategorizedByUserId(userId);
        List<CategoriyAndCategorized> userWithCategories = new ArrayList<CategoriyAndCategorized>();
        for(Categorized c : categorizeds){
            userWithCategories.add(AppDatabase.getInstance(UserProfile.this).userCategorizedDao().oneCategoryByUser(c.getCategoryId()));
        }
        for(int i = 0; i < userWithCategories.size(); i++){
            description.setText(categories.getText() + " " + userWithCategories.get(i).category.getName());
        }

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int submitGrade = Integer.parseInt(grade.getSelectedItem().toString());
                Review review = new Review(comment.getText().toString(), submitGrade, userId);
                AppDatabase.getInstance(UserProfile.this).reviewDao().addReview(review);
            }
        });

        ArrayList<Review> arrayList = new ArrayList<Review>();
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(UserProfile.this, arrayList);

        listView = findViewById(R.id.reviewsList);
        listView.setAdapter(reviewListAdapter);
        List<ReviewAndUser> reviews = AppDatabase.getInstance(UserProfile.this).userReviewDao().getUserReviews(userId);
        for(ReviewAndUser r : reviews){
            reviewListAdapter.add(r.review);
        }


    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults)
    {
        if(requestCode == 101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhoneNumber();
            }
            else
            {
                Log.e("nesto", "Permission not Granted");
            }
        }
    }
}