package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.apps.heber.restaurante.R;

public class CategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void abrirAdicionarNovaCategoria(View view){
        startActivity(new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class));
    }
}

