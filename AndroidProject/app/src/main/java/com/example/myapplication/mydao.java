package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface mydao {
    @Insert
    public void addAthlete(Athlitis athlitis);

    @Query ("select * from athlete")
    public LiveData<List<Athlitis>> getAthlete();

    @Delete
    public void deleteAthlete(Athlitis athlitis);

    @Update
    public void updateAthlete(Athlitis athlitis);


    @Insert
    public void addSport(Sport sport);

    @Query ("select * from Sport")
    public LiveData<List<Sport>> getSport();

    @Delete
    public void deleteSport(Sport sport);

    @Update
    public void updateSport(Sport sport);


    @Insert
    public void addOmada(Omada omada);

    @Query("select * from team")
    public LiveData<List<Omada>> getOmada();

    @Delete
    public void deleteOmada(Omada omada);

    @Update
    public void updateOmada(Omada omada);


    @Query("SELECT DISTINCT count(a.athlete_id) as aid, S.id_sport as sid, S.onoma_sport as sonoma, s.fullo as filo, s.eidos_athlimatos as eathl " +
            "FROM Sport S inner join athlete a on a.sport_id=S.id_sport " +
            "group by S.id_sport")
    public List<ResultStringInt> getQuery1();

    @Query("SELECT DISTINCT t.poli as pol, count(t.id_omadas) as oid, min(t.etos_idrisis) as etidr, t.onoma_omadas as tonoma, count(s.id_sport) as numathl " +
            "FROM team t inner join Sport s on t.kodikos_sport = s.id_sport " +
            "group by poli")
    public List<ResultStringInt2> getQuery2();

    /*@Query("SELECT DISTINCT t.poli as pol, count(t.id_omadas) as oid, min(t.etos_idrisis) as etidr, t.onoma_omadas as tonoma, count(a.athlete_id) as numathl " +
            "FROM team t inner join athlete a on t.kodikos_sport = a.sport_id " +
            "group by poli")
    public List<ResultStringInt2> getQuery2();*/

    /*@Query("select o.id_omadas as oid, o.onoma_omadas as oonoma, s.onoma_sport as sname " +
            "from team o inner join sport s on o.kodikos_sport = s.id_sport " +
            "where poli = '?' ")
    public List<ResultStringInt3> getQuery3();*/

    @Query("select s.onoma_sport as sonoma, min(a.athlete_borndt) as maxbdate, max(a.athlete_borndt) as minbdate, count(a.athlete_id) as numath, count(t.id_omadas) as numom " +
            "from athlete a inner join sport s on a.sport_id = s.id_sport inner join team t on t.kodikos_sport = s.id_sport " +
            "group by s.onoma_sport")
    public List<ResultStringInt3> getQuery3();


}




