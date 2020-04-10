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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.DAO.ItemPedidoDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.CategoriaNovo;
import com.apps.heber.restaurante.modelo.Pedido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdicionarPedidoActivity extends AppCompatActivity {

    private TextView textNomeProduto, textIngredientes, textQuantidade, textValorUnitario, textValorResultado;
    private EditText editObservacao;
    private Spinner spinner;

    private Cardapio cardapioSelecionado;
    private Pedido pedidoSelecionado;
    private CategoriaNovo spinnerCategoria;
    private List<CategoriaNovo> listaCategorias = new ArrayList<>();
    //private QuantMesas quantMesas;

    private int quantidadeCardapio;
    private int quantidadePedido;

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCategoria.php";
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

        //Recebendo dados
        cardapioSelecionado = (Cardapio) getIntent().getSerializableExtra("cardapioSelecionado");
        Log.i("INFO", "zzzCardapio: "+cardapioSelecionado.toString());
        pedidoSelecionado = (Pedido) getIntent().getSerializableExtra("pedidoSelecionado");
        //Log.i("INFO", "vvvPedido: "+pedidoSelecionado);

        listagemSpinnerCategoria();
        verificaSpinner();
        imprimirCardapio();












        //quantMesas = (QuantMesas) getIntent().getSerializableExtra("quantMesa");
        //Log.v("INFO", "Quant mesas5 - final: "+ quantMesas.getIdMesa());

        //if (quantMesas != null){
//
        //}
        /*
        numeroMesa = (int) getIntent().getSerializableExtra("numeroMesa");
        Log.v("INFO", "Numero mesa Final: "+numeroMesa);
         */

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void imprimirCardapio(){

        //Se for adicionar pedido a mesa
        if (cardapioSelecionado != null){

            quantidadeCardapio = 1;

            textNomeProduto.setText(cardapioSelecionado.getNomeProduto());
            textIngredientes.setText(cardapioSelecionado.getIngredientes());
            textValorUnitario.setText(String.valueOf(cardapioSelecionado.getValor()));
            textValorResultado.setText(String.valueOf(cardapioSelecionado.getValor())); //Mostra o valor para não iniciar com 0.00
            spinner.setEnabled(true); //Desativa para não poder alterar o valor
            Log.i("INFO","vvvSl: CardapioSelecionado get id: " + cardapioSelecionado.getIdCategoria());

        }

        //Se for editar pedido da mesa
        if(pedidoSelecionado != null){
            Log.v("INFO", "cardapio: "+pedidoSelecionado);
            quantidadePedido = pedidoSelecionado.getQuantidade();
            //Log.i("INFO","Quant: "+ quant);

            textNomeProduto.setText(pedidoSelecionado.getNomeProduto());
            textIngredientes.setText(pedidoSelecionado.getIngredientes());
            textQuantidade.setText(String.valueOf(pedidoSelecionado.getQuantidade()));
            textValorUnitario.setText(String.valueOf(pedidoSelecionado.getValorUnitario()));
            textValorResultado.setText(String.valueOf(pedidoSelecionado.getValorTotal()));
            editObservacao.setText(pedidoSelecionado.getObservacao());

            //Log.v("SPINNER", "Quero selecionar o ID: " + pedidoSelecionado.getFkIdCategoria());
            for (int i = 0; i < spinner.getCount(); i++){

                Categoria categoriaId = (Categoria) spinner.getItemAtPosition(i);
                //Log.v("SPINNER", "Selecionando " + i + " / " + categoriaId.getId());

                if (categoriaId.getId().equals(pedidoSelecionado.getFkIdCategoria())) {
                    spinner.setSelection(i);
                    spinner.setEnabled(false); //Desativa para não poder alterar o valor
                    //Log.v("SPINNER", "Selecionado " + i);
                }
            }
            //Log.i("INFO","vvvSl: PedidoSelecionado get fk: "+pedidoSelecionado.getFkIdCategoria());
        }
    }

    public void verificaSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i=0; i<listaCategorias.size(); i++){
                    if(i == cardapioSelecionado.getIdCategoria()){
                        position = i;
                        spinner.setSelection(position);
                    }
                    Log.v("INFO", "xxxLista:" + cardapioSelecionado);
                }

                //descobrir a posicao do meu id categoria android studio spinner
                Toast.makeText(getApplicationContext(),
                        "posicao do id: " + spinner.getSelectedItemPosition(),
                        Toast.LENGTH_SHORT).show();
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

        if (pedidoSelecionado != null){
            quantidadePedido += 1;

            valorTotal = quantidadePedido*pedidoSelecionado.getValorUnitario();
            textQuantidade.setText(String.valueOf(quantidadePedido));

            //Log.i("INFO","Sl: Soma Pedido");
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
        if (pedidoSelecionado != null){
            if (quantidadePedido <= 1)
                return;
            else {
                //Log.i("INFO", "Antes: " + quantidadePedido);

                quantidadePedido --;
                valorTotal = quantidadePedido*pedidoSelecionado.getValorUnitario();
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
                //menuSalvar();
        }
        return super.onOptionsItemSelected(item);
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
