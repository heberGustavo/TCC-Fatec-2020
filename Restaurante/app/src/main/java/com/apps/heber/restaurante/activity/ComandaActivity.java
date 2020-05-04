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
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterPedido;
import com.apps.heber.restaurante.helper.RecyclerItemClickListener;
import com.apps.heber.restaurante.modelo.ItemPedido;
import com.apps.heber.restaurante.modelo.QuantMesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private String url_registrar_fluxo_mesa = "https://restaurantecome.000webhostapp.com/registrarFluxoMesa.php";
    private String urlExcluirItensMesaPorID = "https://restaurantecome.000webhostapp.com/excluirItensPedidoMesaPorId.php";

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
                                                    listagemProdutoNaComanda();
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
        getMenuInflater().inflate(R.menu.menu_comanda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvar_comanda:
                menuSalvar();
                break;

            case R.id.menu_limparMesa:
                menuLimparMesa(numeroMesa.getId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuSalvar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ComandaActivity.this);
        builder.setTitle("Gasto total da mesa");
        builder.setMessage("R$ " + valorTotalMesa);
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

                        if (valorTotalMesa <= 0){
                            Toast.makeText(getApplicationContext(),
                                    "Comanda vazia",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //Configuração data
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                            Date dataSistema = new Date();

                            final String dataFormatada = formato.format(dataSistema);
                            final String idMesa = String.valueOf(numeroMesa.getId());
                            final String tipo = "Mesa";
                            final String receita = String.valueOf(valorTotalMesa);
                            final String despesa = String.valueOf(0);
                            final String statusMesa = String.valueOf(0); //Torna mesa disponivel

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_registrar_fluxo_mesa,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //Log.v("Info", "zzzResponse: " + response);
                                            Toast.makeText(ComandaActivity.this,
                                                    "Comanda " + numeroMesa.getId() + " fechada",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(ComandaActivity.this,
                                                    "Erro ao fechar comanda! --> Erro 2",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("tipo", tipo);
                                    params.put("dataFluxo", dataFormatada);
                                    params.put("receita", receita);
                                    params.put("despesa", despesa);
                                    params.put("idMesa", idMesa);
                                    params.put("statusMesa", statusMesa);

                                    Log.v("zzzParametros", "Paramentros: " + params.toString());
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(stringRequest);
                        }
                    }
                });
                builder2.show();
            }
        });
        builder.show();
    }

    private void menuLimparMesa(final int id){
        //Cancelar pedidos da mesa

        AlertDialog.Builder builder = new AlertDialog.Builder(ComandaActivity.this);
        builder.setTitle("Confirmar exclusão.");
        builder.setMessage("DESEJA CANCELAR TODOS PEDIDOS DA MESA ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                excluirItensPedidoMesaPorId(id);

            }
        });

        builder.setNegativeButton("Não", null);

        builder.create();
        builder.show();
    }

    private void excluirItensPedidoMesaPorId(int id){

        final String idMesa = String.valueOf(id);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlExcluirItensMesaPorID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Info", "zzzResponse: " + response);
                        Toast.makeText(ComandaActivity.this,
                                "Limpando mesa ...",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("INFO", "zzzErro2: " + error.toString());
                        Toast.makeText(ComandaActivity.this,
                                "Erro ao limpar mesa! --> " + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idMesa);

                Log.v("zzzParametros", "Paramentros: " + params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

        listaItemPedidos.clear();
        valorTotalMesa = 0;

        String url_parametro = url_listar_item_protudo + numeroMesa.getId();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_parametro, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Verifica se o resultado trouxe dados - MELHOR FORMA
                        if(response.length() >= 1){
                            progressBarItemPedido.setVisibility(View.GONE);
                            descricaoItemPedido.setVisibility(View.GONE);
                        }
                        else{
                            progressBarItemPedido.setVisibility(View.GONE);
                            descricaoItemPedido.setVisibility(View.VISIBLE);
                        }
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
                                itemPedido.setIdMesa(jsonObject.getInt("idMesa"));
                                itemPedido.setNomeCategoria(jsonObject.getString("nomeCategoria"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                            listaItemPedidos.add(itemPedido);

                            //Acumula o total gasto na mesa
                            valorTotalMesa += itemPedido.getValorTotal();

                        }

                        adapter = new AdapterPedido(listaItemPedidos, getApplicationContext());
                        recyclerPedidos.setAdapter(adapter);

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
