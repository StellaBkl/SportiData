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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;

public class RecyclerAdapterSport extends RecyclerView.Adapter<RecyclerAdapterSport.MyViewHolder>{
    private NotificationManagerCompat notManager;

    private List<Sport> athlimata = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item_layout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Sport sport = athlimata.get(position);

        holder.Tv1.setText(String.valueOf(sport.getKodikos_sport()));
        holder.Tv2.setText(sport.getOnoma_sport());
        holder.Tv3.setText(sport.getEidos_athlimatos());
        holder.Tv4.setText(sport.getFullo());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notManager = NotificationManagerCompat.from(v.getContext());
                DialogPlus updateODialog = DialogPlus.newDialog(holder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_update_sport))
                        .create();

                View myvi = updateODialog.getHolderView();


                EditText ETUpdateSportID = myvi.findViewById(R.id.UpSportID);
                EditText ETUpdateNameSport = myvi.findViewById(R.id.UpSportName);
                EditText ETUpdateSportType = myvi.findViewById(R.id.UpSportType);
                EditText ETUpdateSportGender = myvi.findViewById(R.id.UpSportGender);
                Button updateSB = myvi.findViewById(R.id.UpdateSBtn);
                Button cancelSB = myvi.findViewById(R.id.CancelSBtn);

                ETUpdateSportID.setText(String.valueOf(sport.getKodikos_sport()));
                ETUpdateNameSport.setText(sport.getOnoma_sport());
                ETUpdateSportType.setText(sport.getEidos_athlimatos());
                ETUpdateSportGender.setText(sport.getFullo());

                updateODialog.show();

                updateSB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int var_sportid = 0;
                        try {
                            var_sportid = Integer.parseInt(ETUpdateSportID.getText().toString());
                        } catch (NumberFormatException ex) {
                            System.out.println("Could not parse " + ex);
                        }
                        String var_name = ETUpdateNameSport.getText().toString();
                        String var_type = ETUpdateSportType.getText().toString();
                        String var_fullo = ETUpdateSportGender.getText().toString();

                        try{
                            Sport spor = new Sport();
                            spor.setKodikos_sport(var_sportid);
                            spor.setOnoma_sport(var_name);
                            spor.setEidos_athlimatos(var_type);
                            spor.setFullo(var_fullo);

                            MainActivity.myDatabase.myDao().updateSport(spor);
                           // Toast.makeText(v.getContext(), "Η ανανέωση έγινε με επιτυχία", Toast.LENGTH_LONG).show();

                            Athlimata spor1 = new Athlimata();
                            spor1.setId_sport(var_sportid);
                            spor1.setOnoma_sport(var_name);

                            MainActivity.db.
                                    collection("Athlimata").
                                    document("" + var_sportid).
                                    set(spor1).addOnCompleteListener(new OnCompleteListener<Void>() {

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

                        }catch(Exception e){
                            String message = e.getMessage();
                            Toast.makeText(v.getContext(),message,Toast.LENGTH_LONG).show();
                        }


                        updateODialog.dismiss();




                    }
                });


                cancelSB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateODialog.dismiss();
                    }
                });

            }
        }); // end of listener edit


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notManager = NotificationManagerCompat.from(v.getContext());
                AlertDialog.Builder Sbuilder = new AlertDialog.Builder(holder.Tv1.getContext());
                Sbuilder.setTitle("Διαγραφή Αθλήματος");
                Sbuilder.setMessage("Θέλετε σίγουρα να διαγράψετε αυτό το αρχείο;");

                Sbuilder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        int var_sportid = sport.getKodikos_sport();


                        try{
                            Sport sp = new Sport();
                            sp.setKodikos_sport(var_sportid);

                            MainActivity.myDatabase.myDao().deleteSport(sp);
                            //Toast.makeText(v.getContext(), "Το Άθλημα διαγράφηκε", Toast.LENGTH_LONG).show();

                            Athlimata sp1 = new Athlimata();
                            sp1.setId_sport(var_sportid);

                            MainActivity.db.
                                    collection("Athlimata").
                                    document(""+ var_sportid).
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

                        }catch(Exception e){
                            String message = e.getMessage();
                            Toast.makeText(v.getContext(),message,Toast.LENGTH_LONG).show();
                        }

                    }
                });

                Sbuilder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Sbuilder.show();

            }
        }); //end of listener delete

    }

    @Override
    public int getItemCount() {
        return athlimata.size();
    }


    public void setAthlimata(List<Sport> sports){
        this.athlimata=sports;
        notifyDataSetChanged();

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Tv1;
        private TextView Tv2;
        private TextView Tv3;
        private TextView Tv4;

        ImageView edit;
        ImageView delete;

        public MyViewHolder(View itemView){
            super(itemView);

            Tv1 = itemView.findViewById(R.id.STvQuery1);
            Tv2 = itemView.findViewById(R.id.STvQuery2);
            Tv3 = itemView.findViewById(R.id.STvQuery3);
            Tv4 = itemView.findViewById(R.id.STvQuery4);


            edit = (ImageView) itemView.findViewById(R.id.STvUpdate);
            delete = (ImageView) itemView.findViewById(R.id.STvDelete);

        }

    }

    public void sendOnChannel1(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Διαγραφή Αθλήματος")
                .setContentText("Η διαγραφή του αθλήματος εγινε με επιτυχία")
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(17,notification);
    }

    public void sendOnChannel2(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Ανανέωση Αθλήματος")
                .setContentText("Η ανανέωση του αθλήματος εγινε με επιτυχία")
                .setColor(Color.YELLOW)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(18,notification);
    }

}
