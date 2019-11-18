package com.apps.heber.restaurante.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.FluxoCaixaDAO;
import com.apps.heber.restaurante.DAO.ItemPedidoDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterPedido;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ComandaActivity extends AppCompatActivity {

    private RecyclerView recyclerPedidos;
    private List<Pedido> listaPedidos = new ArrayList<>();
    private AdapterPedido adapterPedido;
    private Pedido pedidoSelecionado;

    private int numeroMesa;
    private double gastoMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        recyclerPedidos = findViewById(R.id.recyclerPedidos);

        numeroMesa = (int) getIntent().getSerializableExtra("numeroMesa");
        this.getSupportActionBar().setTitle("Mesa "+ (numeroMesa+1));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Log.v("INFO", "Numero da mesa1: "+numeroMesa);

        clickRecyclerView();
    }

    private void clickRecyclerView() {
        recyclerPedidos.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerPedidos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        pedidoSelecionado = listaPedidos.get(position);

                        Intent intent = new Intent(ComandaActivity.this, AdicionarPedidoActivity.class);
                        intent.putExtra("pedidoSelecionado", pedidoSelecionado);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        pedidoSelecionado = listaPedidos.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ComandaActivity.this);
                        builder.setTitle("Confirmar exclusão.");
                        builder.setMessage("Deseja excluir o cardapio: " + pedidoSelecionado.getNomeProduto() + "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(getApplicationContext());

                                if (itemPedidoDAO.deletar(pedidoSelecionado)){
                                    //Atualiza os dados na lista
                                    carregarRecycler();
                                    Toast.makeText(getApplicationContext(),
                                            "Sucesso ao remover pedido!",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao excluir pedido!",
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar_comanda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvar_comanda:
                menuSalvar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuSalvar() {
        ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(getApplicationContext());
        gastoMesa = itemPedidoDAO.listarGastoMesa(numeroMesa);

        AlertDialog.Builder builder = new AlertDialog.Builder(ComandaActivity.this);

        builder.setTitle("Gasto total da mesa");
        builder.setMessage("R$ " + gastoMesa);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Fechar comanda", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog.Builder builder2 = new AlertDialog.Builder(ComandaActivity.this);

                builder2.setTitle("Fechar comanda");
                builder2.setMessage("Deseja fechar a comanda da mesa?");
                builder2.setNegativeButton("Não", null);
                builder2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Salva na tela do Fluxo de Caixa
                        FluxoCaixaDAO fluxoCaixaDAO = new FluxoCaixaDAO(getApplicationContext());

                        if (fluxoCaixaDAO.salvar(gastoMesa)){
                            Toast.makeText(getApplicationContext(),
                                    "Fechando comanda...",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao fechar comanda...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder2.show();
            }
        });
        builder.show();
    }

    public void carregarRecycler(){
        //Listar
        ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(getApplicationContext());
        listaPedidos = itemPedidoDAO.listar(numeroMesa);

        //Adapter
        adapterPedido = new AdapterPedido(listaPedidos, getApplicationContext());

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPedidos.setLayoutManager(layoutManager);
        recyclerPedidos.setHasFixedSize(true);
        recyclerPedidos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        recyclerPedidos.setAdapter(adapterPedido);
    }

    public void abrirCardapioCategoriaFazerPedido(View view){
        Intent intent = new Intent(ComandaActivity.this, CardapioCategoriaFazerPedidoActivity.class);
        intent.putExtra("numeroMesa", numeroMesa);

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecycler();
    }
}
