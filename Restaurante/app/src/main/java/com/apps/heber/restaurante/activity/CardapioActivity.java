package com.apps.heber.restaurante.activity;

import android.content.Intent;
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
import com.apps.heber.restaurante.adapter.AdapterCardapio;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;

import java.util.List;

public class CardapioActivity extends AppCompatActivity {

    private RecyclerView recyclerCardapio;
    private AdapterCardapio adapterCardapio;
    private List<Cardapio> listaCardapios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cardápio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCardapio = findViewById(R.id.recyclerCardapio);
        configuracaoRecyclerView();

        recyclerCardapio.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCardapio,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new Intent(CardapioActivity.this, AdicionarNovoCardapioActivity.class));;
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
        //Adapter
        adapterCardapio = new AdapterCardapio(listaCardapios, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCardapio.setLayoutManager(layoutManager);
        recyclerCardapio.setHasFixedSize(true);
        recyclerCardapio.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerCardapio.setAdapter(adapterCardapio);
    }
}
