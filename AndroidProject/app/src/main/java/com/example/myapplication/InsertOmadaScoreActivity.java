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

public class InsertOmadaScoreActivity extends AppCompatActivity {

    EditText OomadaID,OsportID,OresultID,OepidosiID,Oepidosi;
    Button BtnO;
    Toolbar toolbarOS;
    private NotificationManagerCompat notManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_omada_score);

        toolbarOS = (Toolbar) findViewById(R.id.InsertOSToolbar);
        setSupportActionBar(toolbarOS);
        getSupportActionBar().setTitle("Εισαγωγή Score Ομάδας");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notManager = NotificationManagerCompat.from(this);

        OepidosiID = findViewById(R.id.ETInsertOSEpidosiID);
        Oepidosi = findViewById(R.id.ETInsertOSEpidosi);
        OomadaID = findViewById(R.id.ETInsertOSOmadaID);
        OsportID = findViewById(R.id.ETInsertOSAthlimaID);
        OresultID = findViewById(R.id.ETInsertOSAgonaID);

        BtnO = findViewById(R.id.BInsertOmScore);
        BtnO.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                int var_epidosiID = 0;

                try{
                    var_epidosiID = Integer.parseInt(OepidosiID.getText().toString());
                }catch(NumberFormatException ex){
                    System.out.println("Could not parse " + ex);
                }

                String var_omadaID = OomadaID.getText().toString();
                String var_sportID = OsportID.getText().toString();
                String var_resultID = OresultID.getText().toString();
                String var_epidosi = Oepidosi.getText().toString();


                if (var_epidosiID == 0 || var_omadaID.trim().isEmpty() || var_sportID.trim().isEmpty() || var_resultID.trim().isEmpty() || var_epidosi.trim().isEmpty()) {

                    sendOnChannel2(v);
                    //Toast.makeText(getApplicationContext(),"Προσθεστε στοιχεία", Toast.LENGTH_SHORT).show();
                    return;

                }
                DocumentReference var_OmadaId = MainActivity.db.document(("/Omades/") + var_omadaID);
                DocumentReference var_SportId = MainActivity.db.document(("/Athlimata/") + var_sportID);
                DocumentReference var_ResultId = MainActivity.db.document(("/Result/") +  var_resultID);

                try {
                    omades_score oma = new omades_score();
                    oma.setId_omadas(var_OmadaId);
                    oma.setId_sport(var_SportId);
                    oma.setId_result(var_ResultId);
                    oma.setIdepidosi(var_epidosiID);
                    oma.setEpidosi(var_epidosi);

                    /*MainActivity.db.
                            collection("omades_score").
                            add(oma).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                            collection("omades_score").

                            document("" + var_epidosiID).
                            set(oma).addOnCompleteListener(new OnCompleteListener<Void>() {

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
                OomadaID.setText("");
                OsportID.setText("");
                OresultID.setText("");
                OepidosiID.setText("");
                Oepidosi.setText("");
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return false;
    }

    public void sendOnChannel1(View v){
        String message = OomadaID.getText().toString();
        String message1 = Oepidosi.getText().toString();
        String message2 = OsportID.getText().toString();


        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Score Ομάδας")
                .setContentText("Στοιχεία")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Η προσθήκη της ομάδας με id: "+ message+" και επίδοση "+message1+" στο άθλημα με id: "+message2+" έγινε με επιτυχία")
                        .setBigContentTitle("Εισαγωγή Score Ομάδας"))
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(11,notification);
    }

    public void sendOnChannel2(View v){

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Score Ομάδας")
                .setContentText("Πρέπει να πρoσθέσετε στοιχεία")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();


        notManager.notify(12,notification);
    }

}