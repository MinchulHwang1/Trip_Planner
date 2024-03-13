package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import android.os.Bundle;
import java.util.Objects;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private PopupList mPopupList;
    private SaveList mSaveList;
    Button button = null;
    EditText editText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.nv);

        mPopupList = new PopupList(this);
        mSaveList = new SaveList(this);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String myText = null;
                if(id == R.id.list) {
                    myText = "Saved List";
                    mPopupList.showPopupList();
                } else if(id == R.id.weather) {
                    myText = "Weather";
                    mPopupList.showWeatherPopup();
                } else if(id == R.id.News) {
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
        button = (Button) findViewById(R.id.buttonNameId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                       // If Button called "Make a Plan!" is clicked
                editText = findViewById(R.id.nameId);                           // Take EditText references
                String inputText = editText.getText().toString().trim();        // Take EditText Contents
                Intent intent = null;

                // If the editText Widget is empty
                if(inputText.isEmpty()){
                    Toast t = Toast.makeText(getApplicationContext(), "Please insert your name.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else{       // If not, then go to next page
                    mSaveList.saveName(inputText);
                    intent = new Intent(MainActivity.this, SecondPage.class);
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
        if(id == R.id.weather) {
            myText = "Weather";

        } else if(id == R.id.settings) {
            myText = "Settings";
        }
        Bundle args = new Bundle();
        args.putString("Menu", myText);
        mPopupList.startMyFragment(args);
        dl.closeDrawers();

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }




    /**
     * Name	    : saveName
     * Purpose  : To save the name which is insert on editText widget.
     * Inputs	: String        name        the name user input
     * Outputs	: NONE
     * Returns	: Nothing
     */

}

//    public void saveName(String name) {
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        dbHelper.insertData(name);
//    }
