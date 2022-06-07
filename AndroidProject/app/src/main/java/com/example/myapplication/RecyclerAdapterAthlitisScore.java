package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.lang.ref.Reference;

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;
import static com.example.myapplication.R.id.UpdateCountryResult;
import static com.example.myapplication.R.id.UpdateDateResult;
import static com.example.myapplication.R.id.UpdateOnomaSResult;
import static com.example.myapplication.R.id.UpdatePoliResult;

public class RecyclerAdapterAthlitisScore extends FirestoreRecyclerAdapter<Athlitis_score,RecyclerAdapterAthlitisScore.MyViewHolder>{

    private NotificationManagerCompat notManager;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerAdapterAthlitisScore(@NonNull FirestoreRecyclerOptions<Athlitis_score> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull Athlitis_score athlitis_score) {

        myViewHolder.TvQ1.setText(String.valueOf(athlitis_score.getId_epidosi()));
        myViewHolder.TvQ2.setText(athlitis_score.getEpidosi());
        myViewHolder.TvQ3.setText(athlitis_score.getId_athliti().getPath().substring(9));
        myViewHolder.TvQ4.setText(athlitis_score.getId_sport().getPath().substring(10));
        myViewHolder.TvQ5.setText(athlitis_score.getId_result().getPath().substring(7));


        myViewHolder.edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                notManager = NotificationManagerCompat.from(v.getContext());
                DialogPlus updateDialog = DialogPlus.newDialog(myViewHolder.TvQ1.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_update_athlitis_score))
                        .create();
                /* Dialog updateDialog = new Dialog(myViewHolder.TvQ1.getContext());
                updateDialog.setContentView(R.layout.dialog_update_result);*/


                View myv = updateDialog.getHolderView();

                EditText id_epidosi = myv.findViewById(R.id.UpAEpidosiID);
                EditText epidosi = myv.findViewById(R.id.UpAEpidosi);
                EditText id_athliti = myv.findViewById(R.id.UpAthlitiID);
                EditText id_sport = myv.findViewById(R.id.UpAAthlimaID);
                EditText id_result = myv.findViewById(R.id.UpAAgonaID);

                Button updateASB = myv.findViewById(R.id.UpdateASBtn);
                Button cancelASB = myv.findViewById(R.id.CancelASBtn);

                id_epidosi.setText(String.valueOf(athlitis_score.getId_epidosi()));
                epidosi.setText(athlitis_score.getEpidosi());
                id_athliti.setText(athlitis_score.getId_athliti().getPath().substring(9));
                id_sport.setText(athlitis_score.getId_sport().getPath().substring(10));
                id_result.setText(athlitis_score.getId_result().getPath().substring(7));

                updateDialog.show();

                updateASB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int var_idepidosi = 0;

                        try{
                            var_idepidosi = Integer.parseInt(id_epidosi.getText().toString());
                        }catch(NumberFormatException ex){
                            System.out.println("Could not parse " + ex);
                        }

                        int var_athlitiid = Integer.parseInt(id_athliti.getText().toString());
                        DocumentReference var_Athlitiid  = MainActivity.db.document(("/Athlites/") +var_athlitiid );

                        int var_sportID = Integer.parseInt(id_sport.getText().toString());
                        DocumentReference var_SporId = MainActivity.db.document(("/Athlimata/") + var_sportID);

                        int var_resultid = Integer.parseInt(id_result.getText().toString());
                        DocumentReference var_RESId = MainActivity.db.document(("/Result/") + var_resultid);

                        String var_epidosi = epidosi.getText().toString();


                        try {
                            Athlitis_score sc = new Athlitis_score();
                            sc.setId_athliti(var_Athlitiid);
                            sc.setId_sport(var_SporId);
                            sc.setId_result(var_RESId);
                            sc.setId_epidosi(var_idepidosi);
                            sc.setEpidosi(var_epidosi);

                            MainActivity.db.
                                    collection("Athlitis_score").

                                    document("" +var_idepidosi).
                                    set(sc).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override

                                public void onComplete(@NonNull Task<Void> task) {
                                    sendOnChannel2(v);
                                    //Toast.makeText(v.getContext(), "Η ανανέωση έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(v.getContext(), "Η ανανέωση δεν έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                                        }

                                    });

                        } catch (Exception e) {

                            String message = e.getMessage();
                            Toast.makeText(v.getContext(), message, Toast.LENGTH_LONG).show();
                        }

                        updateDialog.dismiss();


                    }
                });

                cancelASB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDialog.dismiss();
                    }
                });

            }

        }); //end onClickListener edit


        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notManager = NotificationManagerCompat.from(v.getContext());
                AlertDialog.Builder dbuilder = new AlertDialog.Builder(myViewHolder.delete.getContext());
                dbuilder.setTitle("Διαγραφή Score Αθλητών");
                dbuilder.setMessage("Θέλετε σίγουρα να διαγράψετε αυτό το αρχείο;");

                dbuilder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            int var_epidosiID = athlitis_score.getId_epidosi();


                            Athlitis_score as = new Athlitis_score();
                            as.setId_epidosi(var_epidosiID);


                            MainActivity.db.
                                    collection("Athlitis_score").

                                    document("" + var_epidosiID).
                                    delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override

                                public void onComplete(@NonNull Task<Void> task) {
                                    sendOnChannel1(v);
                                   // Toast.makeText(v.getContext(), "Η  διαγραφή έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                                }
                            })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(v.getContext(), "Η διαγραφή δεν έγινε με επιτυχία", Toast.LENGTH_LONG).show();
                                        }

                                    });

                        } catch (Exception e) {

                            String message = e.getMessage();
                            Toast.makeText(v.getContext(), message, Toast.LENGTH_LONG).show();
                        }

                    }
                });

                dbuilder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dbuilder.show();

            }
        }); //end onClickListener delete

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.athlitis_score_item_layout,parent,false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TvQ1;
        TextView TvQ2;
        TextView TvQ3;
        TextView TvQ4;
        TextView TvQ5;

        ImageView edit;
        ImageView delete;

        public MyViewHolder(View itemView){
            super(itemView);

            TvQ1 = itemView.findViewById(R.id.ASTvQuery1);
            TvQ2 = itemView.findViewById(R.id.ASTvQuery2);
            TvQ3 = itemView.findViewById(R.id.ASTvQuery3);
            TvQ4 = itemView.findViewById(R.id.ASTvQuery4);
            TvQ5 = itemView.findViewById(R.id.ASTvQuery5);

            edit = (ImageView) itemView.findViewById(R.id.ASTvUpdate);
            delete = (ImageView) itemView.findViewById(R.id.ASTvDelete);


        }

    }

    public void sendOnChannel1(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Διαγραφή Score Αθλητή")
                .setContentText("Η διαγραφή του score του Αθλητή εγινε με επιτυχία")
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(21,notification);
    }

    public void sendOnChannel2(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Ανανέωση Αθλητή")
                .setContentText("Η ανανέωση του score του Αθλητή εγινε με επιτυχία")
                .setColor(Color.YELLOW)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(22,notification);
    }


}
