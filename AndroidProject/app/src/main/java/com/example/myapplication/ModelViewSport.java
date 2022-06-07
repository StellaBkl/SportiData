package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelViewSport extends AndroidViewModel {

    private RepositorySport repositoryS;
    private LiveData<List<Sport>> allDataSport;

    public ModelViewSport(@NonNull Application application) {
        super(application);
        repositoryS = new RepositorySport(application);
        allDataSport = repositoryS.getAllDataSport();
    }

    public void insertSport(Sport sport){

        repositoryS.insertSport(sport);
    }
    public void updateSport(Sport sport){
        repositoryS.updateSport(sport);

    }
    public void daleteSport(Sport sport){
        repositoryS.daleteSport(sport);

    }

    public LiveData<List<Sport>> getAllDataSport(){

        return allDataSport;
    }

}
