package com.example.hackfest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    EditText Email, Password;
    AppCompatButton SignIn;
    TextView NewUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);
        Email = findViewById(R.id.et_SignInEmail);
        Password = findViewById(R.id.et_SignInPassword);
        SignIn = findViewById(R.id.btn_SignIn);
        NewUser = findViewById(R.id.tv_New_User);
        mAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In");

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String s1 = Email.getText().toString();
                String s2 = Password.getText().toString();
                if (s1.isEmpty()) {
                    Email.setError("Please Provide Email");
                    progressDialog.dismiss();
                    return;
                } else if (s2.isEmpty()) {
                    Password.setError("please enter password");
                    progressDialog.dismiss();
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(s1.trim(), s2.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                startActivity(new Intent(SignInActivity.this, MainActivity2.class));
                                finishAffinity();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignInActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    NewUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                            finishAffinity();
                        }
                    });
                }
            }
        });
    }
}