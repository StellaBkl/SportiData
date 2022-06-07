package com.example.myapplication;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Sport")
public class Sport {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_sport")
    private int kodikos_sport;

    @ColumnInfo(name = "onoma_sport")
    private String onoma_sport;

    @ColumnInfo(name = "eidos_athlimatos")
    private String eidos_athlimatos;

    @ColumnInfo(name = "fullo")
    private String fullo;

    public int getKodikos_sport() {
        return kodikos_sport;
    }

    public void setKodikos_sport(int kodikos_sport) {
        this.kodikos_sport = kodikos_sport;
    }

    public String getOnoma_sport() {
        return onoma_sport;
    }

    public void setOnoma_sport(String onoma_sport) {
        this.onoma_sport = onoma_sport;
    }

    public String getEidos_athlimatos() {
        return eidos_athlimatos;
    }

    public void setEidos_athlimatos(String eidos_athlimatos) {
        this.eidos_athlimatos = eidos_athlimatos;
    }

    public String getFullo() {
        return fullo;
    }

    public void setFullo(String fullo) {
        this.fullo = fullo;
    }
}
