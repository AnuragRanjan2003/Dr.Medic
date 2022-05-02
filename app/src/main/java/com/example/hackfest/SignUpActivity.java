package com.example.hackfest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import Models.User;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    EditText Email,Password,UserName,Age,Gender;
    AppCompatButton SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        Email=findViewById(R.id.et_SignUpEmail);
        Password=findViewById(R.id.et_SignUpPassword);
        UserName=findViewById(R.id.et_SignUpUsername);
        Age=findViewById(R.id.et_Age);
        Gender=findViewById(R.id.et_Gender);
        SignUp=findViewById(R.id.btn_SignUp);
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Creating Account");
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String s1=Email.getText().toString();
                String s2=UserName.getText().toString();
                String s3=Password.getText().toString();
                String s4=Age.getText().toString();
                String s5=Gender.getText().toString();
                if(s1.isEmpty()){
                    Email.setError("Please provide Email");
                    progressDialog.dismiss();
                    return;
                }else if(s2.isEmpty()){
                    UserName.setError("Please provide a username");
                    progressDialog.dismiss();
                    return;
                }else if(s3.isEmpty()){
                    Password.setError("Please set a password ");
                    progressDialog.dismiss();
                    return;
                }
                else if(s4.isEmpty()){
                    Age.setError("Please enter age");
                    progressDialog.dismiss();
                    return;
                }else if(s5.isEmpty()){
                    Gender.setError("Please Specify gender");
                    progressDialog.dismiss();
                    return;
                }else{
                    mAuth.createUserWithEmailAndPassword(s1.trim(),s3.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user=new User(s2.trim(),s4.trim(),s5.trim());
                                database.getReference("users").child(task.getResult().getUser().getUid()).setValue(user);
                                progressDialog.dismiss();
                                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                                Toast.makeText(SignUpActivity.this, "Account Created.Please Sign In", Toast.LENGTH_SHORT).show();
                                finishAffinity();
                            }else{
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}