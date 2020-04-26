package com.apps.heber.restaurante.activity;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdicionarDespesaActivity extends AppCompatActivity {

    private EditText campoValor;
    private TextInputEditText campoData, campoCategoria;

    Calendar calendar;
    DatePickerDialog pickerDialog;
    private String dataUsuario;
    private String dataBanco;

    private String url_registrar_despesa = "https://restaurantecome.000webhostapp.com/registrarReceitaDespesa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_despesa);
        this.getSupportActionBar().setTitle("Adicionar Despesa");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        campoValor = findViewById(R.id.editValorDespesa);
        campoData = findViewById(R.id.editDataDespesa);
        campoCategoria = findViewById(R.id.editCategoriaDespesa);

        campoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataUsuario = "00/00/0000";
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes  = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(AdicionarDespesaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String diaSelecionado = String.format("%02d", (dayOfMonth));
                        String mesSelecionado = String.format("%02d", (month+1));

                        dataBanco = year + "-" + mesSelecionado + "-" + diaSelecionado;
                        dataUsuario = diaSelecionado + "/" + mesSelecionado + "/" + year;
                        campoData.setText(dataUsuario);

                    }
                }, ano, mes, dia);
                pickerDialog.show();
            }
        });
    }

    private void menuSalvar(){
        //Salvar
        final String _valorDespesa = campoValor.getText().toString();
        final String _data = campoData.getText().toString();
        final String _categoria = campoCategoria.getText().toString();

        if(!_valorDespesa.isEmpty()){
            if (!_data.isEmpty()){
                if(!_categoria.isEmpty()){
                    //Log.v("info", "zzzData String: " + formato.format(data));

                    final String _valorReceita = String.valueOf(0);
                    final String dataFluxo = dataBanco;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_registrar_despesa,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.v("Info", "zzzResponse: " + response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String sucess = jsonObject.getString("sucess");
                                        if (sucess.equals("1")) {
                                            Toast.makeText(AdicionarDespesaActivity.this,
                                                    "Despesa adicionada",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        Log.v("INFO", "zzzErro2: " + e.toString());
                                        Toast.makeText(AdicionarDespesaActivity.this,
                                                "Erro ao registrar! --> Erro 01",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.v("INFO", "zzzErro2: " + error.toString());
                                    Toast.makeText(AdicionarDespesaActivity.this,
                                            "Erro ao registrar! --> " + error.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("receita", _valorReceita);
                            params.put("despesa", _valorDespesa);
                            params.put("dataFluxo", dataFluxo);
                            params.put("tipo", _categoria);

                            Log.v("zzzParametros", "Paramentros: " + params.toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);

                }else{
                    Toast.makeText(getApplicationContext(), "Preencha a Categoria", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Preencha a Data", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Preencha o Valor", Toast.LENGTH_SHORT).show();
        }
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
