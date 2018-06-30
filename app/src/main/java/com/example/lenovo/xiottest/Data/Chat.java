package com.example.lenovo.xiottest.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity

public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "message")
    private String message;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
