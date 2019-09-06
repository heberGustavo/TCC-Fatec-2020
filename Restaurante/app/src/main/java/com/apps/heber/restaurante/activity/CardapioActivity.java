package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.apps.heber.restaurante.R;

public class CardapioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cardápio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void adicionarNovoCardapio(View view){
        startActivity(new Intent(CardapioActivity.this, AdicionarNovoCardapioActivity.class));
    }
}