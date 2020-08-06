package com.example.foodorderingsys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class choice extends AppCompatActivity {
    androidx.appcompat.app.AlertDialog.Builder builder;
    TextView tvpname;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvpname = (TextView) findViewById(R.id.tvusername);
        Intent intent = getIntent();
        s = intent.getStringExtra("ename");
        tvpname.setText(s);
    }
    public void employee(View v) {
        Intent i1 = new Intent(this, adminWork.class);
        i1.putExtra("ename", s);
        startActivity(i1);
        finish();

    }
    public void customer(View v) {
        Intent i2 = new Intent(this, customerAdminWork.class);
        i2.putExtra("ename", s);
        startActivity(i2);
        finish();
    }

    public void Menu(View v) {
        Intent i2 = new Intent(this, CategoryMenu.class);
        i2.putExtra("ename", s);
        i2.putExtra("num", 1);
        i2.putExtra("num1", 10);
        startActivity(i2);
        finish();
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
}
