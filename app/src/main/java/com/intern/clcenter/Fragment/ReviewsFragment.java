package com.intern.clcenter.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intern.clcenter.Adapter.ReviewAdapter;
import com.intern.clcenter.R;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reviews, container, false);

      RecyclerView recyclerView = view.findViewById(R.id.reviewrecycler);

       LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//   RecyclerView.LayoutManager layoutManager;
        recyclerView.setLayoutManager(layoutManager);

        //passing image array and assiging adapter  ,,, adapter is object of recyler_adapter class
     ReviewAdapter reviewAdapter= new ReviewAdapter();
        recyclerView.setAdapter(reviewAdapter);

return  view;
    }
}
