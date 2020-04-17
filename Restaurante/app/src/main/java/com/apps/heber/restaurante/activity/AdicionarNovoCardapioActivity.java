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
import com.apps.heber.restaurante.modelo.CategoriaNovo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdicionarNovoCardapioActivity extends AppCompatActivity {

    private EditText editValor;
    private TextInputEditText editNomeProduto, editIngredientes;
    private Spinner spinner;
    private List<CategoriaNovo> listaCategorias = new ArrayList<>();

    private String url_listar_categoria = "https://restaurantecome.000webhostapp.com/listarCategoria.php";
    private String url_registrar_cardapio = "https://restaurantecome.000webhostapp.com/registrarProduto.php";
    private String url_editar_cardapio = "https://restaurantecome.000webhostapp.com/editarCardapio.php";

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

        //Recebendo dados da tela anterior
        cardapioSelecionado = (Cardapio) getIntent().getSerializableExtra("cardapioSelecionado");
        listagemSpinnerCategoria(); //Carrega todas as categorias no Spinner
        verificaSpinnerSelecionado();

        //cardapioCategoria = (int) getIntent().getSerializableExtra("cardapioCategoria");
        //Toast.makeText(getApplicationContext(), ""+ cardapioCategoria, Toast.LENGTH_SHORT).show();



        if (cardapioSelecionado != null) { //Se for edição

            Toast.makeText(getApplicationContext(), "Edição", Toast.LENGTH_SHORT).show();
            cardapioCategoria = (int) getIntent().getSerializableExtra("cardapioCategoria");
            //Log.v("INFO", "Cardapio Categoria: "+cardapioCategoria);

            String valor = String.valueOf(cardapioSelecionado.getValor());
            editValor.setText(valor);
            editNomeProduto.setText(cardapioSelecionado.getNomeProduto());
            editIngredientes.setText(cardapioSelecionado.getIngredientes());
            spinner.setSelection(cardapioCategoria);
            //Log.i("INFO", "fim: "+cardapioCategoria);
        }

        //Toast.makeText(getApplicationContext(), "Novo", Toast.LENGTH_SHORT).show();
    }

    private void verificaSpinnerSelecionado() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                posicaoSpinner = (CategoriaNovo) spinner.getItemAtPosition(position);
                Log.v("INFO", "xxxPosicao Spinner: "+posicaoSpinner.getIdCategoria()+ " : "+posicaoSpinner.getCategoria());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listagemSpinnerCategoria() {

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_categoria, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
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

    public void carregarSpinner() {
        ArrayAdapter<CategoriaNovo> dataAdapter = new ArrayAdapter<CategoriaNovo>(this, android.R.layout.simple_spinner_item, listaCategorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        if(cardapioSelecionado != null){
            for (int i=0; i<listaCategorias.size(); i++){
                //Se editar item cadastrado
                if(listaCategorias.get(i).getCategoria().equals(cardapioSelecionado.getNomeCategoria())){
                    spinner.setSelection(i);
                }
                Log.v("INFO", "xxxPosicao Spinner - for: "+i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar:
                //menuSalvar();
                RegistrarCardapio();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RegistrarCardapio() {

        //Editar cardapio
        if (cardapioSelecionado != null) {

            final String idProduto = String.valueOf(cardapioSelecionado.getIdCardapio());
            final String valor = editValor.getText().toString();
            final String nomeProduto = editNomeProduto.getText().toString();
            final String ingredientes = editIngredientes.getText().toString();
            final String idCategoria = String.valueOf(posicaoSpinner.getIdCategoria());
            final String nomeCategoria = posicaoSpinner.getCategoria();

            if (!valor.isEmpty()) {
                if (!nomeProduto.isEmpty()) {
                    if (!ingredientes.isEmpty()) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_editar_cardapio,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //Log.v("Info", "zzzResponse: " + response);
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);

                                            String sucess = jsonObject.getString("sucess");
                                            if (sucess.equals("1")) {
                                                Toast.makeText(AdicionarNovoCardapioActivity.this,
                                                        "Cardapio editado",
                                                        Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(AdicionarNovoCardapioActivity.this,
                                                    "Erro ao editar! --> Erro 01",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(AdicionarNovoCardapioActivity.this,
                                                "Erro ao editar! --> Erro 02",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("idProduto", idProduto);
                                params.put("preco", valor);
                                params.put("nomeProduto", nomeProduto);
                                params.put("descricao", ingredientes);
                                params.put("idCategoria", idCategoria);
                                params.put("nomeCategoria", nomeCategoria);

                                Log.v("zzzParametros", "Paramentros: " + params.toString());
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);

                    }
                    else
                        Toast.makeText(getApplicationContext(), "Informe os ingredientes!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Informe o nome do produto!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Informe o valor!", Toast.LENGTH_SHORT).show();
        }

        //Criar novo cardapio
        else {
            final String valor = editValor.getText().toString();
            final String nome = editNomeProduto.getText().toString();
            final String ingredientes = editIngredientes.getText().toString();

            if (!valor.isEmpty()) {
                if (!nome.isEmpty()) {
                    if (!ingredientes.isEmpty()) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_registrar_cardapio,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            //Log.v("Info", "zzzResponse: " + jsonObject);

                                            String sucess = jsonObject.getString("sucess");
                                            if (sucess.equals("1")) {
                                                Toast.makeText(AdicionarNovoCardapioActivity.this,
                                                        "Cardapio adicionado",
                                                        Toast.LENGTH_SHORT).show();
                                                editValor.setText("");
                                                editNomeProduto.setText("");
                                                editIngredientes.setText("");
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(AdicionarNovoCardapioActivity.this,
                                                    "Erro ao registrar! --> Erro 01",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //Log.v("INFO", "zzzErro2: " + error.toString());
                                        Toast.makeText(AdicionarNovoCardapioActivity.this,
                                                "Erro ao registrar! --> " + error.toString(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("preco", valor);
                                params.put("nomeProduto", nome);
                                params.put("descricao", ingredientes);
                                params.put("idCategoria", String.valueOf(posicaoSpinner.getIdCategoria()));
                                params.put("nomeCategoria", posicaoSpinner.getCategoria());

                                Log.v("zzzParametros", "Paramentros: " + params.toString());
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Preencha o campo ingredientes!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Preencha o campo nome!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Preencha o campo valor!",
                        Toast.LENGTH_SHORT).show();
            }
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
}