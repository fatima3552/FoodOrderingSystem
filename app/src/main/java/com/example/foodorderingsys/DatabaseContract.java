package com.example.foodorderingsys;
import android.provider.BaseColumns;

public class DatabaseContract
{
    public DatabaseContract() {}
    public static abstract class Employee implements BaseColumns {
        public static final String TABLE_EMPNAME = "Employees";
        public static final String COL_EMPNAME = "EmployeeName";
        public static final String COL_EMPEMAIL = "EmployeeEmail";
        public static final String COL_EMPPASSWORD = "EmployeePassword";
        public static final String COL_EMPCONTACTNO = "EmployeeContactNo";
    }

    public static abstract class Customer implements BaseColumns {
        public static final String TABLE_CUSTNAME = "Customers";
        public static final String COL_CUSTNAME = "CustomerName";
        public static final String COL_CUSTMAIL = "CuctomerEmail";
        public static final String COL_CUSTOMERADDRESS = "CustomerAddress";
        public static final String COL_CUSTPASSWORD = "CustomerPassword";
        public static final String COL_CUSTCONTACTNO = "ContactNo";
    }

    public static abstract class Product implements BaseColumns {
        public static final String TABLE_PRODUCTNAME = "Products";
        public static final String COL_PRODUCTNAME = "ProductName";
        public static final String COL_PRODUCTCATEGORY = "Category";
        public static final String COL_PRODUCTPRICE = "Price";
        public static final String COL_PRODUCTIMAGE = "Image";
    }

    public static abstract class CustomerOrder implements BaseColumns {
        public static final String TABLE_ORDERNAME = "CustOrder";
        public static final String COL_O_CUSTOMERID = "O_CustId";
        public static final String COL_O_PRODUCTID  = "O_ProdId";
        public static final String COL_ORDERADDRESS = "Address";
        public static final String COL_ORDERPRICE = "Price";
        public static final String COL_ORDERBILL = "Bill";
    }
}
