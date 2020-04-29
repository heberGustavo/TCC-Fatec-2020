package com.apps.heber.restaurante.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.apps.heber.restaurante.adapter.AdapterPrincipal;
import com.apps.heber.restaurante.modelo.QuantMesa;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterQuantMesa;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerViewPricipal;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<QuantMesa> mesaList;
    private RecyclerView.Adapter adapter;

    private String url = "https://restaurantecome.000webhostapp.com/listarMesaEmAtendimento.php";

    private TextView descricaoMesa;
    private ProgressBar progressBarMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        descricaoMesa = findViewById(R.id.descricaoMesa);
        progressBarMesa = findViewById(R.id.progressBarMesa);
        recyclerViewPricipal = findViewById(R.id.recyclerPrincipal);

        progressBarMesa.setVisibility(View.VISIBLE);

        mesaList= new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerViewPricipal.getContext(), linearLayoutManager.getOrientation());

        recyclerViewPricipal.setHasFixedSize(true);
        recyclerViewPricipal.setLayoutManager(linearLayoutManager);
        recyclerViewPricipal.addItemDecoration(itemDecoration);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void listagemMesa(){

        mesaList.clear();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() >= 1){
                            progressBarMesa.setVisibility(View.GONE);
                            descricaoMesa.setVisibility(View.GONE);
                        }
                        else{
                            progressBarMesa.setVisibility(View.GONE);
                            descricaoMesa.setVisibility(View.VISIBLE);
                        }
                        for(int i = 0; i < response.length(); i++) {
                            QuantMesa quantMesa = new QuantMesa();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);
                                quantMesa.setId(jsonObject.getInt("id"));
                                quantMesa.setNumero(jsonObject.getInt("numeroMesa"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            mesaList.add(quantMesa);
                        }
                        adapter = new AdapterPrincipal(getApplicationContext(), mesaList);
                        recyclerViewPricipal.setAdapter(adapter);

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

    public void clickRecyclerView(){
        recyclerViewPricipal.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewPricipal,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        QuantMesa numeroMesa = mesaList.get(position);

                        Intent intent = new Intent(PrincipalActivity.this, ComandaActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        listagemMesa();
        clickRecyclerView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();

        } else if (id == R.id.nav_fazer_pedido) {
            startActivity(new Intent(this, MesasActivity.class));

        } else if (id == R.id.nav_cardapio) {
            startActivity(new Intent(this, CardapioCategoriaActivity.class));

        } else if (id == R.id.nav_categoria) {
            startActivity(new Intent(this, CategoriaActivity.class));

        } else if (id == R.id.nav_mesa) {
            startActivity(new Intent(this, AdicionarMesasActivity.class));
        }
        else if (id == R.id.nav_fluxoDeCaixa) {
            startActivity(new Intent(this, FluxoDeCaixaActivity.class));

        } else if (id == R.id.nav_share) {

            Toast.makeText(getApplicationContext(),
                    "Em manutenção",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
