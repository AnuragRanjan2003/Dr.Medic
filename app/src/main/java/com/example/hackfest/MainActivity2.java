package com.example.hackfest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    TextView Welcome,Tv1;
    AppCompatButton Survey,Previous,Profile;
    FirebaseDatabase database;
    ExtendedFloatingActionButton LogOut;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        Welcome=findViewById(R.id.tv_welcome);
        Survey=findViewById(R.id.btn_Survey);
        Tv1=findViewById(R.id.tv_tv1);
        Previous=findViewById(R.id.btn_Prev);
        LogOut=findViewById(R.id.fab_logout);
        Profile=findViewById(R.id.btn_profile);
        Animation open= AnimationUtils.loadAnimation(this,R.anim.button_anim);
        Animation drop=AnimationUtils.loadAnimation(this,R.anim.drop_down);
        LogOut.startAnimation(drop);
        Survey.startAnimation(open);
        Tv1.startAnimation(drop);
        Previous.startAnimation(open);
        Profile.startAnimation(open);
        String fixed="Welcome Back\n";
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser==null){
            startActivity(new Intent(this,AskActivity.class));
            finishAffinity();
        }else {
            database.getReference("users").child(firebaseUser.getUid()).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        String s = fixed + String.valueOf(task.getResult().getValue());
                        Welcome.setText(s);
                        Welcome.startAnimation(drop);
                    }
                }
            });
            Survey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity2.this, SurveyActivity.class));
                }
            });
            LogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                    Toast.makeText(MainActivity2.this, "You Signed Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity2.this, SignInActivity.class));
                    finishAffinity();
                }
            });
            Previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity2.this,PreviousActivity.class));
                }
            });
            Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity2.this,ProfileActivity.class));
                }
            });
        }



    }
}