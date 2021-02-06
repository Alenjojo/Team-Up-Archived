package com.example.teamup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddProjectActivity extends AppCompatActivity {

    private EditText title,description,author;
    private TextView text;
    private Button save;
    private DatabaseReference Post;
    ImageView btnhom;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PRE="shared";
    private static final String TEAM_CODE="teamcode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        init();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        btnhom=findViewById(R.id.imgback);
        btnhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(AddProjectActivity.this, HomeActivity.class);
                startActivity(intSignUp);
            }
        });

    }

    private void save() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("title",title.getText().toString());
        map.put("description",description.getText().toString());
        map.put("author",author.getText().toString());

        Post.push()
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
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        author = findViewById(R.id.author);
        save = findViewById(R.id.save);
        text = findViewById(R.id.text);
        sharedPreferences=getSharedPreferences(SHARED_PRE,MODE_PRIVATE);
        String name=sharedPreferences.getString(TEAM_CODE,null);
        Post = FirebaseDatabase.getInstance().getReference().child("teams").child(name);
    }


}
