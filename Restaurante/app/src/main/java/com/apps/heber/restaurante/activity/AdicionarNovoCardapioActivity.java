package com.apps.heber.restaurante.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.apps.heber.restaurante.R;

import java.util.ArrayList;
import java.util.List;

public class AdicionarNovoCardapioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_novo_cardapio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Novo Cardapio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
