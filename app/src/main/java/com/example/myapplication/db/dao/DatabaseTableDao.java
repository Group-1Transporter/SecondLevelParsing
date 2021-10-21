package com.example.myapplication.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.myapplication.db.DatabaseTable;


@Dao
public interface DatabaseTableDao {
    @Insert
    public void dataInsert(DatabaseTable databaseTable);

}
