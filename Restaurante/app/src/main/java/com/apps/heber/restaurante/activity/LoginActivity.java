package com.apps.heber.restaurante.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.apps.heber.restaurante.R;
import com.apps.heber.restaurante.activity.garcom.PrincipalGarconActivity;
import com.apps.heber.restaurante.modelo.Usuario;
import com.apps.heber.restaurante.modelo.UsuarioNovo;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private TextInputEditText campoEmail;
    private TextInputEditText campoSenha;
    private Button botaoEntrar;

    private Usuario usuario;
    private FirebaseAuth autenticacao;

    private List<UsuarioNovo> listaUsuarios = new ArrayList<>();
    private List<String> listaEmail = new ArrayList<>();
    private List<String> listaSenha = new ArrayList<>();
    private String url_listar_usuario = "https://restaurantecome.000webhostapp.com/listarUsuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.botaoEntrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){
                        progressBar.setVisibility(View.VISIBLE);
                        validandoUsuario(textoEmail, textoSenha);
                    }else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Informe a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Informe o email!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void validandoUsuario(final String email, final String senha){

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_listar_usuario, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() >= 1){
                            progressBar.setVisibility(View.GONE);
                        }
                        for(int i = 0; i < response.length(); i++){
                            UsuarioNovo usuarioNovo = new UsuarioNovo();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                usuarioNovo.setIdUsuario(jsonObject.getInt("idUsuario"));
                                usuarioNovo.setEmail(jsonObject.getString("email"));
                                usuarioNovo.setSenha(jsonObject.getString("senha"));
                                usuarioNovo.setTipo(jsonObject.getString("tipo"));

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Erro 01",
                                        Toast.LENGTH_SHORT).show();
                                Log.v("INFO", "Erro 01: " + e.toString());
                            }
                            listaUsuarios.add(usuarioNovo);
                            listaEmail.add(usuarioNovo.getEmail());
                            listaSenha.add(usuarioNovo.getSenha());
                        }

                        if(listaEmail.contains(email) && listaSenha.contains(senha)){

                            for (int i=0; i<listaUsuarios.size(); i++){
                                if(email.equals(listaUsuarios.get(i).getEmail()) &&
                                        senha.equals(listaUsuarios.get(i).getSenha())){

                                    switch (listaUsuarios.get(i).getTipo()){
                                        case "administrador":
                                            abrirTelaDoAdministrador();
                                            break;
                                        case "garcom":
                                            abrirTelaDoGarcom();
                                            break;
                                    }
                                }
                            }
                            listaUsuarios.clear();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Email ou senha inválido!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Erro 02",
                        Toast.LENGTH_SHORT).show();
                Log.v("INFO", "Erro 02: " + error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    private void abrirTelaDoAdministrador(){
        Toast.makeText(getApplicationContext(),
                "Administrador",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(intent);
    }

    private void abrirTelaDoGarcom(){
        Toast.makeText(getApplicationContext(),
                "Garçom",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, PrincipalGarconActivity.class);
        startActivity(intent);
    }
}
