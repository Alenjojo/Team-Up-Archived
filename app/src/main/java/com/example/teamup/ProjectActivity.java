package com.example.teamup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamup.Chats.ChatsActivity;

public class ProjectActivity  extends AppCompatActivity {
    ImageView arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        arrow=findViewById(R.id.backarrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent =new Intent(ProjectActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
