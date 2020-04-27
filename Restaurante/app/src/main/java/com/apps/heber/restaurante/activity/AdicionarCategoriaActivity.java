package com.apps.heber.restaurante.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.adapter.AdapterCardapio;
import com.apps.heber.restaurante.modelo.Cardapio;
import com.apps.heber.restaurante.modelo.Categoria;
import com.apps.heber.restaurante.modelo.CategoriaNovo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdicionarCategoriaActivity extends AppCompatActivity {

    private TextInputEditText editNomeCategoria;
    private CategoriaNovo categoriaSelecionada;
    private List<Cardapio> listaCardapio = new ArrayList<>();

    RequestQueue requestQueue;

    private static String url_registrar_categoria = "https://restaurantecome.000webhostapp.com/registrarCategoria.php";
    private static String url_editar_categoria = "https://restaurantecome.000webhostapp.com/editarCategoria.php";
    private String url_listar_cardapio = "https://restaurantecome.000webhostapp.com/listarCardapio.php?idCategoria=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Adicionar categoria");
        actionBar.setDisplayHomeAsUpEnabled(true);

        editNomeCategoria = findViewById(R.id.nomeCategoria);
        requestQueue = Volley.newRequestQueue(this);

        categoriaSelecionada = (CategoriaNovo) getIntent().getSerializableExtra("categoriaSelecionada");
        //Log.i("INFO", "xxx Categoria chegando: "+categoriaSelecionada);

        //Se for edicão traz o campo 'nome categoria' preenchido
        if (categoriaSelecionada != null){
            editNomeCategoria.setText(categoriaSelecionada.getCategoria());
            listagemCardapio();
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
                RegistrarCategoria();
                //menuSalvar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RegistrarCategoria(){

        // Editar categoria
        if (categoriaSelecionada != null){

            if(listaCardapio.size()>=1){
                Toast.makeText(getApplicationContext(),
                        "Não é possivel alterar categoria com cardápio cadastrado",
                        Toast.LENGTH_LONG).show();
            }
            else{
                final String idCategoria = String.valueOf(categoriaSelecionada.getIdCategoria());
                final String nomeCategoria = editNomeCategoria.getText().toString();

                if (!nomeCategoria.isEmpty()){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_editar_categoria,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.v("Info", "zzzResponse: " + response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String sucess = jsonObject.getString("sucess");
                                        if (sucess.equals("1")) {
                                            Toast.makeText(AdicionarCategoriaActivity.this,
                                                    "Categoria editada",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        Log.v("INFO", "zzzErro1: " + e.toString());

                                        Toast.makeText(AdicionarCategoriaActivity.this,
                                                "Erro ao editar! --> " + e.toString(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.v("INFO", "zzzErro2: " + error.toString());
                                    Toast.makeText(AdicionarCategoriaActivity.this,
                                            "Erro ao editar! --> " + error.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("idCategoria", idCategoria);
                            params.put("nomeCategoria", nomeCategoria);

                            Log.v("zzzParametros", "Paramentros: " + params.toString());
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(getApplicationContext(), "Informe a categoria", Toast.LENGTH_SHORT).show();
                }
            }
        }

        //Criar nova categoria
        else{
            final String campoCategoria = editNomeCategoria.getText().toString();
            Log.v("INFO", "nome campo: " + campoCategoria);

            if (!campoCategoria.isEmpty()){

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_registrar_categoria,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.v("Info", "zzzResponse: " + jsonObject);

                                    String sucess = jsonObject.getString("sucess");
                                    if (sucess.equals("1")) {
                                        Toast.makeText(AdicionarCategoriaActivity.this,
                                                "Categoria adicionada",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    Log.v("INFO", "zzzErro1: " + e.toString());

                                    Toast.makeText(AdicionarCategoriaActivity.this,
                                            "Erro ao registrar! --> " + e.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.v("INFO", "zzzErro2: " + error.toString());
                                Toast.makeText(AdicionarCategoriaActivity.this,
                                        "Erro ao registrar! --> " + error.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Log.v("zzzParametros", "Paramentros: " + campoCategoria);
                        Map<String, String> params = new HashMap<>();
                        params.put("nomeCategoria", campoCategoria);

                        Log.v("zzzParametros", "Paramentros: " + params.toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }else{
                Toast.makeText(getApplicationContext(),
                        "Preencha o campo!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void listagemCardapio(){

        String url_parametro = url_listar_cardapio + categoriaSelecionada.getIdCategoria();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_parametro, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            Cardapio cardapio = new Cardapio();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                cardapio.setIdCardapio(jsonObject.getLong("idProduto"));
                                cardapio.setValor(jsonObject.getDouble("preco"));
                                cardapio.setNomeProduto(jsonObject.getString("nomeProduto"));
                                cardapio.setIngredientes(jsonObject.getString("descricao"));
                                cardapio.setIdCategoria(jsonObject.getInt("idCategoria"));
                                cardapio.setNomeCategoria(jsonObject.getString("nomeCategoria"));


                            } catch (JSONException e) { }

                            listaCardapio.add(cardapio);
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

    /* - SQLite
    public void menuSalvar(){

        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());

        if (categoriaSelecionada != null){ // Edição

            String nomeCategoria = editNomeCategoria.getText().toString();

            if (!nomeCategoria.isEmpty()){

                Categoria categoriaAtualizada = new Categoria();
                categoriaAtualizada.setId(categoriaSelecionada.getId());
                categoriaAtualizada.setCategoria(nomeCategoria);

                if (categoriaDAO.atualizarCategoria(categoriaAtualizada)){
                    Toast.makeText(getApplicationContext(), "Categoria atualizada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Informe a categoria", Toast.LENGTH_SHORT).show();
            }

        }

        else{//Salvar

            String nomeCategoria = editNomeCategoria.getText().toString();

            if (!nomeCategoria.isEmpty()){

                Categoria categoria = new Categoria();
                categoria.setCategoria(nomeCategoria);

                if (categoriaDAO.salvarCategoria(categoria)){
                    Toast.makeText(getApplicationContext(), "Categoria salva!", Toast.LENGTH_SHORT).show();
                    //Log.i("Info", "idCategoria: "+categoria.getId()+ "Nome: "+categoria.getCategoria());
                    finish();
                }

            }else {
                Toast.makeText(getApplicationContext(), "Informe o nome da categoria", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
