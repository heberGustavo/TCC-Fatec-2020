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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCategoriaNovo;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.CategoriaNovo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerCategoria;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<CategoriaNovo> listaCategorias;
    private RecyclerView.Adapter adapter;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCategoria.php";
    private String urlListarCardapioPorIdCategoria = "https://restaurantecome.000webhostapp.com/listarCardapio.php?idCategoria=";
    private String url_excluir_categoria = "https://restaurantecome.000webhostapp.com/excluirCategoria.php";

    private CategoriaNovo categoriaSelecionada;

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

                        categoriaSelecionada = listaCategorias.get(position);

                        Intent intent = new Intent(CategoriaActivity.this, AdicionarCategoriaActivity.class);
                        intent.putExtra("categoriaSelecionada", categoriaSelecionada);
                        //Log.i("INFO", "xxx Categoria saindo: "+categoriaSelecionada);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        categoriaSelecionada = listaCategorias.get(position);

                        final AlertDialog.Builder builder = new AlertDialog.Builder(CategoriaActivity.this);

                        builder.setTitle("Confirmar exclusão");
                        builder.setMessage("Deseja excluir a tarefa: " +categoriaSelecionada.getCategoria()+ "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                listarCardapioExcluir(categoriaSelecionada.getIdCategoria());
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

                                categoria.setIdCategoria(jsonObject.getInt("idCategoria"));
                                categoria.setCategoria(jsonObject.getString("nomeCategoria"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            listaCategorias.add(categoria);
                        }
                        adapter = new AdapterCategoriaNovo(listaCategorias, getApplicationContext());
                        recyclerCategoria.setAdapter(adapter);

                        if(listaCategorias.size() >= 1){
                            progressBarCategoria.setVisibility(View.GONE);
                        }
                        if(listaCategorias.size() == 0){
                            progressBarCategoria.setVisibility(View.GONE);
                            descricaoCategoria.setVisibility(View.VISIBLE);
                        }

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

    private void listarCardapioExcluir(int id){

        final int idCategoriaSelecionada = id;
        final String idCategoria = String.valueOf(id);
        String url_parametro = urlListarCardapioPorIdCategoria + idCategoria;

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_parametro, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v("INFO", "zzzLista response: " + response.length());
                        if (response.length() >= 1){
                            Toast.makeText(getApplicationContext(),
                                    "Impossivel excluir! Categoria tem cardápio cadastrado",
                                    Toast.LENGTH_LONG).show();
                        } else{
                            excluirCategoria(idCategoriaSelecionada);
                            Toast.makeText(getApplicationContext(),
                                    "Categoria excluida",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Erro 02",
                        Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(arrayRequest);
    }

    private void excluirCategoria(int idCategoria){

        final String idCategoriaSelecionada = String.valueOf(idCategoria);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_excluir_categoria,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Info", "zzzResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String sucess = jsonObject.getString("sucess");
                            if (sucess.equals("1")) {
                                Toast.makeText(CategoriaActivity.this,
                                        "Categoria deletada",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            Log.v("INFO", "zzzErro1: " + e.toString());

                            Toast.makeText(CategoriaActivity.this,
                                    "Erro ao deletar! --> " + e.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("INFO", "zzzErro2: " + error.toString());
                        Toast.makeText(CategoriaActivity.this,
                                "Erro ao deletar! --> " + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idCategoria", idCategoriaSelecionada);

                Log.v("zzzParametros", "Paramentros: " + params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

