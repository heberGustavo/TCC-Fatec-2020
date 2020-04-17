package com.apps.heber.restaurante.activity;

import android.content.Intent;
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
import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapioCategoriaFazerPedidoActivity;
import com.apps.heber.restaurante.adapter.AdapterCategoriaNovo;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.CategoriaNovo;
import com.apps.heber.restaurante.modelo.QuantMesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardapioCategoriaFazerPedidoActivity extends AppCompatActivity {

    private TextView descricaoCardapioCategoriaFazerPedido;
    private ProgressBar progressBarCardapioCategoriaFazerPedido;

    private RecyclerView recyclerView;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<CategoriaNovo> listaCategorias;
    private RecyclerView.Adapter adapter;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCategoria.php";

    private QuantMesa numeroMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_categoria_fazer_pedido);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Categoria");

        recyclerView = findViewById(R.id.recyclerCardapioCategoriaFazerPedido);
        descricaoCardapioCategoriaFazerPedido = findViewById(R.id.descricaoCardapioCategoriaFazerPedido);
        progressBarCardapioCategoriaFazerPedido = findViewById(R.id.progressBarCardapioCategoriaFazerPedido);

        numeroMesa = (QuantMesa) getIntent().getSerializableExtra("numeroMesa");

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(CardapioCategoriaFazerPedidoActivity.this, CardapioFazerPedidoActivity.class);

                        CategoriaNovo categoriaSelecionada = listaCategorias.get(position);
                        intent.putExtra("idCategoria", categoriaSelecionada.getIdCategoria());
                        intent.putExtra("numeroMesa", numeroMesa);

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
        listaCategorias = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
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
                            }
                            listaCategorias.add(categoria);
                        }
                        adapter = new AdapterCategoriaNovo(listaCategorias, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                        if(listaCategorias.size() >= 1){
                            progressBarCardapioCategoriaFazerPedido.setVisibility(View.GONE);
                        }
                        if(listaCategorias.size() == 0){
                            progressBarCardapioCategoriaFazerPedido.setVisibility(View.GONE);
                            descricaoCardapioCategoriaFazerPedido.setVisibility(View.VISIBLE);
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

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemCategoria();
    }
}
