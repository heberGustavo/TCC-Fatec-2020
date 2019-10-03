package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapioCategoria;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CardapioCategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerCardapioCategoria;
    private AdapterCardapioCategoria adapterCardapioCategoria;
    private List<Categoria> listaCategorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cardapio");
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerCardapioCategoria = findViewById(R.id.recyclerCardapioCategoria);
        carregarRecyclerView();

        recyclerCardapioCategoria.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCardapioCategoria,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(CardapioCategoriaActivity.this, CardapioActivity.class);
                        Categoria categoriaSelecionada = listaCategorias.get(position);
                        intent.putExtra("posicao", categoriaSelecionada.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

    }

    public void carregarRecyclerView(){
        //Listar Categoria
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());
        listaCategorias = categoriaDAO.listarCategoria();

        //Adapter
        adapterCardapioCategoria = new AdapterCardapioCategoria(listaCategorias, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCardapioCategoria.setLayoutManager(layoutManager);
        recyclerCardapioCategoria.setHasFixedSize(true);
        recyclerCardapioCategoria.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
        recyclerCardapioCategoria.setAdapter(adapterCardapioCategoria);

    }

    public void adicionarNovoCardapio(View view){
        startActivity(new Intent(CardapioCategoriaActivity.this, AdicionarNovoCardapioActivity.class));
    }
}
