package com.example.myapplication;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultAllMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultAllMainFragment extends Fragment {

    Button allssbtn,allosbtn;
    TableLayout tbl1,tbl2,tbl3;
    TableRow Rtr01,Rtr02,Rtr11,Rtr12,Rtr21,Rtr22;
    TextView Rtext1,Rtext2,Rtext3,Rtext4,Rtext5,Rtext11,Rtext12,Rtext13,Rtext14,Rtext21,Rtext22,Rtext23,Rtext24;
    private CollectionReference ref = MainActivity.db.collection("Result");
    private CollectionReference ref1 = MainActivity.db.collection("omades_score");
    private CollectionReference ref3 = MainActivity.db.collection("Athlimata");
    private CollectionReference ref2 = MainActivity.db.collection("Athlitis_score");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultAllMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultAllMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultAllMainFragment newInstance(String param1, String param2) {
        ResultAllMainFragment fragment = new ResultAllMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_all_main, container, false);


        allssbtn = view.findViewById(R.id.ShowAllOScoreBtn);
        allssbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivity.fragmentmanager.beginTransaction().replace(R.id.fragment_container, new OmadaScoreFragment()).addToBackStack(null).commit();
            }
        });

        allosbtn = view.findViewById(R.id.ShowAllAScoreBtn);
        allosbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivity.fragmentmanager.beginTransaction().replace(R.id.fragment_container, new AthlitisScoreFragment()).addToBackStack(null).commit();
            }
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {


            //querys

            try {

                Rtext1 = view.findViewById(R.id.Rtv1);
                Rtext2 = view.findViewById(R.id.Rtv2);
                Rtext3 = view.findViewById(R.id.Rtv3);
                Rtext4 = view.findViewById(R.id.Rtv4);


                Query query = ref.orderBy("date_competition");

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String res1 = "";
                        String res2 = "";
                        String res3 = "";
                        String res4 = "";
                        //String res5 = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Result res = documentSnapshot.toObject(Result.class);

                            //String rid = String.valueOf(res.getId_result());
                            String rdate = res.getDate_competition();
                            String rcountry = res.getCountry_competition();
                            String rpoli = res.getPoli_competition();
                            String rsname = res.getOnoma_sport();


                            res1 = res1 + "\n " + rdate;
                            res2 = res2 + "\n " + rsname;
                            res3 = res3 + "\n " + rpoli;
                            res4 = res4 + "\n " + rcountry;
                            //res5 = res5 + "\n " +rsname;

                        }
                        Rtext1.setText(res1);
                        Rtext2.setText(res2);
                        Rtext3.setText(res3);
                        Rtext4.setText(res4);
                        //Rtext5.setText(res5);


                    }
                });


            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }


            //query 2


            try {

                Rtext11 = view.findViewById(R.id.R1tv1);
                Rtext12 = view.findViewById(R.id.R1tv2);
                Rtext13 = view.findViewById(R.id.R1tv3);
                Rtext14 = view.findViewById(R.id.R1tv4);


                Query query2 = ref2.orderBy("id_athliti");

                query2.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String res1 = "";
                        String res2 = "";
                        String res3 = "";
                        String res4 = "";
                        //String res5 = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Athlitis_score as = documentSnapshot.toObject(Athlitis_score.class);

                            //String rid = String.valueOf(res.getId_result());
                            String epidosi = as.getEpidosi();
                            String idathl = as.getId_athliti().getPath().substring(9);
                            String idsport = as.getId_sport().getPath().substring(10);
                            String idresult = as.getId_result().getPath().substring(7);


                            res1 = res1 + "\n " + idathl;
                            res2 = res2 + "\n " + epidosi;
                            res3 = res3 + "\n " + idsport;
                            res4 = res4 + "\n " + idresult;
                            //res5 = res5 + "\n " +rsname;

                        }
                        Rtext11.setText(res1);
                        Rtext12.setText(res2);
                        Rtext13.setText(res3);
                        Rtext14.setText(res4);
                        //Rtext5.setText(res5);


                    }
                });


            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }


            //query 3

          /*  final String txt1="";
            final String txt2="";
            final String[] txt3 = { "","","","" };
            final String[] txt4 = { "","" };
            Rtext21 = view.findViewById(R.id.R11tv1);
            Rtext22 = view.findViewById(R.id.R12tv2);
            Rtext23 = view.findViewById(R.id.R13tv3);
            Rtext24 = view.findViewById(R.id.R14tv4);*/

              try {

                Rtext21 = view.findViewById(R.id.R11tv1);
                Rtext22 = view.findViewById(R.id.R12tv2);
                Rtext23 = view.findViewById(R.id.R13tv3);
                Rtext24 = view.findViewById(R.id.R14tv4);

                Query query3 = ref1.orderBy("id_sport");

                query3.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String res1 = "";
                        String res2 = "";
                        String res3 = "";
                        String res4 = "";
                        //String res5 = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            omades_score os = documentSnapshot.toObject(omades_score.class);

                            //String rid = String.valueOf(res.getId_result());
                            String epidosi = os.getEpidosi();
                            String idomad = os.getId_omadas().getPath().substring(7);
                            String idsport = os.getId_sport().getPath().substring(10);
                            String idresult = os.getId_result().getPath().substring(7);


                            res1 = res1 + "\n " + idomad;
                            res2 = res2 + "\n " + epidosi;
                            res3 = res3 + "\n " + idsport;
                            res4 = res4 + "\n " + idresult;
                           //res5 = res5 + "\n " +rsname;
                        }


                        Rtext21.setText(res1);
                        Rtext22.setText(res2);
                        Rtext23.setText(res3);
                        Rtext24.setText(res4);
                        //Rtext5.setText(res5);

                        //txt3[0]=res1;
                        //txt3[1]=res2;

                    }
                });




            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }




     /*   try {


            Rtext21 = view.findViewById(R.id.R11tv1);
            Rtext22 = view.findViewById(R.id.R12tv2);
            Rtext23 = view.findViewById(R.id.R13tv3);
            Rtext24 = view.findViewById(R.id.R14tv4);

            Query query4 = ref3.orderBy("id_sport");

            query4.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String res11 = "";
                    String res12 = "";
                    String res3 = "";
                    String res4 = "";
                    //String res5 = "";

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Athlimata as = documentSnapshot.toObject(Athlimata.class);

                        //String rid = String.valueOf(res.getId_result());
                        String epidosi = String.valueOf(as.getId_sport());
                        String idathl = as.getOnoma_sport();


                        res11 = res11 + "\n " + idathl;
                        res12 = res12 + "\n " + epidosi;

                        //res5 = res5 + "\n " +rsname;

                    }


                    Rtext21.setText(res11);
                    Rtext22.setText(res12);
                    //txt3[2] =res11;
                    //txt3[3] = res12;


                }
            });


        } catch (Exception e) {
            String message = e.getMessage();
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }

*/

       /* Rtext21.setText(txt3[2]);
        Rtext22.setText(txt3[3]);
        Rtext23.setText(txt3[0]);
        Rtext24.setText(txt3[1]);

        */







        }else {


            try {

                Rtext1 = view.findViewById(R.id.Rtv1);
                Rtext2 = view.findViewById(R.id.Rtv2);
                Rtext3 = view.findViewById(R.id.Rtv3);
                Rtext4 = view.findViewById(R.id.Rtv4);


                Query query = ref.orderBy("date_competition");

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String res1 = "";
                        String res2 = "";
                        String res3 = "";
                        String res4 = "";
                        //String res5 = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Result res = documentSnapshot.toObject(Result.class);

                            //String rid = String.valueOf(res.getId_result());
                            String rdate = res.getDate_competition();
                            String rcountry = res.getCountry_competition();
                            String rpoli = res.getPoli_competition();
                            String rsname = res.getOnoma_sport();


                            res1 = res1 + "\n " + rdate;
                            res2 = res2 + "\n " + rsname;
                            res3 = res3 + "\n " + rpoli;
                            res4 = res4 + "\n " + rcountry;
                            //res5 = res5 + "\n " +rsname;

                        }
                        Rtext1.setText(res1);
                        Rtext2.setText(res2);
                        Rtext3.setText(res3);
                        Rtext4.setText(res4);
                        //Rtext5.setText(res5);


                    }
                });


            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }


            //query 2

            try {

                Rtext11 = view.findViewById(R.id.R1tv1);
                Rtext12 = view.findViewById(R.id.R1tv2);
                Rtext13 = view.findViewById(R.id.R1tv3);
                Rtext14 = view.findViewById(R.id.R1tv4);


                Query query2 = ref2.orderBy("id_athliti");

                query2.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String res1 = "";
                        String res2 = "";
                        String res3 = "";
                        String res4 = "";
                        //String res5 = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Athlitis_score as = documentSnapshot.toObject(Athlitis_score.class);

                            //String rid = String.valueOf(res.getId_result());
                            String epidosi = as.getEpidosi();
                            String idathl = as.getId_athliti().getPath().substring(9);
                            String idsport = as.getId_sport().getPath().substring(10);
                            String idresult = as.getId_result().getPath().substring(7);


                            res1 = res1 + "\n " + idathl;
                            res2 = res2 + "\n " + epidosi;
                            res3 = res3 + "\n " + idsport;
                            res4 = res4 + "\n " + idresult;
                            //res5 = res5 + "\n " +rsname;

                        }
                        Rtext11.setText(res1);
                        Rtext12.setText(res2);
                        Rtext13.setText(res3);
                        Rtext14.setText(res4);
                        //Rtext5.setText(res5);


                    }
                });


            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }



            //query 3


           /* final String txt1="";
            final String txt2="";
            final String[] txt3 = { "","","",""};
            final String[] txt4 = { "","" };
            Rtext21 = view.findViewById(R.id.R11tv1);
            Rtext22 = view.findViewById(R.id.R12tv2);
            Rtext23 = view.findViewById(R.id.R13tv3);
            Rtext24 = view.findViewById(R.id.R14tv4);*/

           try {

                Rtext21 = view.findViewById(R.id.R11tv1);
                Rtext22 = view.findViewById(R.id.R12tv2);
                Rtext23 = view.findViewById(R.id.R13tv3);
                Rtext24 = view.findViewById(R.id.R14tv4);

                Query query3 = ref1.orderBy("id_sport");

                query3.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String res1 = "";
                        String res2 = "";
                        String res3 = "";
                        String res4 = "";
                        //String res5 = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            omades_score os = documentSnapshot.toObject(omades_score.class);

                            //String rid = String.valueOf(res.getId_result());
                            String epidosi = os.getEpidosi();
                            String idomad = os.getId_omadas().getPath().substring(7);
                            String idsport = os.getId_sport().getPath().substring(10);
                            String idresult = os.getId_result().getPath().substring(7);


                            res1 = res1 + "\n " + idomad;
                            res2 = res2 + "\n " + epidosi;
                            res3 = res3 + "\n " + idsport;
                            res4 = res4 + "\n " + idresult;
                            //res5 = res5 + "\n " +rsname;

                        }
                        Rtext21.setText(res1);
                        Rtext22.setText(res2);
                        Rtext23.setText(res3);
                        Rtext24.setText(res4);
                        //Rtext5.setText(res5);

                        //txt3[0]=res1;
                        //txt3[1]=res2;

                    }
                });




            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }




     /*     try{

              Rtext21 = view.findViewById(R.id.R11tv1);
              Rtext22 = view.findViewById(R.id.R12tv2);
              Rtext23 = view.findViewById(R.id.R13tv3);
              Rtext24 = view.findViewById(R.id.R14tv4);

                Query query4 = ref3.orderBy("id_sport");

                query4.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String res11 = "";
                        String res12 = "";
                        String res3 = "";
                        String res4 = "";
                        //String res5 = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Athlimata as = documentSnapshot.toObject(Athlimata.class);

                            //String rid = String.valueOf(res.getId_result());
                            String epidosi = String.valueOf(as.getId_sport());
                            String idathl = as.getOnoma_sport();


                            res11 = res11 + "\n " + idathl;
                            res12 = res12 + "\n " + epidosi;

                            //res5 = res5 + "\n " +rsname;

                        }

                        Rtext21.setText(res11);
                        Rtext22.setText(res12);

                        //txt3[2] =res11;
                        //txt3[3] = res12;


                    }
                });




            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }





            Rtext21.setText(txt3[2]);
            Rtext22.setText(txt3[3]);
            Rtext23.setText(txt3[0]);
            Rtext24.setText(txt3[1]);

*/


        }


        return view;
    }
}