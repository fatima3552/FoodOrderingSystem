package com.example.foodorderingsys;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOrder extends AppCompatActivity
{
    String v1, v2;
    private ArrayList<Product> scores;
    private MySharedPreference sharedPreference;
    private Gson gson;
    private ListView listScore;
    int i1 = 0, bill = 0;
    String  name;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView totBill, cName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listScore = (ListView) findViewById(R.id.list);
        dbHelper = new DatabaseHelper(this);
        totBill = findViewById(R.id.totBill);
        cName = findViewById(R.id.tvName);
        Intent i = getIntent();
        v1 = i.getStringExtra("cid");
        v2 = i.getStringExtra("var");


        gson = new Gson();
        sharedPreference = new MySharedPreference(getApplicationContext());
        getHighScoreListFromSharedPreference();

        if (scores.size() == 0)
        {
            Toast.makeText(ConfirmOrder.this, "No items in cart", Toast.LENGTH_SHORT).show();
        }

        else
        {
            ListViewAdapter adapter = new ListViewAdapter(ConfirmOrder.this, R.layout.item_listview, scores);
            listScore.setAdapter(adapter);
        }

        db = dbHelper.getWritableDatabase();
        Cursor cursor2 = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer._ID + "=?",
                new String[]{v1}, null, null, null, null);

        if (cursor2.getCount() > 0)
        {
            while (cursor2.moveToNext())
            {
                name = cursor2.getString(1);
            }
        }
        cName.setText(name);


        getHighScoreListFromSharedPreference();
        for (i1 = 0; i1 < scores.size(); i1++)
        {
            bill += Integer.parseInt(scores.get(i1).getPrice()) * Integer.parseInt(scores.get(i1).getQty());
            totBill.setText(String.valueOf(bill));
        }

    }
        private void getHighScoreListFromSharedPreference() {
            //retrieve data from shared preference
            Type type = new TypeToken<List<Product>>(){}.getType();
            scores = gson.fromJson(v2, type);

            if (scores == null) {
                scores = new ArrayList<>();
            }
        }

        public void addItem(View view)
        {
            //db = dbHelper.getWritableDatabase();
           // String column[] = {DatabaseContract.Product._ID};
            if (scores.size() == 0)
            {
                Toast.makeText(ConfirmOrder.this, "No items in cart", Toast.LENGTH_SHORT).show();
            }

            else {
                Intent i = new Intent(this, CheckOut.class);
                i.putExtra("bill", String.valueOf(bill));
                i.putExtra("cid", v1);
                i.putExtra("var", v2);
                i.putExtra("name", name);

                startActivity(i);
            }
        }


        public void menu(View view)
        {
            Intent i4 = new Intent(this, CategoryMenu.class);
            startActivity(i4);
            finish();
        }

    }


