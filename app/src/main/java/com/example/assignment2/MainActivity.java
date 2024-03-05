package com.example.assignment2;

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
                if(id == R.id.account) {
                    myText = "Account";
                } else if(id == R.id.settings) {
                    myText = "Settings";
                } else if(id == R.id.mycart) {
                    myText = "My Cart";
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        TODO: Start fragment
        int id = item.getItemId();
        String myText = null;
        if(id == R.id.account) {
            myText = "Account";
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
