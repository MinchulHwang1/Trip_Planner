/**
 * File             : SecondPage.java
 * Project          : PROG3150 - Mobile Application Development
 * Programmer       : Min Chul Hwang - 8818858
 * First Version    : Feb. 11. 2024
 * Description      : This file is the second script of this project which can save the trip plan.
 *                    In this page, it can save depart/arrival date and destination of trip.
 *                    There are 2 buttons beneath of main layout, Prev and Next.
 *                    If user select Prev, the page will go to previous page
 *                    and select Next, the page will go to next page.
 *                    And if user select next button, the information of dates and destination
 *                    will go to next page with user name
 */
package com.example.assignment2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * -* CLASS COMMENT *-
 * NAME    : MainActivity - extends AppCompatActivity
 * PURPOSE : To show second main display using onCreate method, and instantiate all method
 */
public class SecondPage extends AppCompatActivity {
    // Instantiate buttons
    Button fromDateButton = null;
    Button toDateButton = null;
    Button prevButton = null;
    Button nextButton = null;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv1;
    private PopupList mPopupList;
    private SaveList mSaveList;

    /**
     * Name	    : onCreate
     * Purpose  : Instantiate variables and display the factors
     * Inputs	: Bundle        savedInstanceState      Save data needed to reload UI state if the system stops and recreates UI
     * Outputs	: Message which is fit on the situation.
     * Returns	: Nothing
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        mPopupList = new PopupList(this);
        mSaveList = new SaveList(this);

        // Get name from before page
        String name = mSaveList.loadName();
        TextView nameTextView = findViewById(R.id.inputNameString);
        nameTextView.setText(name);


        dl = findViewById(R.id.second_pageDrawer);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nv1 = findViewById(R.id.nv1);
        nv1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String myText = null;
                if (id == R.id.list) {
                    myText = "Saved List";
                    mPopupList.showPopupList();
                } else if (id == R.id.weather) {
                    myText = "Weather";
                    mPopupList.showWeatherPopup();
                } else if (id == R.id.News) {
                    myText = "News";
                    mPopupList.showNewsPopup();
                } else if(id == R.id.Contacts){
                    myText = "Contacts";
                    mPopupList.showContact();
                }
                else {
                    return true;
                }
//                TODO: Start fragment
                Bundle args = new Bundle();
                args.putString("Menu", myText);
                mPopupList.startMyFragment(args);

                dl.closeDrawers();
                return true;
            }

        });

        // Action for a button that specifies a departure date
        fromDateButton = findViewById(R.id.fromButtonId);
        TextView fromDateTextView = findViewById(R.id.fromDateId);
        fromDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This declaration is needed to make DatePicker widget to pick departure date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SecondPage.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String selectedFromDate = selectedYear + "/" + (selectedMonth + 1) + "/" + selectedDay;
                                mSaveList.saveSelectedFromDate(selectedFromDate);

                                fromDateTextView.setText(selectedFromDate);
                            }
                        },
                        year,
                        month,
                        day
                );
                // DatePickerDialog 표시
                datePickerDialog.show();
            }
        });

        // Action for a button that specifies a arrival date
        toDateButton = findViewById(R.id.toButtonId);
        TextView toDateTextView = findViewById(R.id.toDateId);
        toDateButton.setOnClickListener(new View.OnClickListener() {
            // This declaration is needed to make DatePicker widget to pick arrival date
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SecondPage.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String selectedToDate = selectedYear + "/" + (selectedMonth + 1) + "/" + selectedDay;
                                String fromDateText = fromDateTextView.getText().toString();

                                // To check the arrival date is formal of departure date or not
                                if (mSaveList.isValidDate(selectedToDate, fromDateText)) {
                                    mSaveList.saveSelectedToDate(selectedToDate);
                                    toDateTextView.setText(selectedToDate);
                                } else {
                                    Toast t = Toast.makeText(SecondPage.this,
                                            "Invalid date selection. toDate should not be before fromDate.", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        },
                        year,
                        month,
                        day
                );
                datePickerDialog.show();
            }
        });

        // Prev button
        prevButton = findViewById(R.id.prevButtonId);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextButton = findViewById(R.id.nextButtonId);
        EditText destinationEditText = findViewById(R.id.destinationId);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedFromDate = fromDateTextView.getText().toString();
                String selectedToDate = toDateTextView.getText().toString();
                String destinationText = destinationEditText.getText().toString();
                Intent intent = null;

                // Check selectedFromDate, selectedToDate, destination variables are empty or not
                if (selectedFromDate.isEmpty() || selectedToDate.isEmpty() || destinationText.isEmpty()) {
                    Toast.makeText(SecondPage.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                } else if (!mSaveList.isValidDate(selectedToDate, selectedFromDate)) {
                    Toast.makeText(SecondPage.this, "Invalid date selection. toDate should not be before fromDate.", Toast.LENGTH_SHORT).show();
                } else {
                    // Save all information and send them to next page
                    mSaveList.saveSelectedToDate(selectedToDate);
                    mSaveList.saveSelectedFromDate(selectedFromDate);
                    mSaveList.saveDestination(destinationText);

                    intent = new Intent(SecondPage.this, ThirdPage.class);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }


    // 필요하면 사용하기
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        TODO: Start fragment
        int id = item.getItemId();
        String myText = null;
        if (id == R.id.weather) {
            myText = "Weather";

        } else if (id == R.id.settings) {
            myText = "Settings";
        }
        Bundle args = new Bundle();
        args.putString("Menu", myText);
        mPopupList.startMyFragment(args);
        dl.closeDrawers();

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}





//    @SuppressLint("Range")
//    private String loadName() {
//        String name = null;
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query("my_table", new String[]{"name"}, null, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            name = cursor.getString(cursor.getColumnIndex("name"));
//            cursor.close();
//        }
//        db.close();
//        return name;
//    }
