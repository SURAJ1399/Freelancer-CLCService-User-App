package com.intern.clcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    EditText fullname,email,password,cnfrmpass;
    Button signup;
    TextView login;
    CheckBox checkBox;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname=findViewById(R.id.fulNameRegisterET);
        email=findViewById(R.id.emailRegisterET);
        password=findViewById(R.id.passwordRegisterET);
        cnfrmpass=findViewById(R.id.confPasswordRegisterET);
        signup=findViewById(R.id.registerBTN);
        checkBox=findViewById(R.id.termsCB);
        login=findViewById(R.id.linktologinBTN);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(email.getText().toString())&&!TextUtils.isEmpty(fullname.getText().toString()) &&!TextUtils.isEmpty(password.getText().toString())&&
                        password.getText().toString().equals(cnfrmpass.getText().toString()))
                {



                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                Map<String,Object> usersmap=new HashMap<>();//Map for users personal details
                                final Map<String,Object> walletmap=new HashMap<>();//Map for wallet details like balance ,amount paid,new/oldd user

                                usersmap.put("fullname",fullname.getText().toString());
                                usersmap.put("emailid",email.getText().toString());
                                usersmap.put("mobileno","");
                                usersmap.put("address","");
                                usersmap.put("city","");


                                walletmap.put("balance","5");
                                walletmap.put("flag","0");
                                walletmap.put("amountpaid","0");
                                walletmap.put("matchesplayed","0");



                                final SpotsDialog waitingdilog=new SpotsDialog(RegisterActivity.this,R.style.Custom);
                               //waitingdilog.setMessage("Connecti");
                                waitingdilog.show();
                                final FirebaseFirestore firebaseFirestore;
                                firebaseFirestore=FirebaseFirestore.getInstance();


                                firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).set(usersmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        if(task.isSuccessful())
                                        {

                                            firebaseFirestore.collection("Users/"+firebaseAuth.getCurrentUser().getUid()+"/Wallet").add(walletmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                                    Intent intent=new Intent(RegisterActivity.this,Home.class);
                                                    startActivity(intent);

                                                    waitingdilog.dismiss();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Please Fill The Details Again", Toast.LENGTH_SHORT).show();
                                                    waitingdilog.dismiss();
                                                }
                                            });



                                        }
                                        else
                                        { String error=task.getException().getMessage();
                                            Toast.makeText(RegisterActivity.this, " Error:"+error, Toast.LENGTH_LONG).show();

                                        }

                                    }
                                });



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


    }
}
