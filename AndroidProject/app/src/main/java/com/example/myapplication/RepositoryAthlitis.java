package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositoryAthlitis {

    private mydao athlDao;
    private LiveData<List<Athlitis>> allDataAthlitis;

    public RepositoryAthlitis(Application application){
        Database adbase = Database.getInstance(application);
        athlDao = adbase.myDao();
        allDataAthlitis = athlDao.getAthlete();

    }

    public void insertAthlitis(Athlitis athlitis){

        new RepositoryAthlitis.InsertDataAthlitisTask(athlDao).execute(athlitis);
    }
    public void updateAthlitis(Athlitis athlitis){
        new RepositoryAthlitis.UpdateDataAthlitisTask(athlDao).execute(athlitis);

    }
    public void daleteAthlitis(Athlitis athlitis){
        new RepositoryAthlitis.DeleteDataAthlitisTask(athlDao).execute(athlitis);

    }

    public LiveData<List<Athlitis>> getAllDataAthlitis(){

        return allDataAthlitis;
    }

    private static class  InsertDataAthlitisTask extends AsyncTask<Athlitis, Void, Void> {
        private mydao athlDao;

        private InsertDataAthlitisTask(mydao athlitisDao){
            this.athlDao = athlitisDao;
        }
        @Override
        protected Void doInBackground(Athlitis... athlitis) {

            athlDao.addAthlete(athlitis[0]);
            return null;
        }
    }
    private static class  UpdateDataAthlitisTask extends AsyncTask<Athlitis, Void, Void>{
        private mydao athlDao;

        private UpdateDataAthlitisTask(mydao athlitisDao){
            this.athlDao = athlitisDao;
        }
        @Override
        protected Void doInBackground(Athlitis... athlitis) {

            athlDao.updateAthlete(athlitis[0]);
            return null;
        }
    }
    private static class  DeleteDataAthlitisTask extends AsyncTask<Athlitis, Void, Void>{
        private mydao athlDao;

        private DeleteDataAthlitisTask(mydao athlitisDao){
            this.athlDao = athlitisDao;
        }
        @Override
        protected Void doInBackground(Athlitis... athlitis) {

            athlDao.deleteAthlete(athlitis[0]);
            return null;
        }
    }

}
