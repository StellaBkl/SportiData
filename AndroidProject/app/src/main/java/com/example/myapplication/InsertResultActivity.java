package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;

public class InsertResultActivity extends AppCompatActivity {
    EditText TFcompID,TFcompDate,TFcompCountry,TFcompTown,TFSname;
    Button BtnComp;
    Toolbar toolb;
    private NotificationManagerCompat notManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_result);

        toolb = (Toolbar) findViewById(R.id.InsertToolbar);
        setSupportActionBar(toolb);
        getSupportActionBar().setTitle("Εισαγωγή Αγώνα");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notManager = NotificationManagerCompat.from(this);

        TFcompID = findViewById(R.id.editTextIdCompetition);
        TFcompDate = findViewById(R.id.editTextDateCompetition);
        TFcompCountry = findViewById(R.id.editTextCountryCompetition);
        TFcompTown = findViewById(R.id.editTextPoliCompetition);
        TFSname = findViewById(R.id.editTextOnomaSportCompetition);
        BtnComp = findViewById(R.id.buttonCompetition);
        BtnComp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                int var_CompID = 0;

                try {
                    var_CompID = Integer.parseInt(TFcompID.getText().toString());

                }catch(NumberFormatException ex){
                    System.out.println("Could not parse " + ex);
                }

                String var_Cdate = TFcompDate.getText().toString();
                String var_Cxora = TFcompCountry.getText().toString();
                String var_Cpoli = TFcompTown.getText().toString();
                String var_Sname = TFSname.getText().toString();


                if (var_Cdate.trim().isEmpty() || var_Cxora.trim().isEmpty() || var_Cpoli.trim().isEmpty() || var_Sname.trim().isEmpty() ||  var_CompID == 0) {

                    sendOnChannel2(v);
                    //Toast.makeText(getApplicationContext(),"Προσθεστε στοιχεία", Toast.LENGTH_SHORT).show();
                    return;

                }

                try {
                    Result res = new Result();
                    res.setId_result(var_CompID);
                    res.setDate_competition(var_Cdate);
                    res.setCountry_competition(var_Cxora);
                    res.setPoli_competition(var_Cpoli);
                    res.setOnoma_sport(var_Sname);
                    sendOnChannel1(v);
                    MainActivity.db.
                            collection("Result").
                            document(""+var_CompID).
                            set(res).addOnCompleteListener(new OnCompleteListener<Void>() {

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

                TFcompID.setText("");
                TFcompDate.setText("");
                TFcompCountry.setText("");
                TFcompTown.setText("");
                TFSname.setText("");
            }

        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.button,menu);

        return super.onCreateOptionsMenu(menu);
    }*/

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){
           case android.R.id.home:
               NavUtils.navigateUpFromSameTask(this);
               return true;
           case R.id.action_settings:
               return true;
           default: return super.onOptionsItemSelected(item);
        }


    }*/
   @Override
   public boolean onSupportNavigateUp() {
       finish();

       return false;
   }

    public void sendOnChannel1(View v){
        String message = TFcompID.getText().toString();
        String message1 = TFcompDate.getText().toString();
        String message2 = TFSname.getText().toString();


        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Αγώνα")
                .setContentText("Στοιχεία")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Η προσθήκη αγώνα με id: "+ message+" στις "+message1+" του αθλήματος "+message2+" έγινε με επιτυχία")
                        .setBigContentTitle("Εισαγωγή Αγώνα"))
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(7,notification);
    }

    public void sendOnChannel2(View v){

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Εισαγωγή Αγώνα")
                .setContentText("Πρέπει να πρoσθέσετε στοιχεία")
                .setColor(Color.BLUE)
                .setGroup("Eisagogh")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();


        notManager.notify(8,notification);
    }
}