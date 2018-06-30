package com.example.lenovo.xiottest.Data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Chat.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase INSTANCE;

    public static MyDataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "user-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract CharDAO userDao();

}
