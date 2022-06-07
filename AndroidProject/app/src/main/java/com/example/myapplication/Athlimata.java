package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Athlimata {

    private int id_sport;
    private String onoma_sport;

    public int getId_sport() {
        return id_sport;
    }

    public void setId_sport(int id_sport) {
        this.id_sport = id_sport;
    }

    public String getOnoma_sport() {
        return onoma_sport;
    }

    public void setOnoma_sport(String onoma_sport) {
        this.onoma_sport = onoma_sport;
    }
}
