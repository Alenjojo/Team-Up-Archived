package com.example.teamup.Chats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Adapter.MessageAdapter;
import com.example.teamup.HomeActivity;
import com.example.teamup.Model.Chats;
import com.example.teamup.Model.User;
import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsActivity extends AppCompatActivity {
    ImageButton btnsend;
    EditText textsend;
    DatabaseReference reference;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PRE="shared";
    private static final String UID="userID";
    String uid;
    CircleImageView profile_img;
    TextView username;
    FirebaseUser fuser;
    MessageAdapter messageAdapter;
    List<Chats> mchat;
    RecyclerView recyclerView;
    Intent intent;
    ImageView arrow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btnsend=findViewById(R.id.btn_send);
        textsend=findViewById(R.id.edt_send);
        sharedPreferences=getSharedPreferences(SHARED_PRE,MODE_PRIVATE);
        uid=sharedPreferences.getString(UID,null);
        arrow=findViewById(R.id.backarrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent =new Intent(ChatsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        profile_img=findViewById(R.id.profile_img);
        username=findViewById(R.id.username);
        intent=getIntent();
        final String userid=intent.getStringExtra("userid");
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String meg =textsend.getText().toString();
                if(!meg.equals("")){
                    sendMessage(fuser.getUid(),userid,meg);
                }else{
                    Toast.makeText(ChatsActivity.this,"Message is Empty",Toast.LENGTH_SHORT).show();
                }
                textsend.setText("");
            }
        });
        reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
        readMessage(fuser.getUid(),userid,null);
       /* reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                User user=dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if(user.getProfile_image().equals("default")){
                    profile_img.setImageResource(R.mipmap.ic_launcher);
                }else{
                    profile_img.setImageResource(R.mipmap.ic_launcher);
                }
                readMessage(fuser.getUid(),userid,user.getProfile_image());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });*/

        recyclerView=findViewById(R.id.chat_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


    }
    private void sendMessage(String sender,String receiver,String message){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciver",receiver);
        hashMap.put("message",message);
        reference.child("Chats").child("Personal").push().setValue(hashMap);
    }
    private void readMessage(final String myid, final String userid, final String imageurl){
        mchat=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Chats").child("Personal");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                mchat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren());
                Chats chat=dataSnapshot.getValue(Chats.class);
                assert chat != null;
            //    if(chat.getSender().equals(userid) || chat.getSender().equals(myid)){
                    mchat.add(chat);
           //     }
                messageAdapter=new MessageAdapter(ChatsActivity.this,mchat,imageurl);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }
}
