package com.example.rkjc.news_app_2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class ArticleDatabase extends RoomDatabase {

    public abstract NewsItemDao newsItemDao();
    private static final String DATABASE_NAME = "articlesList";
    private static ArticleDatabase sInstance;

    public static ArticleDatabase getDatabase(Context context) {
        if (sInstance == null) {
            synchronized (ArticleDatabase.class) {
                //Log.d(LOG_TAG, "Creating new database instance");
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleDatabase.class, ArticleDatabase.DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }
    //public abstract NewsItemDao newsItemDao();
}
