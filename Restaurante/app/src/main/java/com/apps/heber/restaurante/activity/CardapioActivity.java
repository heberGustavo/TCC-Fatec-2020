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
import com.apps.heber.restaurante.adapter.AdapterCardapio;
import com.apps.heber.restaurante.adapter.AdapterCategoriaNovo;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardapioActivity extends AppCompatActivity {

    private TextView descricaoCardapio;
    private ProgressBar progressBarCardapio;

    private RecyclerView recyclerCardapio;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<Cardapio> listaCardapio;
    private RecyclerView.Adapter adapter;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCardapio.php?idCategoria=";
    private String urlListarItemComandaPorNome = "https://restaurantecome.000webhostapp.com/listarItensComandaPorNome.php?nomeProduto=";
    private String url_excluir_cardapio = "https://restaurantecome.000webhostapp.com/excluirCardapio.php";

    private Cardapio cardapioSelecionado;

    private int posicao;
    private int cardapioCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cardápio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCardapio = findViewById(R.id.recyclerCardapio);
        descricaoCardapio = findViewById(R.id.descricaoCardapio);
        progressBarCardapio = findViewById(R.id.progressBarCardapio);

        //Recebe o ID e usa para fazer a listagem de itens por ID
        posicao = (int) getIntent().getSerializableExtra("posicao");
        //Toast.makeText(getApplicationContext(), "Posicao: " + posicao, Toast.LENGTH_SHORT).show();

        //Recebe a posicao do item clicado
        cardapioCategoria = (int) getIntent().getSerializableExtra("cardapioCategoria");
        //Toast.makeText(getApplicationContext(), "Cardapio Categoria: " + cardapioCategoria, Toast.LENGTH_SHORT).show();

        recyclerCardapio.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCardapio,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Abre uma nova tela para edicao;
                        cardapioSelecionado = listaCardapio.get(position);

                        Intent intent = new Intent(CardapioActivity.this, AdicionarNovoCardapioActivity.class);//Envia o cardapio para a proxima tela
                        Log.v("INFO", "Cardapio 1: " + cardapioSelecionado);
                        intent.putExtra("cardapioSelecionado", cardapioSelecionado);
                        //Envia a posicao do item clicado
                        intent.putExtra("cardapioCategoria", cardapioCategoria);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        cardapioSelecionado = listaCardapio.get(position);

                        //Exclui o item do cardapio

                        AlertDialog.Builder builder = new AlertDialog.Builder(CardapioActivity.this);
                        builder.setTitle("Confirmar exclusão.");
                        builder.setMessage("Deseja excluir o cardapio: " + cardapioSelecionado.getNomeProduto() + "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                listarItensComandaExcluir(cardapioSelecionado.getNomeProduto());

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
        listaCardapio = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerCardapio.getContext(), linearLayoutManager.getOrientation());

        recyclerCardapio.setHasFixedSize(true);
        recyclerCardapio.setLayoutManager(linearLayoutManager);
        recyclerCardapio.addItemDecoration(itemDecoration);
    }

    private void listagemCardapio(){

        String url_parametro = url_listar_categoria + posicao;

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_parametro, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            Cardapio cardapio = new Cardapio();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                cardapio.setIdCardapio(jsonObject.getLong("idProduto"));
                                cardapio.setValor(jsonObject.getDouble("preco"));
                                cardapio.setNomeProduto(jsonObject.getString("nomeProduto"));
                                cardapio.setIngredientes(jsonObject.getString("descricao"));
                                cardapio.setIdCategoria(jsonObject.getInt("idCategoria"));
                                cardapio.setNomeCategoria(jsonObject.getString("nomeCategoria"));


                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                            listaCardapio.add(cardapio);
                        }
                        adapter = new AdapterCardapio(listaCardapio, getApplicationContext());
                        recyclerCardapio.setAdapter(adapter);

                        if(listaCardapio.size() == 0){
                            progressBarCardapio.setVisibility(View.GONE);
                            descricaoCardapio.setVisibility(View.VISIBLE);
                        }
                        if(listaCardapio.size() >= 1){
                            progressBarCardapio.setVisibility(View.GONE);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    private void listarItensComandaExcluir(String nomeProduto){

        final String nomeProdutoSelecionado = nomeProduto;
        String url_parametro = urlListarItemComandaPorNome + nomeProdutoSelecionado;
        Log.v("INFO", "zzzURL: " + url_parametro);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_parametro, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v("INFO", "zzzLista response: " + response.length());
                        if (response.length() >= 1){
                            Toast.makeText(getApplicationContext(),
                                    "Impossivel excluir! Alguma comanda contém esse cardápio!",
                                    Toast.LENGTH_LONG).show();
                        } else{
                            excluirCardapio(nomeProdutoSelecionado);
                            Toast.makeText(getApplicationContext(),
                                    "Cardápio excluido!",
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

    private void excluirCardapio(String nomeProduto){

        final String nomeProdutoSelecionado = nomeProduto;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_excluir_cardapio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Info", "zzzResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String sucess = jsonObject.getString("sucess");
                            if (sucess.equals("1")) {
                                Toast.makeText(CardapioActivity.this,
                                        "Cardápio deletado!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            Log.v("INFO", "zzzErro1: " + e.toString());

                            Toast.makeText(CardapioActivity.this,
                                    "Erro ao deletar! --> " + e.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("INFO", "zzzErro2: " + error.toString());
                        Toast.makeText(CardapioActivity.this,
                                "Erro ao deletar! --> " + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nomeProduto", nomeProdutoSelecionado);

                Log.v("zzzParametros", "Paramentros: " + params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemCardapio();
    }
}
