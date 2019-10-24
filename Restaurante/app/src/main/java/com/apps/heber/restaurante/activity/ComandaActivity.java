package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.ItemPedidoDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterPedido;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ComandaActivity extends AppCompatActivity {

    private TextInputEditText numeroMesa, nomeCliente;
    private RecyclerView recyclerPedidos;
    private List<Pedido> listaPedidos = new ArrayList<>();
    private AdapterPedido adapterPedido;
    private Pedido pedidoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        this.getSupportActionBar().setTitle("Comanda");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numeroMesa = findViewById(R.id.editNumeroMesa);
        nomeCliente = findViewById(R.id.editNomeCliente);
        recyclerPedidos = findViewById(R.id.recyclerPedidos);

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
                        //Log.i("INFO", "hhhPedido: "+pedidoSelecionado);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(),
                                "Click longo",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_salvar:
                menuSalvar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuSalvar() {
        Toast.makeText(getApplicationContext(),
                "Menu salvar!",
                Toast.LENGTH_SHORT).show();
    }

    public void configurarRecycler(){
        //Listar
        ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(getApplicationContext());
        listaPedidos.clear();
        listaPedidos = itemPedidoDAO.listar();

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
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        configurarRecycler();
    }
}
