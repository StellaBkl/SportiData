package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

//import static androidx.core.content.ContextCompat.startActivity;
import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;

public class RecyclerAdapterOmada extends RecyclerView.Adapter<RecyclerAdapterOmada.MyViewHolder> {
    private NotificationManagerCompat notManager;
    private List<Omada> omades = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.omada_item_layout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       Omada omada = omades.get(position);

        holder.Tv1.setText(String.valueOf(omada.getKodikos_omadas()));
        holder.Tv2.setText(omada.getOnoma_omadas());
        holder.Tv3.setText(omada.getOnoma_gipedou());
        holder.Tv4.setText(omada.getPoli());
        holder.Tv5.setText(omada.getXora());
        holder.Tv6.setText(String.valueOf(omada.getSportid()));
        holder.Tv7.setText(String.valueOf(omada.getEtos_idrusis()));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notManager = NotificationManagerCompat.from(v.getContext());
                DialogPlus updateODialog = DialogPlus.newDialog(holder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_update_omada))
                        .create();

                View myvi = updateODialog.getHolderView();


                EditText UOmadaID= myvi.findViewById(R.id.UpOmadaID);
                EditText UOmadaName= myvi.findViewById(R.id.UpOmadaName);
                EditText UOmadaGipedo= myvi.findViewById(R.id.UpOmadaGipedo);
                EditText UOmadaPoli= myvi.findViewById(R.id.UpOmadaPoli);
                EditText UOmadaXora= myvi.findViewById(R.id.UpOmadaXora);
                EditText UOmadaSportID= myvi.findViewById(R.id.UpOmadaSportID);
                EditText UOmadaIdrisi= myvi.findViewById(R.id.UpOmadaIdrisi);
                Button updateOB = myvi.findViewById(R.id.UpdateOBtn);
                Button cancelOB = myvi.findViewById(R.id.CancelOBtn);

                UOmadaID.setText(String.valueOf(omada.getKodikos_omadas()));
                UOmadaName.setText(omada.getOnoma_omadas());
                UOmadaGipedo.setText(omada.getOnoma_gipedou());
                UOmadaPoli.setText(omada.getPoli());
                UOmadaXora.setText(omada.getXora());
                UOmadaSportID.setText(String.valueOf(omada.getSportid()));
                UOmadaIdrisi.setText(String.valueOf(omada.getEtos_idrusis()));

                updateODialog.show();

                updateOB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int var_omadaid = 0;
                        int var_sportid = 0;
                        int var_bd = 0;

                        try {
                            var_omadaid = Integer.parseInt( UOmadaID.getText().toString());
                            var_sportid = Integer.parseInt(UOmadaSportID.getText().toString());
                            var_bd = Integer.parseInt(UOmadaIdrisi.getText().toString());
                        }catch(NumberFormatException ex){
                            System.out.println("Could not parse " + ex);
                        }

                        String var_name = UOmadaName.getText().toString();
                        String var_gipedo = UOmadaGipedo.getText().toString();
                        String var_town = UOmadaPoli.getText().toString();
                        String var_country = UOmadaXora.getText().toString();

                        try{
                            Omada om= new Omada();
                            om.setKodikos_omadas(var_omadaid);
                            om.setOnoma_omadas(var_name);
                            om.setOnoma_gipedou(var_gipedo);
                            om.setPoli(var_town);
                            om.setXora(var_country);
                            om.setEtos_idrusis(var_bd);
                            om.setSportid(var_sportid);

                            MainActivity.myDatabase.myDao().updateOmada(om);
                            //Toast.makeText(v.getContext(),"Η ανανέωση έγινε με επιτυχία",Toast.LENGTH_LONG).show();

                            Omades om1 = new Omades();
                            om1.setId_omadas(var_omadaid);
                            om1.setOnoma_omadas(var_name);

                            MainActivity.db.
                                    collection("Omades").
                                    document("" + var_omadaid).
                                    set(om1).addOnCompleteListener(new OnCompleteListener<Void>() {

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


                cancelOB.setOnClickListener(new View.OnClickListener() {
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
                AlertDialog.Builder Obuilder = new AlertDialog.Builder(holder.Tv1.getContext());
                Obuilder.setTitle("Διαγραφή Ομάδας");
                Obuilder.setMessage("Θέλετε σίγουρα να διαγράψετε αυτό το αρχείο;");

                Obuilder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        int var_Omadaid = omada.getKodikos_omadas();


                        try{
                            Omada om = new Omada();
                            om.setKodikos_omadas(var_Omadaid);

                            MainActivity.myDatabase.myDao().deleteOmada(om);
                           // Toast.makeText(v.getContext(), "Η Ομάδα διαγράφηκε", Toast.LENGTH_LONG).show();

                            Omades om1 = new Omades();
                            om1.setId_omadas(var_Omadaid);

                            MainActivity.db.
                                    collection("Omades").
                                    document(""+ var_Omadaid).
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

                Obuilder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Obuilder.show();

            }
        });  //end of listener delete


        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), GoogleMapsActivity.class));

                String var_town = omada.getPoli();
                Intent intent = new Intent(v.getContext(), GoogleMapsActivity.class);
                intent.putExtra("key", var_town);
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return omades.size();
    }


    public void setOmades(List<Omada> omada){
        this.omades=omada;
        notifyDataSetChanged();

    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Tv1;
        private TextView Tv2;
        private TextView Tv3;
        private TextView Tv4;
        private TextView Tv5;
        private TextView Tv6;
        private TextView Tv7;

         ImageView edit;
         ImageView delete;
         ImageView map;

        public MyViewHolder(View itemView){
            super(itemView);

            Tv1 = itemView.findViewById(R.id.OTvQuery1);
            Tv2 = itemView.findViewById(R.id.OTvQuery2);
            Tv3 = itemView.findViewById(R.id.OTvQuery3);
            Tv4 = itemView.findViewById(R.id.OTvQuery4);
            Tv5 = itemView.findViewById(R.id.OTvQuery5);
            Tv6 = itemView.findViewById(R.id.OTvQuery6);
            Tv7 = itemView.findViewById(R.id.OTvQuery7);

            edit = (ImageView) itemView.findViewById(R.id.OTvUpdate);
            delete = (ImageView) itemView.findViewById(R.id.OTvDelete);
            map = (ImageView) itemView.findViewById(R.id.OTvMaps);

        }

    }

    public void sendOnChannel1(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Διαγραφή Ομάδας")
                .setContentText("Η διαγραφή της ομάδας εγινε με επιτυχία")
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(23,notification);
    }

    public void sendOnChannel2(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Ανανέωση Ομάδας")
                .setContentText("Η ανανέωση της ομάδας εγινε με επιτυχία")
                .setColor(Color.YELLOW)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(24,notification);
    }


}
