package com.example.hackfest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    TextView Welcome;
    AppCompatButton Survey,Previous;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        Welcome=findViewById(R.id.tv_welcome);
        Survey=findViewById(R.id.btn_Survey);
        Previous=findViewById(R.id.btn_Prev);
        String fixed="Welcome Back\n";
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        database.getReference("users").child(firebaseUser.getUid()).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    String s=fixed+String.valueOf(task.getResult().getValue());
                    Welcome.setText(s);
                }
            }
        });
        Survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,SurveyActivity.class));
            }
        });


    }
}