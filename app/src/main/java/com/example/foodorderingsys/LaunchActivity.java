package com.example.foodorderingsys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }
    public void Login(View v)
    {
        Intent i1=new Intent(this,loginActivity.class);
        startActivity(i1);
        finish();
    }
    public void Signup(View v)
    {
        Intent i2=new Intent(this, MainActivity.class);
        startActivity(i2);
        finish();
    }
}
