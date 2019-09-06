package com.apps.heber.restaurante.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.heber.restaurante.R;

public class MonitorarPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitorar_pedido);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Monitorar Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
