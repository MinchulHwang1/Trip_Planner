/**
 * File             : FourthPage.java
 * Project          : PROG3150 - Mobile Application Development
 * Programmer       : Min Chul Hwang - 8818858
 * First Version    : Feb. 11. 2024
 * Description      : This file is the fifth script of this project which can save the trip plan.
 *                    In this page, it will show what the user input or select.
 *                    and user can check the information which is correct.
 */

package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

public class FifthPage extends AppCompatActivity {

    Button prevButton = null;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv4;
    private PopupList mPopupList;
    private SaveList mSaveList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_page);

        mPopupList = new PopupList(this);
        mSaveList = new SaveList(this);


        String name = mSaveList.loadName();
        TextView nameTextView = findViewById(R.id.inputNameString);
        nameTextView.setText(name);

        String destination = mSaveList.loadDestination();
        TextView destinationTextView = findViewById(R.id.destinationId);
        destinationTextView.setText(destination);

        String selectedFromDate = mSaveList.loadFromDate();
        TextView selectedFromDataView = findViewById(R.id.fromDateId);
        selectedFromDataView.setText(selectedFromDate);

        String selectedToDate = mSaveList.loadToDate();
        TextView selectedToDataView = findViewById(R.id.toDateId);
        selectedToDataView.setText(selectedToDate);

        TextView totalPeopleView = findViewById(R.id.totalId);
        int loadedPeople = mSaveList.loadPeople();
        totalPeopleView.setText(String.valueOf(loadedPeople));

        String selectedAccommodation = mSaveList.loadAccommodation();
        TextView selectedAccommodationView = findViewById(R.id.selectedAccommodationId);
        selectedAccommodationView.setText(selectedAccommodation);;


        String selectedTransportation = mSaveList.loadSelectedTransportation();
        TextView selectedTransportationView = findViewById(R.id.wayToGoId);
        selectedTransportationView.setText(selectedTransportation);

/*
        String selectedTransportation = loadSelectedTransportationFromDatabase();
        TextView selectedTransportationView = findViewById(R.id.wayToGoId);
        selectedTransportationView.setText(selectedTransportation);*/

        String selectedTicketStatus = mSaveList.loadTicketStatus();
        TextView selectedTicketStatusView = findViewById(R.id.ticketStatusId);
//        selectedTicketStatusView.setText(selectedTicketStatus);

        String selectedBuyTicketStatus = mSaveList.loadBuyTicketStatus();


        String ticketStatus = null;
        if("Car".equals(selectedTransportation)){
            ticketStatus = "Not Required";
        }
        else if("Train".equals(selectedTransportation)){
            if("Yes".equals(selectedTicketStatus)){
                ticketStatus = "Not Required";
            }
            else{
                if("No".equals(selectedBuyTicketStatus)){
                    ticketStatus = "Required";
                }
                else {
                    ticketStatus = "Not Required";
                }
            }
        }
        else{
            if("Yes".equals(selectedTicketStatus)){
                ticketStatus = "Not Required";
            }
            else{
                if("No".equals(selectedBuyTicketStatus)){
                    ticketStatus = "Required";
                }
                else {
                    ticketStatus = "Not Required";
                }
            }
        }
        selectedTicketStatusView.setText(ticketStatus);


        dl = findViewById(R.id.fifth_pageDrawer);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nv4 = findViewById(R.id.nv4);
        nv4.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String myText = null;
                if (id == R.id.list) {
                    myText = "Saved List";
                    mPopupList.showPopupList("Saved List");
                } else if (id == R.id.weather) {
                    myText = "Weather";
                    mPopupList.showWeatherPopup();
                } else if (id == R.id.News) {
                    myText = "News";
                    mPopupList.showNewsPopup();
                } else {
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


        prevButton = findViewById(R.id.prevButtonId);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
