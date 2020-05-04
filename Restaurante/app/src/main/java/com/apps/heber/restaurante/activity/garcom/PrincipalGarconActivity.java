package com.apps.heber.restaurante.activity.garcom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import com.apps.heber.restaurante.activity.ComandaActivity;
import com.apps.heber.restaurante.activity.MesasActivity;
import com.apps.heber.restaurante.activity.PrincipalActivity;
import com.apps.heber.restaurante.adapter.AdapterPrincipal;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.QuantMesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrincipalGarconActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerViewPricipalGarcom;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<QuantMesa> mesaList;
    private RecyclerView.Adapter adapter;

    private String url = "https://restaurantecome.000webhostapp.com/listarMesaEmAtendimento.php";

    private TextView descricaoMesaGarcom;
    private ProgressBar progressBarMesaGarcom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_garcom);
        Toolbar toolbar = findViewById(R.id.toolbarGarcom);
        setSupportActionBar(toolbar);

        descricaoMesaGarcom = findViewById(R.id.descricaoMesaGarcom);
        progressBarMesaGarcom = findViewById(R.id.progressBarMesaGarcom);
        recyclerViewPricipalGarcom = findViewById(R.id.recyclerPrincipalGarcom);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_garcom);
        NavigationView navigationView = findViewById(R.id.nav_view_garcom);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void carregarRecyclerView(){
        mesaList= new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerViewPricipalGarcom.getContext(), linearLayoutManager.getOrientation());

        recyclerViewPricipalGarcom.setHasFixedSize(true);
        recyclerViewPricipalGarcom.setLayoutManager(linearLayoutManager);
        recyclerViewPricipalGarcom.addItemDecoration(itemDecoration);
    }

    private void listagemMesa(){

        mesaList.clear();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() >= 1){
                            progressBarMesaGarcom.setVisibility(View.GONE);
                            descricaoMesaGarcom.setVisibility(View.GONE);
                        }
                        else{
                            progressBarMesaGarcom.setVisibility(View.GONE);
                            descricaoMesaGarcom.setVisibility(View.VISIBLE);
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
                        recyclerViewPricipalGarcom.setAdapter(adapter);

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
        recyclerViewPricipalGarcom.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewPricipalGarcom,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        QuantMesa numeroMesa = mesaList.get(position);

                        Intent intent = new Intent(PrincipalGarconActivity.this, ComandaActivity.class);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, PrincipalGarconActivity.class));
            finish();

        } else if (id == R.id.nav_fazer_pedido) {
            startActivity(new Intent(this, MesasActivity.class));

        } else if (id == R.id.nav_send) {
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_garcom);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemMesa();
        clickRecyclerView();
    }
}

