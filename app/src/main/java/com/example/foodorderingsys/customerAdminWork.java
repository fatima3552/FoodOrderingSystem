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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class customerAdminWork extends AppCompatActivity {
    androidx.appcompat.app.AlertDialog.Builder builder;
    DatabaseHelper dbHelper;
    String ccid,cname,ccontact,caddress,cpass,cemail,s;
    EditText cName, cCon, cAdd, cEmail, cPass,cid;
    int val5;
    String val1,val2,val3,val4,val6;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_admin_work);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbHelper = new DatabaseHelper(this);
        cName = (EditText) findViewById(R.id.editText_name2);
        cCon = (EditText) findViewById(R.id.editText_contact);
        builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        cAdd = (EditText) findViewById(R.id.editText_add);
        cEmail = (EditText) findViewById(R.id.editText_email);
        cPass = (EditText) findViewById(R.id.editText_pass);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnDelete = (Button) findViewById(R.id.button_delete);
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

    public void insertcustData(View view) {

        db = dbHelper.getWritableDatabase();
        val1 = cName.getText().toString();
        val2 = cid.getText().toString();
        val3 = cEmail.getText().toString();
        val4 = cPass.getText().toString();
        val6=cAdd.getText().toString();
        if (cCon.getText().toString().equals("")) {
            // Toast.makeText(MainActivity.this, "  contact field is empty  ", Toast.LENGTH_LONG).show();
        } else {
            val5 = Integer.parseInt(cCon.getText().toString());
        }
        Cursor cu = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTNAME + " = ? ",
                new String[]{cName.getText().toString()}, null, null, null, null);
        if (cu.getCount() > 0) {
            Toast.makeText(this, "you are already registered", Toast.LENGTH_LONG).show();

        } else if (val1.equals("")) {
            Toast.makeText(this, "name field is empty can't registered  ", Toast.LENGTH_LONG).show();

        } else {

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Customer.COL_CUSTNAME, val1);
            values.put(DatabaseContract.Customer._ID, val2);
            values.put(DatabaseContract.Customer.COL_CUSTMAIL, val3);
            values.put(DatabaseContract.Customer.COL_CUSTPASSWORD, val4);
            values.put(DatabaseContract.Customer.COL_CUSTCONTACTNO, val5);
            values.put(DatabaseContract.Customer.COL_CUSTOMERADDRESS, val6);

            long result = db.insert(DatabaseContract.Customer.TABLE_CUSTNAME, null, values);
            if (result > 0) {
                Toast.makeText(this, "you are registered", Toast.LENGTH_LONG).show();
                clear();
               /* Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                */
            } else {
                Toast.makeText(this, "not registered", Toast.LENGTH_LONG).show();
            }

            db.close();
        }
    }

    public void viewData (View view) {
        Intent i1 = new Intent(this, showcustlist.class);
        i1.putExtra("ename", s);
        startActivity(i1);
    }
     /*   SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + DatabaseContract.Customer.TABLE_CUSTNAME, null);
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Customer Id :" + res.getString(0) + "\n");
            buffer.append("Customer Name :" + res.getString(1) + "\n");
            buffer.append("Customer Email :" + res.getString(2) + "\n");
            buffer.append("Customer Password :" + res.getString(3) + "\n");
            buffer.append("Customer Contact Number :" + res.getString(4) + "\n");
            buffer.append("Customer Address :" + res.getString(5) + "\n\n");
        }
        // Show all data
        showMessage(" Customer Data Information", buffer.toString());
    }
    public void showMessage (String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }*/
    public void cLogin(View v)
    {
        Intent i1=new Intent(this,CustSignIn.class);
        startActivity(i1);

    }
    public void cSearchRecords(View v)
    {
        db = dbHelper.getWritableDatabase();
        String val1 = cid.getText().toString();
        Cursor cursor = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer._ID + " = ? ",
                new String[] { val1}, null, null, null, null);
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                ccid=  cursor.getString(0);
                cname = cursor.getString(1);
                cemail = cursor.getString(2);
                cpass = cursor.getString(3);
                caddress = cursor.getString(4);
                ccontact = cursor.getString(5);

            }
            cName.setText(cname);
            cid.setText(ccid);
            cCon.setText(ccontact);
            cAdd.setText(caddress);
            cEmail.setText(cemail);
            cPass.setText(cpass);
        }
        else
            Toast.makeText(this, "No record found kindly enter ID that you want to search  " , Toast.LENGTH_SHORT).show();


        db.close();

    }
    public void cDeleteRecords(View v)
    {
        db = dbHelper.getWritableDatabase();


        Integer i1= db.delete(DatabaseContract.Customer.TABLE_CUSTNAME, DatabaseContract.Customer._ID +" = ?",new String[] {cid.getText().toString()});
        if (i1 > 0) {
            Toast.makeText(this, i1+"  Records deleted: " , Toast.LENGTH_SHORT).show();
            clear();

        }
        else
            Toast.makeText(this,"Data not Deleted kindly enter ID that you want to Delete",Toast.LENGTH_LONG).show();

        db.close();

    }
    public void cUpdateRecords(View v)
    {
        db = dbHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.Customer.COL_CUSTNAME,cName.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTMAIL,cEmail.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTPASSWORD,cPass.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTCONTACTNO,cCon.getText().toString());
        args.put(DatabaseContract.Customer.COL_CUSTOMERADDRESS,cAdd.getText().toString());
        args.put(DatabaseContract.Employee._ID,cid.getText().toString());
        String[] wherearg={cid.getText().toString()};
        Integer count= db.update(DatabaseContract.Customer.TABLE_CUSTNAME, args, DatabaseContract.Customer._ID+ " = ? ",wherearg);
        if (count > 0) {

            Toast.makeText(this, count+"  Records updated: " , Toast.LENGTH_SHORT).show();
            clear();
        }
        else
            Toast.makeText(this,"Data not Updated kindly enter ID that you want to update",Toast.LENGTH_LONG).show();
        db.close();
    }
    public void cback(View view)
    {
        Intent intent=new Intent(this,choice.class);
        intent.putExtra("ename",s);
        startActivity(intent);
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

