package com.apps.heber.restaurante.activity;

import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.CategoriaNovo;
import com.apps.heber.restaurante.modelo.ItemPedido;
import com.apps.heber.restaurante.modelo.QuantMesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdicionarPedidoActivity extends AppCompatActivity {

    private TextView textNomeProduto, textIngredientes, textQuantidade, textValorUnitario, textValorResultado;
    private EditText editObservacao;
    private Spinner spinner;

    private Cardapio cardapioSelecionado;
    private ItemPedido itemPedidoSelecionado;
    private List<CategoriaNovo> listaCategorias = new ArrayList<>();
    private QuantMesa numeroMesa;

    private int quantidadeCardapio;
    private int quantidadePedido;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCategoria.php";
    private String url_registrar_pedido = "https://restaurantecome.000webhostapp.com/RegistrarItemProduto.php";
    private String url_editar_pedido = "https://restaurantecome.000webhostapp.com/editarItemProduto.php";
    private double valorTotal;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pedido);
        this.getSupportActionBar().setTitle("Descrição do item");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textNomeProduto = findViewById(R.id.textNomeProduto);
        textNomeProduto.setPaintFlags(textNomeProduto.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //Sublinhar

        textIngredientes = findViewById(R.id.textIngredienteDescricao);
        textQuantidade = findViewById(R.id.textQuantidade);
        textValorUnitario = findViewById(R.id.textValorUnitario);
        textValorResultado = findViewById(R.id.textValorResultado);
        editObservacao = findViewById(R.id.editObservacoesDescricao);

        spinner = findViewById(R.id.spinnerCategoriaDescricao);
        spinner.setEnabled(false);

        //Recebendo dados
        numeroMesa = (QuantMesa) getIntent().getSerializableExtra("numeroMesa");
        cardapioSelecionado = (Cardapio) getIntent().getSerializableExtra("cardapioSelecionado");
        itemPedidoSelecionado = (ItemPedido) getIntent().getSerializableExtra("pedidoSelecionado");

        listagemSpinnerCategoria();
        verificarSpinner();
        imprimirCardapio();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void imprimirCardapio(){

        //Se for adicionar pedido a mesa
        if (cardapioSelecionado != null){
            Log.v("INFO", "zzzCardapio Novo: "+ cardapioSelecionado);
            quantidadeCardapio = 1;

            textNomeProduto.setText(cardapioSelecionado.getNomeProduto());
            textIngredientes.setText(cardapioSelecionado.getIngredientes());
            textValorUnitario.setText(String.valueOf(cardapioSelecionado.getValor()));
            textValorResultado.setText(String.valueOf(cardapioSelecionado.getValor())); //Mostra o valor para não iniciar com 0.00
            //Valor do Spinner esta sendo verificado no metodo 'vericarSpinner'
        }

        //Se for editar pedido da mesa
        if(itemPedidoSelecionado != null){
            Log.v("INFO", "zzzChegando id item: " + itemPedidoSelecionado.getIdItemCardapio());

            textNomeProduto.setText(itemPedidoSelecionado.getNomeProduto());
            textIngredientes.setText(itemPedidoSelecionado.getIngrediente());
            textQuantidade.setText(String.valueOf(itemPedidoSelecionado.getQuantidade()));
            textValorUnitario.setText(String.valueOf(itemPedidoSelecionado.getValorUnitario()));
            textValorResultado.setText(String.valueOf(itemPedidoSelecionado.getValorTotal()));
            editObservacao.setText(itemPedidoSelecionado.getObservacoes());
            //Spinner está sendo listado no método 'verificarSpinner'

            //Usado para adicionar ou remover item do cardapio
            quantidadePedido = itemPedidoSelecionado.getQuantidade();
            //Log.i("INFO","xxxx - fora: " + quantidadePedido);
        }
    }

    public void verificarSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i=0; i<listaCategorias.size(); i++){
                    //Se adicionar novo item
                    if(cardapioSelecionado != null){
                        if(listaCategorias.get(i).getCategoria().equals(cardapioSelecionado.getNomeCategoria())){
                            spinner.setSelection(i);
                        }
                    }
                    //Se editar item cadastrado
                    if(itemPedidoSelecionado != null){
                        if(listaCategorias.get(i).getCategoria().equals(itemPedidoSelecionado.getNomeCategoria())){
                            spinner.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void listagemSpinnerCategoria(){

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_categoria, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            CategoriaNovo categoria = new CategoriaNovo();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                categoria.setIdCategoria(jsonObject.getInt("idCategoria"));
                                categoria.setCategoria(jsonObject.getString("nomeCategoria"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                            listaCategorias.add(categoria);
                        }
                        carregarSpinner();

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

    public void carregarSpinner(){
        ArrayAdapter<CategoriaNovo> dataAdapter = new ArrayAdapter<CategoriaNovo>(this,android.R.layout.simple_spinner_item, listaCategorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void adicionarProduto(View view){
        if (cardapioSelecionado != null){

            quantidadeCardapio += 1;
            //Log.i("INFO","Quantidade: "+quantidade);

            valorTotal = quantidadeCardapio*cardapioSelecionado.getValor();
            textQuantidade.setText(String.valueOf(quantidadeCardapio));

        }
        if (itemPedidoSelecionado != null){
            quantidadePedido += 1;
            //Log.i("INFO","xxxx - dentro: " + quantidadePedido);

            valorTotal = quantidadePedido* itemPedidoSelecionado.getValorUnitario();
            textQuantidade.setText(String.valueOf(quantidadePedido));
        }
        textValorResultado.setText(String.valueOf(valorTotal));
    }

    public void removerProduto(View view){
        if (cardapioSelecionado != null) {
            if (quantidadeCardapio <= 1)
                return;
            else {
                //Log.i("INFO", "Antes: " + quantidadeCardapio);

                quantidadeCardapio--;
                valorTotal = quantidadeCardapio * cardapioSelecionado.getValor();
                textQuantidade.setText(String.valueOf(quantidadeCardapio));

                //Log.i("INFO", "Depois: " + quantidadeCardapio);

            }
        }
        if (itemPedidoSelecionado != null){
            if (quantidadePedido <= 1)
                return;
            else {
                //Log.i("INFO", "Antes: " + quantidadePedido);

                quantidadePedido --;
                valorTotal = quantidadePedido* itemPedidoSelecionado.getValorUnitario();
                textQuantidade.setText(String.valueOf(quantidadePedido));

                //Log.i("INFO", "Depois: " + quantidadePedido);
            }
        }
            textValorResultado.setText(String.valueOf(valorTotal));
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
                RegistrarPedido();
        }
        return super.onOptionsItemSelected(item);
    }

    private void RegistrarPedido(){

        //Editar item do pedido
        if(itemPedidoSelecionado != null){

            Log.v("INFO", "zzzChegando id item 2: " + itemPedidoSelecionado.getIdItemCardapio());
            final String idItemPedido = String.valueOf(itemPedidoSelecionado.getIdItemCardapio());
            final String quantidade = textQuantidade.getText().toString();
            final String valorTotal = textValorResultado.getText().toString();
            final String observacao = editObservacao.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_editar_pedido,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("Info", "zzzResponse: " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                String sucess = jsonObject.getString("sucess");
                                if (sucess.equals("1")) {
                                    Toast.makeText(AdicionarPedidoActivity.this,
                                            "Pedido foi editado",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(AdicionarPedidoActivity.this,
                                        "Erro ao editar! --> Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AdicionarPedidoActivity.this,
                                    "Erro ao editar! --> Erro 2",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idItemPedido", idItemPedido);
                    params.put("quantidade", quantidade);
                    params.put("valorTotal", valorTotal);
                    params.put("observacao", observacao);

                    Log.v("zzzParametros", "Paramentros: " + params.toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }


        //Adicionar item no pedido
        if(cardapioSelecionado != null){

            final String nomeProduto = textNomeProduto.getText().toString();
            final String ingredientes = textIngredientes.getText().toString();
            final String quantidade = textQuantidade.getText().toString();
            final String valorUnitario = textValorUnitario.getText().toString();
            final String valorTotal = textValorResultado.getText().toString();
            final String observacao = editObservacao.getText().toString();
            final String idCategoria = String.valueOf(cardapioSelecionado.getIdCategoria());
            final String nomeCategoria = cardapioSelecionado.getNomeCategoria();
            final String idMesa = String.valueOf(numeroMesa.getId());
            final String statusMesa = String.valueOf(1); //Mesa ocupada

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_registrar_pedido,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.v("Info", "zzzResponse: " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                String sucess = jsonObject.getString("sucess");
                                if (sucess.equals("1")) {
                                    Toast.makeText(AdicionarPedidoActivity.this,
                                            "Pedido salvo na mesa " + numeroMesa.getId(),
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(AdicionarPedidoActivity.this,
                                        "Erro ao registrar! --> Erro 01",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AdicionarPedidoActivity.this,
                                    "Erro ao registrar! --> Erro 2",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nomeProduto", nomeProduto);
                    params.put("ingrediente", ingredientes);
                    params.put("quantidade", quantidade);
                    params.put("valorUnitario", valorUnitario);
                    params.put("valorTotal", valorTotal);
                    params.put("observacao", observacao);
                    params.put("idCategoria", idCategoria);
                    params.put("nomeCategoria", nomeCategoria);
                    params.put("idMesa", idMesa);
                    params.put("statusMesa", statusMesa);

                    Log.v("zzzParametros", "Paramentros: " + params.toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    /*public void menuSalvar(){
        ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(getApplicationContext());

        //Editar
        if (pedidoSelecionado != null){
            //Log.v("INFO", "Editar");

            int quantidade = Integer.parseInt(textQuantidade.getText().toString());
            double valorTotal = Double.parseDouble(textValorResultado.getText().toString());
            String observacao = editObservacao.getText().toString();

            Pedido pedidoAtualizado = new Pedido();
            pedidoAtualizado.setIdItemPedido(pedidoSelecionado.getIdItemPedido());
            pedidoAtualizado.setNomeProduto(pedidoSelecionado.getNomeProduto());
            pedidoAtualizado.setFkIdCategoria(pedidoSelecionado.getFkIdCategoria());
            pedidoAtualizado.setIngredientes(pedidoSelecionado.getIngredientes());
            pedidoAtualizado.setValorUnitario(pedidoSelecionado.getValorUnitario());
            pedidoAtualizado.setQuantidade(quantidade);
            pedidoAtualizado.setValorTotal(valorTotal);
            pedidoAtualizado.setObservacao(observacao);
            pedidoAtualizado.setPosicaoMesa(pedidoSelecionado.getPosicaoMesa());
            Log.v("INFO", "Pedido selecionado get mesa: "+ pedidoSelecionado.getPosicaoMesa());

            if (itemPedidoDAO.atualizar(pedidoAtualizado)){
                Toast.makeText(getApplicationContext(), "Pedido atualizado!", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Erro ao atualizar pedido!", Toast.LENGTH_SHORT).show();
            }
        }
        //Salvar
        else {
            //Log.v("INFO", "Salvar");

            //*** Salvar os dados em uma tabela e listar na tela de Comanda
            String nomeProduto = textNomeProduto.getText().toString();
            String ingredientes = textIngredientes.getText().toString();
            int quant = Integer.parseInt(textQuantidade.getText().toString());
            double valorUnitario = Double.parseDouble(textValorUnitario.getText().toString());
            double valorTotal = Double.parseDouble(textValorResultado.getText().toString());
            String observacao = editObservacao.getText().toString();

            Pedido pedido = new Pedido();
            pedido.setNomeProduto(nomeProduto);
            pedido.setFkIdCategoria(Long.valueOf(String.valueOf(cardapioSelecionado.getIdCategoria())));
            //Log.i("INFO", "vvvid: cardapio" + cardapioSelecionado.getIdCategoria());
            pedido.setIngredientes(ingredientes);
            pedido.setQuantidade(quant);
            pedido.setValorUnitario(valorUnitario);
            pedido.setValorTotal(valorTotal);
            pedido.setObservacao(observacao);
            //pedido.setPosicaoMesa(quantMesas.getIdMesa());
            //Log.i("INFO", "Posicao mesa: "+quantMesas.getIdMesa());


            if (itemPedidoDAO.salvar(pedido)){
                Toast.makeText(getApplicationContext(), "Pedido salvo", Toast.LENGTH_SHORT).show();
                Log.v("INFO", "Salvando Pedido: " + pedido.toString());
            }else {
                Toast.makeText(getApplicationContext(), "Erro ao salvar!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
