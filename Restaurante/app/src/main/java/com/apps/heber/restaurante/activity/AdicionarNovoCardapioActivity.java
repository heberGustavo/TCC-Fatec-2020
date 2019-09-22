package com.apps.heber.restaurante.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.CategoriaDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class AdicionarNovoCardapioActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private List<Categoria> listaCategorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_novo_cardapio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Novo Cardapio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.spinnerCategoriaNovoCardapio);

        spinner.setOnItemSelectedListener(this);

        carregarSpinner();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String valor = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_salvar:
                Toast.makeText(getApplicationContext(),
                        "Menu salvar",
                        Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
