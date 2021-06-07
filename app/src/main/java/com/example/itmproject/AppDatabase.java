package com.example.itmproject;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.itmproject.Dao.CategoryDao;
import com.example.itmproject.Dao.UserCategoryDao;
import com.example.itmproject.Dao.UserDao;
import com.example.itmproject.Entities.Category;
import com.example.itmproject.Entities.User;
import com.example.itmproject.Entities.UserCategoryCrossref;

import java.util.concurrent.Executors;

@Database(
        version = 6,
        entities = {
                User.class,
                Category.class,
                UserCategoryCrossref.class
        },
        exportSchema = false

)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract UserCategoryDao userCategoryDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "app_database")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    getInstance(context).categoryDao().insertAll(Category.populateCategories());
                                }
                            });
                        }
                    })
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() //This will cause data loss every time new version of DB is applied
                    .build();
        }

        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "app_database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).categoryDao().insertAll(Category.populateCategories());
                            }
                        });
                    }
                }).allowMainThreadQueries()
                .build();
    }
}
