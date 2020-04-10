package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardapioFazerPedidoActivity extends AppCompatActivity {

    private TextView descricaoCardapioFazerPedido;
    private ProgressBar progressBarCardapioFazerPedido;

    private RecyclerView recyclerView;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<Cardapio> listaCardapio;
    private RecyclerView.Adapter adapter;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCardapio.php?idCategoria=";

    //private QuantMesas quantMesas;

    private int idCategoria;
    private int posicaoSpinner;
    private int numeroMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_fazer_pedido);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Cardapio");

        recyclerView = findViewById(R.id.recyclerCardapioFazerPedido);
        progressBarCardapioFazerPedido = findViewById(R.id.progressBarCardapioFazerPedido);
        descricaoCardapioFazerPedido = findViewById(R.id.descricaoCardapioFazerPedido);

        //Usado para listar a lista
        idCategoria = (int) getIntent().getSerializableExtra("idCategoria");

        //quantMesas = (QuantMesas) getIntent().getSerializableExtra("quantMesa");
        //Log.v("INFO", "Quant mesas4: "+ quantMesas.getIdMesa());

        //if (quantMesas != null){
        //    //UTLIZADO SOMENTE PARA QUE 'numeroMesa' NÃO SEJA NULL
//
//
        //    /*
        //    numeroMesa = (int) getIntent().getSerializableExtra("numeroMesa");
        //    Log.v("INFO", "Numero da mesa2 dentro: "+numeroMesa);
        //     */
        //}

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(CardapioFazerPedidoActivity.this, AdicionarPedidoActivity.class);
                        Cardapio cardapioSelecionado = listaCardapio.get(position);
                        //Envia o cardapio para proxima tela
                        intent.putExtra("cardapioSelecionado", cardapioSelecionado);
                        //Recebe e envia para proxima tela
                        //intent.putExtra("posicaoSpinner", posicaoSpinner);

                        intent.putExtra("numeroMesa", numeroMesa);
                        //Log.v("INFO", "Numero da mesa3 saindo ...: "+numeroMesa);
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
        listaCardapio = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void listagemCardapio(){

        String url_parametro = url_listar_categoria + idCategoria;

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
                                cardapio.setIdCategoria(jsonObject.getInt("idCategoria"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                            listaCardapio.add(cardapio);

                            if(listaCardapio.size() >= 1){
                                progressBarCardapioFazerPedido.setVisibility(View.GONE);
                            }
                            if(listaCardapio.size() == 0){
                                progressBarCardapioFazerPedido.setVisibility(View.GONE);
                                descricaoCardapioFazerPedido.setVisibility(View.VISIBLE);
                            }
                        }
                        adapter = new AdapterCardapio(listaCardapio, getApplicationContext());
                        recyclerView.setAdapter(adapter);

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

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemCardapio();
    }
}
