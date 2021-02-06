package com.example.teamup.Fargments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Adapter.PostAdapter;
import com.example.teamup.AddProjectActivity;
import com.example.teamup.HomeActivity;
import com.example.teamup.Model.Post;
import com.example.teamup.R;
import com.example.teamup.SettingActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Home extends Fragment {
    ImageView btnSet;
    LinearLayout llContext;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    DatabaseReference Get;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PRE="shared";
    private static final String TEAM_CODE="teamcode";
    private static final String NAME="fbname";
    private static final String URL="phurl";
    private static final String UID="userID";
    private static final String FBLOG="fblogin";
    // ProgressBar progressBar;
    TextView fbname;
    ImageView prourl;
    private FirebaseAuth user;
    String teamcode="team 1",fbproname,url,uid,getFblog="0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        fbname=(TextView) view.findViewById(R.id.proname);
        prourl=(ImageView) view.findViewById(R.id.prourl);

        sharedPreferences=this.getActivity().getSharedPreferences(SHARED_PRE, Context.MODE_PRIVATE);
        teamcode=sharedPreferences.getString(TEAM_CODE,null);
        fbproname=sharedPreferences.getString(NAME,null);
        url=sharedPreferences.getString(URL,null);
        uid=sharedPreferences.getString(UID,null);

        fbname.setText(fbproname);
//        Picasso.get().load(url).error(R.drawable.loginback).into(prourl);
//        uid = user.getUid();
        Get = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        Get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String name = (String) dataSnapshot.child("username").getValue();
                String teamcodefire= (String) dataSnapshot.child("teamcode").getValue();
                String profile_img= (String) dataSnapshot.child("profile_image").getValue();
                fbname.setText(name);
//                Picasso.get().load(profile_img).error(R.drawable.loginback).into(prourl);

                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(TEAM_CODE,teamcodefire);
                editor.putString(URL,profile_img);
                editor.putString(NAME,name);
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerDashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(teamcode!=null) {
            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("teams").child(teamcode), Post.class)
                    .build();

            adapter = new PostAdapter(options);
            recyclerView.setAdapter(adapter);
        }

        btnSet=(ImageView)view.findViewById(R.id.imageButton3);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(getActivity(), SettingActivity.class);
                startActivity(intSignUp);

            }
        });

        FloatingActionButton fab =(FloatingActionButton)view.findViewById(R.id.addproject);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intSignUp = new Intent(getActivity(), AddProjectActivity.class);
                startActivity(intSignUp);
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

