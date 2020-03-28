package com.company.pph.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.company.pph.sql.CustomerContract;

import com.company.pph.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "customer.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + CustomerContract.CustomerEntry.TABLE_NAME + " (" +
                CustomerContract.CustomerEntry._ID + " INTEGER NOT NULL," +
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_NAME + " TEXT NOT NULL, " +
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_EMAIL + " TEXT NOT NULL, " +
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_ADDRESS + " TEXT NOT NULL, " +
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_COUNTRY + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }
    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + CustomerContract.CustomerEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

        //Drop User Table if exist

        db1.execSQL(DROP_BENEFICIARY_TABLE);

        // Create tables again
        onCreate(db1);

    }


    //Method to create customer records

    public void addBeneficiary(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CustomerContract.CustomerEntry._ID, customer.getId());
        values.put(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_NAME, customer.getName());
        values.put(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_EMAIL, customer.getEmail());
        values.put(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_ADDRESS, customer.getAddress());
        values.put(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_COUNTRY, customer.getCountry());

        db.insert(CustomerContract.CustomerEntry.TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                CustomerContract.CustomerEntry._ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = CustomerContract.CustomerEntry.COLUMN_CUSTOMER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(CustomerContract.CustomerEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }





    public List<Customer> getAllBeneficiary() {
        // array of columns to fetch
        String[] columns = {
                CustomerContract.CustomerEntry._ID,
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_NAME,
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_EMAIL,
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_ADDRESS,
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_COUNTRY
        };
        // sorting orders
        String sortOrder =
                CustomerContract.CustomerEntry.COLUMN_CUSTOMER_NAME + " ASC";
        List<Customer> beneficiaryList = new ArrayList<Customer>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(CustomerContract.CustomerEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customer beneficiary = new Customer();
                beneficiary.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerEntry._ID))));
                beneficiary.setName(cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_NAME)));
                beneficiary.setEmail(cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_EMAIL)));
                beneficiary.setAddress(cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_ADDRESS)));
                beneficiary.setCountry(cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerEntry.COLUMN_CUSTOMER_COUNTRY)));
                // Adding user record to list
                beneficiaryList.add(beneficiary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return beneficiaryList;
    }

}
