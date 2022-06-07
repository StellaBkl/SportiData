package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorySport {

    private mydao sDao;
    private LiveData<List<Sport>> allDataSport;

    public RepositorySport(Application application){
        Database sdbase = Database.getInstance(application);
        sDao = sdbase.myDao();
        allDataSport = sDao.getSport();

    }

    public void insertSport(Sport sport){

        new RepositorySport.InsertDataSportTask(sDao).execute(sport);
    }
    public void updateSport(Sport sport){
        new RepositorySport.UpdateDataSportTask(sDao).execute(sport);

    }
    public void daleteSport(Sport sport){
        new RepositorySport.DeleteDataSportTask(sDao).execute(sport);

    }

    public LiveData<List<Sport>> getAllDataSport(){

        return allDataSport;
    }

    private static class  InsertDataSportTask extends AsyncTask<Sport, Void, Void> {
        private mydao sDao;

        private InsertDataSportTask(mydao sportDao){
            this.sDao = sportDao;
        }
        @Override
        protected Void doInBackground(Sport... sports) {

            sDao.addSport(sports[0]);
            return null;
        }
    }
    private static class  UpdateDataSportTask extends AsyncTask<Sport, Void, Void>{
        private mydao sDao;

        private UpdateDataSportTask(mydao sportDao){
            this.sDao = sportDao;
        }
        @Override
        protected Void doInBackground(Sport... sports) {

            sDao.updateSport(sports[0]);
            return null;
        }
    }
    private static class  DeleteDataSportTask extends AsyncTask<Sport, Void, Void>{
        private mydao sDao;

        private DeleteDataSportTask(mydao sportDao){
            this.sDao = sportDao;
        }
        @Override
        protected Void doInBackground(Sport... sports) {

            sDao.deleteSport(sports[0]);
            return null;
        }
    }
}
