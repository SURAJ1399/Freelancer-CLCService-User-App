package com.intern.clcenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import dmax.dialog.SpotsDialog;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;
import com.schibstedspain.leku.LocationPickerActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class PostJob extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText etCommet, etTitle, etAddress, etPrice, etDate;
    LinearLayout llPicture,llPost;
    Spinner spinner;
    BottomSheet.Builder builder;
    Uri picUri;
    FirebaseFirestore firebaseFirestore;
    StorageReference storagerefrence;
    FirebaseAuth mAuth;
    String category;
    SimpleDateFormat sdf1, timeZone;
    String    downloadurl;
    String name;
    FirebaseAuth firebaseAuth;
    int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;

    int PLACE_PICKER_REQUEST = 1;
    double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        String DATE_FORMATE_SERVER = "EEE, MMM dd, yyyy hh:mm a"; //Wed, JUL 06, 2018 04:30 pm
        String DATE_FORMATE_TIMEZONE = "z";


        sdf1 = new SimpleDateFormat(DATE_FORMATE_SERVER, Locale.ENGLISH);
        timeZone = new SimpleDateFormat(DATE_FORMATE_TIMEZONE, Locale.ENGLISH);

        etPrice = findViewById(R.id.etPrice);
        etDate = findViewById(R.id.etDate);
        etTitle = findViewById(R.id.etTitle);
        etCommet = findViewById(R.id.etCommet);
        etAddress = findViewById(R.id.etAddress);
        llPicture = findViewById(R.id.llPicture);
        llPost = findViewById(R.id.llPost);
        spinner=findViewById(R.id.spinner);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        // current_user = mAuth.getCurrentUser().getUid();
        storagerefrence = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
     etDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

llPost.setVisibility(View.INVISIBLE
);llPost.setEnabled(false);
        new SingleDateAndTimePickerDialog.Builder(PostJob.this)
                .bottomSheet()
                .curved()
                .defaultDate(new Date())
                .mustBeOnFuture()
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {


                                    etDate.setText(String.valueOf(sdf1.format(date).toString().toUpperCase()));
                                }
                            })
                            .display();
                }
            });

                        etAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                            }
                });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

name=task.getResult().getString("fullname");
                }
            });


        }


            final ProgressDialog progressDialog=new ProgressDialog(PostJob.this);
        progressDialog.setMessage("Uploading Your Request...");
        llPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                final String randomName = UUID.randomUUID().toString();
                final UploadTask filepath = storagerefrence.child("PostedImage").child(randomName + ".jpg").putFile(picUri);
                filepath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
              final Map<String,Object> postmap=new HashMap<>();

                                    if (task.isSuccessful()) {

                                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                downloadurl = uri.toString();
                                                String jobcode=genratekjobcode().toUpperCase();
                                                postmap.put("category",category) ;
                                            postmap.put("title",etTitle.getText().toString());
                                        postmap.put("budget",etPrice.getText().toString());
                                        postmap.put("address",etAddress.getText().toString());
                                        postmap.put("date",etDate.getText().toString());
                                        postmap.put("descentryfee",etCommet.getText().toString());
                                        postmap.put("imageuri",downloadurl);
                                        postmap.put("username",name);
                                        postmap.put("jobcode",jobcode);
                                        postmap.put("status","Pending");

                                    final SpotsDialog waitingdilog=new SpotsDialog(PostJob.this);
                                    waitingdilog.show();
                                    progressDialog.dismiss();
                                            firebaseFirestore.collection("Users/"+mAuth.getCurrentUser().getUid()+"/PostedJobs").add(postmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {

                                            if(task.isSuccessful())
                                            {
                                                firebaseFirestore.collection("PostedJobs").add(postmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                                        if(task.isSuccessful())
                                                        {

                                                            waitingdilog.dismiss();

                                                            Toast.makeText(PostJob.this, "Job Posted", Toast.LENGTH_SHORT).show();
                                                            Intent intent=new Intent(PostJob.this,Home.class);
                                                            //intent.putExtra("uri",downloadurl);
                                                            startActivity(intent);
                                                            finish();


                                                        }
                                                        else
                                                        { String error=task.getException().getMessage();
                                                            Toast.makeText(PostJob.this, " Error:"+error, Toast.LENGTH_LONG).show();

                                                        }

                                                    }
                                                });






                                            }
                                            else
                                            { String error=task.getException().getMessage();
                                                Toast.makeText(PostJob.this, " Error:"+error, Toast.LENGTH_LONG).show();

                                            }

                                        }
                                    });









                                }
                            });




                        } else {

                            Toast.makeText(PostJob.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        final Spinner spinner = findViewById(R.id.spinner);
        //   Button button=(Button)findViewById(R.id.button);

        // Spinner click listener
        spinner.setOnItemSelectedListener(PostJob.this);
        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Category");
     //   categories.add("Item 2");
      //  categories.add("Item 3");
      //  categories.add("Item 4");
      //  categories.add("Item 5");
      //  categories.add("Item 6");
        firebaseFirestore.collection("Category").addSnapshotListener(PostJob.this,new EventListener<QuerySnapshot>() {
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



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button.
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Attaching data adapter to spinner.

        spinner.setAdapter(dataAdapter);


        llPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();
            }
        });

        builder = new BottomSheet.Builder(PostJob.this).sheet(R.menu.menu_cards);
        builder.title(getResources().getString(R.string.take_image));
        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {


                    case R.id.gallery_cards:

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                        break;

                    case R.id.cancel_cards:
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        });
                        break;
                }
            }
        });

    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            picUri = data.getData();

            llPost.setVisibility(View.VISIBLE
            );llPost.setEnabled(true);
            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category=adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public  String genratekjobcode()

    {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

    //Create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(5);

    for (int i = 0; i < 5; i++) {

        // generate a random number between
        // 0 to AlphaNumericString variable length
        int index
                = (int)(AlphaNumericString.length()
                * Math.random());

        // add Character one by one in end of sb
        sb.append(AlphaNumericString
                .charAt(index));
    }

return sb.toString();
}

}



