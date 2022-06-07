package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "team")
public class Omada {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_omadas")
    private int kodikos_omadas;

    @ColumnInfo(name = "onoma_omadas")
    private String onoma_omadas;

    @ColumnInfo(name = "onoma_gipedou")
    private String onoma_gipedou;

    @ColumnInfo(name = "poli")
    private String poli;

    @ColumnInfo(name = "xora")
    private String xora;

    @ColumnInfo(name = "kodikos_sport")
    private int sportid;

    @ColumnInfo(name = "etos_idrisis")
    private int etos_idrusis;

    public Omada() {

    }

    public Omada(int kodikos_omadas, String onoma_omadas, String onoma_gipedou, String poli, String xora, int sportid, int etos_idrusis) {
        this.kodikos_omadas = kodikos_omadas;
        this.onoma_omadas = onoma_omadas;
        this.onoma_gipedou = onoma_gipedou;
        this.poli = poli;
        this.xora = xora;
        this.sportid = sportid;
        this.etos_idrusis = etos_idrusis;
    }

    public int getKodikos_omadas() {
        return kodikos_omadas;
    }

    public void setKodikos_omadas(int kodikos_omadas) {
        this.kodikos_omadas = kodikos_omadas;
    }

    public String getOnoma_omadas() {
        return onoma_omadas;
    }

    public void setOnoma_omadas(String onoma_omadas) {
        this.onoma_omadas = onoma_omadas;
    }

    public String getOnoma_gipedou() {
        return onoma_gipedou;
    }

    public void setOnoma_gipedou(String onoma_gipedou) {
        this.onoma_gipedou = onoma_gipedou;
    }

    public String getPoli() {
        return poli;
    }

    public void setPoli(String poli) {
        this.poli = poli;
    }

    public String getXora() {
        return xora;
    }

    public void setXora(String xora) {
        this.xora = xora;
    }

    public int getSportid() {
        return sportid;
    }

    public void setSportid(int sportid) {
        this.sportid = sportid;
    }

    public int getEtos_idrusis() {
        return etos_idrusis;
    }

    public void setEtos_idrusis(int etos_idrusis) {
        this.etos_idrusis = etos_idrusis;
    }


}

