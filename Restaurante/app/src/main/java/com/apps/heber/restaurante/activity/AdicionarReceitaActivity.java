package com.apps.heber.restaurante.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.heber.restaurante.R;

import java.util.Calendar;

public class AdicionarReceitaActivity extends AppCompatActivity {

    private EditText valor;
    private TextInputEditText data, categoria;

    Calendar calendar;
    DatePickerDialog pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_receita);
        this.getSupportActionBar().setTitle("Adicioanar Receita");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        valor = findViewById(R.id.editValorReceita);
        data = findViewById(R.id.editDataReceita);
        categoria = findViewById(R.id.editCategoriaReceita);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes  = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(AdicionarReceitaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String diaSelecionado = String.format("%02d", (dayOfMonth));
                        String mesSelecionado = String.format("%02d", (month+1));
                        data.setText(diaSelecionado + "/" + mesSelecionado + "/" + year);
                    }
                }, ano, mes, dia);
                pickerDialog.show();
            }
        });
    }

    private void menuSalvar(){
        Toast.makeText(getApplicationContext(),
                "Menu não configurado",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar:
                menuSalvar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
