package com.example.foodorderingsys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnlogin;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String ename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("name", MODE_PRIVATE);
        boolean isLoggedIn= prefs.getBoolean("isLoggedIn", false);
        if(isLoggedIn){
            startActivity(new Intent(getApplicationContext(),choice.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = (EditText) findViewById(R.id.etmail);
        password = (EditText) findViewById(R.id.etpass);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        dbHelper = new DatabaseHelper(this);



    }

    public void signup(View view) {
        Intent i2 = new Intent(this, MainActivity.class);
        startActivity(i2);

    }

    public void emplogin(View view) {
        db = dbHelper.getWritableDatabase();
        if (email.getText().toString().equalsIgnoreCase("") && password.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "enter email and password to login", Toast.LENGTH_LONG).show();
        }
        else if (email.getText().toString().equalsIgnoreCase("") && password.getText().toString().equalsIgnoreCase(password.getText().toString())) {
            Toast.makeText(this, "enter email  to login", Toast.LENGTH_LONG).show();

        }
        else if (password.getText().toString().equalsIgnoreCase("") && email.getText().toString().equalsIgnoreCase(email.getText().toString())) {
            Toast.makeText(this, "enter password  to login", Toast.LENGTH_LONG).show();

        }

        else {
            Cursor e = db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPEMAIL
                            + " = ? ", new String[]{email.getText().toString()}
                    , null, null, null, null);
            Cursor p = db.query(DatabaseContract.Employee.TABLE_EMPNAME, null, DatabaseContract.Employee.COL_EMPPASSWORD
                            + " = ? ", new String[]{password.getText().toString()}
                    , null, null, null, null);
            if (e.getCount() == 0 || p.getCount() == 0) {
                Toast.makeText(this, "Employee does not exist", Toast.LENGTH_LONG).show();
            } else {
                Intent i2 = new Intent(this, choice.class);
                while (p.moveToNext()) {
                    ename = p.getString(1);
                }
                i2.putExtra("ename", ename);
                startActivity(i2);
                email.setText("");
                password.setText("");
                finish();



            }
        }

    }

}
