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
import android.widget.TextView;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.CardapioDAO;
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

    private TextView descricaoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCategoria = findViewById(R.id.recyclerCategoria);
        descricaoCategoria = findViewById(R.id.descricaoCategoria);

        recyclerCategoria.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCategoria,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        categoriaSelecionada = listaCategorias.get(position);

                        Intent intent = new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class);
                        intent.putExtra("categoriaSelecionada", categoriaSelecionada);
                        //Log.i("INFO", "Posicao categoria: "+categoriaSelecionada.getId());
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

                                CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());

                                //Log.v("INFO", "Id categoria: "+categoriaSelecionada.getId());

                                // SE EXISTIR CARDAPIO CADASTRADO NÃO EXCLUI CATEGORIA
                                int count = cardapioDAO.somaCardapio(categoriaSelecionada.getId());
                                if (count >=1){
                                    Toast.makeText(getApplicationContext(),
                                            "Existe cardápio cadastrado nessa categoria!",
                                            Toast.LENGTH_LONG).show();
                                    //Log.v("INFO", "Quant de cardapios: "+count);
                                }

                                else{
                                    CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());

                                    if (categoriaDAO.deletarCategoria(categoriaSelecionada)){

                                        carregarRecyclerView();
                                        Toast.makeText(getApplicationContext(),
                                                "Categoria excluida com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(),
                                                "Erro ao excluir categoria!",
                                                Toast.LENGTH_SHORT).show();
                                    }
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

        //Verificao para mostrar a descrição da tela
        if (listaCategorias.isEmpty()){
            descricaoCategoria.setVisibility(View.VISIBLE);
        }else{
            descricaoCategoria.setVisibility(View.INVISIBLE);
        }

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

