package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "athlete")
public class Athlitis {
    @PrimaryKey
    @ColumnInfo (name = "athlete_id")
    private int aid;

    @ColumnInfo (name = "athlete_name")
    private String aname;

    @ColumnInfo (name = "athlete_sname")
    private String asurname;

    @ColumnInfo (name = "athlete_town")
    private String atown;

    @ColumnInfo (name = "athlete_country")
    private String axora;

    @ColumnInfo (name = "sport_id")
    private int sid;

    @ColumnInfo (name = "athlete_borndt")
    private int etosGenissis;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAsurname() {
        return asurname;
    }

    public void setAsurname(String asurname) {
        this.asurname = asurname;
    }

    public String getAtown() {
        return atown;
    }

    public void setAtown(String atown) {
        this.atown = atown;
    }

    public String getAxora() {
        return axora;
    }

    public void setAxora(String axora) {
        this.axora = axora;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getEtosGenissis() {
        return etosGenissis;
    }

    public void setEtosGenissis(int etosGenissis) {
        this.etosGenissis = etosGenissis;
    }
}
