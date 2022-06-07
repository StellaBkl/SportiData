package com.example.myapplication;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.firebase.firestore.DocumentReference;


public class  omades_score {

    private int idepidosi;
    private DocumentReference id_omadas;
    private DocumentReference id_result;
    private DocumentReference id_sport;
    private String epidosi;



    public DocumentReference getId_omadas() {
        return id_omadas;
    }
    public void setId_omadas(DocumentReference id_omadas) {
        this.id_omadas = id_omadas;
    }

    public DocumentReference getId_result() {
        return id_result;
    }
    public void setId_result(DocumentReference id_result) {
        this.id_result = id_result;
    }

    public DocumentReference getId_sport() {
        return id_sport;
    }
    public void setId_sport(DocumentReference id_sport) {
        this.id_sport = id_sport;
    }

    public String getEpidosi() {
        return epidosi;
    }
    public void setEpidosi(String epidosi) {
        this.epidosi = epidosi;
    }

    public int getIdepidosi() {
        return idepidosi;
    }
    public void setIdepidosi(int idepidosi) {
        this.idepidosi = idepidosi;
    }


}
