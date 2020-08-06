package com.example.foodorderingsys;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class showCustinfo extends AppCompatActivity {
    androidx.appcompat.app.AlertDialog.Builder builder;
    EditText cName, cid, cEmail, cCon, cPass,cAdd;
    TextView tvpname;
    String s;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custinfo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cName = (EditText) findViewById(R.id.etename);
        cid = (EditText) findViewById(R.id.eteid);
        cEmail = (EditText) findViewById(R.id.etemail);
        cCon = (EditText) findViewById(R.id.etcontno);
        cPass = (EditText) findViewById(R.id.etpass);
        cAdd=(EditText) findViewById(R.id.etcadd);
        tvpname = (TextView) findViewById(R.id.tvusername);
        dbHelper = new DatabaseHelper(this);
        builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        Intent intent=getIntent();
        s=intent.getStringExtra("ename");
        cName.setText(intent.getStringExtra("cn"));
        cid.setText(intent.getStringExtra("cid"));
        cEmail.setText(intent.getStringExtra("ce"));
        cCon.setText(intent.getStringExtra("cc"));
        cPass.setText(intent.getStringExtra("cp"));
        cAdd.setText(intent.getStringExtra("ca"));
        tvpname.setText(intent.getStringExtra("cn"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout: {
                builder.setMessage("Do you want to log out?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i = new Intent(getApplicationContext(), loginActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(), "your successfully logout",
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
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void back(View view)
    {
        Intent i2=new Intent(this, showcustlist.class);
        i2.putExtra("ename",s);
        startActivity(i2);
        finish();

    }
    public void cUpdateRecords(View v) {
        db = dbHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.Customer.COL_CUSTNAME, cName.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTMAIL, cEmail.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTPASSWORD, cPass.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTCONTACTNO, cCon.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTOMERADDRESS, cAdd.getText().toString());
        args.put(DatabaseContract.Employee._ID, cid.getText().toString());
        String[] wherearg = {cid.getText().toString()};

        Cursor e = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTNAME
                + " = ? ", new String[]{cName.getText().toString()}, null, null, null, null);
        Cursor p =db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTMAIL
                + " = ? ", new String[]{cEmail.getText().toString()}, null, null, null, null);
        Cursor q =db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTPASSWORD
                + " = ? ", new String[]{cPass.getText().toString()}, null, null, null, null);
        Cursor r =db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTCONTACTNO
                + " = ? ", new String[]{cCon.getText().toString()}, null, null, null, null);
        Cursor s =db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTOMERADDRESS
                + " = ? ", new String[]{cAdd.getText().toString()} , null, null, null, null);
        Cursor t =db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer._ID
                + " = ? ", new String[]{cid.getText().toString()} , null, null, null, null);

        if (e.getCount() > 0 && p.getCount() > 0&& q.getCount() > 0&& r.getCount() > 0&& s.getCount() > 0&& t.getCount() > 0) {
            Toast.makeText(this, "record already updated", Toast.LENGTH_LONG).show();
        }else
        {
            Integer count = db.update(DatabaseContract.Customer.TABLE_CUSTNAME, args, DatabaseContract.Customer._ID + " = ? ", wherearg);
            if (count > 0) {

                Toast.makeText(this, count + "  Records updated: ", Toast.LENGTH_SHORT).show();
                clear();
            }
            else if(cid.getText().toString().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Data not Updated kindly enter ID that you want to update", Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(this, "Data not Updated id not found", Toast.LENGTH_LONG).show();

            }

            db.close();
        }
    }
    public void clear()
    {
        cid.setText("");
        cName.setText("");
        cEmail.setText("");
        cPass.setText("");
        cAdd.setText("");
        cCon.setText("");
    }
}
