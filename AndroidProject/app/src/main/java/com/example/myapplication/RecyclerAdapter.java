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
import com.google.firebase.firestore.DocumentSnapshot;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import static com.example.myapplication.AppNotifications.CHANNEL_1_ID;
import static com.example.myapplication.R.id.UpdateCountryResult;
import static com.example.myapplication.R.id.UpdateDateResult;
import static com.example.myapplication.R.id.UpdateOnomaSResult;
import static com.example.myapplication.R.id.UpdatePoliResult;

public class RecyclerAdapter extends FirestoreRecyclerAdapter<Result,RecyclerAdapter.MyViewHolder>{
    private NotificationManagerCompat notManager;

private OnItemClickListener listener;
    public RecyclerAdapter(@NonNull FirestoreRecyclerOptions<Result> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item_layout,parent,false);


        return new MyViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull Result result) {

        myViewHolder.TvQ1.setText(String.valueOf(result.getId_result()));
        myViewHolder.TvQ2.setText(result.getDate_competition());
        myViewHolder.TvQ3.setText(result.getCountry_competition());
        myViewHolder.TvQ4.setText(result.getPoli_competition());
        myViewHolder.TvQ5.setText(result.getOnoma_sport());

        myViewHolder.edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                notManager = NotificationManagerCompat.from(v.getContext());
               DialogPlus updateDialog = DialogPlus.newDialog(myViewHolder.TvQ1.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_update_result))
                        .create();
                /* Dialog updateDialog = new Dialog(myViewHolder.TvQ1.getContext());
                updateDialog.setContentView(R.layout.dialog_update_result);*/


                View myv = updateDialog.getHolderView();

                EditText rid = myv.findViewById(R.id.UpdateIdResult);
                EditText rdate = myv.findViewById(UpdateDateResult);
                EditText rcountry = myv.findViewById(UpdateCountryResult);
                EditText rtown = myv.findViewById(UpdatePoliResult);
                EditText rsname = myv.findViewById(UpdateOnomaSResult);
                Button updateB = myv.findViewById(R.id.UpdateRBtn);
                Button cancelB = myv.findViewById(R.id.CancelRBtn);

                rid.setText(String.valueOf(result.getId_result()));
                rdate.setText(result.getDate_competition());
                rcountry.setText(result.getCountry_competition());
                rtown.setText(result.getPoli_competition());
                rsname.setText(result.getOnoma_sport());

                updateDialog.show();

                updateB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int var_idresult = 0;

                        try {
                            var_idresult = Integer.parseInt(rid.getText().toString());
                        } catch (NumberFormatException ex) {
                            System.out.println("Could not parse " + ex);
                        }

                        String var_date = rdate.getText().toString();
                        String var_country = rcountry.getText().toString();
                        String var_town = rtown.getText().toString();
                        String var_sname = rsname.getText().toString();


                        try {
                            Result res = new Result();
                            res.setId_result(var_idresult);
                            res.setDate_competition(var_date);
                            res.setCountry_competition(var_country);
                            res.setPoli_competition(var_town);
                            res.setOnoma_sport(var_sname);



                            MainActivity.db.
                                    collection("Result").

                                    document("" + var_idresult).
                                    set(res).addOnCompleteListener(new OnCompleteListener<Void>() {

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

                cancelB.setOnClickListener(new View.OnClickListener() {
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
                dbuilder.setTitle("Διαγραφή Αγώνα");
                dbuilder.setMessage("Θέλετε σίγουρα να διαγράψετε αυτό το αρχείο;");

                dbuilder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            int var_Rid = result.getId_result();


                            Result res = new Result();
                            res.setId_result(var_Rid);


                            MainActivity.db.
                                    collection("Result").

                                    document("" + var_Rid).
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

            TvQ1 = itemView.findViewById(R.id.TvQuery1);
            TvQ2 = itemView.findViewById(R.id.TvQuery2);
            TvQ3 = itemView.findViewById(R.id.TvQuery3);
            TvQ4 = itemView.findViewById(R.id.TvQuery4);
            TvQ5 = itemView.findViewById(R.id.TvQuery5);

            edit = (ImageView) itemView.findViewById(R.id.TvUpdate);
            delete = (ImageView) itemView.findViewById(R.id.TvDelete);


            /*Dialog updateDialog = new Dialog(itemView.getContext());
            updateDialog.setContentView(R.layout.dialog_update_result);
            //updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            itemView.findViewById(R.id.TvUpdate).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){

                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener !=null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }

                    updateDialog.show();
                }
            });*/
        }

    }

    public void sendOnChannel1(View v){


        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Διαγραφή Αγώνα")
                .setContentText("Η διαγραφή του αγώνα εγινε με επιτυχία")
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(13,notification);
    }

    public void sendOnChannel2(View v){

        Notification notification = new NotificationCompat.Builder(v.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Ανανέωση Αγώνα")
                .setContentText("Η ανανέωση του αγώνα εγινε με επιτυχία")
                .setColor(Color.YELLOW)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notManager.notify(14,notification);
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;

    }

}

