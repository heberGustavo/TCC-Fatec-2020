package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.apps.heber.restaurante.R;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();

            Toast.makeText(getApplicationContext(),
                    "Home",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_fazer_pedido) {
            startActivity(new Intent(this, FazerPedidoActivity.class));

            Toast.makeText(getApplicationContext(),
                    "Fazer pedido",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_monitorar_pedido) {
            startActivity(new Intent(this, MonitorarPedidoActivity.class));

            Toast.makeText(getApplicationContext(),
                    "Monitorar pedido",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_cardapio) {
            startActivity(new Intent(this, CardapioActivity.class));

            Toast.makeText(getApplicationContext(),
                    "Cardapio",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_mesas) {
            startActivity(new Intent(this, MesasActivity.class));

            Toast.makeText(getApplicationContext(),
                    "Mesas",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_categoria) {
            startActivity(new Intent(this, CategoriaActivity.class));

            Toast.makeText(getApplicationContext(),
                    "Categoria",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {

            Toast.makeText(getApplicationContext(),
                    "Indefinido 1",
                    Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {

            Toast.makeText(getApplicationContext(),
                    "Indefinido 2",
                    Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
