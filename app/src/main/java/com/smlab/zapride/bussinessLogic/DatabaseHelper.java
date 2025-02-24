package com.smlab.zapride.bussinessLogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "zapride.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "zapride.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table allusers(phoneNumber TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists allusers");
    }

    public Boolean insertData(String phoneNumber, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("password", password);
        long result = MyDatabase.insert("allusers", null, contentValues);
        if(result==-1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkPhoneNumber(String phoneNumber){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where phoneNumber = ?", new String[]{phoneNumber});
        if(cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkPhoneNumberPassword(String phoneNumber, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where phoneNumber = ? and password = ?", new String[]{phoneNumber, password});
        if(cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }
}
