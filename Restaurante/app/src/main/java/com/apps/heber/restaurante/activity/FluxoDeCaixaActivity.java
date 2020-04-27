package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.apps.heber.restaurante.adapter.AdapterFluxoDeCaixa;
import com.apps.heber.restaurante.modelo.FluxoCaixa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FluxoDeCaixaActivity extends AppCompatActivity {

    private TextView campoReceita;
    private TextView campoDespesa;
    private TextView campoSaldo;

    private RecyclerView recyclerFluxoDeCaixa;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<FluxoCaixa> listaFluxoDeCaixa;
    private RecyclerView.Adapter adapter;

    private TextView descricaoFluxoDeCaixa;
    private ProgressBar progressBarFluxoDeCaixa;

    private String url_listar_fluxo = "https://restaurantecome.000webhostapp.com/listarFluxoDeCaixa.php";
    private String url_listar_fluxo_receita = "https://restaurantecome.000webhostapp.com/listarFluxoDeCaixaReceita.php";
    private String url_listar_fluxo_despesa = "https://restaurantecome.000webhostapp.com/listarFluxoDeCaixaDespesa.php";

    private double totalReceita = 0;
    private double totalDespesa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxo_de_caixa);
        this.getSupportActionBar().setTitle("Fluxo de Caixa");

        recyclerFluxoDeCaixa = findViewById(R.id.recyclerFluxoDeCaixa);
        campoReceita = findViewById(R.id.textReceita);
        campoDespesa = findViewById(R.id.textDespesa);
        campoSaldo = findViewById(R.id.textSaldo);
        descricaoFluxoDeCaixa = findViewById(R.id.descricaoFluxoDeCaixa);
        progressBarFluxoDeCaixa = findViewById(R.id.progressBarFluxoDeCaixa);

    }

    public void imprimeValores(){

        //Formata para money
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);//duas casas decimais

        //Imprime valores
        campoDespesa.setText(formatter.format( totalDespesa));
        campoReceita.setText(formatter.format( totalReceita));
        campoSaldo.setText(formatter.format( totalReceita - totalDespesa));
    }

    public void carregarRecyclerView(){
        listaFluxoDeCaixa = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerFluxoDeCaixa.getContext(), linearLayoutManager.getOrientation());

        recyclerFluxoDeCaixa.setHasFixedSize(true);
        recyclerFluxoDeCaixa.setLayoutManager(linearLayoutManager);
        recyclerFluxoDeCaixa.addItemDecoration(itemDecoration);
    }

    public void adicionarReceita(View view){
        Intent intent = new Intent(FluxoDeCaixaActivity.this, AdicionarReceitaActivity.class);
        startActivity(intent);
    }

    public void adicionarDespesa(View view){
        Intent intent = new Intent(FluxoDeCaixaActivity.this, AdicionarDespesaActivity.class);
        startActivity(intent);
    }

    private void listagemFluxoDeCaixa(){

        listaFluxoDeCaixa.clear();
        totalReceita = 0;
        totalDespesa = 0;
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_fluxo, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            FluxoCaixa fluxo = new FluxoCaixa();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                fluxo.setId(jsonObject.getInt("id"));
                                fluxo.setTipo(jsonObject.getString("tipo"));
                                fluxo.setDataFluxo(jsonObject.getString("dataFluxo"));
                                fluxo.setReceita(jsonObject.getDouble("receita"));
                                fluxo.setDespesa(jsonObject.getDouble("despesa"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            listaFluxoDeCaixa.add(fluxo);
                            totalReceita += fluxo.getReceita();
                            totalDespesa += fluxo.getDespesa();
                            imprimeValores();

                        }
                        adapter = new AdapterFluxoDeCaixa(listaFluxoDeCaixa, getApplicationContext());
                        recyclerFluxoDeCaixa.setAdapter(adapter);

                        if(listaFluxoDeCaixa.size() >= 1){
                            progressBarFluxoDeCaixa.setVisibility(View.GONE);
                        }
                        if(listaFluxoDeCaixa.size() == 0){
                            progressBarFluxoDeCaixa.setVisibility(View.GONE);
                            descricaoFluxoDeCaixa.setVisibility(View.VISIBLE);
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

    private void listagemFluxoDeCaixaReceita(){

        listaFluxoDeCaixa.clear();
        totalReceita = 0;
        totalDespesa = 0;
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_fluxo_receita, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            FluxoCaixa fluxo = new FluxoCaixa();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                fluxo.setId(jsonObject.getInt("id"));
                                fluxo.setTipo(jsonObject.getString("tipo"));
                                fluxo.setDataFluxo(jsonObject.getString("dataFluxo"));
                                fluxo.setReceita(jsonObject.getDouble("receita"));
                                fluxo.setDespesa(jsonObject.getDouble("despesa"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            listaFluxoDeCaixa.add(fluxo);
                            totalReceita += fluxo.getReceita();
                            totalDespesa += fluxo.getDespesa();
                            imprimeValores();

                        }
                        adapter = new AdapterFluxoDeCaixa(listaFluxoDeCaixa, getApplicationContext());
                        recyclerFluxoDeCaixa.setAdapter(adapter);

                        if(listaFluxoDeCaixa.size() >= 1){
                            progressBarFluxoDeCaixa.setVisibility(View.GONE);
                        }
                        if(listaFluxoDeCaixa.size() == 0){
                            progressBarFluxoDeCaixa.setVisibility(View.GONE);
                            descricaoFluxoDeCaixa.setVisibility(View.VISIBLE);
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

    private void listagemFluxoDeCaixaDespesa(){

        listaFluxoDeCaixa.clear();
        totalReceita = 0;
        totalDespesa = 0;
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_fluxo_despesa, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            FluxoCaixa fluxo = new FluxoCaixa();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                fluxo.setId(jsonObject.getInt("id"));
                                fluxo.setTipo(jsonObject.getString("tipo"));
                                fluxo.setDataFluxo(jsonObject.getString("dataFluxo"));
                                fluxo.setReceita(jsonObject.getDouble("receita"));
                                fluxo.setDespesa(jsonObject.getDouble("despesa"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            listaFluxoDeCaixa.add(fluxo);
                            totalReceita += fluxo.getReceita();
                            totalDespesa += fluxo.getDespesa();
                            imprimeValores();

                        }
                        adapter = new AdapterFluxoDeCaixa(listaFluxoDeCaixa, getApplicationContext());
                        recyclerFluxoDeCaixa.setAdapter(adapter);

                        if(listaFluxoDeCaixa.size() >= 1){
                            progressBarFluxoDeCaixa.setVisibility(View.GONE);
                        }
                        if(listaFluxoDeCaixa.size() == 0){
                            progressBarFluxoDeCaixa.setVisibility(View.GONE);
                            descricaoFluxoDeCaixa.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filtro_fluxo_de_caixa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_receitaDespesa:
                listagemFluxoDeCaixa();
                break;
            case R.id.menu_Receita:
                listagemFluxoDeCaixaReceita();
                break;
            case R.id.menu_Despesa:
                listagemFluxoDeCaixaDespesa();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemFluxoDeCaixa();
    }
}
