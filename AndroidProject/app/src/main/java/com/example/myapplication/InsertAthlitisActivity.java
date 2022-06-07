package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;
import static com.example.myapplication.AppNotifications.CHANNEL_2_ID;

public class InsertAthlitisActivity extends AppCompatActivity {
    EditText TFAthleteID,TFName,TFSname,TFTown,TFCountry,TFSid,TFBorndate;
    Button SIButton;
    Toolbar toolbarA;
    private NotificationManagerCompat notManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_athlitis);

        toolbarA = (Toolbar) findViewById(R.id.InsertAToolbar);
        setSupportActionBar(toolbarA);
        getSupportActionBar().setTitle("Εισαγωγή Αθλητή");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notManager = NotificationManagerCompat.from(this);

        TFAthleteID = findViewById(R.id.ETInsertAthlitisID);
        TFName = findViewById(R.id.ETInsertAthlitisFName);
        TFSname  = findViewById(R.id.ETInsertAthlitisLName);
        TFTown = findViewById(R.id.ETInsertAthlitisPoli);
        TFCountry = findViewById(R.id.ETInsertAthlitisXora);
        TFSid = findViewById(R.id.ETInsertAthlitisSportID);
        TFBorndate = findViewById(R.id.ETInsertAthlitisEtosGen);
        SIButton = findViewById(R.id.BInsertAthlitis);
        SIButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){



                int var_athleteid = 0;
                int var_sid = 0;
                int var_abd = 0;
                try {
                    var_athleteid = Integer.parseInt(TFAthleteID.getText().toString());
                    var_sid = Integer.parseInt(TFSid.getText().toString());
                    var_abd = Integer.parseInt(TFBorndate.getText().toString());
                }catch(NumberFormatException ex){
                    System.out.println("Could not parse " + ex);
                }
                String var_name = TFName.getText().toString();
                String var_sname = TFSname.getText().toString();
                String var_atown = TFTown.getText().toString();
                String var_acountry = TFCountry.getText().toString();


                if ( var_athleteid == 0 || var_sid == 0 || var_abd == 0 || var_name.trim().isEmpty() || var_sname.trim().isEmpty() || var_atown.trim().isEmpty() || var_acountry.trim().isEmpty()) {

                    sendOnChannel2(v);
                   // Toast.makeText(getApplicationContext(),"Προσθεστε στοιχεία", Toast.LENGTH_SHORT).show();
                    return;

                }


                try {
                    Athlitis athl = new Athlitis();
                    athl.setAid(var_athleteid);
                    athl.setAname(var_name);
                    athl.setAsurname(var_sname);
                    athl.setAtown(var_atown);
                    athl.setAxora(var_acountry);
                    athl.setEtosGenissis(var_abd);
                    athl.setSid(var_sid);

                    MainActivity.myDatabase.myDao().addAthlete(athl);
                    sendOnChannel1(v);
                    //Toast.makeText(getApplicationContext(), "Η προσθήκη έγινε με επιτυχία", Toast.LENGTH_LONG).show();

                    Athlites athl1 = new Athlites();
                    athl1.setId_athliti(var_athleteid);
                    athl1.setOnoma_athliti(var_name);
                    athl1.setEpitheto_athliti(var_sname);

                    MainActivity.db.
                            collection("Athlites").
                            document(""+var_athleteid).
                            set(athl1).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {



                           // Toast.makeText(getApplicationContext(), "Η προσθήκη έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                        }
                    })

                            .addOnFailureListener(new OnFailureListener() {

                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    Toast.makeText(getApplicationContext(), "Η προσθήκη δεν έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                                }

                            });

                }catch(Exception e){
                    String message = e.getMessage();
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

                TFName.setText("");
                TFSname.setText("");
                TFTown.setText("");
                TFCountry.setText("");
                TFAthleteID.setText("");
                TFSid.setText("");
                TFBorndate.setText("");

            }

        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return false;
    }


    public void sendOnChannel1(View v){
        String message = TFName.getText().toString();
        String message1 = TFSname.getText().toString();


        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Αθλητή")
                .setContentText("Στοιχεια")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Η προσθήκη του αθλητή "+ message+" "+message1+" έγινε με επιτυχία")
                        .setBigContentTitle("Εισαγωγή Αθλητή"))
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(1,notification);
    }

    public void sendOnChannel2(View v){

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Αθλητή")
                .setContentText("Πρέπει να πρoσθέσετε στοιχεία")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(2,notification);
    }




}