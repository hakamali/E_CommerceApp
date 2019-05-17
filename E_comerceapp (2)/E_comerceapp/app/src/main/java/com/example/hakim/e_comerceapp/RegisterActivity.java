package com.example.hakim.e_comerceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button createAccountButton;
    private EditText InputName, InputPhonNumber, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_register);
        createAccountButton = findViewById (R.id.register_btn);
        InputName = findViewById (R.id.register_username_input);
        InputPassword = findViewById (R.id.register_passwor_input);
        InputPhonNumber = findViewById (R.id.register_phone_number_input);
        loadingBar=new ProgressDialog (this);
        createAccountButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                CreateAccount ();
            }
        });


    }

    private void CreateAccount() {
        String name = InputName.getText ().toString ();
        String phone = InputPhonNumber.getText ().toString ();
        String password = InputPassword.getText ().toString ();
        if (TextUtils.isEmpty (name)) {
            Toast.makeText (getApplicationContext (), "Please write your name_ _ _",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (phone)) {
            Toast.makeText (getApplicationContext (), "Please write your phone number_ _ _",Toast.LENGTH_SHORT).show ();
        }
      else   if (TextUtils.isEmpty (password)) {
            Toast.makeText (getApplicationContext (), "Please write your password_ _ _",Toast.LENGTH_SHORT).show ();
        }
        else {
            loadingBar.setTitle ("Create Account");
            loadingBar.setMessage ("Please Wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside (false);
            loadingBar.show ();
            validatephoneNumber(name,phone,password);
            
        }
    }

    private void validatephoneNumber(final String name, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance ().getReference ();
        RootRef.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if(!(dataSnapshot.child ("Users").child (phone).exists ()))
           {
               HashMap<String,Object> userdataMap=new HashMap<> ();
               userdataMap.put ("phone",phone);
               userdataMap.put ("password",password);
               userdataMap.put ("name",name);
               RootRef.child("Users").child (phone).updateChildren (userdataMap).addOnCompleteListener (new OnCompleteListener<Void> () {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful ())
                   {
                       Toast.makeText (getApplicationContext (),"Congratulations,your acount has been created",Toast.LENGTH_SHORT).show ();
                       loadingBar.dismiss ();
                       Intent intent=new Intent (RegisterActivity.this,Loginactivity.class);
                       startActivity (intent);
                   }
                   else
                   {
                       loadingBar.dismiss ();
                       Toast.makeText (getApplicationContext (),"Network error please try again after some time......",Toast.LENGTH_SHORT).show ();
                   }
                   }
               });
           }
           else
           {
               Toast.makeText (getApplicationContext (),"This"+phone+"already exists", Toast.LENGTH_SHORT).show ();
               loadingBar.dismiss ();
               Toast.makeText (getApplicationContext (), "please try again using another phone number.", Toast.LENGTH_SHORT).show ();
               Intent intent=new Intent (RegisterActivity.this,MainActivity.class);
               startActivity (intent);
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
