package com.apps.heber.restaurante.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Categoria;

public class AdicionarCategoriaActivity extends AppCompatActivity {

    private TextInputEditText editNomeCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_categoria);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Adicionar categoria");
        actionBar.setDisplayHomeAsUpEnabled(true);

        editNomeCategoria = findViewById(R.id.nomeCategoria);
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void menuSalvar(){
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());

        String nomeCategoria = editNomeCategoria.getText().toString();
        if (!nomeCategoria.isEmpty()){
            Categoria categoria = new Categoria();
            categoria.setCategoria(nomeCategoria);

            if (categoriaDAO.salvarCategoria(categoria)){
                Toast.makeText(getApplicationContext(), "Categoria salva!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Informe o nome da categoria", Toast.LENGTH_SHORT).show();
        }
    }
}
