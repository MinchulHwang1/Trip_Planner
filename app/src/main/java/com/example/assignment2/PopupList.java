package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopupList {
    private Context mContext;

    /**
    * Constructor
    * */
    public PopupList(Context context) {
        mContext = context;
    }

    /**
     * Name	    : showPopupList
     * Purpose  : To show list as popup which is from Database
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void showPopupList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Plan List");
        @SuppressLint("SetJavaScriptEnabled")

        DatabaseHelper dbHelper = new DatabaseHelper(mContext);     // Call DB helper
        String databaseContent = dbHelper.getDatabaseContent();
        //dbHelper.deleteAllData();
        Log.d("DatabaseContent:", databaseContent);

        builder.setMessage(databaseContent);                        // Show all data in Pop-up

        builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Name	    : showWeatherPopup
     * Purpose  : To show weather as popup as webview
     * Inputs	: NONE
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void showWeatherPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Current Weather");
        @SuppressLint("SetJavaScriptEnabled")

        WebView webView = new WebView(mContext);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl("https://weather.com/weather/today/l/c5326b3950212c71c67cee42b213313f555df223d9668aabb44c6719dc67685c");

        builder.setView(webView);

        builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Name	    : startMyFragment
     * Purpose  : To start fragment
     * Inputs	: Bundle        args        to make all data as background
     * Outputs	: NONE
     * Returns	: Nothing
     */
    public void startMyFragment(Bundle args) {
        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(args);
        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout, myFragment).commit();
    }

    /**
     * Name	    : onCreateOptionsMenu
     * Purpose  : to Ceeate Option Menu
     * Inputs	: Menu      menu        a menu which is made as XML file
     * Outputs	: NONE
     * Returns	: true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        ((MainActivity) mContext).getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    /**
     * Name	    : showNewsPopup
     * Purpose  : to show news from CNN Feed.
     * Inputs	: NONE
     * Outputs	: Shows a list of news title
     * Returns	: NONE
     */
    @SuppressLint("StaticFieldLeak")
    public void showNewsPopup() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {

                    URL url = new URL("http://rss.cnn.com/rss/edition_world.rss");

                    // connect HTTP and read RSS feed
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();

                    // Convert RSS feed to string
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    Log.d("stream", stringBuilder.toString());
                    reader.close();
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String rssFeed) {
                // Feed RSS and parsing RSS data
                if (rssFeed != null) {
                    try {
                        // Parsing XML data to show up on Pop-up
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = factory.newPullParser();
                        parser.setInput(new StringReader(rssFeed));

                        int eventType = parser.getEventType();

                        // Title list
                        List<String> titles = new ArrayList<>();

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            String tagName = parser.getName();
                            switch (eventType) {
                                case XmlPullParser.START_TAG:
                                    if (tagName.equalsIgnoreCase("title")) {
                                        String title = parser.nextText();
                                        titles.add(title);
                                    }
                                    break;
                            }
                            eventType = parser.next();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("CNN News");
                        builder.setItems(titles.toArray(new String[0]), null);
                        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to fetch RSS feed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void showContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Contacts");
        @SuppressLint("SetJavaScriptEnabled")

        MyContactInfo myContactInfo = new MyContactInfo(mContext);
        String contactContent = myContactInfo.getAllContacts().toString();
        String stringWithoutBrackets = contactContent.substring(1, contactContent.length() - 1);

        Log.d("DatabaseContent:", stringWithoutBrackets);

        builder.setMessage(stringWithoutBrackets);                        // Show all data in Pop-up

        builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }



    public void showMapPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // LayoutInflater를 사용하여 XML 레이아웃 파일을 인플레이트
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.map_dialog_layout, null);

        // AlertDialog에 설정할 레이아웃 설정
        builder.setView(dialogView);
        builder.setTitle("Current Location"); // AlertDialog의 제목 설정

        // AlertDialog 생성
        AlertDialog alertDialog = builder.create();

        // AlertDialog 보여주기
        alertDialog.show();

        // MapView를 참조하여 Google 지도 초기화
        MapView mapView = dialogView.findViewById(R.id.map_view);
        mapView.onCreate(alertDialog.onSaveInstanceState()); // MapView의 라이프사이클 초기화
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng location = new LatLng(37.5665, 126.9780);
                //googleMap.addMarker(new MarkerOptions().position(location).title("Current Location"));
                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

            }
        });

        alertDialog.show();
    }


}
