package com.sisihub.pushnotification.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sisihub.pushnotification.R;
import com.sisihub.pushnotification.notificaations.MainActivity;
import com.sisihub.pushnotification.portal.StudentsPortal;

public class Home extends AppCompatActivity implements View.OnClickListener {
    ImageButton notB,abB,hB,portB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        notB = (ImageButton) findViewById(R.id.notificationsButon);
        abB = (ImageButton) findViewById(R.id.aboutButton);
        hB = (ImageButton) findViewById(R.id.homeButton);
        portB = (ImageButton) findViewById(R.id.portalButton);
        notB.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.notificationsButon:
                startActivity(new Intent(this, MainActivity.class));
            break;
            case R.id.portalButton:
                startActivity(new Intent(this, StudentsPortal.class));
            break;
        }
    }
}
