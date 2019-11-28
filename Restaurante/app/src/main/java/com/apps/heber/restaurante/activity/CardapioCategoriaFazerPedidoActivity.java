package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapioCategoriaFazerPedidoActivity;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.QuantMesas;

import java.util.ArrayList;
import java.util.List;

public class CardapioCategoriaFazerPedidoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCardapioCategoriaFazerPedidoActivity adapterCardCateFazerPedido;
    private List<Categoria> listaCategorias = new ArrayList<>();
    private QuantMesas quantMesas;

    private int numeroMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_categoria_fazer_pedido);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Categoria");

        recyclerView = findViewById(R.id.recyclerCardapioCategoriaFazerPedido);
        configurarRecyclerView();

        quantMesas = (QuantMesas) getIntent().getSerializableExtra("quantMesa");
        //Log.v("INFO", "Quant mesas3: "+ quantMesas.getIdMesa());

        if (quantMesas != null){
            //UTLIZADO SOMENTE PARA QUE 'numeroMesa' NÃO SEJA NULL
            /*
            numeroMesa = (int) getIntent().getSerializableExtra("numeroMesa");
            Log.v("INFO", "Numero da mesa2 dentro: "+numeroMesa);
             */
        }

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(CardapioCategoriaFazerPedidoActivity.this, CardapioFazerPedidoActivity.class);
                        Categoria categoriaSelecionada = listaCategorias.get(position);
                        intent.putExtra("posicao", categoriaSelecionada.getId());
                        intent.putExtra("posicaoSpinner", position);
                        //Log.i("INFO", "posicao 1: " + position);
                        intent.putExtra("numeroMesa", numeroMesa);
                        intent.putExtra("quantMesa", quantMesas);

                        //Log.v("INFO", "Numero da mesa saindo ... : "+numeroMesa);

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

    public void configurarRecyclerView(){
        //Listar
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());
        listaCategorias = categoriaDAO.listarCategoria();

        //Adapter
        adapterCardCateFazerPedido = new AdapterCardapioCategoriaFazerPedidoActivity(listaCategorias, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
        recyclerView.setAdapter(adapterCardCateFazerPedido);
    }
}
