package com.apps.heber.restaurante.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.heber.restaurante.R;

public class AdicionarDespesaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_despesa);
        this.getSupportActionBar().setTitle("Adicionar Despesa");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
