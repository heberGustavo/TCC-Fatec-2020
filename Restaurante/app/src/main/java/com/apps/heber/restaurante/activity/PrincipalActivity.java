package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.QuantMesasDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterPrincipal;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.QuantMesas;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerViewPricipal;
    private AdapterPrincipal adapterPrincipal;
    private List<QuantMesas> listaMesas;

    private TextView descricaoMesa;

    private QuantMesas quantMesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        descricaoMesa = findViewById(R.id.descricaoMesa);
        recyclerViewPricipal = findViewById(R.id.recyclerPrincipal);
        clickRecyclerView();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void clickRecyclerView(){
        recyclerViewPricipal.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewPricipal,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        quantMesas = listaMesas.get(position);

                        Intent intent = new Intent(PrincipalActivity.this, ComandaActivity.class);
                        intent.putExtra("numeroMesa", position);
                        intent.putExtra("quantMesas", quantMesas);
                        //Log.v("INFO", "Quant mesas1: "+ quantMesas);
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

    public void configuracaoRecyclerView(){
        //Listar
        QuantMesasDAO quantMesasDAO = new QuantMesasDAO(getApplicationContext());
        listaMesas = quantMesasDAO.listarQuantMesa();

        if (listaMesas.isEmpty()){
            descricaoMesa.setVisibility(View.VISIBLE);
        }else {
            descricaoMesa.setVisibility(View.INVISIBLE);
        }

        //Adapter
        adapterPrincipal = new AdapterPrincipal(getApplicationContext(), listaMesas);

        //Recycler
        RecyclerView.Adapter adapter = new AdapterPrincipal(this, listaMesas);
        recyclerViewPricipal.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewPricipal.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        configuracaoRecyclerView();
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
            startActivity(new Intent(this, QuantMesasActivity.class));
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
