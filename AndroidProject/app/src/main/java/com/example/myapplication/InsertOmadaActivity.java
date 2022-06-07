package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;

public class InsertOmadaActivity extends AppCompatActivity {
    EditText ETInsertOmadaID,ETInsertOmadaName,ETInsertOnomaGipedou,ETInsertOmadaPoli,ETInsertOmadaXora,ETInsertOmadaSportID,ETInsertOmadaEtosIdrisis;
    Button InsertOBtn;
    Toolbar toolOm;
    private NotificationManagerCompat notManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_omada);

        toolOm = (Toolbar) findViewById(R.id.InsertOToolbar);
        setSupportActionBar(toolOm);
        getSupportActionBar().setTitle("Εισαγωγή Ομάδας");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notManager = NotificationManagerCompat.from(this);


        ETInsertOmadaID = findViewById(R.id.ETInsertOmadaID);
        ETInsertOmadaName = findViewById(R.id.ETInsertOmadaName);
        ETInsertOnomaGipedou = findViewById(R.id.ETInsertOnomaGipedou);
        ETInsertOmadaPoli = findViewById(R.id.ETInsertOmadaPoli);
        ETInsertOmadaXora = findViewById(R.id.ETInsertOmadaXora);
        ETInsertOmadaSportID = findViewById(R.id.ETInsertOmadaSportID);
        ETInsertOmadaEtosIdrisis = findViewById(R.id.ETInsertOmadaEtosIdrisis);
        InsertOBtn = findViewById(R.id.BInsertOmada);

        InsertOBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int var_omadaid = 0;
                int var_sportid = 0;
                int var_idrisi = 0;
                try {
                    var_omadaid = Integer.parseInt(ETInsertOmadaID.getText().toString());
                    var_sportid = Integer.parseInt(ETInsertOmadaSportID.getText().toString());
                    var_idrisi = Integer.parseInt(ETInsertOmadaEtosIdrisis.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }

                String var_name = ETInsertOmadaName.getText().toString();
                String var_gipedo = ETInsertOnomaGipedou.getText().toString();
                String var_poli = ETInsertOmadaPoli.getText().toString();
                String var_xora = ETInsertOmadaXora.getText().toString();


                if ( var_omadaid == 0 || var_sportid == 0 || var_idrisi == 0 || var_name.trim().isEmpty() || var_gipedo.trim().isEmpty() || var_poli.trim().isEmpty() || var_xora.trim().isEmpty()) {

                    sendOnChannel2(v);
                   // Toast.makeText(getApplicationContext(),"Προσθεστε στοιχεία", Toast.LENGTH_SHORT).show();
                    return;

                }



                try{
                    Omada om = new Omada();
                    om.setKodikos_omadas(var_omadaid);
                    om.setOnoma_omadas(var_name);
                    om.setOnoma_gipedou(var_gipedo);
                    om.setPoli(var_poli);
                    om.setXora(var_xora);
                    om.setSportid(var_sportid);
                    om.setEtos_idrusis(var_idrisi);

                    MainActivity.myDatabase.myDao().addOmada(om);
                    sendOnChannel1(v);
                   // Toast.makeText(getApplicationContext(), "Η προσθήκη έγινε με επιτυχία", Toast.LENGTH_LONG).show();

                    Omades om1 = new Omades();
                    om1.setId_omadas(var_omadaid);
                    om1.setOnoma_omadas(var_name);

                    MainActivity.db.
                            collection("Omades").
                            document(""+var_omadaid).
                            set(om1).addOnCompleteListener(new OnCompleteListener<Void>() {

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


                }catch(Exception e){
                    String message = e.getMessage();
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

                ETInsertOmadaID.setText("");
                ETInsertOmadaName.setText("");
                ETInsertOnomaGipedou.setText("");
                ETInsertOmadaPoli.setText("");
                ETInsertOmadaXora.setText("");
                ETInsertOmadaSportID.setText("");
                ETInsertOmadaEtosIdrisis.setText("");
            }

        });




    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return false;
    }

    public void sendOnChannel1(View v){
        String message = ETInsertOmadaID.getText().toString();
        String message1 = ETInsertOmadaName.getText().toString();


        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Ομάδας")
                .setContentText("Στοιχεία")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Η προσθήκη της ομάδας "+message1+" με id: "+message+" έγινε με επιτυχία")
                        .setBigContentTitle("Εισαγωγή Ομάδας"))
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(3,notification);
    }

    public void sendOnChannel2(View v){

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Ομάδας")
                .setContentText("Πρέπει να πρoσθέσετε στοιχεία")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(4,notification);
    }


}