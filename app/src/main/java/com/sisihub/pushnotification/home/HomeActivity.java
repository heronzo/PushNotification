package com.sisihub.pushnotification.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.sisihub.pushnotification.R;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String[] hItems = {"Home","Portal","Notifications","AboutApp"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GridView gridView = (GridView) findViewById(R.id.homeGrid);
        gridView.setAdapter(new ArrayAdapter<String>(this, R.layout.cell, hItems));
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
