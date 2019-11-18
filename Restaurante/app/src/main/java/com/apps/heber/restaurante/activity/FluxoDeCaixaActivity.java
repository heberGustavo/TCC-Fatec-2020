package com.apps.heber.restaurante.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.apps.heber.restaurante.DAO.FluxoCaixaDAO;
import com.apps.heber.restaurante.R;

public class FluxoDeCaixaActivity extends AppCompatActivity {

    private TextView campoReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxo_de_caixa);
        this.getSupportActionBar().setTitle("Fluxo de Caixa");

        campoReceita = findViewById(R.id.textValorReceita);

        mostrarFluxo();
    }

    public void mostrarFluxo(){
        FluxoCaixaDAO fluxoCaixaDAO = new FluxoCaixaDAO(getApplicationContext());

        double receita = fluxoCaixaDAO.valorReceita();
        campoReceita.setText("R$ "+ receita);
    }
}
