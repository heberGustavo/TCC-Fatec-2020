package com.apps.heber.restaurante.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.DAO.CardapioDAO;
import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCategoriaNovo;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.CategoriaNovo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdicionarNovoCardapioActivity extends AppCompatActivity {

    private EditText editValor;
    private TextInputEditText editNomeProduto, editIngredientes;
    private Spinner spinner;
    private List<CategoriaNovo> listaCategorias = new ArrayList<>();
    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCategoria.php";

    private Cardapio cardapioSelecionado;

    private CategoriaNovo posicaoSpinner; // Variavel global para saber a posição do Spinner

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

        listagemCategoria();
        //carregarSpinner(); //Carrega todas as categorias no Spinner

        //Recebendo dados da tela anterior
        cardapioSelecionado = (Cardapio) getIntent().getSerializableExtra("cardapioSelecionado");
        //posicao = (Long) getIntent().getSerializableExtra("posicaoId");

        verificaSpinnerSelecionado();

        if (cardapioSelecionado != null){ //Se for edição

            cardapioCategoria = (int) getIntent().getSerializableExtra("cardapioCategoria");
            //Log.v("INFO", "Cardapio Categoria: "+cardapioCategoria);

            String valor = String.valueOf(cardapioSelecionado.getValor());
            editValor.setText(valor);
            editNomeProduto.setText(cardapioSelecionado.getNomeProduto());
            editIngredientes.setText(cardapioSelecionado.getIngredientes());
            spinner.setSelection(cardapioCategoria);
            //Log.i("INFO", "fim: "+cardapioCategoria);
        }

    }

    private void verificaSpinnerSelecionado() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Pega o id do item selecionado
                posicaoSpinner = (CategoriaNovo) spinner.getItemAtPosition(position);
                //Log.v("INFO", "Posicao Spinner: "+posicaoSpinner.getId()+ " : "+posicaoSpinner.getCategoria());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listagemCategoria(){

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_categoria, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            CategoriaNovo categoria = new CategoriaNovo();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                categoria.setCategoria(jsonObject.getString("nomeCategoria"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
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
                Log.v("INFO", "Erro 02: " + error.toString());
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
                //menuSalvar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@RequiresApi(api = Build.VERSION_CODES.N)
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
                        cardapio.setIdCategoria(posicaoSpinner.getIdCategoria());

                        if (cardapioDAO.salvar(cardapio)){
                            Toast.makeText(getApplicationContext(),"Cardápio salvo!", Toast.LENGTH_SHORT).show();

                            //Log.i("INFO", "Nome: "+cardapio.getNomeProduto()+
                            //        "idCategoria: " + cardapio.getIdCategoria() +
                            //        "Valor: " + cardapio.getValor() +
                            //        "Ingrediente: " + cardapio.getIngredientes()+
                            //        "idCardapio: "+cardapio.getIdCardapio());

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
    }*/
}
