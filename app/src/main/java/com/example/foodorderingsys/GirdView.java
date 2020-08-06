package com.example.foodorderingsys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class GirdView extends AppCompatActivity {
    TextView tv;
    GridView gridView;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String n, emp;
    int intt2, intt3;
    List<gridViewProduct> list;
    gridViewProduct gridViewproduct;

    customadopter cadpter;
    String s, sname, category, price, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gird_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gridView = findViewById(R.id.grid1View);
        tv = findViewById(R.id.tvgird);
        list= new ArrayList<>();
        Intent i5 = getIntent();
        //  intt3=i5.getIntExtra("num2",0);
        intt2 = i5.getIntExtra("num1", 0);
        emp = i5.getStringExtra("ename");
        dbHelper = new DatabaseHelper(this);

        if (intt2 == 10) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    n = String.valueOf(list.get(position).getName());

                    Cursor cursor = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, null,
                            DatabaseContract.Product.COL_PRODUCTNAME + " = ? ",
                            new String[]{(n)}, null, null, null, null);
                    while (cursor.moveToNext()) {
                        // buffer.append("Id :"+ cursor.getString(0)+"\n");
                        sname = cursor.getString(1);
                        category = cursor.getString(2);
                        price = cursor.getString(3);
                        img = cursor.getString(4);
                    }
                    Intent intent = new Intent(getApplicationContext(), displayProdInfo.class);
                    intent.putExtra("name", sname);
                    intent.putExtra("category", category);
                    intent.putExtra("price", price);
                    intent.putExtra("img", img);
                    intent.putExtra("ename", emp);
                    intent.putExtra("num1", intt2);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    n = String.valueOf(list.get(position).getName());
                    Cursor cursor = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, null,
                            DatabaseContract.Product.COL_PRODUCTNAME + " = ? ",
                            new String[]{(n)}, null, null, null, null);
                    while (cursor.moveToNext()) {
                        // buffer.append("Id :"+ cursor.getString(0)+"\n");
                        sname = cursor.getString(1);
                        category = cursor.getString(2);
                        price = cursor.getString(3);
                        img = cursor.getString(4);
                    }
                    Intent intent1 = new Intent(getApplicationContext(), custSeeProduct.class);
                    intent1.putExtra("name", sname);
                    intent1.putExtra("category", category);
                    intent1.putExtra("price", price);
                    // intent1.putExtra("quantity", quantity);
                    intent1.putExtra("img", img);
                    String x = "product";
                    intent1.putExtra("cart", x);
                    intent1.putExtra("name1", x);
                    startActivity(intent1);
                    finish();
                }
            });
        }
        Intent i = getIntent();
        s = i.getStringExtra("category");
        showinfo();
        cadpter = new customadopter(GirdView.this, list);
        gridView.setAdapter(cadpter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.searchmenu,menu);
        MenuItem searchitem =menu.findItem(R.id.search);
        SearchView searchView =(SearchView)searchitem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    cadpter.getFilter().filter("");
                    gridView.clearTextFilter();
                }
                else {
                    cadpter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    public void showinfo() {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = dbHelper.readRVdata(s);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                gridViewproduct= new gridViewProduct(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                list.add(gridViewproduct);
            }

        }


    }

    public class customadopter extends BaseAdapter implements Filterable {
        private Context context;
        private ArrayList<String> pname;
        private ArrayList<String> price;
        private ArrayList<String> image;
        private List<gridViewProduct>list;
        private List<gridViewProduct> liii ;

        private LayoutInflater layoutInflate;

        @Override
        public int getCount() {
            return list.size();
        }

        public customadopter(Context context, List<gridViewProduct> list) {
            this.context = context;
            this.list=list;
            liii= new ArrayList(list);
            this.layoutInflate = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }




        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflate.inflate(R.layout.gird_row_items, parent, false);
            }
            TextView tvn = convertView.findViewById(R.id.tvitemname);
            TextView tvp = convertView.findViewById(R.id.tvitemprice);
            ImageView img = convertView.findViewById(R.id.ivitemimg);
            String e=list.get(position).getName();
            tvn.setText(e);
            tv.setText(s +" Menu");
            tvp.setText(String.valueOf(list.get(position).getPrice()));
            String imgname = String.valueOf(list.get(position).getImage());
            String uri = "drawable/" + imgname;
            String PACKAGE_NAME = context.getApplicationContext().getPackageName();
            int icon = context.getResources().getIdentifier(uri, "drawable", PACKAGE_NAME);
            img.setImageResource(icon);
            return convertView;
        }
        @Override
        public Filter getFilter() {
            return listfilter;
        }
        private Filter listfilter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                ArrayList<gridViewProduct> filteredList=new ArrayList<>();
                if(constraint==null||constraint.length()==0)
                {
                    filteredList.addAll(liii);
                }
                else
                {

                    String filterpattern = constraint.toString().toLowerCase().trim();
                    for( gridViewProduct item:liii){
                        if(item.getName().toLowerCase().contains(filterpattern)||item.getPrice().toLowerCase().contains(filterpattern)){
                            filteredList.add(item);
                        }

                    }
                }
                FilterResults results=new FilterResults();
                results.values=filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                list.addAll((ArrayList)results.values);
                notifyDataSetChanged();
            }
        };
    }
}