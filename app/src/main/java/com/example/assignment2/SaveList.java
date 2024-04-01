package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
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

    /**
     * Name	    : saveSelectedFromDate
     * Purpose  : To save the departure date which is selected on Datepicker widget.
     * Inputs	: String        selectedFromDate        the date user picked
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void saveSelectedFromDate(String selectedFromDate) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selectedFromDate", selectedFromDate);
        editor.apply();
    }

    /**
     * Name	    : saveSelectedToDate
     * Purpose  : To save the arrival date which is selected on Datepicker widget.
     * Inputs	: String        selectedToDate        the date user picked
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void saveSelectedToDate(String selectedToDate) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selectedToDate", selectedToDate);
        editor.apply();
    }

    /**
     * Name	    : isValidDate
     * Purpose  : To check the dates user picked is vaild or not
     *            It only can be between departure date and arrival date
     * Inputs	: String        toDate        the date user picked
     *            String        fromDate      the date user picked
     * Outputs	: NONE
     * Returns	: Nothing
     */
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

    /**
     * Name	    : saveDestination
     * Purpose  : To save the destination written down in editText area.
     * Inputs	: String        destination        the destination written in editText area
     * Outputs	: NONE
     * Returns	: Nothing
     */
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

    /**
     * Name	    : loadPeople
     * Purpose  : To load people number from seek bar
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: Value of people
     */
    public int loadPeople() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getInt("people", 1);
    }

    /**
     * Name	    : loadAccommodation
     * Purpose  : To load Accommodation from spinner
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: string of Accommodation
     */
    public String loadAccommodation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("Accommodation", "");
    }

    /**
     * Name	    : loadSelectedTransportation
     * Purpose  : To load transportation from radio button
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: string of Transportation
     */
    public String loadSelectedTransportation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("selectedTransportation", "");
    }

    /**
     * Name	    : saveSelectedTransportation
     * Purpose  : To Save transportation from radio button
     * Inputs	: String       selectedTransportation       A string of transportation
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void saveSelectedTransportation(String selectedTransportation) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selectedTransportation", selectedTransportation);
        editor.apply();
    }

    /**
     * Name	    : saveTicketStatus
     * Purpose  : To Save ticket status from radio button
     * Inputs	: String        saveTicketStatusString      A string of ticket status
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void saveTicketStatus(String saveTicketStatusString) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("saveTicketStatus", saveTicketStatusString);
        editor.apply();
    }

    /**
     * Name	    : saveBuyTicketStatus
     * Purpose  : To Save buy-ticket status from radio button
     * Inputs	: String        saveBuyTicketStatusString       A string of buy-ticket status
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void saveBuyTicketStatus(String saveBuyTicketStatusString) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("saveBuyTicketStatus", saveBuyTicketStatusString);
        editor.apply();
    }

    /**
     * Name	    : loadTicketStatus
     * Purpose  : To load ticket status from radio button
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: A string of saveTicketStatus value
     */
    public String loadTicketStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("saveTicketStatus", "");
    }

    /**
     * Name	    : loadBuyTicketStatus
     * Purpose  : To load buy-ticket status from radio button
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: A string of saveBuyTicketStatus value
     */
    public String loadBuyTicketStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("saveBuyTicketStatus", "");
    }

    public void savedTime(String savedTimeString) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        Log.d( "savedTimefromMethod: ", savedTimeString);
        editor.putString("savedTime", savedTimeString);
        editor.apply();
    }

    public String loadSavedTime() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("savedTime", "");
    }
}
