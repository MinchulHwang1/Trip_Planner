package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;
import android.app.AlertDialog;
import android.os.Handler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

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
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String myText = null;
                if(id == R.id.list) {
                    myText = "Saved List";
                    showPopupList("Saved List");
                } else if(id == R.id.weather) {
                    myText = "Weather";
                    showWeatherPopup();
                } else if(id == R.id.News) {
                    myText = "News";
                } else {
                    return true;
                }
//                TODO: Start fragment
                Bundle args = new Bundle();
                args.putString("Menu", myText);
                startMyFragment(args);

                dl.closeDrawers();
                return true;
            }

        });


    }


    private void showPopupList(String message) {
        // 팝업을 생성하고 설정합니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);

        // 팝업을 표시합니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        // 5초 후에 팝업을 닫습니다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 5000); // 5000 밀리초 = 5초
    }

    public void showWeatherPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Current Weather");
        @SuppressLint("SetJavaScriptEnabled")

        // WebView를 생성하고 설정합니다.
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadUrl("https://weather.com/weather/today/l/c5326b3950212c71c67cee42b213313f555df223d9668aabb44c6719dc67685c");

        // AlertDialog에 WebView를 추가합니다.
        builder.setView(webView);

        // AlertDialog를 표시합니다.
        builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // AlertDialog를 닫습니다.
            }
        });
        builder.show();
    }

    private void startMyFragment(Bundle args ) {
        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout, myFragment).commit();
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
        startMyFragment(args);
        dl.closeDrawers();

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
