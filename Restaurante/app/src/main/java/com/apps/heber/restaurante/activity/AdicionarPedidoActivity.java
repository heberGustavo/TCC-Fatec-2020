package com.apps.heber.restaurante.activity;

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

import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.DAO.ItemPedidoDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.Pedido;

import java.util.List;

public class AdicionarPedidoActivity extends AppCompatActivity {

    private TextView textNomeProduto, textIngredientes, textQuantidade, textValorUnitario, textValorResultado;
    private EditText editObservacao;

    private Cardapio cardapioSelecionado;
    private Pedido pedidoSelecionado;

    private Spinner spinner;
    private Categoria posicaoSpinner;

    private int quantidade;
    private int valorSpinner;

    private double valorTotal;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pedido);
        this.getSupportActionBar().setTitle("Descrição do item");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textNomeProduto = findViewById(R.id.textNomeProduto);
        textIngredientes = findViewById(R.id.textIngredienteDescricao);
        textQuantidade = findViewById(R.id.textQuantidade);
        textValorUnitario = findViewById(R.id.textValorUnitario);
        textValorResultado = findViewById(R.id.textValorResultado);
        editObservacao = findViewById(R.id.editObservacoesDescricao);

        spinner = findViewById(R.id.spinnerCategoriaDescricao);

        quantidade = Integer.parseInt(textQuantidade.getText().toString());

        cardapioSelecionado = (Cardapio) getIntent().getSerializableExtra("cardapioSelecionado");
        pedidoSelecionado = (Pedido) getIntent().getSerializableExtra("pedidoSelecionado");
        //Log.i("INFO", "vvvPedido: "+pedidoSelecionado);

        carregarSpinner();

        imprimirCardapio();

        verificarSpinner();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void imprimirCardapio(){

        if (cardapioSelecionado != null){

            valorSpinner = (int) getIntent().getSerializableExtra("posicaoSpinner");

            textNomeProduto.setText(cardapioSelecionado.getNomeProduto());
            textIngredientes.setText(cardapioSelecionado.getIngredientes());
            textValorUnitario.setText(String.valueOf(cardapioSelecionado.getValor()));
            textValorResultado.setText(String.valueOf(cardapioSelecionado.getValor()));
            spinner.setSelection(valorSpinner);
            spinner.setEnabled(false); //Desativa para não poder alterar o valor
            //Log.i("INFO","CardapioSelecionado");

        }

        if(pedidoSelecionado != null){

            textNomeProduto.setText(pedidoSelecionado.getNomeProduto());
            textIngredientes.setText(pedidoSelecionado.getIngredientes());
            textQuantidade.setText(String.valueOf(pedidoSelecionado.getQuantidade()));
            textValorUnitario.setText(String.valueOf(pedidoSelecionado.getValorUnitario()));
            textValorResultado.setText(String.valueOf(pedidoSelecionado.getValorTotal()));
            editObservacao.setText(pedidoSelecionado.getObservacao());
            spinner.setSelection(Integer.parseInt(String.valueOf(pedidoSelecionado.getFkIdCategoria())));
            spinner.setEnabled(false); //Desativa para não poder alterar o valor
            //Log.i("INFO","PedidoSelecionado");
        }
    }

    public void carregarSpinner(){
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());
        List<Categoria> labels = categoriaDAO.listarCategoria();

        ArrayAdapter<Categoria> dataAdapter = new ArrayAdapter<Categoria>(this,android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void adicionarProduto(View view){
        quantidade++;
        valorTotal = quantidade*cardapioSelecionado.getValor();

        textQuantidade.setText(String.valueOf(quantidade));
        textValorResultado.setText(String.valueOf(valorTotal));
    }

    public void removerProduto(View view){
        if (quantidade <= 1){
            return;
        }else {
            quantidade--;
            valorTotal = quantidade*cardapioSelecionado.getValor();

            textQuantidade.setText(String.valueOf(quantidade));
            textValorResultado.setText(String.valueOf(valorTotal));
        }
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void verificarSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Pega o id do Spinner selecionado
                posicaoSpinner = (Categoria) spinner.getItemAtPosition(position);
                //Log.i("INFO", "vvvId do spinner: "+posicaoSpinner.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void menuSalvar(){

        //*** Salvar os dados em uma tabela e listar na tela de Comanda

        String nomeProduto = textNomeProduto.getText().toString();
        String ingredientes = textIngredientes.getText().toString();
        int quant = Integer.parseInt(textQuantidade.getText().toString());
        double valorUnitario = Double.parseDouble(textValorUnitario.getText().toString());
        double valorTotal = Double.parseDouble(textValorResultado.getText().toString());
        String observacao = editObservacao.getText().toString();

        ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(getApplicationContext());

        Pedido pedido = new Pedido();
        pedido.setNomeProduto(nomeProduto);
        pedido.setFkIdCategoria(Long.valueOf(String.valueOf(posicaoSpinner.getId())));
        pedido.setIngredientes(ingredientes);
        pedido.setQuantidade(quant);
        pedido.setValorUnitario(valorUnitario);
        pedido.setValorTotal(valorTotal);
        pedido.setObservacao(observacao);

        /*
        Log.i("INFO", "vvvValores: " +
                "Nome: "+nomeProduto+","+
                "Ingredientes: "+ingredientes+","+
                "Quantidade: "+quant+","+
                "Valor uni: "+valorUnitario+","+
                "Valor tota: "+valorTotal+","+
                "Obs: "+observacao+","+
                "Posicao: "+posicaoSpinner.getId());
         */

        if (itemPedidoDAO.salvar(pedido)){
            Toast.makeText(getApplicationContext(), "Pedido salvo", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Erro!!!", Toast.LENGTH_SHORT).show();
        }

    }

}
