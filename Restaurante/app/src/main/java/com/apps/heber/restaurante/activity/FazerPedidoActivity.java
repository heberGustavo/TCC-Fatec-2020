package com.apps.heber.restaurante.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.apps.heber.restaurante.R;

public class FazerPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_pedido);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fazer Pedido");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
