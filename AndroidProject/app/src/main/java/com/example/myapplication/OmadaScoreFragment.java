package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OmadaScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OmadaScoreFragment extends Fragment{

    Button InsertABtn,DeleteABtn,UpdateABtn,SeeABtn,InsertOBtn,DeleteOBtn,UpdateOBtn,SeeOBtn;
    private RecyclerAdapterOmadaScore adapter;
    private CollectionReference ref = MainActivity.db.collection("omades_score");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OmadaScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OmadaScoreFragment newInstance(String param1, String param2) {
        OmadaScoreFragment fragment = new OmadaScoreFragment();
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
        View view =  inflater.inflate(R.layout.fragment_omada_score, container, false);

        FloatingActionButton btn = view.findViewById(R.id.OmadaScoreAddbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InsertOmadaScoreActivity.class));
            }
        });


        Query query = ref.orderBy("idepidosi");
        FirestoreRecyclerOptions<omades_score> options = new FirestoreRecyclerOptions.Builder<omades_score>()
                .setQuery(query,omades_score.class)
                .build();

        adapter = new RecyclerAdapterOmadaScore(options);
        RecyclerView resView1 = view.findViewById(R.id.RvOmadaScore);
        resView1.setHasFixedSize(true);
        resView1.setLayoutManager(new LinearLayoutManager(getContext()));
        resView1.setAdapter(adapter);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}