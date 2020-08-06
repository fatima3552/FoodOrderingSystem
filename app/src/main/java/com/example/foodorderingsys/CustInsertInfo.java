package com.example.foodorderingsys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class CustInsertInfo extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText cName, cCon, cAdd, cEmail, cPass, cPass2,cid;
    String val5;
    AwesomeValidation awesomeValidation;
    String val1,val2,val3,val4,val6;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_insert_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbHelper = new DatabaseHelper(this);
        cName = (EditText) findViewById(R.id.editText_name2);
        cCon = (EditText) findViewById(R.id.editText_contact);
        cAdd = (EditText) findViewById(R.id.editText_add);
        cEmail = (EditText) findViewById(R.id.editText_email);
        cPass = (EditText) findViewById(R.id.editText_pass);

        cPass2= (EditText) findViewById(R.id.editText_pass2);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnDelete = (Button) findViewById(R.id.button_delete);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(cName, RegexTemplate.NOT_EMPTY, "enter valid name");
        awesomeValidation.addValidation(cAdd, RegexTemplate.NOT_EMPTY, "enter address");
        awesomeValidation.addValidation(cCon, "[0-9]{11}$", "phone number should not be more then 11 digits");
        awesomeValidation.addValidation(cEmail, Patterns.EMAIL_ADDRESS, "Enter valid Email");
        awesomeValidation.addValidation(cPass,   "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$", "password should include  atleast 4 charaters have atleast one  special charater and minimum one letter");



    }

    public void insertcustData(View view) {
        if( !awesomeValidation.validate() ){
            Toast.makeText(this, "signup failed", Toast.LENGTH_LONG).show();

        }
        else {

            db = dbHelper.getWritableDatabase();
            val1 = cName.getText().toString();
            val3 = cEmail.getText().toString();
            val4 = cPass.getText().toString();
            val6 = cAdd.getText().toString();
            if (cCon.getText().toString().equals("")) {
                // Toast.makeText(MainActivity.this, "  contact field is empty  ", Toast.LENGTH_LONG).show();
            } else {
                val5 = cCon.getText().toString();
            }
            Cursor cu = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null, DatabaseContract.Customer.COL_CUSTNAME + " = ? ",
                    new String[]{cName.getText().toString()}, null, null, null, null);
            if (cu.getCount() > 0) {
                Toast.makeText(this, "you are already registered", Toast.LENGTH_LONG).show();

            } else if (val1.equals("")) {
                Toast.makeText(this, "name field is empty can't registered  ", Toast.LENGTH_LONG).show();

            }
            else if (!val4.equals(cPass2.getText().toString()))
            {
                Toast.makeText(this, "password and confirm password are not same", Toast.LENGTH_LONG).show();

            }else {

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.Customer.COL_CUSTNAME, val1);
                values.put(DatabaseContract.Customer.COL_CUSTMAIL, val3);
                values.put(DatabaseContract.Customer.COL_CUSTPASSWORD, val4);
                values.put(DatabaseContract.Customer.COL_CUSTCONTACTNO, val5);
                values.put(DatabaseContract.Customer.COL_CUSTOMERADDRESS, val6);

                long result = db.insert(DatabaseContract.Customer.TABLE_CUSTNAME, null, values);
                if (result > 0) {
                    Toast.makeText(this, "you are registered", Toast.LENGTH_LONG).show();
               /* Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                 cid.setText("");
          */  cName.setText("");
                    cEmail.setText("");
                    cPass.setText("");
                    cPass2.setText("");
                    cAdd.setText("");
                    cCon.setText("");
                } else {
                    Toast.makeText(this, "not registered", Toast.LENGTH_LONG).show();
                }

                db.close();
            }
        }
    }

    public void cLogin(View v)
    {
        Intent i1=new Intent(this,CustSignIn.class);
        startActivity(i1);
        finish();
    }

}
