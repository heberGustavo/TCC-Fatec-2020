//Essa activity é para ver todas as mesas cadastradas

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
import com.apps.heber.restaurante.adapter.AdapterQuantMesa;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.QuantMesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MesasActivity extends AppCompatActivity {

    private TextView descricaoMesa;
    private ProgressBar progressBarMesa;

    private RecyclerView recyclerFazerPedido;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<QuantMesa> listaMesas;
    private RecyclerView.Adapter adapter;

    private String url = "https://restaurantecome.000webhostapp.com/listarMesa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_pedido);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mesa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerFazerPedido = findViewById(R.id.recyclerFazerPedido);
        descricaoMesa = findViewById(R.id.descricaoMesa);
        progressBarMesa = findViewById(R.id.progressBarMesa);

        recyclerFazerPedido.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerFazerPedido,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            QuantMesa numeroMesa = listaMesas.get(position);
                            Intent intent = new Intent(MesasActivity.this, ComandaActivity.class);
                            intent.putExtra("numeroMesa", numeroMesa);

                            startActivity(intent);
                        }catch (Exception e){
                            Log.v("INFO", "zzzErroMesa: " + e.toString());
                            Log.v("INFO", "zzzErroMesa: " + e.getMessage());
                        }

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

    public void configuracaoRecyclerView(){
        listaMesas = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerFazerPedido.getContext(), linearLayoutManager.getOrientation());

        recyclerFazerPedido.setHasFixedSize(true);
        recyclerFazerPedido.setLayoutManager(linearLayoutManager);
        recyclerFazerPedido.addItemDecoration(itemDecoration);
    }

    private void listagemMesa(){

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            QuantMesa quantMesa = new QuantMesa();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                quantMesa.setId(jsonObject.getInt("id"));
                                quantMesa.setNumero(jsonObject.getInt("numeroMesa"));
                                quantMesa.setStatus(jsonObject.getInt("statusMesa"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            listaMesas.add(quantMesa);
                        }
                        adapter = new AdapterQuantMesa(getApplicationContext(), listaMesas);
                        recyclerFazerPedido.setAdapter(adapter);

                        if(listaMesas.size() >= 1){
                            progressBarMesa.setVisibility(View.GONE);
                        }
                        if(listaMesas.size() == 0){
                            progressBarMesa.setVisibility(View.GONE);
                            descricaoMesa.setVisibility(View.VISIBLE);
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
    protected void onStart() {
        super.onStart();
        configuracaoRecyclerView();
        listagemMesa();
    }
}
