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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hakim.e_comerceapp.Model.Users;
import com.example.hakim.e_comerceapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class Loginactivity extends AppCompatActivity {
    private EditText InputPassword, InputPhoneNumber;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink,NotAdminLink;
    private String parentDbName = "Users";
    private CheckBox checkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_loginactivity);
        LoginButton = findViewById (R.id.login_btn);
        InputPassword = findViewById (R.id.login_password_input);
        InputPhoneNumber = findViewById (R.id.login_phone_number_input);
        AdminLink=findViewById (R.id.admin_panel_link);
        NotAdminLink=findViewById (R.id.not_admin_panel_link);
        loadingBar=new ProgressDialog (this);
        checkBoxRememberMe=findViewById (R.id.remember_me_checkbox);
        Paper.init (this);
        LoginButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        AdminLink.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                LoginButton.setText ("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility (View.VISIBLE);
                parentDbName="Admins";

            }
        });
        NotAdminLink.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                LoginButton.setText ("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility (View.INVISIBLE);
                parentDbName="Users";

            }
        });
    }

    private void LoginUser() {
        String phone = InputPhoneNumber.getText ().toString ();
        String password = InputPassword.getText ().toString ();
          if (TextUtils.isEmpty (phone)) {
            Toast.makeText (getApplicationContext (), "Please write your phone number_ _ _",Toast.LENGTH_SHORT).show ();
        }
        else  if (TextUtils.isEmpty (password)) {
            Toast.makeText (getApplicationContext (), "Please write your password_ _ _",Toast.LENGTH_SHORT).show ();
        }
        else {
              loadingBar.setTitle ("Login Account");
              loadingBar.setMessage ("Please Wait, while we are checking the credentials.");
              loadingBar.setCanceledOnTouchOutside (false);
              loadingBar.show ();
              AllowAccessToAccount(phone,password);

          }
    }

    private void AllowAccessToAccount( final String phone,  final String password) {
        if (checkBoxRememberMe.isChecked ()){
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance ().getReference ();
        RootRef.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child (parentDbName).child (phone).exists ())
                {
                    Users usersData=dataSnapshot.child (parentDbName).child (phone).getValue (Users.class);
                    if (usersData.getPhone().equals (phone))
                    {
                        if (usersData.getPassword ().equals (password))
                        {
                           if (parentDbName.equals ("Admins"))
                            {
                                Toast.makeText (getApplicationContext (),"Welcome Admin, You are Logged in succeefully",Toast.LENGTH_SHORT).show ();
                                loadingBar.dismiss ();
                                Intent intent=new Intent (Loginactivity.this, AdminCategoryActivity.class);
                                startActivity (intent);
                            }
                            else if (parentDbName.equals ("Users"))
                           {
                               Toast.makeText (getApplicationContext (),"Logged in succeefully",Toast.LENGTH_SHORT).show ();
                               loadingBar.dismiss ();
                               Intent intent=new Intent (Loginactivity.this,HomeActivity.class);
                               Prevalent.currentOnlineUser = usersData;
                               startActivity (intent);
                           }

                        }
                        else
                        {
                            loadingBar.dismiss ();
                            Toast.makeText (getApplicationContext (),"Password is incorrect",Toast.LENGTH_SHORT).show ();
                        }

                    }
                }
                else
                {
                    Toast.makeText (getApplicationContext (),"Account with this"+phone+"Number do not exists.",Toast.LENGTH_SHORT).show ();
                    loadingBar.dismiss ();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
