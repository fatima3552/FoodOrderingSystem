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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class showempinfo extends AppCompatActivity {
    androidx.appcompat.app.AlertDialog.Builder builder;
    EditText etempname, etempid, etempemail, etempcontno, etempassword;
    TextView tvpname;
    String s;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showempinfo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etempname = (EditText) findViewById(R.id.etename);
        etempid = (EditText) findViewById(R.id.eteid);
        etempemail = (EditText) findViewById(R.id.etemail);
        etempcontno = (EditText) findViewById(R.id.etcontno);
        etempassword = (EditText) findViewById(R.id.etpass);
        tvpname = (TextView) findViewById(R.id.tvusername);
        dbHelper = new DatabaseHelper(this);
        builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        Intent intent=getIntent();
        s=intent.getStringExtra("ename");
        etempname.setText(intent.getStringExtra("en"));
        etempid.setText(intent.getStringExtra("eid"));
        etempemail.setText(intent.getStringExtra("ee"));
        etempcontno.setText(intent.getStringExtra("ec"));
        etempassword.setText(intent.getStringExtra("ep"));
        tvpname.setText(intent.getStringExtra("en"));
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
        Intent i2=new Intent(this, showemplist.class);
        i2.putExtra("ename",s);
        startActivity(i2);
        finish();

    }
    public void UpdateRecords(View v) {

        db = dbHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.Employee.COL_EMPNAME, etempname.getText().toString());
        args.put(DatabaseContract.Employee.COL_EMPEMAIL, etempemail.getText().toString());
        args.put(DatabaseContract.Employee.COL_EMPPASSWORD, etempassword.getText().toString());
        args.put(DatabaseContract.Employee.COL_EMPCONTACTNO, etempcontno.getText().toString());
        args.put(DatabaseContract.Employee._ID, etempid.getText().toString());

        String[] wherearg = {etempid.getText().toString()};
        Cursor e = db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPNAME
                + " = ? ", new String[]{etempname.getText().toString()}, null, null, null, null);
        Cursor p =db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPEMAIL
                + " = ? ", new String[]{etempemail.getText().toString()}, null, null, null, null);
        Cursor q =db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPPASSWORD
                + " = ? ", new String[]{etempassword.getText().toString()}, null, null, null, null);
        Cursor r =db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPCONTACTNO
                + " = ? ", new String[]{etempcontno.getText().toString()}, null, null, null, null);
        Cursor s =db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee._ID
                + " = ? ", new String[]{etempid.getText().toString()} , null, null, null, null);
        if (e.getCount() > 0 && p.getCount() > 0&& q.getCount() > 0&& r.getCount() > 0&& s.getCount() > 0) {
            Toast.makeText(this, "record already updated", Toast.LENGTH_LONG).show();
        }
        else
        {
            Integer count = db.update(DatabaseContract.Employee.TABLE_EMPNAME, args, DatabaseContract.Employee._ID + " = ? ", wherearg);
            if (count > 0) {
                Toast.makeText(this, "  Record updated: ", Toast.LENGTH_SHORT).show();
            }
            else if(etempid.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(this, "Data not Updated kindly enter ID that you want to update", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Data not Updated id not found", Toast.LENGTH_LONG).show();

            }
            db.close();
        }
    }
}
