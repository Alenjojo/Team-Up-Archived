package com.example.teamup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Fargments.Fragment_Chat;
import com.example.teamup.Fargments.Fragment_Home;
import com.example.teamup.Fargments.Fragment_Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import com.example.teamup.Adapter.PostAdapter;

public class HomeActivity extends AppCompatActivity {
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


    private Fragment_Home fragment_home;
    private Fragment_Chat fragment_chat;
    private Fragment_Users fragment_users;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        fbname=findViewById(R.id.proname);
//        prourl=findViewById(R.id.prourl);

        chipNavigationBar=findViewById(R.id.chipnavigation);
        chipNavigationBar.setItemSelected(R.id.home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Fragment_Home()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i){
                switch(i){
                    case R.id.home:
                        fragment=new Fragment_Home();
                        break;
                    case R.id.chats:
                        fragment=new Fragment_Chat();
                        break;
                    case R.id.profile:
                        fragment=new Fragment_Users();
                        break;
                }
                if(fragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();
                }
            }
        });


        chipNavigationBar.showBadge(R.id.home);
        chipNavigationBar.showBadge(R.id.chats, 88);
//        menu.showBadge(R.id.menu_favorites, 88)
//        menu.showBadge(R.id.settings, 10000)

//       // progressBar=findViewById(R.id.progressBar);
//       // progressBar.setVisibility(View.VISIBLE);
//        sharedPreferences=getSharedPreferences(SHARED_PRE,MODE_PRIVATE);
//        teamcode=sharedPreferences.getString(TEAM_CODE,null);
//         fbproname=sharedPreferences.getString(NAME,null);
//         url=sharedPreferences.getString(URL,null);
//         uid=sharedPreferences.getString(UID,null);
//
//
////teamcode="team 1";
////        fbname.setText(fbproname);
//        Picasso.get().load(url).error(R.drawable.loginback).into(prourl);
//
//            Get = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
//            Get.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
//                    String name = (String) dataSnapshot.child("username").getValue();
//                    String teamcodefire= (String) dataSnapshot.child("teamcode").getValue();
//                    String profile_img= (String) dataSnapshot.child("profile_image").getValue();
//                    fbname.setText(name);
//                    Picasso.get().load(profile_img).error(R.drawable.loginback).into(prourl);
//
//                    SharedPreferences.Editor editor=sharedPreferences.edit();
//                    editor.putString(TEAM_CODE,teamcodefire);
//                    editor.putString(URL,profile_img);
//                    editor.putString(NAME,name);
//                    editor.apply();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError){
//
//                }
//
//
//        recyclerView = findViewById(R.id.recyclerDashboard);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//if(teamcode!=null) {
//    FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
//                    .setQuery(FirebaseDatabase.getInstance().getReference().child("teams").child(teamcode), Post.class)
//                    .build();
//
//    adapter = new PostAdapter(options);
//    recyclerView.setAdapter(adapter);
//}
//      //  progressBar.setVisibility(View.GONE);
//
//
//
//        btnSet=findViewById(R.id.imageButton3);
//        btnMes=findViewById(R.id.imageButton);
//        btnPro=findViewById(R.id.imageButton2);
//        btnaddmem=findViewById(R.id.btnadd);
//        llContext=findViewById(R.id.llcontent);
//
//

//        });
//        btnMes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intSignUp = new Intent(HomeActivity.this, MessageActivity.class);
//                startActivity(intSignUp);
//             //   fragment_chat=new Fragment_Chat();
//             //   getSupportFragmentManager().beginTransaction().replace(R.id.framelay,fragment_chat).commit();
//            }
//        });
//        btnPro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intSignUp = new Intent(HomeActivity.this, ProfileActivity.class);
//                startActivity(intSignUp);
//               // fragment_users=new Fragment_Users();
//              //  getSupportFragmentManager().beginTransaction().replace(R.id.framelay,fragment_users).commit();
//            }
//        });
//         btnaddmem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intSignUp = new Intent(HomeActivity.this, AddProjectActivity.class);
//                startActivity(intSignUp);
//            }
//        });
//      /*  llContext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(HomeActivity.this,ProjectActivity.class);
//                startActivity(intent);
//            }
//        });*/

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
/*<RelativeLayout
            android:id="@+id/progressLayout"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:background="#fff">

<ProgressBar
                android:id="@+id/progressBar"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_centerInParent="true"/>

</RelativeLayout>*/