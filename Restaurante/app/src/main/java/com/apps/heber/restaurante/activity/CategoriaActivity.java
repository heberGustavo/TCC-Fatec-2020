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
import com.apps.heber.restaurante.adapter.AdapterCategoria;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerCategoria;
    private List<Categoria> listaCategorias = new ArrayList<>();
    private AdapterCategoria adapterCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCategoria = findViewById(R.id.recyclerCategoria);
        configuracaoRecyclerView();

        recyclerCategoria.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCategoria,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Click no item categoria",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Click Longo no item categoria",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));
    }

    public void configuracaoRecyclerView(){
        //Listar Categoria
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());

        //Adapter
        adapterCategoria = new AdapterCategoria(listaCategorias, getApplicationContext());

        //Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCategoria.setLayoutManager(layoutManager);
        recyclerCategoria.setHasFixedSize(true);
        recyclerCategoria.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerCategoria.setAdapter(adapterCategoria);
    }

    public void abrirAdicionarNovaCategoria(View view){
        startActivity(new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class));
    }
}

