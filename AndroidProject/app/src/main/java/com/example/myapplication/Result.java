package com.example.myapplication;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Result {

    private int id_result;
    private String date_competition;
    private String country_competition;
    private String poli_competition;
    private String onoma_sport;

    public Result(){

    }

    public Result(int id_result, String date_competition, String country_competition, String poli_competition, String onoma_sport) {
        this.id_result = id_result;
        this.date_competition = date_competition;
        this.country_competition = country_competition;
        this.poli_competition = poli_competition;
        this.onoma_sport = onoma_sport;
    }


    public int getId_result() {
        return id_result;
    }

    public void setId_result(int id_result) {
        this.id_result = id_result;
    }

    public String getDate_competition() {
        return date_competition;
    }

    public void setDate_competition(String date_competition) {
        this.date_competition = date_competition;
    }

    public String getCountry_competition() {
        return country_competition;
    }

    public void setCountry_competition(String country_competition) {
        this.country_competition = country_competition;
    }

    public String getPoli_competition() {
        return poli_competition;
    }

    public void setPoli_competition(String poli_competition) {
        this.poli_competition = poli_competition;
    }

    public String getOnoma_sport() {
        return onoma_sport;
    }

    public void setOnoma_sport(String onoma_sport) {
        this.onoma_sport = onoma_sport;
    }
}
