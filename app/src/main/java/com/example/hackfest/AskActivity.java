package com.example.hackfest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        getSupportActionBar().hide();
        Button SignIn;
        AppCompatButton SignUp;
        SignIn=findViewById(R.id.btn_signIn1);
        SignUp=findViewById(R.id.btn_signUp1);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskActivity.this,SignInActivity.class));
                finishAffinity();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskActivity.this,SignUpActivity.class));
                finishAffinity();
            }
        });
    }
}