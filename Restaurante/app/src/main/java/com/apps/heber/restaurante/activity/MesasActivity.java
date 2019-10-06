package com.apps.heber.restaurante.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterPedido;
import com.apps.heber.restaurante.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class MesasActivity extends AppCompatActivity {

    private TextInputEditText numeroMesa, nomeCliente;
    private RecyclerView recyclerPedidos;
    private List<Pedido> listaPedidos = new ArrayList<>();
    private AdapterPedido adapterPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numeroMesa = findViewById(R.id.editNumeroMesa);
        nomeCliente = findViewById(R.id.editNomeCliente);
        recyclerPedidos = findViewById(R.id.recyclerPedidos);

        configurarRecycler();
    }

    public void configurarRecycler(){
        //Listar

        //Adapter
        adapterPedido = new AdapterPedido(listaPedidos, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPedidos.setLayoutManager(layoutManager);
        recyclerPedidos.setHasFixedSize(true);
        recyclerPedidos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerPedidos.setAdapter(adapterPedido);
    }
}
