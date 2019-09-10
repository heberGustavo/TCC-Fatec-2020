package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapioCategoria;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.List;

public class CardapioCategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerCardapioCategoria;
    private AdapterCardapioCategoria adapterCardapioCategoria;
    private List<Categoria> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cardapio");
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerCardapioCategoria = findViewById(R.id.recyclerCardapioCategoria);
        configuracaoRecyclerView();

    }

    public void configuracaoRecyclerView(){
        //Adapter
        adapterCardapioCategoria = new AdapterCardapioCategoria(listaCategorias, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCardapioCategoria.setLayoutManager(layoutManager);
        recyclerCardapioCategoria.setHasFixedSize(true);
        recyclerCardapioCategoria.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
        recyclerCardapioCategoria.setAdapter(adapterCardapioCategoria);

    }

    public void abrirCardapioActivity(View view){
        startActivity(new Intent(CardapioCategoriaActivity.this, CardapioActivity.class));
    }
}
