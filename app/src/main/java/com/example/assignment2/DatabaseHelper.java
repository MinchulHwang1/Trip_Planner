package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    // 테이블 생성 쿼리
    private static final String CREATE_TABLE_NAME = "CREATE TABLE my_table (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS my_table (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "departure_date TEXT, " +
                "arrival_date TEXT, " +
                "destination TEXT, " +
                "people INTEGER, " +
                "accommodation TEXT, " +
                "transportation TEXT, " +
                "ticket_status TEXT, " +
                "buy_ticket_status TEXT)");
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
        values.put("fromDate", fromDate);
        values.put("toDate", toDate);
        values.put("destination", destination);
        values.put("people", people);
        values.put("accommodation", accommodation);
        values.put("transportation", transportation);
        values.put("ticket_status", ticketStatus);
        values.put("buy_ticket_status", buyTicketStatus);
        db.insert("my_table", null, values);
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