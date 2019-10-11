package com.apps.heber.restaurante.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.CardapioDAO;
import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.List;

public class AdicionarNovoCardapioActivity extends AppCompatActivity {

    private EditText editValor;
    private TextInputEditText editNomeProduto, editIngredientes;
    private Spinner spinner;

    private Cardapio cardapioSelecionado;

    private Categoria posicaoSpinner; // Variavel global para saber a posição do Spinner

    private Long posicao;
    private int cardapioCategoria;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_novo_cardapio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Novo Cardapio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editValor = findViewById(R.id.editValorNovoCardapio);
        editNomeProduto = findViewById(R.id.editNomeProdutoNovoCardapio);
        editIngredientes = findViewById(R.id.editIngredientesNovoCardapio);
        spinner = findViewById(R.id.spinnerCategoriaNovoCardapio);

        carregarSpinner(); //Carrega todas as categorias no Spinner

        cardapioSelecionado = (Cardapio) getIntent().getSerializableExtra("cardapioSelecionado");
        posicao = (Long) getIntent().getSerializableExtra("posicaoId");
        cardapioCategoria = (int) getIntent().getSerializableExtra("cardapioCategoria");

        if (cardapioSelecionado != null){ //Se for edição

            String valor = String.valueOf(cardapioSelecionado.getValor());
            editValor.setText(valor);
            editNomeProduto.setText(cardapioSelecionado.getNomeProduto());
            editIngredientes.setText(cardapioSelecionado.getIngredientes());
            spinner.setSelection(cardapioCategoria);
            Log.i("INFO", "fim: "+cardapioCategoria);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void carregarSpinner(){
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());
        List<Categoria> labels = categoriaDAO.listarCategoria();

        ArrayAdapter<Categoria> dataAdapter = new ArrayAdapter<Categoria>(this,android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_salvar:
                menuSalvar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void menuSalvar(){
        CardapioDAO cardapioDAO = new CardapioDAO(getApplicationContext());

        //Editar
        if (cardapioSelecionado != null){

            String valor = editValor.getText().toString();
            String nomeProduto = editNomeProduto.getText().toString();
            String ingredientes = editIngredientes.getText().toString();

            if (!valor.isEmpty()){
                if (!nomeProduto.isEmpty()){
                    if (!ingredientes.isEmpty()){

                        Cardapio cardapioAtualizado = new Cardapio();
                        cardapioAtualizado.setIdCardapio(cardapioSelecionado.getIdCardapio());
                        cardapioAtualizado.setValor(Double.parseDouble(valor));
                        cardapioAtualizado.setNomeProduto(nomeProduto);
                        cardapioAtualizado.setIngredientes(ingredientes);
                        cardapioAtualizado.setIdCategoria(Long.valueOf((posicaoSpinner.getId())));

                        if (cardapioDAO.atualizar(cardapioAtualizado)){
                            Toast.makeText(getApplicationContext(), "Cardápio atualizado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Informe os ingredientes!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Informe o nome do produto!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Informe o valor!", Toast.LENGTH_SHORT).show();
            }
        }
        //Salvar
        else {

            String valor = editValor.getText().toString();
            String nome = editNomeProduto.getText().toString();
            String ingredientes = editIngredientes.getText().toString();

            if (!valor.isEmpty()){
                if (!nome.isEmpty()){
                    if (!ingredientes.isEmpty()){

                        double valorDouble = Double.parseDouble(editValor.getText().toString());

                        Cardapio cardapio = new Cardapio();
                        cardapio.setValor(valorDouble);
                        cardapio.setNomeProduto(nome);
                        cardapio.setIngredientes(ingredientes);
                        cardapio.setIdCategoria(posicaoSpinner.getId());

                        if (cardapioDAO.salvar(cardapio)){
                            Toast.makeText(getApplicationContext(),"Cardápio salvo!", Toast.LENGTH_SHORT).show();

                            /*
                            Log.i("INFO", "Nome: "+cardapio.getNomeProduto()+
                                    "idCategoria: " + cardapio.getIdCategoria() +
                                    "Valor: " + cardapio.getValor() +
                                    "Ingrediente: " + cardapio.getIngredientes()+
                                    "idCardapio: "+cardapio.getIdCardapio());
                             */


                            finish();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"Informe os ingredientes!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Informe o nome do produto!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Informe o valor!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
