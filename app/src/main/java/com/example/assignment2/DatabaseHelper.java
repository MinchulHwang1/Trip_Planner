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
    private static final int DATABASE_VERSION = 3;

    // Table query
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
            "buyTicketStatus TEXT, " +
            "savedTime TEXT)";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        db.execSQL(CREATE_TABLE_NAME);
    }

    //Upgrade Database Table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드 시 필요한 작업 수행 (여기서는 단순히 테이블을 삭제하고 다시 생성)
        db.execSQL("DROP TABLE IF EXISTS my_table");
        onCreate(db);
    }

    /**
     * Name	    : insertData
     * Purpose  : to insert data to table
     * Inputs	: a data which is fit on key and value
     * Outputs	: log messages that each value of each key
     * Returns	: NONE
     */
    public void insertData(String name, String fromDate, String toDate, String destination,
                           int people, String accommodation, String transportation,
                           String ticketStatus, String buyTicketStatus, String savedTime) {
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
        values.put("savedTime", savedTime);

        Log.d("name : ", name);
        Log.d("fromDate : ", fromDate);
        Log.d("toDate : ", toDate);
        Log.d("destination : ", destination);
        Log.d("people : ", String.valueOf(people));
        Log.d("accommodation : ", accommodation);
        Log.d("transportation : ", transportation);
        Log.d("savedTime : ", savedTime);

        db.insert("my_table", null, values);
        db.close();
    }

    /**
     * Name	    : getDatabaseContent
     * Purpose  : to get data which is saved in Database
     * Inputs	: None
     * Outputs	: NONE
     * Returns	: a list of query
     */
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
                @SuppressLint("Range") int people = cursor.getInt(cursor.getColumnIndex("people"));
                @SuppressLint("Range") String accommodation = cursor.getString(cursor.getColumnIndex("accommodation"));
                @SuppressLint("Range") String transportation = cursor.getString(cursor.getColumnIndex("transportation"));
                @SuppressLint("Range") String ticketStatus = cursor.getString(cursor.getColumnIndex("ticketStatus"));
                @SuppressLint("Range") String buyTicketStatus = cursor.getString(cursor.getColumnIndex("buyTicketStatus"));
                @SuppressLint("Range") String savedTime = cursor.getString(cursor.getColumnIndex("savedTime"));

                contentBuilder.append("Name: ").append(name).append("\n");
                contentBuilder.append("From Date: ").append(selectedFromDate).append("\n"); // 수정된 열 이름 사용
                contentBuilder.append("To Date: ").append(selectedToDate).append("\n");         // 수정된 열 이름 사용
                contentBuilder.append("Destination: ").append(destination).append("\n");
                contentBuilder.append("people: ").append(people).append("\n");         // 수정된 열 이름 사용
                contentBuilder.append("accommodation: ").append(accommodation).append("\n");
                contentBuilder.append("transportation: ").append(transportation).append("\n");
                contentBuilder.append("ticket status: ").append(ticketStatus).append("\n");
                contentBuilder.append("buy ticket: ").append(buyTicketStatus).append("\n");
                contentBuilder.append("saved time: ").append(savedTime).append("\n\n\n");

                Log.d("name 1: ", name);
                Log.d("fromDate 1: ", selectedFromDate);
                Log.d("toDate 1: ", selectedToDate);
                Log.d("destination 1: ", destination);
                Log.d("people 1: ", String.valueOf(people));
                Log.d("accommodation 1: ", accommodation);
                Log.d("transportation 1: ", transportation);
                Log.d("savedTime 1: ", savedTime);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return contentBuilder.toString();
    }

    /**
     * Name	    : deleteAllData
     * Purpose  : to delete all data in Database
     * Inputs	: None
     * Outputs	: NONE
     * Returns	: NONE
     */
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("my_table", null, null);
        db.close();
    }
}