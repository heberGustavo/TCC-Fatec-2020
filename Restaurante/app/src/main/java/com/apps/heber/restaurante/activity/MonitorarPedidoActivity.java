package com.apps.heber.restaurante.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterMonitoramentoPedidos;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class MonitorarPedidoActivity extends AppCompatActivity {

    private RecyclerView recyclerMonitorarPedido;
    private AdapterMonitoramentoPedidos adapterMonitoramentoPedidos;
    private List<Pedido> listaPedidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitorar_pedido);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Monitorar Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerMonitorarPedido = findViewById(R.id.recyclerMonitorarPedido);
        configurarRecycler();

        recyclerMonitorarPedido.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerMonitorarPedido,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Click no item",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Click Longo no item",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

    }

    public void configurarRecycler(){
        //Adapter
        adapterMonitoramentoPedidos = new AdapterMonitoramentoPedidos(listaPedidos, getApplicationContext());

        //Recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMonitorarPedido.setLayoutManager(layoutManager);
        recyclerMonitorarPedido.setHasFixedSize(true);
        recyclerMonitorarPedido.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerMonitorarPedido.setAdapter(adapterMonitoramentoPedidos);
    }
}
