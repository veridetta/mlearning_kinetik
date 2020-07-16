package com.vrcorp.mobilelearningfisika_gelombangbunyi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LatihanList extends AppCompatActivity {
    CardView latihan, tes, home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        latihan = findViewById(R.id.btn_latihan);
        tes = findViewById(R.id.btn_tes);
        home = findViewById(R.id.btn_home);
        latihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatihanList.this, LatihanSoal.class);
                startActivity(intent);
            }
        });
        tes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatihanList.this, TesActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LatihanList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}