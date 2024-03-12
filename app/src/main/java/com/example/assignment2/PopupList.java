package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class PopupList {
    private Context mContext;

    public PopupList(Context context) {
        mContext = context;
    }

    public void showPopupList(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 5000); // 5000 milliseconds = 5 seconds
    }

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

    public void startMyFragment(Bundle args) {
        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(args);
        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout, myFragment).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        ((MainActivity) mContext).getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    public void showNewsPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("CNN News");

        WebView webView = new WebView(mContext);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl("https://edition.cnn.com/");

        builder.setView(webView);

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
