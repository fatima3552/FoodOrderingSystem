package com.example.foodorderingsys;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryMenu extends AppCompatActivity {
    FloatingActionButton fab,fab2;
    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<String> category;
    SQLiteDatabase db;
    String s;
    int intt,intt2;
    CustomAdopter customAdopter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category_menu);
        fab  = findViewById(R.id.fab);
        fab2 = findViewById(R.id.fab);
        dbHelper = new DatabaseHelper(this);
        category = new ArrayList<>();
        Intent i=getIntent();
        s= i.getStringExtra("ename");
        intt=i.getIntExtra("num",0);
        intt2=i.getIntExtra("num1",0);
        recyclerView = (RecyclerView) findViewById(R.id.cateMenuRecyclerview);
        if(intt==1||intt==3) {
            Drawable drawable = getResources().getDrawable(R.drawable.add1);
            fab.setImageDrawable(drawable);
        }

        if(intt==1||intt==3) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    intent.putExtra("num1",intt2);
                    intent.putExtra("ename", s);
                    startActivity(intent);
                }
            });
        }
        else {
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), custSeeProduct.class);
                    String x = "cart";
                    intent.putExtra("cart", x);
                    startActivity(intent);
                }
            });
        }
        display();

        customAdopter = new CustomAdopter(CategoryMenu.this, category,s,intt2);
        recyclerView.setAdapter(customAdopter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryMenu.this));
    }

    void display() {
        category.add("starters");
        category.add("burgers");
        category.add("italian");
        category.add("chinese");
        category.add("dessert");
        category.add("salad");
        category.add("drinks");
    }

    public void Menu(View v) {
        if(intt==1||intt==3) {
            Intent i2 = new Intent(this, choice.class);
            i2.putExtra("ename", s);
            i2.putExtra("num", 1);
            i2.putExtra("num1", 10);
            startActivity(i2);
            finish();
        }else
        {
            Intent i8 = new Intent(this, NavigationDrawer.class);
            startActivity(i8);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.searchmenu,menu);
        MenuItem searchitem =menu.findItem(R.id.search);
        SearchView searchView =(SearchView)searchitem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdopter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
