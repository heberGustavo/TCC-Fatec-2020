package com.apps.heber.restaurante.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.heber.restaurante.DAO.QuantMesasDAO;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.helper.DbHelper;
import com.apps.heber.restaurante.modelo.QuantMesas;

public class QuantMesasActivity extends AppCompatActivity {

    private TextInputEditText quantMesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quant_mesas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Adicionar mesa");
        actionBar.setDisplayHomeAsUpEnabled(true);

        quantMesas = findViewById(R.id.editQuantMesas);

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
                salvarMesas();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void salvarMesas(){
        QuantMesasDAO quantMesasDAO = new QuantMesasDAO(getApplicationContext());

        String campoquantidade = quantMesas.getText().toString();

        if (!campoquantidade.isEmpty()){

            int quantidade = Integer.parseInt(campoquantidade);

            for (int i=1; i<=quantidade; i++){
                QuantMesas quantMesas = new QuantMesas();
                quantMesas.setNumeroMesa(i);

                if (quantMesasDAO.salvarQuantMesas(quantMesas)){

                }
            }
            Toast.makeText(getApplicationContext(),
                    "Salvando ...",
                    Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(),
                    "Preencha o campo!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
