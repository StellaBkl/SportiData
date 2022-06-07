package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositoryOmada {

    private mydao omadaDao;
    private LiveData<List<Omada>> allDataOmada;

    public RepositoryOmada(Application application){
        Database dbase = Database.getInstance(application);
        omadaDao = dbase.myDao();
        allDataOmada = omadaDao.getOmada();

    }

    public void insertOmada(Omada omada){

        new InsertDataOmadaTask(omadaDao).execute(omada);
    }
    public void updateOmada(Omada omada){
        new UpdateDataOmadaTask(omadaDao).execute(omada);

    }
    public void daleteOmada(Omada omada){
        new DeleteDataOmadaTask(omadaDao).execute(omada);

    }

    public LiveData<List<Omada>> getAllDataOmada(){

        return allDataOmada;
    }

    private static class  InsertDataOmadaTask extends AsyncTask<Omada, Void, Void>{
        private mydao omadaDao;

        private InsertDataOmadaTask(mydao omadaDao){
            this.omadaDao = omadaDao;
        }
        @Override
        protected Void doInBackground(Omada... omadas) {

            omadaDao.addOmada(omadas[0]);
            return null;
        }
    }
    private static class  UpdateDataOmadaTask extends AsyncTask<Omada, Void, Void>{
        private mydao omadaDao;

        private UpdateDataOmadaTask(mydao omadaDao){
            this.omadaDao = omadaDao;
        }
        @Override
        protected Void doInBackground(Omada... omadas) {

            omadaDao.updateOmada(omadas[0]);
            return null;
        }
    }
    private static class  DeleteDataOmadaTask extends AsyncTask<Omada, Void, Void>{
        private mydao omadaDao;

        private DeleteDataOmadaTask(mydao omadaDao){
            this.omadaDao = omadaDao;
        }
        @Override
        protected Void doInBackground(Omada... omadas) {

            omadaDao.deleteOmada(omadas[0]);
            return null;
        }
    }

}
