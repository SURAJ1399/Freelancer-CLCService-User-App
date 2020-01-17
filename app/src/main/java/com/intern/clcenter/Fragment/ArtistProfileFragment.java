package com.intern.clcenter.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.intern.clcenter.Model.AvilableArtistModel;
import com.intern.clcenter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;


public class ArtistProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ViewPager pager;
    View view;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;
    String cat;
    String usernam;
    int state = 0;
    String artisurl, bannr;

    int mMaxScrollSize;
    static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    boolean mIsAvatarShown = true;
    PersonalInfoFragment persnoalInfo = new PersonalInfoFragment();
    PreviousWorkFragment previousWork = new PreviousWorkFragment();
    ReviewsFragment reviews = new ReviewsFragment();


    CircleImageView circleImageView;
    ImageView banner;
    AppBarLayout appBar;
    TextView name, work, location;
    Button updatebtn;
    FirebaseFirestore firebaseFirestore;
    StorageReference storagerefrence;
    FirebaseAuth firebaseAuth;
    Uri picUri;

    String category;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public  String usernan;


    public ArtistProfileFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_artist_profile, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        circleImageView = view.findViewById(R.id.artistpic);
        banner = view.findViewById(R.id.ivBanner);
        name = view.findViewById(R.id.name);


        location = view.findViewById(R.id.location);
        work = view.findViewById(R.id.work);
        storagerefrence = FirebaseStorage.getInstance().getReference();

        Bundle bundle=getArguments();
        usernam=bundle.getString("username");


     //   Toast.makeText(getContext(), ""+usernan, Toast.LENGTH_SHORT).show();


        firebaseFirestore.collection("Artists").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String a=doc.getDocument().getString("username");
                                if(a.equals(usernam))
                                { //Toast.makeText(getContext(), ""+doc.getDocument().getString("location"), Toast.LENGTH_SHORT).show();
                                    String dp = doc.getDocument().getString("artispic");
                                    String bnr =doc.getDocument().getString("banner");

                                    name.setText(doc.getDocument().getString("fullname"));
                                    location.setText(doc.getDocument().getString("location"));
                                    work.setText(doc.getDocument().getString("workcat"));

                                    //workcat.setText(task.getResult().getString("workcat"));

                                    Glide.with(ArtistProfileFragment.this).load(dp).into(circleImageView);
                                    Glide.with(ArtistProfileFragment.this).load(bnr).into(banner);

                                }}
                }
            }
        });

//
//        firebaseFirestore.collection("Artists").document(usernan).get().
//                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                          @Override
//                                          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                                              String dp = task.getResult().getString("artispic");
//                                              String bnr = task.getResult().getString("banner");
//
//                                              name.setText(task.getResult().getString("fullname"));
//                                              location.setText(task.getResult().getString("location"));
//                                              work.setText(task.getResult().getString("workcat"));
//
//                                              //workcat.setText(task.getResult().getString("workcat"));
//
//                                              Glide.with(ArtistProfileFragment.this).load(dp).into(circleImageView);
//                                              Glide.with(ArtistProfileFragment.this).load(bnr).into(banner);
//
//                                            //  Toast.makeText(getActivity(), "Toast"+usernan+"" +task.getResult().getString("location") , Toast.LENGTH_SHORT).show();
//
//
//                                          }
//                                      }
//
//                ).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//
//
//


        pager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tabLayout);
        appBar = view.findViewById(R.id.appbar);
        mMaxScrollSize = appBar.getTotalScrollRange();
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout.getTotalScrollRange();

                int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

                if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
                    mIsAvatarShown = false;

                    circleImageView.animate().scaleY(0).scaleX(0).setDuration(200).start();
                }

                if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
                    mIsAvatarShown = true;

                    circleImageView.animate().scaleY(1).scaleX(1).setDuration(200).start();
                }
            }


        });


        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(persnoalInfo, "Info");
        adapter.addFragment(previousWork, "Works");
        adapter.addFragment(reviews, "Reviews");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        String username;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            picUri = data.getData();
            if (state == 1) {
                banner.setImageURI(picUri);
            }
            if (state == 0) {
                circleImageView.setImageURI(picUri);
            }

        }
    }


}



