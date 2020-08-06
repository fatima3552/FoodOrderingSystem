package com.example.foodorderingsys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    EditText etempname, etempemail, etempcontno, etempassword, etconfirmempassword;
    String val1, val2, val3, val4;
    TextView tvpname;
    String val5 ;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        etempname = (EditText) findViewById(R.id.etename);
        etempemail = (EditText) findViewById(R.id.etemail);
        etempcontno = (EditText) findViewById(R.id.etcontno);
        etempassword = (EditText) findViewById(R.id.etpass);
        tvpname = (TextView) findViewById(R.id.tvusername);
        etconfirmempassword = (EditText) findViewById(R.id.etconfirmpass);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        Intent intent = getIntent();
        if(intent.hasExtra("ename")) {
            tvpname.setText(intent.getStringExtra("ename"));
        }
        awesomeValidation.addValidation(etempname, RegexTemplate.NOT_EMPTY, "enter valid name");
        awesomeValidation.addValidation(etempcontno, "[0-9]{11}$", "phone number should not be more then 11 digits");
        awesomeValidation.addValidation(etempemail, Patterns.EMAIL_ADDRESS, "Enter valid Email");
        awesomeValidation.addValidation(etempassword,   "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$", "password should include  atleast 4 charaters have atleast one  special charater and minimum one letter");
    }

    public void insertEmpData(View view) {

        if( !awesomeValidation.validate() ){
            Toast.makeText(MainActivity.this, "signup failed", Toast.LENGTH_LONG).show();

        }
        else {
            db = dbHelper.getWritableDatabase();
            val1 = etempname.getText().toString().trim();

            val3 = etempemail.getText().toString().trim();
            val4 = etempassword.getText().toString().trim();
            if (etempcontno.getText().toString().equals("")) {
                // Toast.makeText(MainActivity.this, "  contact field is empty  ", Toast.LENGTH_LONG).show();
            } else {
                val5 = etempcontno.getText().toString().trim();
            }
            Cursor cu = db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPNAME + " = ? ",
                    new String[]{etempname.getText().toString()}, null, null, null, null);
            if (cu.getCount() > 0) {
                Toast.makeText(MainActivity.this, "you are already registered", Toast.LENGTH_LONG).show();

            } /*else if (val1.equals("") && val5 == 0) {
            Toast.makeText(MainActivity.this, "name field is empty can't registered  ", Toast.LENGTH_LONG).show();

        }*/
            else if (!val4.equals(etconfirmempassword.getText().toString()))
            {
                Toast.makeText(MainActivity.this, "password and confirm password are not same", Toast.LENGTH_LONG).show();

            }
            else {

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.Employee.COL_EMPNAME, val1);
                values.put(DatabaseContract.Employee.COL_EMPEMAIL, val3);
                values.put(DatabaseContract.Employee.COL_EMPPASSWORD, val4);
                values.put(DatabaseContract.Employee.COL_EMPCONTACTNO, val5);
                long result = db.insert(DatabaseContract.Employee.TABLE_EMPNAME, null, values);
                if (result > 0&&val4.equals(etconfirmempassword.getText().toString())) {

                    Toast.makeText(MainActivity.this, "you are registered", Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);*/
                    clear();

                } else {
                    Toast.makeText(MainActivity.this, "not registered", Toast.LENGTH_LONG).show();
                }

                db.close();
            }
        }


    }

    public void Login(View v) {
        Intent i1 = new Intent(this, loginActivity.class);
        startActivity(i1);
        finish();

    }

    public void clear() {
        etempname.setText("");
        etconfirmempassword.setText("");
        etempemail.setText("");
        etempcontno.setText("");
        etempassword.setText("");

    }


}

