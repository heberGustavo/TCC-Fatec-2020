package com.apps.heber.restaurante.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdicionarMesasActivity extends AppCompatActivity {

    private TextInputEditText numeroMesa;

    private RequestQueue requestQueue;

    private static String url_registrar_mesa = "https://restaurantecome.000webhostapp.com/registrarMesa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quant_mesas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Adicionar mesa");
        actionBar.setDisplayHomeAsUpEnabled(true);

        numeroMesa = findViewById(R.id.editQuantMesas);
        requestQueue = Volley.newRequestQueue(this);

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
                Log.v("zzz", "Clique salvar");
                RegistrarMesa(); // MySQL
                //salvarMesas(); // SQLite
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //MySQL
    public void RegistrarMesa(){
        final String campoquantidade = numeroMesa.getText().toString();

        if (!campoquantidade.isEmpty()){

            final int quantidade = Integer.parseInt(campoquantidade);
            int i;

            for(i=1; i<= quantidade; i++) {

                final int finalI = i;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_registrar_mesa,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.v("Info", "zzzResponse: " + jsonObject);

                                    String sucess = jsonObject.getString("sucess");
                                    if (sucess.equals("1")) {
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    Log.v("INFO", "zzzErro1: " + e.toString());

                                    Toast.makeText(AdicionarMesasActivity.this,
                                            "Erro ao registrar! --> " + e.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.v("INFO", "zzzErro2: " + error.toString());
                                Toast.makeText(AdicionarMesasActivity.this,
                                        "Erro ao registrar! --> " + error.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("numeroMesa", finalI+"");

                        Log.v("zzzParametros", "Paramentros: " + params.toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        }else{
            Toast.makeText(getApplicationContext(),
                    "Preencha o campo!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    //SQLite
    /*
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
     */
}
