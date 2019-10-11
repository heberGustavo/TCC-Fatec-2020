package com.apps.heber.restaurante.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.CardapioDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapio;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CardapioActivity extends AppCompatActivity {

    private RecyclerView recyclerCardapio;
    private AdapterCardapio adapterCardapio;
    private List<Cardapio> listaCardapios = new ArrayList<>();
    private Cardapio cardapioSelecionado;

    private Long posicao;
    private int cardapioCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cardápio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCardapio = findViewById(R.id.recyclerCardapio);

        posicao = (Long) getIntent().getSerializableExtra("posicao");
        cardapioCategoria = (int) getIntent().getSerializableExtra("cardapioCategoria");
        Log.i("INFO", "cardapioCategoria2: "+ cardapioCategoria);

        recyclerCardapio.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerCardapio,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Abre uma nova tela para edicao;
                        cardapioSelecionado = listaCardapios.get(position);

                        Intent intent = new Intent(CardapioActivity.this, AdicionarNovoCardapioActivity.class);
                        intent.putExtra("cardapioSelecionado", cardapioSelecionado);
                        intent.putExtra("cardapioCategoria", cardapioCategoria);
                        intent.putExtra("posicaoId", posicao);
                        //Log.i("INFO", "valor do id1 passando: "+posicao);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //Exclui o item do cardapio

                        //LINHA ABAIXO -> Pega a posicao do cardapio selecionado
                        cardapioSelecionado = listaCardapios.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(CardapioActivity.this);
                        builder.setTitle("Confirmar exclusão.");
                        builder.setMessage("Deseja excluir o cardapio: " + cardapioSelecionado.getNomeProduto() + "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());

                                if (cardapioDAO.deletar(cardapioSelecionado)){
                                    //Atualiza os dados na lista
                                    carregarRecyclerView();
                                    Toast.makeText(getApplicationContext(),
                                            "Sucesso ao remover cardápio!",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao excluir cardápio!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Não", null);

                        builder.create();
                        builder.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));
    }

    public void carregarRecyclerView(){
        //Listar cardapios
        CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());
        listaCardapios = cardapioDAO.listar(posicao);
        //Log.i("INFO", "Lista de cardapios: "+posicao);

        //Adapter
        adapterCardapio = new AdapterCardapio(listaCardapios, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCardapio.setLayoutManager(layoutManager);
        recyclerCardapio.setHasFixedSize(true);
        recyclerCardapio.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerCardapio.setAdapter(adapterCardapio);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
    }
}
