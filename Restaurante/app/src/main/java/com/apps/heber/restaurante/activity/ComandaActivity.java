package com.apps.heber.restaurante.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.DAO.FluxoCaixaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterPedido;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.ItemPedido;
import com.apps.heber.restaurante.modelo.QuantMesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandaActivity extends AppCompatActivity {

    private TextView descricaoItemPedido;
    private ProgressBar progressBarItemPedido;

    private RecyclerView recyclerPedidos;
    private DividerItemDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<ItemPedido> listaItemPedidos;
    private RecyclerView.Adapter adapter;

    private ItemPedido itemPedidoSelecionado;
    private QuantMesa numeroMesa;

    private double valorTotalMesa;

    private String url_listar_item_protudo = "https://restaurantecome.000webhostapp.com/listarItemProduto.php?idMesa=";
    private String url_excluir_pedido = "https://restaurantecome.000webhostapp.com/excluirProdutoComanda.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        this.getSupportActionBar().setTitle("Mesa");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        descricaoItemPedido = findViewById(R.id.descricaoComanda);
        progressBarItemPedido = findViewById(R.id.progressBarItemPedido);

        numeroMesa = (QuantMesa) getIntent().getSerializableExtra("numeroMesa");

        clickRecyclerView();
    }

    private void clickRecyclerView() {
        recyclerPedidos.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerPedidos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        itemPedidoSelecionado = listaItemPedidos.get(position);

                        Intent intent = new Intent(ComandaActivity.this, AdicionarPedidoActivity.class);
                        intent.putExtra("pedidoSelecionado", itemPedidoSelecionado);
                        Log.v("INFO", "zzzSaindo id item: " + itemPedidoSelecionado.getIdItemCardapio());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        itemPedidoSelecionado = listaItemPedidos.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ComandaActivity.this);
                        builder.setTitle("Confirmar exclusão.");
                        builder.setMessage("Deseja excluir o cardapio: " + itemPedidoSelecionado.getNomeProduto() + "?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                        //ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(getApplicationContext());
//
                                //if (itemPedidoDAO.deletar(itemPedidoSelecionado)){
                                //    //Atualiza os dados na lista
                                //    listagemProdutoNaComanda();
                                //    Toast.makeText(getApplicationContext(),
                                //            "Sucesso ao remover pedido!",
                                //            Toast.LENGTH_SHORT).show();
                                //}else {
                                //    Toast.makeText(getApplicationContext(),
                                //            "Erro ao excluir pedido!",
                                //            Toast.LENGTH_SHORT).show();
                                //}


                                //Excluir item no pedido
                                Log.v("INFO", "zzzChegando id item excluir: " + itemPedidoSelecionado.getIdItemCardapio());
                                final String idItemPedido = String.valueOf(itemPedidoSelecionado.getIdItemCardapio());

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_excluir_pedido,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.v("Info", "zzzResponse: " + response);
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);

                                                    String sucess = jsonObject.getString("sucess");
                                                    if (sucess.equals("1")) {
                                                        Toast.makeText(ComandaActivity.this,
                                                                "Pedido excluido",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    Toast.makeText(ComandaActivity.this,
                                                            "Erro ao editar! --> Erro 01",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(ComandaActivity.this,
                                                        "Erro ao editar! --> Erro 2",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("idItemPedido", idItemPedido);

                                        Log.v("zzzParametros", "Paramentros: " + params.toString());
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(stringRequest);
                            }
                        });

                        listagemProdutoNaComanda();

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

        final double valorGasto = valorTotalMesa;
        Log.v("INFO", "Gasto total: " + valorGasto);


        AlertDialog.Builder builder = new AlertDialog.Builder(ComandaActivity.this);

        builder.setTitle("Gasto total da mesa");
        builder.setMessage("R$ " + valorGasto);
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
                        //ItemPedidoDAO dao = new ItemPedidoDAO(getApplicationContext());

                        if (valorGasto <= 0){
                            Toast.makeText(getApplicationContext(),
                                    "Comanda vazia",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //dao.deletar(quantMesas.getIdMesa())
                            if (fluxoCaixaDAO.salvar(valorGasto)){
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
                    }
                });
                builder2.show();
            }
        });
        builder.show();
    }

    public void abrirCardapioCategoriaFazerPedido(View view){
        Intent intent = new Intent(ComandaActivity.this, CardapioCategoriaFazerPedidoActivity.class);
        intent.putExtra("numeroMesa", numeroMesa);

        startActivity(intent);
    }

    public void carregarRecyclerView(){
        listaItemPedidos = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerPedidos.getContext(), linearLayoutManager.getOrientation());

        recyclerPedidos.setHasFixedSize(true);
        recyclerPedidos.setLayoutManager(linearLayoutManager);
        recyclerPedidos.addItemDecoration(itemDecoration);
    }

    private void listagemProdutoNaComanda(){

        String url_parametro = url_listar_item_protudo + numeroMesa.getId();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_parametro, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            ItemPedido itemPedido = new ItemPedido();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                itemPedido.setIdItemCardapio(jsonObject.getInt("idItemPedido"));
                                itemPedido.setNomeProduto(jsonObject.getString("nomeProduto"));
                                itemPedido.setIngrediente(jsonObject.getString("ingrediente"));
                                itemPedido.setQuantidade(jsonObject.getInt("quantidade"));
                                itemPedido.setValorUnitario(jsonObject.getDouble("valorUnitario"));
                                itemPedido.setValorTotal(jsonObject.getDouble("valorTotal"));
                                itemPedido.setObservacoes(jsonObject.getString("observacao"));
                                itemPedido.setNomeCategoria(jsonObject.getString("nomeCategoria"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                            listaItemPedidos.add(itemPedido);
                            //Acumula o total gasto na mesa
                            valorTotalMesa += itemPedido.getValorTotal();
                            //Log.v("INFO", "Gasto total: Lista == " + valorTotalMesa);
                        }
                        adapter = new AdapterPedido(listaItemPedidos, getApplicationContext());
                        recyclerPedidos.setAdapter(adapter);

                        if(listaItemPedidos.size() >= 1){
                            progressBarItemPedido.setVisibility(View.GONE);
                        }
                        if(listaItemPedidos.size() == 0){
                            progressBarItemPedido.setVisibility(View.GONE);
                            descricaoItemPedido.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Erro 02",
                        Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarRecyclerView();
        listagemProdutoNaComanda();
    }
}
