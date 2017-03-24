package com.sisihub.pushnotification.portal;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sisihub.pushnotification.R;

public class Portal extends AppCompatActivity {
    private String[] drawerConts;
    private DrawerLayout myDrawer;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        drawerConts = getResources().getStringArray(R.array.list_array);
        myDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_nav_items);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list, drawerConts));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

    }
}
