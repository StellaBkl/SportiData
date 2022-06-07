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

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;
import static com.example.myapplication.R.id.UpdateCountryResult;
import static com.example.myapplication.R.id.UpdateDateResult;
import static com.example.myapplication.R.id.UpdateOnomaSResult;
import static com.example.myapplication.R.id.UpdatePoliResult;

public class RecyclerAdapterOmadaScore extends FirestoreRecyclerAdapter<omades_score,RecyclerAdapterOmadaScore.MyViewHolder>{

    private NotificationManagerCompat notManager;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerAdapterOmadaScore(@NonNull FirestoreRecyclerOptions<omades_score> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull omades_score omades_scores) {

        myViewHolder.TvQ1.setText(String.valueOf(omades_scores.getIdepidosi()));
        myViewHolder.TvQ2.setText(omades_scores.getEpidosi());
        myViewHolder.TvQ3.setText(omades_scores.getId_omadas().getPath().substring(7));
        myViewHolder.TvQ4.setText(omades_scores.getId_sport().getPath().substring(10));
        myViewHolder.TvQ5.setText(omades_scores.getId_result().getPath().substring(7));


        myViewHolder.edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                notManager = NotificationManagerCompat.from(v.getContext());
                DialogPlus updateDialog = DialogPlus.newDialog(myViewHolder.TvQ1.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_update_omades_score))
                        .create();
                /* Dialog updateDialog = new Dialog(myViewHolder.TvQ1.getContext());
                updateDialog.setContentView(R.layout.dialog_update_result);*/


                View myv = updateDialog.getHolderView();

                EditText id_epidosi = myv.findViewById(R.id.UpEpidosiID);
                EditText epidosi = myv.findViewById(R.id.UpEpidosi);
                EditText id_omadas = myv.findViewById(R.id.UpOmadesID);
                EditText id_sport = myv.findViewById(R.id.UpAthlimaID);
                EditText id_result = myv.findViewById(R.id.UpAgonaID);

                Button updateOSB = myv.findViewById(R.id.UpdateOSBtn);
                Button canceOSlB = myv.findViewById(R.id.CancelOSBtn);

                id_epidosi.setText(String.valueOf(omades_scores.getIdepidosi()));
                epidosi.setText(omades_scores.getEpidosi());
                id_omadas.setText(omades_scores.getId_omadas().getPath().substring(7));
                id_sport.setText(omades_scores.getId_sport().getPath().substring(10));
                id_result.setText(omades_scores.getId_result().getPath().substring(7));

                updateDialog.show();

                updateOSB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int var_idepidosi = 0;

                        try {
                            var_idepidosi = Integer.parseInt(id_epidosi.getText().toString());
                        } catch (NumberFormatException ex) {
                            System.out.println("Could not parse " + ex);
                        }


                        int var_omadaid = Integer.parseInt(id_omadas.getText().toString());
                        DocumentReference var_Omadaid  = MainActivity.db.document(("/Omades/") +var_omadaid);

                        int var_sportID = Integer.parseInt(id_sport.getText().toString());
                        DocumentReference var_SporId = MainActivity.db.document(("/Athlimata/") + var_sportID);

                        int var_resultid = Integer.parseInt(id_result.getText().toString());
                        DocumentReference var_RESId = MainActivity.db.document(("/Result/") + var_resultid);

                        String var_epidosi = epidosi.getText().toString();


                        try {
                            omades_score sc = new omades_score();
                            sc.setId_omadas(var_Omadaid );
                            sc.setId_sport(var_SporId);
                            sc.setId_result(var_RESId);
                            sc.setIdepidosi(var_idepidosi);
                            sc.setEpidosi(var_epidosi);

                            MainActivity.db.
                                    collection("omades_score").

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

                canceOSlB.setOnClickListener(new View.OnClickListener() {
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
                dbuilder.setTitle("Διαγραφή Score Ομάδων");
                dbuilder.setMessage("Θέλετε σίγουρα να διαγράψετε αυτό το αρχείο;");

                dbuilder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            int var_epidosiID = omades_scores.getIdepidosi();


                            omades_score os= new omades_score();
                            os.setIdepidosi(var_epidosiID);


                            MainActivity.db.
                                    collection("omades_score").

                                    document("" + var_epidosiID).
                                    delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override

                                public void onComplete(@NonNull Task<Void> task) {
                                    sendOnChannel1(v);
                                    //Toast.makeText(v.getContext(), "Η  διαγραφή έγινε με επιτυχία", Toast.LENGTH_LONG).show();
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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.omada_score_item_layout,parent,false);

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

            TvQ1 = itemView.findViewById(R.id.OSTvQuery1);
            TvQ2 = itemView.findViewById(R.id.OSTvQuery2);
            TvQ3 = itemView.findViewById(R.id.OSTvQuery3);
            TvQ4 = itemView.findViewById(R.id.OSTvQuery4);
            TvQ5 = itemView.findViewById(R.id.OSTvQuery5);

            edit = (ImageView) itemView.findViewById(R.id.OSTvUpdate);
            delete = (ImageView) itemView.findViewById(R.id.OSTvDelete);


        }

    }


    public void sendOnChannel1(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Διαγραφή Score Ομάδας")
                .setContentText("Η διαγραφή του score της ομάδας εγινε με επιτυχία")
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(15,notification);
    }

    public void sendOnChannel2(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Ανανέωση Score Ομάδας")
                .setContentText("Η ανανέωση του score της ομάδας εγινε με επιτυχία")
                .setColor(Color.YELLOW)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(16,notification);
    }


}
