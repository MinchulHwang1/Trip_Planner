package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveList {
    private Context mContext;

    public SaveList(Context context) {
        mContext = context;
    }

    /**
     * Name	    : saveName
     * Purpose  : To save the name which is insert on editText widget.
     * Inputs	: String        name        the name user input
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void saveName(String name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Name", name);
        editor.apply();
    }

    /**
     * Name	    : loadName
     * Purpose  : To load name data called Name saved in main page
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public String loadName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("Name", "");
    }


    public void saveSelectedFromDate(String selectedFromDate) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selectedFromDate", selectedFromDate);
        editor.apply();
    }

    public void saveSelectedToDate(String selectedToDate) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selectedToDate", selectedToDate);
        editor.apply();
    }

    public boolean isValidDate(String toDate, String fromDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date toDateObj = simpleDateFormat.parse(toDate);
            Date fromDateObj = simpleDateFormat.parse(fromDate);

            return !toDateObj.before(fromDateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveDestination(String destination) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Destination", destination);
        editor.apply();
    }

    /**
     * Name	    : loadDestination
     * Purpose  : To load destination data called Destination saved in second page
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public String loadDestination() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("Destination", "");
    }

    /**
     * Name	    : loadFromDate
     * Purpose  : To load departure date data called selectedFromDate saved in second page
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public String loadFromDate() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("selectedFromDate", "");
    }

    /**
     * Name	    : loadToDate
     * Purpose  : To load arrival date data called selectedToDate saved in second page
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public String loadToDate() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("selectedToDate", "");
    }

    /**
     * Name	    : savePeople
     * Purpose  : To save the number of people checked in seek bar.
     * Inputs	: int        people        the number of people
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void savePeople(int people) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("people", people);
        editor.apply();
    }

    /**
     * Name	    : saveAccommodation
     * Purpose  : To save the accommodation selected in spinner.
     * Inputs	: String        accommodation        the string of accommodation
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void saveAccommodation(String accommodation) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Accommodation", accommodation);
        editor.apply();
    }

    public int loadPeople() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getInt("people", 1);
    }

    public String loadAccommodation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("Accommodation", "");
    }

    public String loadSelectedTransportation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("selectedTransportation", "");
    }

    public void saveSelectedTransportation(String selectedTransportation) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selectedTransportation", selectedTransportation);
        editor.apply();
    }
    public void saveTicketStatus(String saveTicketStatusString) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("saveTicketStatus", saveTicketStatusString);
        editor.apply();
    }

    public void saveBuyTicketStatus(String saveBuyTicketStatusString) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("saveBuyTicketStatus", saveBuyTicketStatusString);
        editor.apply();
    }

    public String loadTicketStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("saveTicketStatus", "");
    }

    public String loadBuyTicketStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("saveBuyTicketStatus", "");
    }
}
