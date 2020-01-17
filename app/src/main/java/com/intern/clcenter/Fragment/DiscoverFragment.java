package com.intern.clcenter.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.intern.clcenter.Adapter.AvilableJobsAdapter;

import com.intern.clcenter.Model.AvilableArtistModel;
import com.intern.clcenter.PostJob;
import com.intern.clcenter.R;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class DiscoverFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<AvilableArtistModel>avilableArtistModelslist;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String category;
    AvilableJobsAdapter avilableJobsAdapter;
    View view;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    public DiscoverFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        view = inflater.inflate(R.layout.fragment_discover, container, false);


        //Passing each menu ID as a set of Ids because each.

        avilableArtistModelslist=new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        avilableJobsAdapter = new AvilableJobsAdapter(avilableArtistModelslist);
        recyclerView.setAdapter(avilableJobsAdapter);

        final Spinner spinner = view.findViewById(R.id.spinner);
        //   Button button=(Button)findViewById(R.id.button);

        // Spinner click listener
       // spinner.setOnItemSelectedListener(geta);
        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Category");
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Category").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String category = doc.getDocument().getString("category");
                            categories.add(category);

                        }
                    }
                }


            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            //blogRecyclerAdapter.notifyDataSetChanged();

            Query firstquery = firebaseFirestore.collection("Artists").orderBy("rating", Query.Direction.DESCENDING);
            firebaseFirestore.collection("Artists").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                           // String blogpostid = doc.getDocument().getId();
                           AvilableArtistModel avilableArtistModels = doc.getDocument().toObject(AvilableArtistModel.class);
                            avilableArtistModelslist.add(avilableArtistModels);
                           // Toast.makeText(getContext(), ""+doc.getDocument().getString("username"), Toast.LENGTH_SHORT).show();
                           avilableJobsAdapter.notifyDataSetChanged();

                        }
                    }
                }
            });
        }




        return  view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category=adapterView.getItemAtPosition(i).toString();
        firebaseFirestore.collection("Artists").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                         String category2 = doc.getDocument().getString("workcat");
                         if(category2.equals(category))
                         {avilableArtistModelslist.clear();
                        AvilableArtistModel avilableArtistModels = doc.getDocument().toObject(AvilableArtistModel.class);
                        avilableArtistModelslist.add(avilableArtistModels);
                        // Toast.makeText(getContext(), ""+doc.getDocument().getString("username"), Toast.LENGTH_SHORT).show();
                        avilableJobsAdapter.notifyDataSetChanged();}

                    }
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}

