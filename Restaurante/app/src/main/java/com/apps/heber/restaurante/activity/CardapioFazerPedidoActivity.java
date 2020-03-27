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

import com.apps.heber.restaurante.DAO.CardapioDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapioFazerPedido;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;

import java.util.ArrayList;
import java.util.List;

public class CardapioFazerPedidoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCardapioFazerPedido adapterCardapioFazerPedido;
    private List<Cardapio> listaCardapios = new ArrayList<>();
    //private QuantMesas quantMesas;

    private Long posicao;
    private int posicaoSpinner;
    private int numeroMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_fazer_pedido);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Cardapio");

        recyclerView = findViewById(R.id.recyclerCardapioFazerPedido);

        //Usado para listar a lista
        posicao = (Long) getIntent().getSerializableExtra("posicao");
        //quantMesas = (QuantMesas) getIntent().getSerializableExtra("quantMesa");
        //Log.v("INFO", "Quant mesas4: "+ quantMesas.getIdMesa());

        //if (quantMesas != null){
        //    //UTLIZADO SOMENTE PARA QUE 'numeroMesa' NÃO SEJA NULL
//
//
        //    /*
        //    numeroMesa = (int) getIntent().getSerializableExtra("numeroMesa");
        //    Log.v("INFO", "Numero da mesa2 dentro: "+numeroMesa);
        //     */
        //}

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(CardapioFazerPedidoActivity.this, AdicionarPedidoActivity.class);
                        Cardapio cardapioSelecionado = listaCardapios.get(position);
                        //Envia o cardapio para proxima tela
                        intent.putExtra("cardapioSelecionado", cardapioSelecionado);
                        //Recebe e envia para proxima tela
                        intent.putExtra("posicaoSpinner", posicaoSpinner);
                        //intent.putExtra("quantMesa", quantMesas);

                        intent.putExtra("numeroMesa", numeroMesa);
                        //Log.v("INFO", "Numero da mesa3 saindo ...: "+numeroMesa);
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
        CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());
        listaCardapios = cardapioDAO.listar(posicao);

        if (!listaCardapios.isEmpty()){
            //Log.i("INFO", "Posicao 1: "+posicao);
            posicaoSpinner = (int) getIntent().getSerializableExtra("posicaoSpinner");

            numeroMesa = (int) getIntent().getSerializableExtra("numeroMesa");
            Log.v("INFO", "Numero da mesa3 chegando ...: "+numeroMesa);
        }

        //Adapter
        adapterCardapioFazerPedido = new AdapterCardapioFazerPedido(listaCardapios, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerView.setAdapter(adapterCardapioFazerPedido);
    }

    @Override
    protected void onStart() {
        super.onStart();
        configurarRecyclerView();
    }
}
