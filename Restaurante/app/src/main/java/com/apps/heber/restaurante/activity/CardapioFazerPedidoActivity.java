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
import com.apps.heber.restaurante.DAO.CategoriaDAO;
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

    private Long posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_fazer_pedido);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Cardapio");

        recyclerView = findViewById(R.id.recyclerCardapioFazerPedido);

        posicao = (Long) getIntent().getSerializableExtra("posicao");
        Log.i("INFO", "Posicao 1: "+posicao);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(CardapioFazerPedidoActivity.this, DescricaoProdutoActivity.class);
                        Cardapio cardapioSelecionado = listaCardapios.get(position);
                        intent.putExtra("posicao", cardapioSelecionado);
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
