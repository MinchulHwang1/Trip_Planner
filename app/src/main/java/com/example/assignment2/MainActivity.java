package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.content.SharedPreferences;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private PopupList mPopupList;
    private SaveList mSaveList;
    Button button = null;
    EditText editText = null;


    public MyContactInfo myContactInfo;
    private static final int PERMISSION_REQUEST_CODE = 100;
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

        myContactInfo = new MyContactInfo(this);

        //myContactInfo.addContact("Minchul Hwang", "548-333-4892", "mhwang8858@conestogac.on.ca");
        mPopupList = new PopupList(this);
        mSaveList = new SaveList(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        }
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
                } else if(id == R.id.Map){
                    myText = "Map";
                    mPopupList.showMapPopup();
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

        } else if(id == R.id.Contacts) {
            myText = "Contacts";
        }
        Bundle args = new Bundle();
        args.putString("Menu", myText);
        mPopupList.startMyFragment(args);
        dl.closeDrawers();

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void requestPermission() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 경우 권한 요청 다이얼로그 표시
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // 권한 요청 결과 처리
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여된 경우
                Toast.makeText(this, "Permission Granted.", Toast.LENGTH_SHORT).show();
            } else {
                // 권한이 거부된 경우
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                // 사용자에게 권한 설정을 위한 안내 메시지를 표시하고 시스템 설정으로 이동할 수 있도록 유도
                showPermissionDialog();
            }
        }
    }

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission settings required")
                .setMessage("To use the app, location permission is required. Please allow the permission in settings..")
                .setPositiveButton("Move to setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 시스템 설정 화면으로 이동
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}

