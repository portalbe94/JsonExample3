package com.example.business.recycleviewjsonexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShoppingDbHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "Shopping.db";
    public static final String SQL_CREATE_ENTRIES = "Create Table ShoppingList (itemId Integer PRIMARY KEY, ProductName TEXT)";
    public static final String SQL_DELETE_ENTRIES = "DROP Table if exists ShoppingList";

    public ShoppingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public long addItem(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put ("itemId", id);
        values.put ("ProductName", name);
        return        db.insert("ShoppingList", null, values);

    }


}
