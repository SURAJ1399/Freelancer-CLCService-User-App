package com.intern.clcenter.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.intern.clcenter.Home;
import com.intern.clcenter.R;
import com.intern.clcenter.RegisterActivity;
import com.kyleduo.switchbutton.SwitchButton;


import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import dmax.dialog.SpotsDialog;

import static android.content.Context.MODE_PRIVATE;


public class PersonalInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener  {


    TextView bio,qualif,artistname,email,location,mobileno;
    TextView artistcharge;
    TextView jobdone,rating;
    Spinner artistwork;
    RatingBar ratingBar;
    String placeholder="Work Category";
    SwitchButton ratetype;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
Button booknow;

    String ratetyp;
    String  usernan;
    Map<String,Object> bookingsmap=new HashMap<>();

    public PersonalInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View itemView= inflater.inflate(R.layout.fragment_personal_info, container, false);


        bio=itemView.findViewById(R.id.bio);
        qualif=itemView.findViewById(R.id.qualification);
        artistname=itemView.findViewById(R.id.name);
        email=itemView.findViewById(R.id.email);
        booknow=itemView.findViewById(R.id.book);
        location=itemView.findViewById(R.id.location);

        rating=itemView.findViewById(R.id.rating);

        artistcharge=itemView.findViewById(R.id.artistcharge);
        jobdone=itemView.findViewById(R.id.jobscomplete);
        ratingBar=itemView.findViewById(R.id.ratingbar);
        ratetype=itemView.findViewById(R.id.ratetype);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Map for users personal details
                final Map<String,Object> walletmap=new HashMap<>();//Map for wallet details like balance ,amount paid,new/oldd user

            //    usersmap.put("fullname",fullname.getText().toString());



                final SpotsDialog waitingdilog=new SpotsDialog(getActivity(),R.style.Custom);
                waitingdilog.show();
                final FirebaseFirestore firebaseFirestore;
                firebaseFirestore=FirebaseFirestore.getInstance();
                firebaseFirestore.collection("Artists/"+usernan+"/Bookings").add(walletmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        Intent intent=new Intent(getActivity(), Home.class);
                        startActivity(intent);

                        waitingdilog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Booking Failed", Toast.LENGTH_SHORT).show();
                        waitingdilog.dismiss();
                    }
                });






            }
        });



      /*  update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 DocumentReference documentReference= firebaseFirestore.collection("Artists").document(firebaseAuth.getCurrentUser().getUid());
                 documentReference.update("bio",bio.getText().toString());
                documentReference.update("fullname",artistname.getText().toString());
                documentReference.update("qualification",qualif.getText().toString());
                documentReference.update("emailid",email.getText().toString());

                documentReference.update("location",location.getText().toString());

                documentReference.update("mobileno",mobileno.getText().toString());

                documentReference.update("workcat", placeholder);
                documentReference.update("price",artistcharge.getText().toString());

                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);



            }
        });*/


        return  itemView;




    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        placeholder=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = getContext().getSharedPreferences("xyz", Context.MODE_PRIVATE);
        usernan= prefs.getString("usernam", null);
        Toast.makeText(getContext(), "sh"+usernan, Toast.LENGTH_SHORT).show();
        firebaseFirestore.collection("Artists").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String a=doc.getDocument().getString("username");
                        if(a.equals(usernan))
                        { //Toast.makeText(getContext(), ""+doc.getDocument().getString("location"), Toast.LENGTH_SHORT).show();
                            bio.setText(doc.getDocument().getString("bio"));
                            artistname.setText(doc.getDocument().getString("fullname"));
                            qualif.setText(doc.getDocument().getString("qualification"));
                            email.setText(doc.getDocument().getString("emailid"));
                            location.setText(doc.getDocument().getString("location"));
                            mobileno.setText(doc.getDocument().getString("mobileno"));

                            placeholder=doc.getDocument().getString("workcat");




                            artistcharge.setText("₹ "+doc.getDocument().getString("price"));
                            jobdone.setText(doc.getDocument().getString("jobcomp")+ " Jobs Completed");

                            int star= doc.getDocument().getLong("rating").intValue();
                            rating.setText(star+"");
                            ratingBar.setRating(star);

                              ratetyp=doc.getDocument().getString("ratetype");
                              if(ratetyp.equals("HOURLY"))
                                ratetype.performClick();

                            bookingsmap.put("artistpic",doc.getDocument().getString("location"));
                            bookingsmap.put("workcat",doc.getDocument().getString("workcat"));
                            bookingsmap.put("address",doc.getDocument().getString("location"));
                            bookingsmap.put("date",doc.getDocument().getString("date"));
                            bookingsmap.put("status","pending");
                            bookingsmap.put("name",doc.getDocument().getString("fullname"));
                            bookingsmap.put("mobileno",doc.getDocument().getString("mobileno"));
                            bookingsmap.put("whatsappno",doc.getDocument().getString("whatsappno"));
                            bookingsmap.put("clientusername",firebaseAuth.getCurrentUser().getUid());







                        }}
                }
            }
        });


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            firebaseFirestore.collection("Artists").document(usernan).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Toast.makeText(getActivity(), ""+usernan, Toast.LENGTH_SHORT).show();

                }
            });


        }


    }
}
