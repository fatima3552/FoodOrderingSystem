package com.example.foodorderingsys;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    androidx.appcompat.app.AlertDialog.Builder builder;
    String val1, val2, val3, val6;
    int val4, val5,intt2;
    EditText etpname, etpid, etpcat, etpprice, etpqty, etpimg;
    String pid,pname,pcat,pprice,pqty,pimg, s;
    TextView tvpname;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbHelper = new DatabaseHelper(this);
        builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etpname = (EditText) findViewById(R.id.etpname);
        etpcat = (EditText) findViewById(R.id.etpcategory);
        etpprice = (EditText) findViewById(R.id.etpprice);
        etpimg = (EditText) findViewById(R.id.etpimg);
        tvpname=(TextView)findViewById(R.id.tvusername);
        Intent intent=getIntent();
        tvpname.setText(intent.getStringExtra("ename"));
        intt2=intent.getIntExtra("num1",0);
        Intent i= getIntent();
        s=i.getStringExtra("ename");
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
                        Toast.makeText(getApplicationContext(),"your successfully logout",
                                Toast.LENGTH_SHORT).show();
                    }


                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
                //Creating dialog box
                androidx.appcompat.app.AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Logout ");
                alert.show();
                return  true;}
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void insertProdData(View view)
    {
        db = dbHelper.getWritableDatabase();
        val1 = etpname.getText().toString();
        val3 = etpcat.getText().toString();
        val6 = etpimg.getText().toString();

        if (etpprice.getText().toString().equals("")) {
            // Toast.makeText(MainActivity.this, "  contact field is empty  ", Toast.LENGTH_LONG).show();
        } else {
            val4 = Integer.parseInt(etpprice.getText().toString());
        }

        Cursor cu = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, null, DatabaseContract.Product.COL_PRODUCTNAME + " = ? ",
                new String[]{etpname.getText().toString()}, null, null, null, null);
        if (cu.getCount() > 0) {
            Toast.makeText(this, "product already present ", Toast.LENGTH_LONG).show();

        } else if (val1.equals("")) {
            Toast.makeText(this, "name field is empty can't enter the product ", Toast.LENGTH_LONG).show();

        }

        else
        {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Product.COL_PRODUCTNAME, val1);
            values.put(DatabaseContract.Product.COL_PRODUCTCATEGORY, val3);
            values.put(DatabaseContract.Product.COL_PRODUCTPRICE, val4);
            values.put(DatabaseContract.Product.COL_PRODUCTIMAGE, val6);
            long result = db.insert(DatabaseContract.Product.TABLE_PRODUCTNAME, null, values);
            if (result > 0) {
                Toast.makeText(this, "product entered", Toast.LENGTH_LONG).show();
                clear();

               /* Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                */
            } else {
                Toast.makeText(this, "product not entered", Toast.LENGTH_LONG).show();
            }

            db.close();
        }

    }

    public void viewproData(View view)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+DatabaseContract.Product.TABLE_PRODUCTNAME,null);
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(" Id :"+ res.getString(0)+"\n");
            buffer.append("Product Name :"+ res.getString(1)+"\n");
            buffer.append("Product category :"+ res.getString(2)+"\n");
            buffer.append("Product price :"+ res.getString(3)+"\n");
            buffer.append("Product image :"+ res.getString(4)+"\n\n");
        }
        // Show all data
        showMessage(" Products Information",buffer.toString());
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void SearchRecords(View v)
    {
        db = dbHelper.getWritableDatabase();
        String val1 = etpid.getText().toString();
        Cursor cursor = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, null, DatabaseContract.Product._ID + " = ? ",
                new String[] { val1}, null, null, null, null);
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                pid=  cursor.getString(0);
                pname = cursor.getString(1);
                pcat = cursor.getString(2);
                pprice= cursor.getString(3);
                pimg=cursor.getString(4);
            }

            etpname.setText(pname);
            etpid.setText(pid);
            etpcat.setText(pcat);
            etpprice.setText(pprice);
            etpimg.setText(pimg);
        }
        else
            Toast.makeText(this, "No record found kindly enter ID that you want to search  " , Toast.LENGTH_SHORT).show();

        db.close();

    }

    public void DeleteRecords(View v)
    {
        db = dbHelper.getWritableDatabase();
        Integer i1= db.delete(DatabaseContract.Product.TABLE_PRODUCTNAME, DatabaseContract.Product.COL_PRODUCTNAME +" = ?",new String[] {etpname.getText().toString()});
        if (i1 > 0) {
            Toast.makeText(this, i1+"  Records deleted: " , Toast.LENGTH_SHORT).show();
            clear();
        }
        else
            Toast.makeText(this,"Data not Deleted kindly enter ID that you want to Delete",Toast.LENGTH_LONG).show();
        db.close();
    }

    public void UpdateRecords(View v)
    {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Product.COL_PRODUCTNAME,etpname.getText().toString() );
        values.put(DatabaseContract.Product.COL_PRODUCTCATEGORY, etpcat.getText().toString());
        values.put(DatabaseContract.Product.COL_PRODUCTPRICE, etpprice.getText().toString());
        values.put(DatabaseContract.Product.COL_PRODUCTIMAGE, etpimg.getText().toString());
        String[] wherearg={etpid.getText().toString()};
        Integer count= db.update(DatabaseContract.Product.TABLE_PRODUCTNAME, values, DatabaseContract.Product.COL_PRODUCTNAME + " = ? ",wherearg);
        if (count > 0) {

            Toast.makeText(this, count+"  Records updated: " , Toast.LENGTH_SHORT).show();
            clear();
        }
        else
            Toast.makeText(this,"Data not Updated kindly enter ID that you want to update",Toast.LENGTH_LONG).show();
        db.close();
    }

    public void menu(View view)
    {
        Intent i4=new Intent(this, CategoryMenu.class);
        i4.putExtra("num",1);
        i4.putExtra("num2",10);
        i4.putExtra("num1",intt2);
        startActivity(i4);
        finish();
    }

    public void cback(View view)
    {
        Intent intent=new Intent(this,choice.class);
        intent.putExtra("ename",s);
        startActivity(intent);
    }

    public void clear()
    {
        etpname.setText("");
        etpcat.setText("");
        etpprice.setText("");
        etpimg.setText("");
    }


}
