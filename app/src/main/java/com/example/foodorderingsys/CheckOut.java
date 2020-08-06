package com.example.foodorderingsys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CheckOut extends AppCompatActivity {

    String v1, v2;
    private ArrayList<Product> scores;
    private MySharedPreference sharedPreference;
    private Gson gson;
    private ListView listScore;
    int i1 = 0, bill = 0;
    String id, add, productId="", productPrice="", name;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView totBill, cName, tBill;
    RadioButton rCash, rCard;

    Animation topAnim, botAnim;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        String bill = i.getStringExtra("bill");
        tBill = findViewById(R.id.tvBill);
        tBill.setText(bill);

        listScore = (ListView) findViewById(R.id.list);
        dbHelper = new DatabaseHelper(this);
        cName = findViewById(R.id.tvName);
        rCash = findViewById(R.id.radioButtoncash);
        rCard = findViewById(R.id.radioButtoncard);
        v1 = i.getStringExtra("cid");
        v2 = i.getStringExtra("var");
        name = i.getStringExtra("name");


        gson = new Gson();
        sharedPreference = new MySharedPreference(getApplicationContext());
        getHighScoreListFromSharedPreference();

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        img = findViewById(R.id.imageView4);
        img.setAnimation(botAnim);

        cName.setText(name);

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
        db = dbHelper.getWritableDatabase();
        String column[] = {DatabaseContract.Product._ID};
        if (scores.size() == 0 )
        {
            Toast.makeText(this, "No items in cart", Toast.LENGTH_SHORT).show();
        }

        else if  (!rCash.isChecked() && !rCard.isChecked())
        {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
        }

        else
        {
            getHighScoreListFromSharedPreference();

            for (i1 = 0; i1 < scores.size(); i1++) {
                Cursor cursor = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, null, DatabaseContract.Product.COL_PRODUCTNAME + "=?",
                        new String[]{String.valueOf(scores.get(i1).getName())}, null, null, null, null);
                Cursor cursor1 = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer._ID + "=?",
                        new String[]{v1}, null, null, null, null);

                if (cursor.getCount() > 0 && cursor1.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        id = cursor.getString(0);
                    }
                    while (cursor1.moveToNext()) {
                        add = cursor1.getString(4);
                    }
                }


                productId += id + ", ";
                productPrice += scores.get(i1).getPrice() + ",";
            }

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.CustomerOrder.COL_O_CUSTOMERID, String.valueOf(v1));
            values.put(DatabaseContract.CustomerOrder.COL_O_PRODUCTID, String.valueOf(productId));
            values.put(DatabaseContract.CustomerOrder.COL_ORDERADDRESS, add);
            values.put(DatabaseContract.CustomerOrder.COL_ORDERBILL, String.valueOf(bill));
            values.put(DatabaseContract.CustomerOrder.COL_ORDERPRICE, String.valueOf(productPrice));
            long result = db.insert(DatabaseContract.CustomerOrder.TABLE_ORDERNAME, null, values);
            if (result>0)
            {
                Toast.makeText(getApplicationContext(),"Order Placed", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Error placing order", Toast.LENGTH_LONG).show();
            }


            String phoneNo = "03015829599";
            String sms = "Your order is on it's way, will be there shortly.";
            try
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
            }

            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"SMS Failed, please try again later!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}