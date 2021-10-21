package com.example.myapplication.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {
               DatabaseTable.class,
        },
        version = 1,
        exportSchema = false
)
public abstract class MyDataBase extends RoomDatabase {

    private static MyDataBase myDataBase = null;

    public static MyDataBase getInstance(Context context){
        if (myDataBase == null){
            myDataBase = Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class, "sample_Parsing.db")
                    .allowMainThreadQueries().build();
        }
        return myDataBase;
    }
    private static final Callback onCreateCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

}
