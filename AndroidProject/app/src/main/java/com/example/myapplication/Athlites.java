package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Athlites {

    private int id_athliti;
    private String onoma_athliti;
    private String epitheto_athliti;

    public int getId_athliti() {
        return id_athliti;
    }

    public void setId_athliti(int id_athliti) {
        this.id_athliti = id_athliti;
    }

    public String getOnoma_athliti() {
        return onoma_athliti;
    }

    public void setOnoma_athliti(String onoma_athliti) {
        this.onoma_athliti = onoma_athliti;
    }

    public String getEpitheto_athliti() {
        return epitheto_athliti;
    }

    public void setEpitheto_athliti(String epitheto_athliti) {
        this.epitheto_athliti = epitheto_athliti;
    }
}
