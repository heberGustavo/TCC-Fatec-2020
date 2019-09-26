package com.apps.heber.restaurante.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private AdapterCategoria adapterCategoria;
    private List<Categoria> listaCategorias = new ArrayList<>();
    private Categoria categoriaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCategoria = findViewById(R.id.recyclerCategoria);

        recyclerCategoria.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCategoria,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        categoriaSelecionada = listaCategorias.get(position);

                        Intent intent = new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class);
                        intent.putExtra("categoriaSelecionada", categoriaSelecionada);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        categoriaSelecionada = listaCategorias.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(CategoriaActivity.this);

                        builder.setTitle("Confirmar exclusão");
                        builder.setMessage("Deseja excluir a tarefa: " +categoriaSelecionada.getCategoria()+ "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());

                                if (categoriaDAO.deletarCategoria(categoriaSelecionada)){

                                    carregarRecyclerView();
                                    Toast.makeText(getApplicationContext(),
                                            "Tarefa excluida com sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao excluir tarefa",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Não", null);

                        builder.create();
                        builder.show();
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
        adapterCategoria = new AdapterCategoria(listaCategorias, getApplicationContext());

        //Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCategoria.setLayoutManager(layoutManager);
        recyclerCategoria.setHasFixedSize(true);
        recyclerCategoria.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerCategoria.setAdapter(adapterCategoria);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
    }

    public void abrirAdicionarNovaCategoria(View view){
        startActivity(new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class));
    }
}

