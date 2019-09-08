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
import com.apps.heber.restaurante.adapter.AdapterFazerPedido;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Mesa;

import java.util.ArrayList;
import java.util.List;

public class FazerPedidoActivity extends AppCompatActivity {

    private RecyclerView recyclerFazerPedido;
    private AdapterFazerPedido adapterFazerPedido;
    private List<Mesa> listaMesas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_pedido);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fazer Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerFazerPedido = findViewById(R.id.recyclerFazerPedido);
        configuracaoRecyclerView();

        recyclerFazerPedido.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerFazerPedido,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Click na Mesa",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Click Longo na Mesa",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));
    }

    public void configuracaoRecyclerView(){
        //Adapter
        adapterFazerPedido = new AdapterFazerPedido(listaMesas, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerFazerPedido.setLayoutManager(layoutManager);
        recyclerFazerPedido.setHasFixedSize(true);
        recyclerFazerPedido.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
        recyclerFazerPedido.setAdapter(adapterFazerPedido);

    }
}
