package com.example.assignment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {
    private TextView text;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        assert getArguments() != null;
        String menu = getArguments().getString("Menu");
        text = view.findViewById(R.id.detail);
        text.setText(menu);
        return view;
    }
}