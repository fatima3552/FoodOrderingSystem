package com.example.foodorderingsys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CallUs extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_call_us);
    }

    public void Call(View v)
    {
        Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "0511122334"));

        try
        {
            startActivity(intent);
        }
        catch (SecurityException s)
        {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        }
    }

    public void smsSendMessage(View view) {
        String smsNumber = String.format("smsto: %s","03015829599");
        String sms = "";
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse(smsNumber));
        smsIntent.putExtra("sms_body", sms);
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
            Toast.makeText(getApplicationContext(), "Opening!",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "Can't resolve app for ACTION_SENDTO Intent");
        }
    }

}
