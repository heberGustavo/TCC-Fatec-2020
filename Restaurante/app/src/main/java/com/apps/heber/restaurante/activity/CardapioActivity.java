package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapio;
import com.apps.heber.restaurante.adapter.AdapterCategoriaNovo;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardapioActivity extends AppCompatActivity {

    private TextView descricaoCardapio;
    private ProgressBar progressBarCardapio;

    private RecyclerView recyclerCardapio;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<Cardapio> listaCardapio;
    private RecyclerView.Adapter adapter;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCardapio.php?idCategoria=";

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
                        //cardapioSelecionado = listaCardapios.get(position);

                        Intent intent = new Intent(CardapioActivity.this, AdicionarNovoCardapioActivity.class);
                        //Envia o cardapio para a proxima tela
                        //intent.putExtra("cardapioSelecionado", cardapioSelecionado);
                        //Envia a posicao do item clicado
                        intent.putExtra("cardapioCategoria", cardapioCategoria);

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        /*
                        //Exclui o item do cardapio

                        //LINHA ABAIXO -> Pega a posicao do cardapio selecionado
                        cardapioSelecionado = listaCardapios.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(CardapioActivity.this);
                        builder.setTitle("Confirmar exclusão.");
                        builder.setMessage("Deseja excluir o cardapio: " + cardapioSelecionado.getNomeProduto() + "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());

                                if (cardapioDAO.deletar(cardapioSelecionado)){
                                    //Atualiza os dados na lista
                                    carregarRecyclerView();
                                    Toast.makeText(getApplicationContext(),
                                            "Sucesso ao remover cardápio!",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao excluir cardápio!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Não", null);

                        builder.create();
                        builder.show();
                         */
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

                                cardapio.setValor(jsonObject.getDouble("preco"));
                                cardapio.setNomeProduto(jsonObject.getString("nomeProduto"));
                                cardapio.setIngredientes(jsonObject.getString("descricao"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                            listaCardapio.add(cardapio);

                            if(listaCardapio.size() >= 1){
                                progressBarCardapio.setVisibility(View.GONE);
                            }
                            if(listaCardapio.size() == 0){
                                progressBarCardapio.setVisibility(View.GONE);
                                descricaoCardapio.setVisibility(View.VISIBLE);
                            }
                        }
                        adapter = new AdapterCardapio(listaCardapio, getApplicationContext());
                        recyclerCardapio.setAdapter(adapter);

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

    //SQLite
    /*public void carregarRecyclerView(){
        //Listar cardapios
        CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());
        listaCardapios = cardapioDAO.listar(posicao);
        //Log.i("INFO", "Lista de cardapios: "+posicao);

        if (listaCardapios.isEmpty()){
            descricaoCardapio.setVisibility(View.VISIBLE);
        }else{
            descricaoCardapio.setVisibility(View.INVISIBLE);
        }

        //Adapter
        adapterCardapio = new AdapterCardapio(listaCardapios, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCardapio.setLayoutManager(layoutManager);
        recyclerCardapio.setHasFixedSize(true);
        recyclerCardapio.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerCardapio.setAdapter(adapterCardapio);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemCardapio();
    }
}
