package com.intern.clcenter.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.intern.clcenter.Adapter.AvilableJobsAdapter;
import com.intern.clcenter.Adapter.MyBookingsAdapter;
import com.intern.clcenter.R;

import java.util.ArrayList;
import java.util.List;


public class MyBookingFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    MyBookingsAdapter myBookingsAdapter;
    View view;
    private OnFragmentInteractionListener mListener;

    public MyBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_booking, container, false);
        recyclerView = view.findViewById(R.id.recyler);

        layoutManager = new LinearLayoutManager(getActivity());//   RecyclerView.LayoutManager layoutManager;
        recyclerView.setLayoutManager(layoutManager);

        //passing image array and assiging adapter  ,,, adapter is object of recyler_adapter class
        myBookingsAdapter = new MyBookingsAdapter();
        recyclerView.setAdapter(myBookingsAdapter);



        return  view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

