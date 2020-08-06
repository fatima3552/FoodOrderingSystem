package com.example.foodorderingsys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ImageSlider imageSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        imageSlider=findViewById(R.id.image_slider);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.c1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.c2 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.b1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.c3 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.p4 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.p2 , ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent i = new Intent(this, NavigationDrawer.class);
                startActivity(i);
                break;
            case R.id.nav_Menucategory:
                Intent i1 = new Intent(this, CategoryMenu.class);
                startActivity(i1);
                break;
            /*case R.id.nav_login:
                Intent i2 = new Intent(this, CustSignIn.class);
                startActivity(i2);
                break;*/
            case R.id.nav_signup:
                Intent i3 = new Intent(this, CustInsertInfo.class);
                startActivity(i3);
                break;
            case R.id.nav_cart:
                Intent intent = new Intent(getApplicationContext(), custSeeProduct.class);
                String x = "cart";
                intent.putExtra("cart", x);
                startActivity(intent);
                break;
            case R.id.nav_admin:
                Intent i4 = new Intent(this, loginActivity.class);
                startActivity(i4);
                break;
            /*case R.id.nav_Feedback:
                Toast.makeText(this, "feedBack", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.nav_contact:
                Toast.makeText(this, "contact", Toast.LENGTH_SHORT).show();
                Intent i6 = new Intent(this, CallUs.class);
                startActivity(i6);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else
        {
            super.onBackPressed();
        }
    }

    public void seeMenu(View view) {
        Intent i5 = new Intent(this, CategoryMenu.class);

        startActivity(i5);

    }
}
