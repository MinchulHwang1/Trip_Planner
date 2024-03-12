/**
 * File             : FourthPage.java
 * Project          : PROG3150 - Mobile Application Development
 * Programmer       : Min Chul Hwang - 8818858
 * First Version    : Feb. 11. 2024
 * Description      : This file is the fourth script of this project which can save the trip plan.
 *                    In this page, it can save the transportation and status of ticket.
 *                    There are 2 buttons beneath of main layout, Prev and Next.
 *                    If user select Prev, the page will go to previous page
 *                    and select Next, the page will go to next page.
 *                    And if user select next button, the information of dates, destination and information which get this page
 *                    will go to next page with user name.
 */

package com.example.assignment2;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class FourthPage extends AppCompatActivity {
    RadioGroup radioGroup = null;
    RadioGroup ticketRadioGroup = null;
    RadioGroup ticketBuyRadioGroup = null;
    Button buyNowButton = null;
    Button nextButton = null;
    Button prevButton = null;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv3;
    private PopupList mPopupList;
    private SaveList mSaveList;

    private boolean checkActivitedButton = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_page);

        mPopupList = new PopupList(this);
        mSaveList = new SaveList(this);

        // Take information from before page
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
        selectedAccommodationView.setText(selectedAccommodation);

        radioGroup = findViewById(R.id.radioGroup);
        ticketRadioGroup = findViewById(R.id.radioGroupForTicket);
        ticketBuyRadioGroup = findViewById(R.id.radioGroupForBuyingTicket);
        buyNowButton = findViewById(R.id.buyNowButtonId);
        nextButton = findViewById(R.id.nextButtonId);
        prevButton = findViewById(R.id.prevButtonId);

        // the button is activity when the right situation
        disableRadioGroup(ticketRadioGroup);
        disableRadioGroup(ticketBuyRadioGroup);
        buyNowButton.setEnabled(false);
        nextButton.setEnabled(false);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                if(selectedRadioButton != null){
                    String selectedTransportation = selectedRadioButton.getText().toString();
                    if("Car".equals(selectedTransportation)){
                        disableRadioGroup(ticketRadioGroup);
                        disableRadioGroup(ticketBuyRadioGroup);
                        buyNowButton.setEnabled(false);
                        nextButton.setEnabled(true);
                    }
                    else {
                        enableRadioGroup(ticketRadioGroup);
                        nextButton.setEnabled(false);
                    }
                    mSaveList.saveSelectedTransportation(selectedTransportation);
                }
            }
        });

        ticketRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String SelectedTicketStatus = null;
                if(selectedRadioButton != null && checkedId == R.id.yesButton){
                    SelectedTicketStatus = selectedRadioButton.getText().toString();
                    disableRadioGroup(ticketBuyRadioGroup);
                    buyNowButton.setEnabled(false);
                    mSaveList.saveTicketStatus(SelectedTicketStatus);
                    nextButton.setEnabled(true);
                }
                else if(selectedRadioButton == null){
                    disableRadioGroup(ticketBuyRadioGroup);
                    buyNowButton.setEnabled(false);
                    nextButton.setEnabled(false);
                }
                else{
                    SelectedTicketStatus = selectedRadioButton.getText().toString();
                    enableRadioGroup(ticketBuyRadioGroup);
                    nextButton.setEnabled(false);
                    mSaveList.saveTicketStatus(SelectedTicketStatus);
                }
            }
        });


        ticketBuyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String SelectedTicketStatus = null;
                if(selectedRadioButton != null && checkedId == R.id.noButton1){
                    SelectedTicketStatus = selectedRadioButton.getText().toString();
                    buyNowButton.setEnabled(false);
                    nextButton.setEnabled(true);
                    checkActivitedButton = false;
                    mSaveList.saveBuyTicketStatus(SelectedTicketStatus);
                }
                else if(selectedRadioButton == null){
                    buyNowButton.setEnabled(false);
                    nextButton.setEnabled(false);
                    checkActivitedButton = false;
                }
                else{
                    SelectedTicketStatus = selectedRadioButton.getText().toString();
                    buyNowButton.setEnabled(true);
                    nextButton.setEnabled(false);
                    mSaveList.saveBuyTicketStatus(SelectedTicketStatus);
                }
            }
        });

        dl = findViewById(R.id.fourth_pageDrawer);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nv3 = findViewById(R.id.nv3);

        nv3.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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


        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View v) {

                WebView webView = findViewById(R.id.myWebView);
                checkActivitedButton = true;

                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return false;
                    }
                });

                webView.loadUrl("https://www.skyscanner.ca");
                buyNowButton.setEnabled(false);
                prevButton.setEnabled(true);
                nextButton.setEnabled(true);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseHelper dbHelper;
        dbHelper = new DatabaseHelper(this);
        nextButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View v) {
                String name = mSaveList.loadName();
                String fromDate = mSaveList.loadFromDate();
                String toDate = mSaveList.loadToDate();
                String destination = mSaveList.loadDestination();
                int people = mSaveList.loadPeople();
                String accommodation = mSaveList.loadAccommodation();
                String transportation = mSaveList.loadSelectedTransportation();
                String ticketStatus = mSaveList.loadTicketStatus();
                String buyTicketStatus = mSaveList.loadBuyTicketStatus();

                dbHelper.insertData(name, fromDate, toDate,destination, people, accommodation, transportation, ticketStatus, buyTicketStatus);
                /*
                String selectedTransportation = loadSelectedTransportation();
                saveSelectedTransportationToDatabase(selectedTransportation);


                String selectedTransportationFromDB = loadSelectedTransportationFromDatabase();
                Log.d("Database", "Selected Transportation from Database: " + selectedTransportationFromDB);
*/
                intent = new Intent(FourthPage.this, FifthPage.class);
                startActivity(intent);
            }
        });
    }
    private void enableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(true);
        }
    }

    private void disableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

}
