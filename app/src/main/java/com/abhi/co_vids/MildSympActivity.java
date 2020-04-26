package com.abhi.co_vids;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MildSympActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mild_symp);
        final CheckBox moreSerious1 = findViewById(R.id.checkBox);
        final CheckBox moreSerious2 = findViewById(R.id.checkBox2);
        final CheckBox moreSerious3 = findViewById(R.id.checkBox3);
        final CheckBox lessSerious1 = findViewById(R.id.checkBox5);
        final CheckBox lessSerious2 = findViewById(R.id.checkBox4);
        final CheckBox lessSerious3 = findViewById(R.id.checkBox6);
        final TextView noCovid = findViewById(R.id.noCovid);

        Button continueOn = findViewById(R.id.continueOn);
        continueOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int overAllSeriousness = 0;
                overAllSeriousness += moreSerious1.isChecked() ? 3 : 0;
                overAllSeriousness += moreSerious2.isChecked() ? 3 : 0;
                overAllSeriousness += moreSerious3.isChecked() ? 3 : 0;
                overAllSeriousness += lessSerious1.isChecked() ? 1 : 0;
                overAllSeriousness += lessSerious2.isChecked() ? 1 : 0;
                overAllSeriousness += lessSerious3.isChecked() ? 1 : 0;
                Log.d("OC", "onClick: " + overAllSeriousness);
                if (overAllSeriousness >= 3) {
                    Log.d("OC", "onClick: serious");
                    startActivity(new Intent(getApplicationContext(), CovidLikeActivity.class));
                } else {
                    noCovid.setVisibility(View.VISIBLE);
                    noCovid.setEnabled(true);
                }
            }
        });

        noCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

}
