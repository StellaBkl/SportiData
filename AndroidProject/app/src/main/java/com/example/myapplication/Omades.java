package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Omades {

    private int id_omadas;
    private String onoma_omadas;

    public int getId_omadas() {
        return id_omadas;
    }

    public void setId_omadas(int id_omadas) {
        this.id_omadas = id_omadas;
    }

    public String getOnoma_omadas() {
        return onoma_omadas;
    }

    public void setOnoma_omadas(String onoma_omadas) {
        this.onoma_omadas = onoma_omadas;
    }
}
