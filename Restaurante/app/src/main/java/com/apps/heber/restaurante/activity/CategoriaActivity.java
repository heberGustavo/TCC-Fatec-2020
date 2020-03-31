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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.DAO.CardapioDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCategoriaNovo;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.CategoriaNovo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerCategoria;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<CategoriaNovo> listaCategorias;
    private RecyclerView.Adapter adapter;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCategoria.php";

    //private Categoria categoriaSelecionada;

    private TextView descricaoCategoria;
    private ProgressBar progressBarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCategoria = findViewById(R.id.recyclerCategoria);
        descricaoCategoria = findViewById(R.id.descricaoCategoria);
        progressBarCategoria = findViewById(R.id.progressBarCardapio);

        recyclerCategoria.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCategoria,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //categoriaSelecionada = listaCategorias.get(position);

                        Intent intent = new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class);
                        //intent.putExtra("categoriaSelecionada", categoriaSelecionada);
                        //Log.i("INFO", "Posicao categoria: "+categoriaSelecionada.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        //categoriaSelecionada = listaCategorias.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(CategoriaActivity.this);

                        builder.setTitle("Confirmar exclusão");
                        //builder.setMessage("Deseja excluir a tarefa: " +categoriaSelecionada.getCategoria()+ "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());

                                //Log.v("INFO", "Id categoria: "+categoriaSelecionada.getId());

                                // SE EXISTIR CARDAPIO CADASTRADO NÃO EXCLUI CATEGORIA
                                //int count = cardapioDAO.somaCardapio(categoriaSelecionada.getId());
                                //if (count >=1){
                                //    Toast.makeText(getApplicationContext(),
                                //            "Existe cardápio cadastrado nessa categoria!",
                                //            Toast.LENGTH_LONG).show();
                                //    //Log.v("INFO", "Quant de cardapios: "+count);
                                //}
//
                                //else{
                                //    CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());
//
                                //    if (categoriaDAO.deletarCategoria(categoriaSelecionada)){
//
                                //        carregarRecyclerView();
                                //        Toast.makeText(getApplicationContext(),
                                //                "Categoria excluida com sucesso!",
                                //                Toast.LENGTH_SHORT).show();
                                //    }else {
                                //        Toast.makeText(getApplicationContext(),
                                //                "Erro ao excluir categoria!",
                                //                Toast.LENGTH_SHORT).show();
                                //    }
                                //}
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
        listaCategorias = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerCategoria.getContext(), linearLayoutManager.getOrientation());

        recyclerCategoria.setHasFixedSize(true);
        recyclerCategoria.setLayoutManager(linearLayoutManager);
        recyclerCategoria.addItemDecoration(itemDecoration);
    }

    private void listagemCategoria(){

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_categoria, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            CategoriaNovo categoria = new CategoriaNovo();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                categoria.setCategoria(jsonObject.getString("nomeCategoria"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            listaCategorias.add(categoria);

                            if(listaCategorias.size() >= 1){
                                progressBarCategoria.setVisibility(View.GONE);
                            }
                            if(listaCategorias.size() == 0){
                                progressBarCategoria.setVisibility(View.GONE);
                                descricaoCategoria.setVisibility(View.VISIBLE);
                            }
                        }
                        adapter = new AdapterCategoriaNovo(listaCategorias, getApplicationContext());
                        recyclerCategoria.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Erro 02",
                        Toast.LENGTH_SHORT).show();
                Log.v("INFO", "Erro 02: " + error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    public void abrirAdicionarNovaCategoria(View view){
        startActivity(new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemCategoria();
    }
}

