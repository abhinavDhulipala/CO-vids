package com.abhi.co_vids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DangerSympActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_symp);
        findViewById(R.id.mildSymp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MildSympActivity.class));
            }
        });

    }


    public void emergency(View view) {
        startActivity(new Intent(getApplicationContext(), EmergencyActivity.class));
    }
}
