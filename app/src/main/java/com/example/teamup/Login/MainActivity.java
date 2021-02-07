package com.example.teamup.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.radar.sdk.Radar;

public class MainActivity extends AppCompatActivity {
    EditText emailId, password,teamcode,username;
    Button btnSignUp;
    TextView tvSignIn;
    String uid,getUrl;
    FirebaseAuth mFirebaseAuth;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PRE="shared";
    private static final String TEAM_CODE="teamcode";
    private static final String URL="phurl";
    private static final String UID="userID";
    private DatabaseReference Post;
    CircleImageView circleimg;
    StorageReference storageReference;
    private static final int ImageBack=1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Radar.initialize(this, "prj_test_pk_97c6fc2541aa46acf49104d64e08881638fd6753");

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText1);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button2);
        tvSignIn = findViewById(R.id.textView);
        teamcode=findViewById(R.id.teamcode);
        circleimg= findViewById(R.id.cicimg);
        circleimg.setImageResource(R.drawable.loginback);
        storageReference= FirebaseStorage.getInstance().getReference("profile_uploads");
        sharedPreferences=getSharedPreferences(SHARED_PRE,MODE_PRIVATE);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(TEAM_CODE,teamcode.getText().toString());
        editor.apply();

                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {

                                save();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        circleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openImage();
            }
        });
    }

    private void save() {
        HashMap<String,Object> map = new HashMap<>();
        onAuthStateChanged(mFirebaseAuth);
        init();
        map.put("username",username.getText().toString());
        map.put("teamcode",teamcode.getText().toString());
        map.put("uid", uid);
        map.put("profile_image",getUrl);
        System.out.println("azx"+uid);
        Post.child(uid)
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("jfbvkj", "onComplete: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("jfbvkj", "onFailure: "+e.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("jfbvkj", "onSuccess: ");
            }
        });
    }
    private void init() {
        username=findViewById(R.id.editText);
        Post = FirebaseDatabase.getInstance().getReference().child("Users");
        sharedPreferences=getSharedPreferences(SHARED_PRE,MODE_PRIVATE);
        getUrl=sharedPreferences.getString(URL,null);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(UID,uid);
        editor.apply();
    }
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
            FirebaseUser user=firebaseAuth.getCurrentUser();
            updateUI(user);
        }
    private void updateUI(FirebaseUser user){

            uid = user.getUid();
        }
        private void openImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,ImageBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ImageBack &&resultCode==RESULT_OK )//&&data!=null && data.getData()!=null)
        {
            imageUri=data.getData();
            StorageReference Imagename = storageReference.child("profileimg_"+imageUri.getLastPathSegment());
            Imagename.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(URL,imageUri.toString());
                    editor.apply();
                    Picasso.get().load(imageUri).error(R.drawable.loginback).into(circleimg);
                    Toast.makeText(MainActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
                }
            })     .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(MainActivity.this,"Upload Failed",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
