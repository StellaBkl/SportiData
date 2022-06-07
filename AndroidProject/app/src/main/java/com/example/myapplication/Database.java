package com.example.myapplication;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities =  {Athlitis.class,Sport.class,Omada.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database instance;
    public abstract mydao myDao();


    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "athletesDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();

        }
       return instance;
    }
    private  static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            //new PopulateDbAsyncTask(instance).execute();
        }

    };
   /* private  static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private mydao odao;
        private PopulateDbAsyncTask(Database db){
            odao = db.myDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //odao.addOmada(new Omada(4,"hgdf","sghfgh","hfgdh","gdhdf",1,1));
            return null;
        }
    }*/
}

