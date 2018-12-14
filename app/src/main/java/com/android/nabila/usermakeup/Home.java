package com.android.nabila.usermakeup;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.btListSC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToListProduk("skincare");
            }
        });
        findViewById(R.id.btListMU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToListProduk("makeup");
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(Home.this, Profile.class);
                startActivity(myintent);
            }
        });
    }

    private void goToListProduk(String jenisProduk) {
        Intent intent = new Intent(this,ListProduk.class);
        intent.putExtra("jenisProduk", jenisProduk);
        startActivity(intent);
    }
}
