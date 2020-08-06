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

public class adminWork extends AppCompatActivity {
    androidx.appcompat.app.AlertDialog.Builder builder;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    EditText etempname, etempid, etempemail, etempcontno, etempassword;
    String val1, val2, val3, val4, ename, eid, email, epass, econno, s;
    TextView tvpname;
    int val5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbHelper = new DatabaseHelper(this);
        builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        etempname = (EditText) findViewById(R.id.etename);
        etempid = (EditText) findViewById(R.id.eteid);
        etempemail = (EditText) findViewById(R.id.etemail);
        etempcontno = (EditText) findViewById(R.id.etcontno);
        etempassword = (EditText) findViewById(R.id.etpass);
        tvpname = (TextView) findViewById(R.id.tvusername);
        Intent intent = getIntent();
        tvpname.setText(intent.getStringExtra("ename"));
        s = intent.getStringExtra("ename");
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
    public void insertEmpData(View view) {

        db = dbHelper.getWritableDatabase();
        val1 = etempname.getText().toString();
        val2 = etempid.getText().toString();
        val3 = etempemail.getText().toString();
        val4 = etempassword.getText().toString();
        if (etempcontno.getText().toString().equals("")) {
            // Toast.makeText(MainActivity.this, "  contact field is empty  ", Toast.LENGTH_LONG).show();
        } else {
            val5 = Integer.parseInt(etempcontno.getText().toString());
        }
        Cursor cu = db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPNAME + " = ? ",
                new String[]{etempname.getText().toString()}, null, null, null, null);
        if (cu.getCount() > 0) {
            Toast.makeText(this, "you are already registered", Toast.LENGTH_LONG).show();

        } else if (val1.equals("") && val5 == 0) {
            Toast.makeText(this, "name field is empty can't registered  ", Toast.LENGTH_LONG).show();

        } else {

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Employee.COL_EMPNAME, val1);
            values.put(DatabaseContract.Employee._ID, val2);
            values.put(DatabaseContract.Employee.COL_EMPEMAIL, val3);
            values.put(DatabaseContract.Employee.COL_EMPPASSWORD, val4);
            values.put(DatabaseContract.Employee.COL_EMPCONTACTNO, val5);
            long result = db.insert(DatabaseContract.Employee.TABLE_EMPNAME, null, values);
            if (result > 0) {
                Toast.makeText(this, "you are registered", Toast.LENGTH_LONG).show();
                etempname.setText("");
                etempid.setText("");
                etempemail.setText("");
                etempcontno.setText("");
                etempassword.setText("");
            } else {
                Toast.makeText(this, "not registered", Toast.LENGTH_LONG).show();
            }

            db.close();
        }


    }

    public void viewData(View view) {
        Intent i1 = new Intent(this, showemplist.class);
        i1.putExtra("ename",s);
        startActivity(i1);
        /*SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + DatabaseContract.Employee.TABLE_EMPNAME, null);
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Employee Id :" + res.getString(0) + "\n");
            buffer.append("Employee Name :" + res.getString(1) + "\n");
            buffer.append("Employee Email :" + res.getString(2) + "\n");
            buffer.append("Employee Password :" + res.getString(3) + "\n");
            buffer.append("Employee Contact Number :" + res.getString(4) + "\n\n");

        }
        // Show all data
        showMessage(" Employee Data Information", buffer.toString());*/
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void Login(View v) {
        Intent i1 = new Intent(this, loginActivity.class);
        startActivity(i1);
        finish();

    }

    public void SearchRecords(View v) {
        db = dbHelper.getWritableDatabase();


        String val1 = etempid.getText().toString();
        Cursor cursor = db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee._ID + " = ? ",
                new String[]{val1}, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                eid = cursor.getString(0);
                ename = cursor.getString(1);
                email = cursor.getString(2);
                epass = cursor.getString(3);
                econno = cursor.getString(4);

            }
            etempid.setText(eid);
            etempname.setText(ename);
            etempemail.setText(email);
            etempassword.setText(epass);
            etempcontno.setText(econno);
        } else
            Toast.makeText(this, "No record found kindly enter ID that you want to search  ", Toast.LENGTH_SHORT).show();


        db.close();

    }

    public void DeleteRecords(View v) {
        db = dbHelper.getWritableDatabase();


        Integer i1 = db.delete(DatabaseContract.Employee.TABLE_EMPNAME, DatabaseContract.Employee._ID + " = ?", new String[]{etempid.getText().toString()});
        if (i1 > 0) {
            Toast.makeText(this, i1 + "  Records deleted: ", Toast.LENGTH_SHORT).show();
            etempname.setText("");
            etempid.setText("");
            etempemail.setText("");
            etempcontno.setText("");
            etempassword.setText("");
        } else
            Toast.makeText(this, "Data not Deleted kindly enter ID that you want to Delete", Toast.LENGTH_LONG).show();

        db.close();

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
        Integer count = db.update(DatabaseContract.Employee.TABLE_EMPNAME, args, DatabaseContract.Employee._ID + " = ? ", wherearg);
        if (count > 0) {

            Toast.makeText(this, count + "  Records updated: ", Toast.LENGTH_SHORT).show();
            etempname.setText("");
            etempid.setText("");
            etempemail.setText("");
            etempcontno.setText("");
            etempassword.setText("");
        } else
            Toast.makeText(this, "Data not Updated kindly enter ID that you want to update", Toast.LENGTH_LONG).show();
        db.close();
    }

    public void signup(View view) {
        Intent intent = new Intent(this, choice.class);
        intent.putExtra("ename", s);
        startActivity(intent);
        finish();
    }
}
