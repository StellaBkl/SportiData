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

public class RecyclerAdapterAthlitis extends RecyclerView.Adapter<RecyclerAdapterAthlitis.MyViewHolder>{
    private NotificationManagerCompat notManager;
    private List<Athlitis> athlites = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.athlitis_item_layout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Athlitis athlitis = athlites.get(position);

        holder.TvA1.setText(String.valueOf(athlitis.getAid()));
        holder.TvA2.setText(athlitis.getAname());
        holder.TvA3.setText(athlitis.getAsurname());
        holder.TvA4.setText(athlitis.getAtown());
        holder.TvA5.setText(athlitis.getAxora());
        holder.TvA6.setText(String.valueOf(athlitis.getSid()));
        holder.TvA7.setText(String.valueOf(athlitis.getEtosGenissis()));



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notManager = NotificationManagerCompat.from(v.getContext());
                DialogPlus updateODialog = DialogPlus.newDialog(holder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_update_athlitis))
                        .create();

                View myvi = updateODialog.getHolderView();


                EditText UTFAthleteID = myvi.findViewById(R.id.UpAthlitisID);
                EditText UTFName = myvi.findViewById(R.id.UpAthlitisFName);
                EditText UTFSname = myvi.findViewById(R.id.UpAthlitisLName);
                EditText UTFTown = myvi.findViewById(R.id.UpAthlitisPoli);
                EditText UTFCountry = myvi.findViewById(R.id.UpAthlitisXora);
                EditText UTFSID = myvi.findViewById(R.id.UpAthlitisSportID);
                EditText UTFBorndate = myvi.findViewById(R.id.UpAthlitisEtosGen);
                Button updateAB = myvi.findViewById(R.id.UpdateABtn);
                Button cancelAB = myvi.findViewById(R.id.CancelABtn);

                UTFAthleteID.setText(String.valueOf(athlitis.getAid()));
                UTFName.setText(athlitis.getAname());
                UTFSname.setText(athlitis.getAsurname());
                UTFTown.setText(athlitis.getAtown());
                UTFCountry.setText(athlitis.getAxora());
                UTFSID.setText(String.valueOf(athlitis.getSid()));
                UTFBorndate.setText(String.valueOf(athlitis.getEtosGenissis()));

                updateODialog.show();

                updateAB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int var_athleteid = 0;
                        int var_sid = 0;
                        int var_abd = 0;

                        try {
                            var_athleteid = Integer.parseInt(UTFAthleteID.getText().toString());
                            var_sid = Integer.parseInt(UTFSID.getText().toString());
                            var_abd = Integer.parseInt(UTFBorndate.getText().toString());
                        }catch(NumberFormatException ex){
                            System.out.println("Could not parse " + ex);
                        }

                        String var_name = UTFName.getText().toString();
                        String var_sname = UTFSname.getText().toString();
                        String var_atown = UTFTown.getText().toString();
                        String var_acountry = UTFCountry.getText().toString();

                        try{
                            Athlitis athl = new Athlitis();
                            athl.setAid(var_athleteid);
                            athl.setAname(var_name);
                            athl.setAsurname(var_sname);
                            athl.setAtown(var_atown);
                            athl.setAxora(var_acountry);
                            athl.setEtosGenissis(var_abd);
                            athl.setSid(var_sid);

                            MainActivity.myDatabase.myDao().updateAthlete(athl);
                           // Toast.makeText(v.getContext(),"Η ανανέωση έγινε με επιτυχία",Toast.LENGTH_LONG).show();

                            Athlites athl1 = new Athlites();
                            athl1.setId_athliti(var_athleteid);
                            athl1.setOnoma_athliti(var_name);
                            athl1.setEpitheto_athliti(var_sname);

                            MainActivity.db.
                                    collection("Athlites").
                                    document("" + var_athleteid).
                                    set(athl1).addOnCompleteListener(new OnCompleteListener<Void>() {

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


                cancelAB.setOnClickListener(new View.OnClickListener() {
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
                AlertDialog.Builder Abuilder = new AlertDialog.Builder(holder.TvA1.getContext());
                Abuilder.setTitle("Διαγραφή Αθλητή");
                Abuilder.setMessage("Θέλετε σίγουρα να διαγράψετε αυτό το αρχείο;");

                Abuilder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        int var_athlid = athlitis.getAid();


                        try{
                            Athlitis athl = new Athlitis();
                            athl.setAid(var_athlid);

                            MainActivity.myDatabase.myDao().deleteAthlete(athl);
                            //Toast.makeText(v.getContext(), "Ο Αθλητής διαγράφηκε", Toast.LENGTH_LONG).show();

                            Athlites athl1 = new Athlites();
                            athl1.setId_athliti(var_athlid);

                            MainActivity.db.
                                    collection("Athlites").
                                    document(""+ var_athlid).
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

                Abuilder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Abuilder.show();

            }
        }); //end of listener delete

    }

    @Override
    public int getItemCount() {
        return athlites.size();
    }


    public void setAthlites(List<Athlitis> athl){
        this.athlites=athl;
        notifyDataSetChanged();

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TvA1;
        private TextView TvA2;
        private TextView TvA3;
        private TextView TvA4;
        private TextView TvA5;
        private TextView TvA6;
        private TextView TvA7;

        ImageView edit;
        ImageView delete;

        public MyViewHolder(View itemView){
            super(itemView);

            TvA1 = itemView.findViewById(R.id.ATvQuery1);
            TvA2 = itemView.findViewById(R.id.ATvQuery2);
            TvA3 = itemView.findViewById(R.id.ATvQuery3);
            TvA4 = itemView.findViewById(R.id.ATvQuery4);
            TvA5 = itemView.findViewById(R.id.ATvQuery5);
            TvA6 = itemView.findViewById(R.id.ATvQuery6);
            TvA7 = itemView.findViewById(R.id.ATvQuery7);

            edit = (ImageView) itemView.findViewById(R.id.ATvUpdate);
            delete = (ImageView) itemView.findViewById(R.id.ATvDelete);

        }

    }

    public void sendOnChannel1(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Διαγραφή Αθλητή")
                .setContentText("Η διαγραφή του αθλητή εγινε με επιτυχία")
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(19,notification);
    }

    public void sendOnChannel2(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Ανανέωση Αθλητή")
                .setContentText("Η ανανέωση του αθλητή εγινε με επιτυχία")
                .setColor(Color.YELLOW)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(20,notification);
    }

}
