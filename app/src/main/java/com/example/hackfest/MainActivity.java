package com.example.hackfest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                }catch (Exception e){ e.printStackTrace();}
                finally {
                    startActivity(new Intent(MainActivity.this,AskActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}