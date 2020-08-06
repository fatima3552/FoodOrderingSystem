package com.example.foodorderingsys;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class displayProdInfo extends AppCompatActivity {
    AlertDialog.Builder builder;
    TextView tvn, tvq;
    EditText etp, etc;
    ImageView iv;
    String q, i, emp;
    int count, intt2;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_prod_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvn = findViewById(R.id.TextViewName);
        tvq = findViewById(R.id.textViewqty);
        etp = findViewById(R.id.editTextprice);
        etc = findViewById(R.id.etcat);
        builder = new AlertDialog.Builder(this);
        iv = findViewById(R.id.imageView);
        dbHelper = new DatabaseHelper(this);
        Intent it = getIntent();
        q = it.getStringExtra("quantity");
        i = it.getStringExtra("img");
        intt2 = it.getIntExtra("num1", 0);
        emp = it.getStringExtra("ename");
        count = Integer.parseInt(q);
        String uri = "drawable/" + i;
        int icon = getResources().getIdentifier(uri, "drawable", getPackageName());
        iv.setImageResource(icon);
        tvn.setText(it.getStringExtra("name"));
        tvq.setText(q);
        etp.setText(it.getStringExtra("price"));
        etc.setText(it.getStringExtra("category"));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:{
                builder.setMessage("Do you want to log out?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent   i = new Intent(getApplicationContext(), loginActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(),"your successfully logout", Toast.LENGTH_SHORT).show();
                    }


                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Logout ");
                alert.show();
                return  true;}
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void add(View view) {

        count = count + 1;
        tvq.setText(String.valueOf(count));

    }

    public void sub(View view) {
        count = count - 1;
        tvq.setText(String.valueOf(count));

    }

    public void UpdatepRecords(View v) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Product.COL_PRODUCTNAME, tvn.getText().toString());
        values.put(DatabaseContract.Product.COL_PRODUCTCATEGORY, etc.getText().toString());
        values.put(DatabaseContract.Product.COL_PRODUCTPRICE, etp.getText().toString());
        String[] wherearg = {tvn.getText().toString()};
        Cursor e = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, null, DatabaseContract.Product.COL_PRODUCTPRICE
                        + " = ? ", new String[]{etp.getText().toString()}
                , null, null, null, null);
        if (e.getCount() > 0) {
            Toast.makeText(this, "record already updated", Toast.LENGTH_LONG).show();
        } else {
            Integer count = db.update(DatabaseContract.Product.TABLE_PRODUCTNAME, values, DatabaseContract.Product.COL_PRODUCTNAME + " = ? ", wherearg);
            if (count > 0) {
                Toast.makeText(this, "  Records updated: ", Toast.LENGTH_SHORT).show();
                db.close();
            } else {

                Toast.makeText(this, "record not updated", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addnewitems(View v) {
        Intent i2 = new Intent(this, Main2Activity.class);
        i2.putExtra("ename", emp);
        startActivity(i2);
        finish();

    }

    public void menu(View view) {
        Intent i4 = new Intent(this, CategoryMenu.class);
        i4.putExtra("num", 3);
        i4.putExtra("ename", emp);
        i4.putExtra("num1", intt2);

        startActivity(i4);
        finish();
    }
}
