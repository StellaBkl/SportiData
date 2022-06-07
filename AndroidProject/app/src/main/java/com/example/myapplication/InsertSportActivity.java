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

public class InsertSportActivity extends AppCompatActivity {
    EditText ETSportId,ETSportName,ETSportType,ETSportGender;
    Button BInsertSport;
    Toolbar toolbarS;
    private NotificationManagerCompat notManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_sport);

        toolbarS = (Toolbar) findViewById(R.id.InsertSToolbar);
        setSupportActionBar(toolbarS);
        getSupportActionBar().setTitle("Εισαγωγή Αθλήματος");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notManager = NotificationManagerCompat.from(this);

        ETSportId= findViewById(R.id.ETInsertAthlimaID);
        ETSportName= findViewById(R.id.ETInsertAthlimaName);
        ETSportType= findViewById(R.id.ETInsertAthlimaType);
        ETSportGender= findViewById(R.id.ETInsertAthlGender);
        BInsertSport= findViewById(R.id.BInsertSport);
        BInsertSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int var_sportid=0;
                try{
                    var_sportid=Integer.parseInt(ETSportId.getText().toString());
                }
                catch(NumberFormatException ex){
                    System.out.println("Could not parse " + ex);
                }

                String var_sname=ETSportName.getText().toString();
                String var_type=ETSportType.getText().toString();
                String var_Gender=ETSportGender.getText().toString();


                if ( var_sportid == 0 || var_sname.trim().isEmpty() || var_type.trim().isEmpty() || var_Gender.trim().isEmpty()) {

                    sendOnChannel2(v);
                    //Toast.makeText(getApplicationContext(),"Προσθεστε στοιχεία", Toast.LENGTH_SHORT).show();
                    return;

                }


                try{
                    Sport spor = new Sport();
                    spor.setKodikos_sport(var_sportid);
                    spor.setOnoma_sport(var_sname);
                    spor.setEidos_athlimatos(var_type);
                    spor.setFullo(var_Gender);

                    MainActivity.myDatabase.myDao().addSport(spor);
                    sendOnChannel1(v);
                    //Toast.makeText(getApplicationContext(),"Η προσθήκη έγινε με επιτυχία",Toast.LENGTH_LONG).show();

                    Athlimata spor1 = new Athlimata();
                    spor1.setId_sport(var_sportid);
                    spor1.setOnoma_sport(var_sname);

                    MainActivity.db.
                            collection("Athlimata").
                            document(""+var_sportid).
                            set(spor1).addOnCompleteListener(new OnCompleteListener<Void>() {

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

                ETSportName.setText("");
                ETSportId.setText("");
                ETSportType.setText("");
                ETSportGender.setText("");



            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return false;
    }

    public void sendOnChannel1(View v){
        String message = ETSportId.getText().toString();
        String message1 = ETSportName.getText().toString();


        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Αθλήματος")
                .setContentText("Στοιχεία")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Η προσθήκη του αθλήματος "+ message1+" με id: "+message+" έγινε με επιτυχία")
                        .setBigContentTitle("Εισαγωγή Αθλήματος"))
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(5,notification);
    }

    public void sendOnChannel2(View v){

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Αθλήματος")
                .setContentText("Πρέπει να πρoσθέσετε στοιχεία")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(6,notification);
    }
}