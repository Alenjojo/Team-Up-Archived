package com.example.teamup.Fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Adapter.ProfileAdapter;
import com.example.teamup.Model.Profile;
import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Users extends Fragment {
    private RecyclerView recyclerView;
    private ProfileAdapter profileadapter;
    private List<Profile> mProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_users,container,false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerusers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProfile=new ArrayList<>();
        readUser();
        return view;

    }

    private void readUser(){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                mProfile.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Profile profile = snapshot.getValue(Profile.class);

                    assert profile != null;
                    assert firebaseUser != null;
                    if(!profile.getUid().equals(firebaseUser.getUid())){
                        mProfile.add(profile);
                    }
                }
                profileadapter=new ProfileAdapter(getContext(),mProfile);
                recyclerView.setAdapter(profileadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }
}
