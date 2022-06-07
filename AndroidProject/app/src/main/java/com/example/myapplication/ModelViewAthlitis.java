package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelViewAthlitis extends AndroidViewModel {

    private RepositoryAthlitis repositoryA;
    private LiveData<List<Athlitis>> allDataAthlitis;

    public ModelViewAthlitis(@NonNull Application application) {
        super(application);
        repositoryA = new RepositoryAthlitis(application);
        allDataAthlitis = repositoryA.getAllDataAthlitis();
    }

    public void insertAthlitis(Athlitis athlitis){

        repositoryA.insertAthlitis(athlitis);
    }
    public void updateAthlitis(Athlitis athlitis){
        repositoryA.updateAthlitis(athlitis);

    }
    public void daleteAthlitis(Athlitis athlitis){
        repositoryA.daleteAthlitis(athlitis);

    }

    public LiveData<List<Athlitis>> getAllDataAthlitis(){

        return allDataAthlitis;
    }

}
