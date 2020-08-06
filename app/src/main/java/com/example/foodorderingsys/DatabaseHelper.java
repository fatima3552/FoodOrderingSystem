package com.example.foodorderingsys;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "FOSystem.db";
    private static final String CREATE_TBL_EMPLOYEE = "CREATE TABLE "
            + DatabaseContract.Employee.TABLE_EMPNAME + " ("
            + DatabaseContract.Employee._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Employee.COL_EMPNAME + " TEXT NOT NULL, "
            + DatabaseContract.Employee.COL_EMPEMAIL + " TEXT,"
            + DatabaseContract.Employee.COL_EMPPASSWORD+ " TEXT, "
            +DatabaseContract.Employee.COL_EMPCONTACTNO+ " INTEGER )";
    private static final String CREATE_TBL_CUSTOMER = "CREATE TABLE "
            + DatabaseContract.Customer.TABLE_CUSTNAME + " ("
            + DatabaseContract.Customer._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Customer.COL_CUSTNAME + " TEXT NOT NULL, "
            + DatabaseContract.Customer.COL_CUSTMAIL + " TEXT,"
            + DatabaseContract.Customer.COL_CUSTPASSWORD + " TEXT,"
            +DatabaseContract.Customer.COL_CUSTOMERADDRESS +" TEXT,"
            +DatabaseContract.Customer.COL_CUSTCONTACTNO+" INTEGER )";
    private static final String CREATE_TBL_PRODUCT = "CREATE TABLE "
            + DatabaseContract.Product.TABLE_PRODUCTNAME + " ("
            + DatabaseContract.Product._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Product.COL_PRODUCTNAME + " TEXT NOT NULL, "
            + DatabaseContract.Product.COL_PRODUCTCATEGORY + " TEXT,"
            + DatabaseContract.Product.COL_PRODUCTPRICE + " INTEGER,"
            +DatabaseContract.Product.COL_PRODUCTIMAGE +" TEXT )";


    private static final String CREATE_TBL_CUST_ORDER ="CREATE TABLE "
             + DatabaseContract.CustomerOrder.TABLE_ORDERNAME + " ("
             + DatabaseContract.CustomerOrder._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + DatabaseContract.CustomerOrder.COL_O_CUSTOMERID + " INTEGER NOT NULL, "
             +DatabaseContract.CustomerOrder.COL_O_PRODUCTID + " INTEGER, "
             + DatabaseContract.CustomerOrder.COL_ORDERADDRESS + " TEXT, "
             + DatabaseContract.CustomerOrder.COL_ORDERPRICE+ " INTEGER,"
             + DatabaseContract.CustomerOrder.COL_ORDERBILL + " INTEGER, "
             +"FOREIGN KEY ("+DatabaseContract.Customer._ID +") REFERENCES "+DatabaseContract.Customer.TABLE_CUSTNAME+" ( "+DatabaseContract.Customer._ID+ " ), "
             +"FOREIGN KEY ("+DatabaseContract.Product._ID +") REFERENCES "+DatabaseContract.Product.TABLE_PRODUCTNAME+"( "+DatabaseContract.Product._ID+ " )); ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.Employee.TABLE_EMPNAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.Product.TABLE_PRODUCTNAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.Customer.TABLE_CUSTNAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.CustomerOrder.TABLE_ORDERNAME);
        onCreate(db);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_EMPLOYEE);
        db.execSQL(CREATE_TBL_PRODUCT);
        db.execSQL(CREATE_TBL_CUSTOMER);
        db.execSQL(CREATE_TBL_CUST_ORDER);


    }

    Cursor readRVdata (String cat)
    {
       SQLiteDatabase db = this.getWritableDatabase();

       String []columns = {DatabaseContract.Product.COL_PRODUCTNAME,  DatabaseContract.Product.COL_PRODUCTPRICE, DatabaseContract.Product.COL_PRODUCTIMAGE};

       Cursor cursor = db.query(DatabaseContract.Product.TABLE_PRODUCTNAME, columns, DatabaseContract.Product.COL_PRODUCTCATEGORY+"=?",new String[]{cat}, null, null, null, null);
        return cursor;
    }

    public ArrayList<HashMap<String, String>> selectAllIds() {
        try
        {
            ArrayList<HashMap<String, String>> idArrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseContract.Employee.TABLE_EMPNAME;

            Cursor cursor = database.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do
                    {
                        map = new HashMap<String, String>();
                        map.put("id", cursor.getString(0));
                        idArrayList.add(map);

                    } while (cursor.moveToNext());
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            database.close();
            return idArrayList;


        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<HashMap<String, String>> selectcuctAllIds() {
        try {
            ArrayList<HashMap<String, String>> idArrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase database = this.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseContract.Customer.TABLE_CUSTNAME;

            Cursor cursor = database.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        map.put("cid", cursor.getString(0));
                        idArrayList.add(map);

                    } while (cursor.moveToNext());
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            database.close();
            return idArrayList;


        } catch (Exception e) {
            return null;
        }

    }


}
