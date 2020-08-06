package com.example.foodorderingsys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class showcustlist extends AppCompatActivity {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    ListView listView;
    TextView textView;
    String s,cn,cid,cc,cp,ce,ca;
    List<String> usersList;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> myIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcustlist);
        listView=findViewById(R.id.list);
        textView=findViewById(R.id.tvusername);
        Intent intent=getIntent();
        s=intent.getStringExtra("ename");
        textView.setText(s);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        usersList = new ArrayList();
        String[] columns = {DatabaseContract.Customer.COL_CUSTNAME};
        String sortOrder = DatabaseContract.Customer.COL_CUSTNAME + " ASC";
        Cursor cursor = db.query(DatabaseContract.Customer.TABLE_CUSTNAME,null ,
                null  , null,
                null, null, sortOrder, null);
        while (cursor.moveToNext()) {
            String n= cursor.getString(1) ;
            String id= cursor.getString(0) ;
            usersList.add(n);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // StringBuffer buffer = new StringBuffer();
                Intent intent= new Intent(getApplicationContext(),showCustinfo.class);
                String name = parent.getItemAtPosition(position).toString();
                Cursor c = db.query(DatabaseContract.Customer.TABLE_CUSTNAME, null,
                        DatabaseContract.Customer.COL_CUSTNAME+ " LIKE ?  ",
                        new String[] {name }, null, null, null, null);
                while (c.moveToNext()) {
                    cid= c.getString(0);
                    cn= c.getString(1);
                    ce=c.getString(2);
                    cp = c.getString(3);
                    ca= c.getString(4);
                    cc= c.getString(5);
                }
                intent.putExtra("cid",cid);
                intent.putExtra("cn",cn);
                intent.putExtra("ce",ce);
                intent.putExtra("cp",cp);
                intent.putExtra("cc",cc);
                intent.putExtra("ca",ca);
                intent.putExtra("ename",s);

                startActivity(intent);
            }

        });
        registerForContextMenu(listView);



        //  Toast.makeText(this," not employee available ",Toast.LENGTH_LONG).show();


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select");
        //menu.setHeaderIcon(R.drawable.setting_icon);
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.deletemenu,menu);
    }

    @Override

    public boolean onContextItemSelected(@NonNull MenuItem item)
    {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch(item.getItemId())
        {
            case R.id.delete2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure to Remove this employee?");
                builder.setMessage("This employee will be Removed Permanently");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        myIds = dbHelper.selectcuctAllIds();
                        int id = Integer.parseInt(myIds.get(info.position).get("cid"));

                        //     Toast.makeText(getApplicationContext(),usersList.get(info.position) +"Delete", Toast.LENGTH_LONG).show();
                        DeleteRecords(id);
                        usersList.remove(info.position);
                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void DeleteRecords(int id) {
        db = dbHelper.getWritableDatabase();
        Integer i1 = db.delete(DatabaseContract.Customer.TABLE_CUSTNAME, DatabaseContract.Customer._ID + " = ?", new String[]{String.valueOf(id)});
        if (i1 > 0) {
            Toast.makeText(this,  "  Records deleted: ", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Data not Deleted ", Toast.LENGTH_LONG).show();
        db.close();
    }
    public void back(View v)
    {
        Intent i2=new Intent(this, customerAdminWork.class);
        i2.putExtra("ename",s);
        startActivity(i2);
    }
}
