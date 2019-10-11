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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.List;

public class DescricaoProdutoActivity extends AppCompatActivity {

    private ImageButton imageRemover, imageAdicionar;
    private TextView textNomeProduto, textIngredientes, textQuantidade, textValor, textValorResultado;
    private Spinner spinner;

    private int quantidade;
    private double valorUnitario;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_produto);
        this.getSupportActionBar().setTitle("Descrição do item");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textNomeProduto = findViewById(R.id.textNomeProduto);
        textIngredientes = findViewById(R.id.textIngredienteDescricao);
        textQuantidade = findViewById(R.id.textQuantidade);
        textValor = findViewById(R.id.textValorUnitario);
        textValorResultado = findViewById(R.id.textValorResultado);

        spinner = findViewById(R.id.spinnerCategoriaDescricao);

        imageRemover = findViewById(R.id.imageRemover);
        imageAdicionar = findViewById(R.id.imageAdicionar);

        quantidade = Integer.parseInt(textQuantidade.getText().toString());
        valorUnitario = Double.parseDouble(textValor.getText().toString());

        carregarSpinner();

        imprimirCardapio();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void imprimirCardapio(){

        Cardapio cardapio = (Cardapio) getIntent().getSerializableExtra("posicao");
        textNomeProduto.setText(cardapio.getNomeProduto());
        textIngredientes.setText(cardapio.getIngredientes());
        textValor.setText(String.valueOf(cardapio.getValor()));
        //spinner.setSelection(Math.toIntExact(cardapio.getIdCategoria()));

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
        textQuantidade.setText(String.valueOf(quantidade));
        textValorResultado.setText("R$ " + String.valueOf(quantidade*valorUnitario));
    }

    public void removerProduto(View view){
        if (quantidade <= 1){
            return;
        }else {
            quantidade--;
            textQuantidade.setText(String.valueOf(quantidade));
            textValorResultado.setText("R$ " +String.valueOf(quantidade*valorUnitario));
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

    public void menuSalvar(){
        Toast.makeText(getApplicationContext(), "Menu salvar", Toast.LENGTH_SHORT).show();
    }
}
