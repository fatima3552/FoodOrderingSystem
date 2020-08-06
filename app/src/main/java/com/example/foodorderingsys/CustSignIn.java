package com.example.foodorderingsys;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CustSignIn extends AppCompatActivity {
    EditText cname, password;
    Button btnlogin;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String ename, var;
    String cId;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cust_sign_in);
        cname = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        dbHelper = new DatabaseHelper(this);

        i = getIntent();
        var = i.getStringExtra("cart");
        //Toast.makeText(this, var, Toast.LENGTH_LONG).show();
    }


    public void csignup(View view)
    {
        Intent i3 = new Intent(this, CustInsertInfo.class);
        startActivity(i3);
        finish();
    }


    public void custlogin(View view)
    {
        db = dbHelper.getWritableDatabase();
        if (cname.getText().toString().equalsIgnoreCase("") && password.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "enter email and password to login", Toast.LENGTH_LONG).show();
        }
        else if (cname.getText().toString().equalsIgnoreCase("") && password.getText().toString().equalsIgnoreCase(password.getText().toString())) {
            Toast.makeText(this, "enter email  to login", Toast.LENGTH_LONG).show();
        }
        else if (password.getText().toString().equalsIgnoreCase("") && cname.getText().toString().equalsIgnoreCase(cname.getText().toString())) {
            Toast.makeText(this, "enter password  to login", Toast.LENGTH_LONG).show();
        }
        else
        {
            Cursor e = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTNAME
                            + " = ? ", new String[]{cname.getText().toString()}
                    , null, null, null, null);
            Cursor p = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTPASSWORD
                            + " = ? ", new String[]{password.getText().toString()}
                    , null, null, null, null);
            if (e.getCount() == 0 || p.getCount() == 0)
            {
                if (cname.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "enter name and password to login", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Customer does not exist", Toast.LENGTH_LONG).show();
                }
            }
            else
            {

                if(i.hasExtra("cart"))
                {
                    db = dbHelper.getWritableDatabase();
                    Cursor s = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTNAME
                            + " = ? ", new String[]{cname.getText().toString()}, null, null, null, null);
                    if (s.getCount() > 0) {
                        while (s.moveToNext()) {
                            cId = s.getString(0);
                        }
                    }
                    Intent i2 = new Intent(this, ConfirmOrder.class);
                    i2.putExtra("cid", cId);
                    i2.putExtra("var", var);
                    startActivity(i2);
                }


                else
                {
                    Toast.makeText(this, "you are login ", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(this, CategoryMenu.class);
                    startActivity(i2);
                }



                  /*  while (p.moveToNext()) {
                        ename= p.getString(1);
                    }
                     i2.putExtra("ename",ename);*/

                           // cname.setText("");
                           // password.setText("");

            }
        }
    }

    public void forgotpassword(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        LayoutInflater li =getLayoutInflater() ;
        View promptsView = li.inflate(R.layout.dialog, null);

        alertDialogBuilder.setView(promptsView).setTitle("Validation required");


        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Verify",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                db = dbHelper.getWritableDatabase();
                                String s=userInput.getText().toString();
                                Cursor eee = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTMAIL
                                        + " = ? ", new String[]{s}, null, null, null, null);
                                if(eee.getCount()>0)
                                {
                                    dialog.dismiss();
                                    newdialog(s);


                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "  This user email does not exist ", Toast.LENGTH_SHORT).show();

                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void newdialog(final String email){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        LayoutInflater li =getLayoutInflater() ;
        View promptsView = li.inflate(R.layout.dialog2, null);


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView).setTitle(" Account verified, Change your password");


        final EditText pass1 = (EditText) promptsView
                .findViewById(R.id.etpass);
        final EditText pass2 = (EditText) promptsView
                .findViewById(R.id.etconfirmpass);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                String s1=pass1.getText().toString();
                                String s2=pass2.getText().toString();
                                String[] wherearg = {email};
                                if(s1.equals(s2)){
                                    db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseContract.Customer.COL_CUSTPASSWORD, s1);
                                    Integer count = db.update(DatabaseContract.Customer.TABLE_CUSTNAME, values, DatabaseContract.Customer.COL_CUSTMAIL + " = ? ", wherearg);
                                    if (count > 0) {
                                        Toast.makeText(getApplicationContext(), "  your password is updated: ", Toast.LENGTH_SHORT).show();
                                        db.close();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "  your password is not  updated: ", Toast.LENGTH_SHORT).show();

                                    }

                                }else if(s1.equals("")||s2.equals(""))
                                {
                                    Toast.makeText(getApplicationContext(), "  field should not be empty", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "  password and confirm password doesnot match", Toast.LENGTH_SHORT).show();

                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

}
