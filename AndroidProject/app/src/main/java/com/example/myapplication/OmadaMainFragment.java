package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OmadaMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OmadaMainFragment extends Fragment{

    Button Insertob,Seeob,delodata,updateodata;

    private ModelViewOmada mvO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OmadaMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OmadaMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OmadaMainFragment newInstance(String param1, String param2) {
        OmadaMainFragment fragment = new OmadaMainFragment();
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
        View view =  inflater.inflate(R.layout.fragment_omada_main, container, false);


        FloatingActionButton btn = view.findViewById(R.id.OmadaAddbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InsertOmadaActivity.class));
            }
        });


        RecyclerView resView1 = view.findViewById(R.id.RvOmada);
        final RecyclerAdapterOmada Oadapter = new RecyclerAdapterOmada();
        resView1.setHasFixedSize(true);
        resView1.setLayoutManager(new LinearLayoutManager(getContext()));
        resView1.setAdapter(Oadapter);

        mvO = new ViewModelProvider(this).get(ModelViewOmada.class);
        mvO.getAllDataOmada().observe(getViewLifecycleOwner(), new Observer<List<Omada>>() {
            @Override
            public void onChanged(@Nullable List<Omada> omadas) {
                Oadapter.setOmades(omadas);

            }
        });



        return view;
    }

}