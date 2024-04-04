/**
 * File             : ThirdPage.java
 * Project          : PROG3150 - Mobile Application Development
 * Programmer       : Min Chul Hwang - 8818858
 * First Version    : Feb. 11. 2024
 * Description      : This file is the third script of this project which can save the trip plan.
 *                    In this page, it can save the number of people and accommodation where user want to stay.
 *                    There are 2 buttons beneath of main layout, Prev and Next.
 *                    If user select Prev, the page will go to previous page
 *                    and select Next, the page will go to next page.
 *                    And if user select next button, the information of dates and destination
 *                    will go to next page with user name.
 */
package com.example.assignment2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

/**
 * -* CLASS COMMENT *-
 * NAME    : MainActivity - extends AppCompatActivity
 * PURPOSE : To show third main display using onCreate method, and instantiate all method
 */
public class ThirdPage extends AppCompatActivity {

    Button prevButton = null;
    Button nextButton = null;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv2;
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
        setContentView(R.layout.third_page);

        mPopupList = new PopupList(this);
        mSaveList = new SaveList(this);

        // Get name from before page
        String name = mSaveList.loadName();
        TextView nameTextView = findViewById(R.id.inputNameString);
        nameTextView.setText(name);

        // Get destination from before page
        String destination = mSaveList.loadDestination();
        TextView destinationTextView = findViewById(R.id.destinationId);
        destinationTextView.setText(destination);

        // Get selectedFromDate from before page
        String selectedFromDate = mSaveList.loadFromDate();
        TextView selectedFromDataView = findViewById(R.id.fromDateId);
        selectedFromDataView.setText(selectedFromDate);

        // Get selectedToDate from before page
        String selectedToDate = mSaveList.loadToDate();
        TextView selectedToDataView = findViewById(R.id.toDateId);
        selectedToDataView.setText(selectedToDate);

        // Get seekBar and show the number of people
        SeekBar seekBar = findViewById(R.id.seekbar);
        TextView totalPeople = findViewById(R.id.totalId);

        dl = findViewById(R.id.third_pageDrawer);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nv2 = findViewById(R.id.nv2);

        nv2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // Check when the seekbar has been changed
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // calculate at least number
                totalPeople.setText(String.valueOf(progress+1));
                mSaveList.savePeople(progress+1);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Make spinner to select accommodation.
        // There are lots of options on it.
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                ThirdPage.this, R.array.accommodation_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        // Set previous button to back before page
        prevButton = findViewById(R.id.prevButtonId);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set Next page to go next page
        nextButton = findViewById(R.id.nextButtonId);
        nextButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View v) {

                // There is not option to check what the user input is correct or not,
                // So it needs to be check alert dialog.
                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdPage.this);

                builder.setTitle("Confirmation");

                String confirmationMessage = "Are you sure to move next page?\n"
                        + "People: " + totalPeople.getText() + "\n"
                        + "Accommodation: " + spinner.getSelectedItem().toString();

                builder.setMessage(confirmationMessage);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // If user said yes, the spinner information is saved and send it to next page
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedAccommodation = spinner.getSelectedItem().toString();
                        mSaveList.saveAccommodation(selectedAccommodation);
                        intent = new Intent(ThirdPage.this, FourthPage.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // Or just stay in that page
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
