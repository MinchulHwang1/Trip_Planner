package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyContactInfo extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyContacts.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    private static final String COLUMN_EMAIL = "email";

    public MyContactInfo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT," +
                COLUMN_EMAIL + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addContact(String name, String phone, String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EMAIL, mail);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getAllContacts() {
        List<String> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                // 가져온 연락처 정보를 리스트에 추가
                contactList.add("Name: " + name + "\nPhone: " + phone + "\nEmail: " + email);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }


//    public Cursor getAllContacts() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        return cursor;
//    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // 모든 테이블을 삭제하는 SQL 쿼리 실행
        db.execSQL("DELETE FROM " + TABLE_NAME);
        // 데이터베이스를 닫음
        db.close();
    }
}