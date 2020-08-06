package com.example.foodorderingsys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class custSeeProduct extends AppCompatActivity {
    TextView tvn, tvq, etp, etc, tBill, tQty;
    ImageView iv, iv2;
    String q, i, emp, v, id;
    String name, price, qty, x, v1, v2, v3, v4;
    String s, sname, category, pprice, quantity, img, n;
    public static final String mypreference = "mypref";

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    int count = 1;
    int count1 = 1;
    int icon, icon1;
    int i1 = 0;
    int bill = 0, totBill = 0;
    Button b1, cButton;

    private ArrayList<Product> scores;
    private MySharedPreference sharedPreference;
    private HashSet<String> scoreset;
    private Gson gson;
    private View btnGet;
    private ViewGroup buttonLayout, listViewLayout;
    private ListView listScore;
    private View btnOK, btnCon;
    private View btnAdd, btnSub;
    Product highscore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cust_see_product);


        gson = new Gson();
        sharedPreference = new MySharedPreference(getApplicationContext());
        getHighScoreListFromSharedPreference();

        listScore = (ListView) findViewById(R.id.list);
        buttonLayout = (ViewGroup) findViewById(R.id.layout_add);
        btnOK = findViewById(R.id.button5);
        btnCon = findViewById(R.id.confirm_order);
        btnGet = findViewById(R.id.fab2);
        listViewLayout = (ViewGroup) findViewById(R.id.layout_listview);
        btnAdd = findViewById(R.id.buttonadd2);
        tBill = findViewById(R.id.totBill);
        tvn = findViewById(R.id.TextViewName);
        tQty = findViewById(R.id.tvitemqty);
        tvq = findViewById(R.id.textViewqty);
        etp = findViewById(R.id.editTextprice);
        etc = findViewById(R.id.etcat);
        iv = findViewById(R.id.imageView);
        iv2 = findViewById(R.id.ivitemimg);
        b1 = findViewById(R.id.buttonminus);
        cButton = findViewById(R.id.confirm_order);

        Intent it = getIntent();
        i = it.getStringExtra("img");
        emp = it.getStringExtra("ename");
        x = it.getStringExtra("cart");
        if (it.hasExtra("cart")) {
            if (x.equals("cart")) {
                if (scores.size() == 0) {
                    Toast.makeText(custSeeProduct.this, "No items in cart", Toast.LENGTH_SHORT).show();

                    listViewLayout.setVisibility(View.VISIBLE);
                    cButton.setVisibility(View.INVISIBLE);
                    buttonLayout.setVisibility(View.GONE);
                } else
                {
                    getHighScoreListFromSharedPreference(); //get data
                    //set adapter for listview and visible it
                    ListViewAdapter adapter = new ListViewAdapter(custSeeProduct.this, R.layout.item_listview, scores);
                    listScore.setAdapter(adapter);
                    listViewLayout.setVisibility(View.VISIBLE);
                    //                  tBill.setText(String.valueOf(totBill));
                    //                    tQty.setText(String.valueOf(count1));
                    buttonLayout.setVisibility(View.GONE);
                }
            } else if (x.equals("product")) {
                buttonLayout.setVisibility(View.VISIBLE);
            }
        }
        //tBill.setText(v1);

        String uri = "drawable/" + i;
        icon = getResources().getIdentifier(uri, "drawable", getPackageName());
        iv.setImageResource(icon);
        tvn.setText(it.getStringExtra("name"));
        etp.setText(it.getStringExtra("price"));
        etc.setText(it.getStringExtra("category"));


        name = it.getStringExtra("name");
        price = it.getStringExtra("price");
        qty = String.valueOf(count);


        btnGet.setOnClickListener(onGettingDataListener());
        btnOK.setOnClickListener(onAddToCartListener());
        //btnCon.setOnClickListener(onConfirmListener());
        //btnAdd.setOnClickListener(onAdding());
        //btnSub.setOnClickListener(onSubtracting());


        v1 = it.getStringExtra("name1");
        v2 = it.getStringExtra("n");
        v3 = it.getStringExtra("img1");
        v4 = it.getStringExtra("quantity");

        if (it.hasExtra("name1")) {
            if (v1.equals("edit")) {
                //Toast.makeText(custSeeProduct.this, v2, Toast.LENGTH_SHORT).show();
                db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, null,
                        DatabaseContract.Product.COL_PRODUCTNAME + " = ? ",
                        new String[]{v2}, null, null, null, null);

                while (cursor.moveToNext()) {
                    // buffer.append("Id :"+ cursor.getString(0)+"\n");
                    sname = cursor.getString(1);
                    category = cursor.getString(2);
                    pprice = cursor.getString(3);
                    //quantity = cursor.getString(4);
                    img = cursor.getString(4);
                }

                String uri1 = "drawable/" + v3;
                icon1 = getResources().getIdentifier(uri1, "drawable", getPackageName());
                iv.setImageResource(icon1);
                tvn.setText(sname);
                etp.setText(pprice);
                etc.setText(category);
                qty = String.valueOf(count);
                tvq.setText(String.valueOf(v4));
                count = Integer.valueOf(v4);
                buttonLayout.setVisibility(View.VISIBLE);

                db.close();
            }
        }
    }

    public void add(View view) {
        count = count + 1;
        tvq.setText(String.valueOf(count));
        b1.setVisibility(View.VISIBLE);
    }

    public void sub(View view) {
        if (count == 1) {
            //b1.setEnabled(false);
            b1.setVisibility(View.INVISIBLE);
            Toast.makeText(custSeeProduct.this, "Minimum quantity", Toast.LENGTH_SHORT).show();
        } else {
            count = count - 1;
        }
        tvq.setText(String.valueOf(count));
    }



    public void menu(View view) {
        Intent i4 = new Intent(this, CategoryMenu.class);
        startActivity(i4);
        finish();
    }


    public void addItem(View view) {
        Intent i = new Intent(this, CustSignIn.class);
        String jsonScore = sharedPreference.getHighScoreList();
        i.putExtra("cart", jsonScore);
        startActivity(i);
    }


    private void saveScoreListToSharedpreference(ArrayList<Product> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);
        sharedPreference.saveHighScoreList(jsonScore);
        //save to shared preference
    }


    private void getHighScoreListFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreList();
        Type type = new TypeToken<List<Product>>() {}.getType();
        scores = gson.fromJson(jsonScore, type);

        if (scores == null) {
            scores = new ArrayList<>();
        }
    }



    private View.OnClickListener onGettingDataListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (scores.size() == 0) {
                    Toast.makeText(custSeeProduct.this, "No items in Cart", Toast.LENGTH_SHORT).show();
                } else {
                    getHighScoreListFromSharedPreference(); //get data
                    //set adapter for listview and visible it

                    ListViewAdapter adapter = new ListViewAdapter(custSeeProduct.this, R.layout.item_listview, scores);
                    listScore.setAdapter(adapter);
                    listViewLayout.setVisibility(View.VISIBLE);
                    buttonLayout.setVisibility(View.GONE);
                }
            }
        };
    }



    private View.OnClickListener onAddToCartListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHighScoreListFromSharedPreference();
                boolean check=false;

                for (i1 = 0; i1 < scores.size(); i1++) {

                    if (scores.get(i1).getName().equals(tvn.getText().toString())) {
                        check = true;
                    }
                    else
                        check=false;
                }
                if(!check) {
                    Product highScore = new Product();

                    if (!tvq.getText().toString().equals("0")) {
                        if (v1.equals("edit")) {
                            highScore.setName(tvn.getText().toString());
                            highScore.setPrice(etp.getText().toString());
                            highScore.setQty(tvq.getText().toString());
                            highScore.setImg(v3);
                        } else {
                            highScore.setName(tvn.getText().toString());
                            highScore.setPrice(etp.getText().toString());
                            highScore.setQty(tvq.getText().toString());
                            highScore.setImg(i);
                        }
                        scores.add(highScore); //add to scores list
                        saveScoreListToSharedpreference(scores); //save to share pref
                        Toast.makeText(custSeeProduct.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Toast.makeText(custSeeProduct.this, "Please Select Quantity", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(custSeeProduct.this, "Item already in cart", Toast.LENGTH_LONG).show();

                    ListViewAdapter adapter = new ListViewAdapter(custSeeProduct.this, R.layout.item_listview, scores);
                    listScore.setAdapter(adapter);
                    listViewLayout.setVisibility(View.VISIBLE);
                    buttonLayout.setVisibility(View.GONE);
                }
            }
        };
    }
}



