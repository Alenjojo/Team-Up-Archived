package com.example.teamup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import com.example.teamup.Login.LoginActivity;

public class SettingActivity extends AppCompatActivity {
    Button btnLogout;
    TextView proname,proteamcode;
    ImageView proimg,backarrow;
    FirebaseAuth mFirebaseAuth;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PRE="shared";
    private static final String TEAM_CODE="teamcode";
    private static final String NAME="fbname";
    private static final String URL="phurl";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String txtproname,url,teamcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnLogout = findViewById(R.id.logout);
        proname=findViewById(R.id.txtproname);
        proimg=findViewById(R.id.imgpro);
        proteamcode=findViewById(R.id.txtteamcode);

        sharedPreferences=getSharedPreferences(SHARED_PRE,MODE_PRIVATE);
        teamcode=sharedPreferences.getString(TEAM_CODE,null);
        proteamcode.setText(teamcode);
        txtproname=sharedPreferences.getString(NAME,null);
        proname.setText(txtproname);
        url=sharedPreferences.getString(URL,null);
        Picasso.get().load(url).error(R.drawable.loginback).into(proimg);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });

        backarrow=findViewById(R.id.imgback);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(intSignUp);

            }
        });

    }
}
