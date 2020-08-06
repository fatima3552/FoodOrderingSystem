package com.example.foodorderingsys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private  static int SLASH_SCREEN = 5000; //5sec
    Animation topAnim, botAnim;
    ImageView img;
    TextView logo,desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        img = findViewById(R.id.imageView);
        logo = findViewById(R.id.tLogo);
        desc = findViewById(R.id.tDesc);

        img.setAnimation(topAnim);
        logo.setAnimation(botAnim);
        desc.setAnimation(botAnim);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(StartActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        },SLASH_SCREEN);
//        getSupportActionBar().hide();

    }
}
