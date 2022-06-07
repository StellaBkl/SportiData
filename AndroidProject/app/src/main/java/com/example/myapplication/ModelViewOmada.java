package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelViewOmada extends AndroidViewModel {
    private RepositoryOmada repositoryO;
    private LiveData<List<Omada>> allDataOmada;

    public ModelViewOmada(@NonNull Application application) {
        super(application);
        repositoryO = new RepositoryOmada(application);
        allDataOmada = repositoryO.getAllDataOmada();
    }

    public void insertOmada(Omada omada){

       repositoryO.insertOmada(omada);
    }
    public void updateOmada(Omada omada){
        repositoryO.updateOmada(omada);

    }
    public void daleteOmada(Omada omada){
        repositoryO.daleteOmada(omada);

    }

    public LiveData<List<Omada>> getAllDataOmada(){

        return allDataOmada;
    }
}
