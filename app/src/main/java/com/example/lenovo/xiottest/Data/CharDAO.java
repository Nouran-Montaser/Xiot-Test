package com.example.lenovo.xiottest.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Lenovo on 6/30/2018.
 */
@Dao
public interface CharDAO {

    @Query("SELECT * FROM chat")
    List<Chat> getAll();

    @Query("SELECT * FROM chat WHERE name LIKE :name LIMIT 1")
    Chat findByName(String name);

    @Insert
    void insertAll(List<Chat> products);

    @Insert
    void insertAll(Chat msg);

    @Update
    void update(Chat product);

    @Delete
    void delete(Chat product);


}

