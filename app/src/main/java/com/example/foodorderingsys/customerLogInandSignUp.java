package com.example.foodorderingsys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class customerLogInandSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log_inand_sign_up);
    }
    public void CustInsert(View view)
    {
        Intent intent1= new Intent(this, CustInsertInfo.class);
        startActivity(intent1);
        finish();
    }

    public void CustLogin(View view)
    {
        Intent intent2= new Intent(this,CustSignIn.class);
        startActivity(intent2);
        finish();
    }
}