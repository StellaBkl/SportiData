package com.example.myapplication;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMainFragment extends Fragment {
    TableLayout tl1,tl2,tl3;
    TableRow tr01,tr02,tr11,tr12,tr21,tr22;
    TextView text1,text2,text3,text0,text4,text11,text12,text13,text10,text14,textSearch,text21,text22,text23,text24,text25;
    EditText etSearch;
    Button btnSearch;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllMainFragment newInstance(String param1, String param2) {
        AllMainFragment fragment = new AllMainFragment();
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
        View view =   inflater.inflate(R.layout.fragment_all_main, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            try {
                tl1 = view.findViewById(R.id.table1);
                tr01 = view.findViewById(R.id.tb0);
                tr02 = view.findViewById(R.id.tb1);
                text1 = view.findViewById(R.id.tv1);
                text2 = view.findViewById(R.id.tv2);
                text3 = view.findViewById(R.id.tv3);
                text0 = view.findViewById(R.id.tv0);

                List<ResultStringInt> rsi = MainActivity.myDatabase.myDao().getQuery1();
                String res1 = "";
                String res2 = "";
                String res3 = "";
                String res4 = "";
                //String res5 = "";

                for (ResultStringInt i : rsi) {
                    int sid = i.getsid();
                    int aid = i.getaid();
                    String spname = i.getsonoma();
                    String fil = i.getfilo();
                   //String eidosA = i.getEathl();

                    res1 = res1 + "\n " + sid;
                    res2 = res2 + "\n" + spname;
                    res3 = res3 + "\n" + aid;
                    res4 = res4 + "\n" + fil;

                }

                text1.setText(res1);
                text2.setText(res2);
                text0.setText(res3);
                text3.setText(res4);

            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }


            try {
                tl2 = view.findViewById(R.id.table2);
                tr11 = view.findViewById(R.id.tr0);
                tr12 = view.findViewById(R.id.tr1);
                text11 = view.findViewById(R.id.tv21);
                text12 = view.findViewById(R.id.tv22);
                text13 = view.findViewById(R.id.tv23);
                text10 = view.findViewById(R.id.tv24);


                List<ResultStringInt2> rsi1 = MainActivity.myDatabase.myDao().getQuery2();
                String res1 = "";
                String res2 = "";
                String res3 = "";
                String res4 = "";
                //String res5 = "";

                for (ResultStringInt2 i : rsi1) {
                    int oid = i.getoid();
                    int etidr = i.getetidr();
                    String poli = i.getpol();
                    String tonoma = i.gettonoma();
                    //int numa = i.getNumathl();

                    res1 = res1 + "\n " + poli;
                    res2 = res2 + "\n" + oid;
                    res3 = res3 + "\n" + etidr;
                    res4 = res4 + "\n" + tonoma;
                    //res5 = res5 + "\n" + numa;
                }

                text11.setText(res1);
                text12.setText(res2);
                text13.setText(res3);
                text10.setText(res4);


            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }


            try {
                tl3 = view.findViewById(R.id.table3);
                tr21 = view.findViewById(R.id.tr31);
                tr22 = view.findViewById(R.id.tr32);
                text21 = view.findViewById(R.id.tv31);
                text22 = view.findViewById(R.id.tv32);
                text23 = view.findViewById(R.id.tv33);


                List<ResultStringInt3> rsi3 = MainActivity.myDatabase.myDao().getQuery3();
                String res1 = "";
                String res2 = "";
                String res3 = "";
                //String res4 = "";
               // String res5 = "";

                for (ResultStringInt3 i : rsi3) {
                    String sname = i.getSonoma();
                    int min = i.getMinbdate();
                    int max = i.getMaxbdate();
                    int numomad = i.getNumath();
                    int numathl = i.getNumom();


                    res1 = res1 + "\n " + sname;
                    res2 = res2 + "\n" + numomad;
                    res3 = res3 + "\n" + numathl;
                    //res4 = res4 + "\n" + min;
                    //res5 = res5 + "\n" + max;
                }

                text21.setText(res1);
                text22.setText(res2);
                text23.setText(res3);
                //text24.setText(res4);
               // text25.setText(res5);

            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }


        }else {
            try {
                tl1 = view.findViewById(R.id.table1);
                tr01 = view.findViewById(R.id.tb0);
                tr02 = view.findViewById(R.id.tb1);
                text1 = view.findViewById(R.id.tv1);
                text2 = view.findViewById(R.id.tv2);
                text3 = view.findViewById(R.id.tv3);
                text0 = view.findViewById(R.id.tv0);
                text4 = view.findViewById(R.id.tv4);

                List<ResultStringInt> rsi = MainActivity.myDatabase.myDao().getQuery1();
                String res1 = "";
                String res2 = "";
                String res3 = "";
                String res4 = "";
                String res5 = "";

                for (ResultStringInt i : rsi) {
                    int sid = i.getsid();
                    int aid = i.getaid();
                    String spname = i.getsonoma();
                    String fil = i.getfilo();
                    String eidosA = i.getEathl();

                    res1 = res1 + "\n " + sid;
                    res2 = res2 + "\n" + spname;
                    res3 = res3 + "\n" + aid;
                    res4 = res4 + "\n" + fil;
                    res5 = res5 + "\n" + eidosA;
                }

                text1.setText(res1);
                text2.setText(res2);
                text0.setText(res3);
                text3.setText(res4);
                text4.setText(res5);

            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }


            try {
                tl2 = view.findViewById(R.id.table2);
                tr11 = view.findViewById(R.id.tr0);
                tr12 = view.findViewById(R.id.tr1);
                text11 = view.findViewById(R.id.tv21);
                text12 = view.findViewById(R.id.tv22);
                text13 = view.findViewById(R.id.tv23);
                text10 = view.findViewById(R.id.tv24);
                text14 = view.findViewById(R.id.tv223);

                List<ResultStringInt2> rsi1 = MainActivity.myDatabase.myDao().getQuery2();
                String res1 = "";
                String res2 = "";
                String res3 = "";
                String res4 = "";
                String res5 = "";

                for (ResultStringInt2 i : rsi1) {
                    int oid = i.getoid();
                    int etidr = i.getetidr();
                    String poli = i.getpol();
                    String tonoma = i.gettonoma();
                    int numa = i.getNumathl();

                    res1 = res1 + "\n " + poli;
                    res2 = res2 + "\n" + oid;
                    res3 = res3 + "\n" + etidr;
                    res4 = res4 + "\n" + tonoma;
                    res5 = res5 + "\n" + numa;
                }

                text11.setText(res1);
                text12.setText(res2);
                text13.setText(res3);
                text10.setText(res4);
                text14.setText(res5);

            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

        /*try{

            textSearch = view.findViewById(R.id.tvSearch);
            etSearch = view.findViewById(R.id.etSearchTown);
            btnSearch = view.findViewById(R.id.btnSearchTown);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String var_poli = etSearch.getText().toString();

                    try{

                        //ResultStringInt3 r = new ResultStringInt3();
                      //  r.setPoli(var_poli);
                        List<ResultStringInt3> rsi3 = MainActivity.myDatabase.myDao().getQuery3();
                        String result = "";

                        for(ResultStringInt3 i: rsi3){
                            i.setPoli(var_poli);
                            int oid = i.getOid();
                            String oname = i.getOonoma();
                            String sname = i.getSname();


                            result = result + "\n Athlete id: " + oid + "\n Athlete's Name: " + oname + "\n Athlete's SurName: " +sname;

                        }
                        textSearch.setText(result);

                    }catch(Exception e){
                    String message = e.getMessage();
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                   }

                }
            });


        }catch(Exception e){
        String message = e.getMessage();
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
       }*/

            try {
                tl3 = view.findViewById(R.id.table3);
                tr21 = view.findViewById(R.id.tr31);
                tr22 = view.findViewById(R.id.tr32);
                text21 = view.findViewById(R.id.tv31);
                text22 = view.findViewById(R.id.tv32);
                text23 = view.findViewById(R.id.tv33);
                text24 = view.findViewById(R.id.tv34);
                text25 = view.findViewById(R.id.tv35);

                List<ResultStringInt3> rsi3 = MainActivity.myDatabase.myDao().getQuery3();
                String res1 = "";
                String res2 = "";
                String res3 = "";
                String res4 = "";
                String res5 = "";

                for (ResultStringInt3 i : rsi3) {
                    String sname = i.getSonoma();
                    int min = i.getMinbdate();
                    int max = i.getMaxbdate();
                    int numomad = i.getNumath();
                    int numathl = i.getNumom();


                    res1 = res1 + "\n " + sname;
                    res2 = res2 + "\n" + numomad;
                    res3 = res3 + "\n" + numathl;
                    res4 = res4 + "\n" + min;
                    res5 = res5 + "\n" + max;
                }

                text21.setText(res1);
                text22.setText(res2);
                text23.setText(res3);
                text24.setText(res4);
                text25.setText(res5);

            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

        }

        return view;

    }



}