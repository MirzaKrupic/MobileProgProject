package com.example.itmproject;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.itmproject.Dao.CategorizedDao;
import com.example.itmproject.Dao.CategoryDao;
import com.example.itmproject.Dao.ReviewDao;
import com.example.itmproject.Dao.UserCategorizedDao;
import com.example.itmproject.Dao.UserCategoryDao;
import com.example.itmproject.Dao.UserDao;
import com.example.itmproject.Dao.UserReviewDao;
import com.example.itmproject.Entities.Categorized;
import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.Review;
import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserCategoryCrossref;

import java.util.concurrent.Executors;

@Database(
        version = 13,
        entities = {
                User.class,
                Category.class,
                UserCategoryCrossref.class,
                Review.class,
                Categorized.class
        },
        exportSchema = false

)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract UserCategoryDao userCategoryDao();
    public abstract UserReviewDao userReviewDao();
    public abstract ReviewDao reviewDao();
    public abstract CategorizedDao categorizedDaoDao();
    public abstract UserCategorizedDao userCategorizedDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() //This will cause data loss every time new version of DB is applied
                    .build();
        }
        return INSTANCE;
    }
}
