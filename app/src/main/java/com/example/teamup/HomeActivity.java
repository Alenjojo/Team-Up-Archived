package com.example.teamup;

import android.Manifest;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Fargments.Fragment_Chat;
import com.example.teamup.Fargments.Fragment_Home;
import com.example.teamup.Fargments.Fragment_Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import com.example.teamup.Adapter.PostAdapter;

import io.radar.sdk.Radar;
import io.radar.sdk.model.RadarContext;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;

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
        Radar.initialize(this, "prj_test_pk_97c6fc2541aa46acf49104d64e08881638fd6753");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION }, 0);
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 0);
        }

        Radar.getContext(new Radar.RadarContextCallback() {
            @Override
            public void onComplete(Radar.RadarStatus status, Location location, RadarContext context) {
                // do something with context
            }
        });

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

    }


}
