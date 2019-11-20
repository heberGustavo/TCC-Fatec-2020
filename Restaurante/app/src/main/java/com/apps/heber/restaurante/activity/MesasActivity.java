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
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.QuantMesasDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterQuantMesas;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.QuantMesas;

import java.util.ArrayList;
import java.util.List;

public class MesasActivity extends AppCompatActivity {

    private RecyclerView recyclerFazerPedido;
    private AdapterQuantMesas adapterQuantMesas;
    private List<QuantMesas> listaMesas = new ArrayList<>();

    private QuantMesas quantMesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_pedido);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mesa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerFazerPedido = findViewById(R.id.recyclerFazerPedido);
        configuracaoRecyclerView();

        recyclerFazerPedido.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerFazerPedido,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        quantMesas = listaMesas.get(position);

                        Intent intent = new Intent(MesasActivity.this, ComandaActivity.class);
                        intent.putExtra("numeroMesa", position);
                        intent.putExtra("quantMesas", quantMesas);
                        Log.v("INFO", "Quant mesas1: "+ quantMesas);
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
        QuantMesasDAO quantMesasDAO = new QuantMesasDAO(getApplicationContext());
        listaMesas = quantMesasDAO.listarQuantMesa();

        //Adapter
        adapterQuantMesas = new AdapterQuantMesas(listaMesas, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerFazerPedido.setLayoutManager(layoutManager);
        recyclerFazerPedido.setHasFixedSize(true);
        recyclerFazerPedido.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
        recyclerFazerPedido.setAdapter(adapterQuantMesas);

    }
}
