package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.google.firebase.firestore.DocumentReference;

public class Athlitis_score {

    private int id_epidosi;
    private DocumentReference id_athliti;
    private DocumentReference id_result;
    private DocumentReference id_sport;
    private String epidosi;


    public DocumentReference getId_athliti() {
        return id_athliti;
    }
    public void setId_athliti(DocumentReference id_athliti) {
        this.id_athliti = id_athliti;
    }

    public DocumentReference getId_result() {
        return id_result;
    }
    public void setId_result(DocumentReference id_result) {
        this.id_result = id_result;
    }

    public  DocumentReference getId_sport() {
        return id_sport;
    }
    public void setId_sport(DocumentReference id_sport) { this.id_sport = id_sport; }

    public int getId_epidosi() {
        return id_epidosi;
    }
    public void setId_epidosi(int id_epidosi) {
        this.id_epidosi = id_epidosi;
    }

    public String getEpidosi() {
        return epidosi;
    }
    public void setEpidosi(String epidosi) {
        this.epidosi = epidosi;
    }
}
