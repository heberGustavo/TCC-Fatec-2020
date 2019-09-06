package com.apps.heber.restaurante.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.heber.restaurante.R;

public class MesasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mesas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
