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
import com.google.firebase.firestore.DocumentReference;

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;

public class InsertAthlitisScoreActivity extends AppCompatActivity {

    EditText athlID,sportID,resultID,epidosiID,epidosi;
    Button BtnA;

    Toolbar toolbarAS;
    private NotificationManagerCompat notManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_athlitis_score);

        toolbarAS = (Toolbar) findViewById(R.id.InsertASToolbar);
        setSupportActionBar(toolbarAS);
        getSupportActionBar().setTitle("Εισαγωγή Score Αθλητή");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notManager = NotificationManagerCompat.from(this);

        epidosiID = findViewById(R.id.ETInsertASEpidosiID);
        epidosi = findViewById(R.id.ETInsertASEpidosi);
        athlID = findViewById(R.id.ETInsertASAthlitisID);
        sportID= findViewById(R.id.ETInsertASAthlimaID);
        resultID = findViewById(R.id.ETInsertASAgonaID);

        BtnA = findViewById(R.id.BInsertAthlScore);
        BtnA.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                int var_epidosiID = 0;

                try{
                    var_epidosiID = Integer.parseInt(epidosiID.getText().toString());
                }catch(NumberFormatException ex){
                    System.out.println("Could not parse " + ex);
                }

                String var_atlhid = athlID.getText().toString();
                String var_sportid = sportID.getText().toString();
                String var_resultid = resultID.getText().toString();
                String var_epidosi = epidosi.getText().toString();

                if (var_epidosiID == 0 || var_atlhid.trim().isEmpty() || var_sportid.trim().isEmpty() || var_resultid.trim().isEmpty() || var_epidosi.trim().isEmpty()) {

                    sendOnChannel2(v);
                    //Toast.makeText(getApplicationContext(),"Προσθεστε στοιχεία", Toast.LENGTH_SHORT).show();
                    return;

                }
                DocumentReference var_AthId = MainActivity.db.document(("/Athlites/") + var_atlhid);
                DocumentReference var_SporId = MainActivity.db.document(("/Athlimata/") + var_sportid);
                DocumentReference var_RESId = MainActivity.db.document(("/Result/") + var_resultid);

                try {
                    Athlitis_score sc = new Athlitis_score();
                    sc.setId_athliti(var_AthId);
                    sc.setId_sport(var_SporId);
                    sc.setId_result(var_RESId);
                    sc.setId_epidosi(var_epidosiID);
                    sc.setEpidosi(var_epidosi);

                   /* MainActivity.db.
                            collection("Athlitis_score").
                            add(sc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity(), "Η προσθήκη έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                                }


                            })
                            .addOnFailureListener((e) -> {
                                Toast.makeText(getActivity(), "Η προσθήκη δεν έγινε με επιτυχία", Toast.LENGTH_LONG).show();

                            });*/
                    sendOnChannel1(v);
                    MainActivity.db.
                            collection("Athlitis_score").

                            document("" + var_epidosiID).
                            set(sc).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override

                        public void onComplete(@NonNull Task<Void> task) {


                            //Toast.makeText(getApplicationContext(), "Η προσθήκη έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Η προσθήκη δεν έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                                }

                            });


                } catch (Exception ex) {
                    String message = ex.getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                athlID.setText("");
                sportID.setText("");
                resultID.setText("");
                epidosiID.setText("");
                epidosi.setText("");
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return false;
    }

    public void sendOnChannel1(View v){
        String message = athlID.getText().toString();
        String message1 = epidosi.getText().toString();
        String message2 = sportID.getText().toString();


        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Score Αθλητή")
                .setContentText("Στοιχεία")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Η προσθήκη του αθλητή με id: "+ message+" και επίδοση "+message1+" στο άθλημα με id: "+message2+" έγινε με επιτυχία")
                        .setBigContentTitle("Εισαγωγή Score Αθλητή"))
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(9,notification);
    }

    public void sendOnChannel2(View v){

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Score Αθλητή")
                .setContentText("Πρέπει να πρoσθέσετε στοιχεία")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(10,notification);
    }

}