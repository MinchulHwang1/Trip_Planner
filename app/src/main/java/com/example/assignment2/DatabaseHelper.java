package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 2;

    // 테이블 생성 쿼리
    public static final String CREATE_TABLE_NAME = "CREATE TABLE my_table (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "selectedFromDate TEXT, " +
            "selectedToDate TEXT, " +
            "destination TEXT, " +
            "people INTEGER, " +
            "accommodation TEXT, " +
            "transportation TEXT, " +
            "ticketStatus TEXT, " +
            "buyTicketStatus TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        db.execSQL(CREATE_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드 시 필요한 작업 수행 (여기서는 단순히 테이블을 삭제하고 다시 생성)
        db.execSQL("DROP TABLE IF EXISTS my_table");
        onCreate(db);
    }


    public void insertData(String name, String fromDate, String toDate, String destination,
                           int people, String accommodation, String transportation,
                           String ticketStatus, String buyTicketStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("selectedFromDate", fromDate);
        values.put("selectedToDate", toDate);
        values.put("destination", destination);
        values.put("people", people);
        values.put("accommodation", accommodation);
        values.put("transportation", transportation);
        values.put("ticketStatus", ticketStatus);
        values.put("buyTicketStatus", buyTicketStatus);

        Log.d("name : ", name);
        Log.d("fromDate : ", fromDate);
        Log.d("toDate : ", toDate);
        Log.d("destination : ", destination);
        Log.d("people : ", String.valueOf(people));
        Log.d("accommodation : ", accommodation);
        Log.d("transportation : ", transportation);

        db.insert("my_table", null, values);
        db.close();
    }

    public String getDatabaseContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder contentBuilder = new StringBuilder();

        try {
            Cursor cursor = db.query("my_table", null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String selectedFromDate = cursor.getString(cursor.getColumnIndex("selectedFromDate")); // 수정된 열 이름 사용
                @SuppressLint("Range") String selectedToDate = cursor.getString(cursor.getColumnIndex("selectedToDate"));     // 수정된 열 이름 사용
                @SuppressLint("Range") String destination = cursor.getString(cursor.getColumnIndex("destination"));
                @SuppressLint("Range") String people = cursor.getString(cursor.getColumnIndex("people"));
                @SuppressLint("Range") String accommodation = cursor.getString(cursor.getColumnIndex("accommodation"));
                @SuppressLint("Range") String transportation = cursor.getString(cursor.getColumnIndex("transportation"));
                @SuppressLint("Range") String ticketStatus = cursor.getString(cursor.getColumnIndex("ticketStatus"));
                @SuppressLint("Range") String buyTicketStatus = cursor.getString(cursor.getColumnIndex("buyTicketStatus"));

                contentBuilder.append("Name: ").append(name).append("\n");
                contentBuilder.append("From Date: ").append(selectedFromDate).append("\n"); // 수정된 열 이름 사용
                contentBuilder.append("To Date: ").append(selectedToDate).append("\n");         // 수정된 열 이름 사용
                contentBuilder.append("Destination: ").append(destination).append("\n");
                contentBuilder.append("people: ").append(people).append("\n");         // 수정된 열 이름 사용
                contentBuilder.append("accommodation: ").append(accommodation).append("\n");
                contentBuilder.append("transportation: ").append(transportation).append("\n");
                contentBuilder.append("ticket status: ").append(ticketStatus).append("\n");
                contentBuilder.append("buy ticket: ").append(buyTicketStatus).append("\n\n\n");
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return contentBuilder.toString();
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("my_table", null, null);
        db.close();
    }
    /**
    // 데이터 추가 메서드
    public void insertNameData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        db.insert("my_table", null, values);
        db.close();
    }

    public void insertFromDateData(String selectedFromDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("selectedFromDate", selectedFromDate);
        db.insert("fromDates", null, values);
        db.close();
    }

    public void insertToDateData(String selectedToDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("selectedToDate", selectedToDate);
        db.insert("toDates", null, values);
        db.close();
    }

    public void insertDeatinationData(String destination) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("destination", destination);
        db.insert("Destination", null, values);
        db.close();
    }

    public void insertPeopleData(int people) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("people", people);
        db.insert("People", null, values);
        db.close();
    }

    public void insertAccommodationData(String accommodation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("accommodation", accommodation);
        db.insert("Accommodation", null, values);
        db.close();
    }

    public void insertTransportationData(String transportation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("transportation", transportation);
        db.insert("Transportation", null, values);
        db.close();
    }

    public void insertTicketStatusData(String ticketStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ticketStatus", ticketStatus);
        db.insert("TicketStatus", null, values);
        db.close();
    }

    public void insertBuyTicketStatusData(String buyTicketStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("buyTicketStatus", buyTicketStatus);
        db.insert("BuyTicketStatus", null, values);
        db.close();
    }*/
}