package com.example.teamup.Login;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.teamup.HomeActivity;
import com.example.teamup.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;


public class LoginActivity extends AppCompatActivity {
    EditText emailId, password,teamcode;
    Button btnSignIn;
    TextView tvSignUp;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mFirebaseAuth;
  //  private TextView textViewUser;
   // private ImageView mLogo;
    private static final String TAG="FacebookAuthentication";
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PRE="shared";
    private static final String TEAM_CODE="teamcode";
    private static final String NAME="fbname";
    private static final String URL="phurl";
    private static final String UID="userID";
    private static final String FBLOG="fblogin";
    String name,url,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignIn = findViewById(R.id.button2);
        tvSignUp = findViewById(R.id.textView);
        teamcode=findViewById(R.id.teamcode);
        sharedPreferences=getSharedPreferences(SHARED_PRE,MODE_PRIVATE);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                   // Toast.makeText(LoginActivity.this,"Please com.example.teamup.Login",Toast.LENGTH_SHORT).show();
                }
            }
        };


        btnSignIn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Entered Email and Password is incorrect",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                FirebaseUser user=mFirebaseAuth.getCurrentUser();
                                updateUI(user);
                                Intent intToHome = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });

        mFirebaseAuth=FirebaseAuth.getInstance();

        // textViewUser=findViewById(R.id.fbtxt);
       //  mLogo=findViewById(R.id.fbimg);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(authStateListener!=null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }




    private void updateUI(FirebaseUser user){
        if(user!=null){
            uid=user.getUid();
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(UID, uid);
            editor.apply();
          //  textViewUser.setText(user.getDisplayName());
            name=user.getDisplayName();
            if(user.getPhotoUrl()!=null){
                String photoUrl=user.getPhotoUrl().toString();
                photoUrl=photoUrl+"?type=large";
                url=photoUrl;
              // Picasso.get().load(photoUrl).into(mLogo);

                editor.putString(NAME, name);
                editor.putString(URL, url);
                editor.apply();
            }
        }
        else{
          //  textViewUser.setText("Unavailable");
          //  mLogo.setImageResource(R.drawable.loginback);
        }
    }


}
