package com.example.foodorderingsys;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddToCart extends AppCompatActivity {
    ArrayList<String> iname, iprice, iimage, iqty, send;
    TextView tv;
    GridView gridView;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String name, price, image, quantity, add, cId, pId;
    double p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gridView = findViewById(R.id.grid1View);
        tv = findViewById(R.id.tvgird);
        iname = new ArrayList<>();
        iprice = new ArrayList<>();
        iimage = new ArrayList<>();
        iqty = new ArrayList<>();

        send = new ArrayList<>();
        Intent i = getIntent();
        name = i.getStringExtra("name");
        price = i.getStringExtra("price");
        image = i.getStringExtra("img");
        quantity = i.getStringExtra("qty");

        dbHelper = new DatabaseHelper(this);
        customadopter cadpter = new customadopter(AddToCart.this, iname, iprice, iimage, iqty);
        gridView.setAdapter(cadpter);

        iname.add(name);
        iprice.add(price);
        iimage.add(image);
        iqty.add(quantity);
    }


    public void InsertOrderInfo(View view) {
        db = dbHelper.getWritableDatabase();
        cId = "1";
        //pId = 1;
        add = "i-8";
        p = 500;


       String[] columns = {DatabaseContract.Customer._ID, DatabaseContract.Customer.COL_CUSTOMERADDRESS};
        Cursor cursor = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, columns, DatabaseContract.Customer._ID + "=?", new String[]{cId}, null, null, null, null);
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext())
            {
                add = cursor.getString(1);
            }
        }

        String[] columns1 = {DatabaseContract.Product._ID, DatabaseContract.Product.COL_PRODUCTNAME};
        Cursor cursor1 = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, columns1, DatabaseContract.Product.COL_PRODUCTNAME + "=?", new String[]{name}, null, null, null, null);
        if (cursor1.getCount() == 0) {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor1.moveToNext()) {
                pId = cursor1.getString(0);
            }
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CustomerOrder.COL_O_CUSTOMERID, cId);
        values.put(DatabaseContract.CustomerOrder.COL_O_PRODUCTID, pId);
        values.put(DatabaseContract.CustomerOrder.COL_ORDERADDRESS, add);
        values.put(DatabaseContract.CustomerOrder.COL_ORDERPRICE, p);
        values.put(DatabaseContract.CustomerOrder.COL_ORDERBILL, p);

        long result = db.insert(DatabaseContract.CustomerOrder.TABLE_ORDERNAME, null, values);
        if (result > 0) {
            Toast.makeText(this, "Entered", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Not Entered", Toast.LENGTH_LONG).show();
        }
    }

    public class customadopter extends BaseAdapter {
        private Context context;
        private ArrayList name;
        private ArrayList price;
        private ArrayList image;
        private ArrayList quantity;
        private LayoutInflater layoutInflate;

        @Override
        public int getCount() {
            return image.size();
        }

        public customadopter(Context context, ArrayList name, ArrayList price, ArrayList image, ArrayList quantity) {
            this.context = context;
            this.name = name;
            this.price = price;
            this.image = image;
            this.quantity = quantity;
            this.layoutInflate = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public Object getItem(int position) {
            return iname.size();
        }

        @Override
        public long getItemId(int position) {
            return iname.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflate.inflate(R.layout.grid_row_item_cart, parent, false);
            }
            TextView tvn = convertView.findViewById(R.id.tvitemname);
            TextView tvp = convertView.findViewById(R.id.tvitemprice);
            ImageView img = convertView.findViewById(R.id.ivitemimg);
            TextView tvq = convertView.findViewById(R.id.tvpQty);
            tvn.setText(String.valueOf(name.get(position)));
            tvp.setText(String.valueOf(price.get(position)));
            final String imgname = String.valueOf(image.get(position));
            String uri = "drawable/" + imgname;
            String PACKAGE_NAME = context.getApplicationContext().getPackageName();
            int icon = context.getResources().getIdentifier(uri, "drawable", PACKAGE_NAME);
            img.setImageResource(icon);
            return convertView;
        }
    }
}