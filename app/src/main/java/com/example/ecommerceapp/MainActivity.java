package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private static final String LOGIN_CLIENTE_URL = "https://central-park-ecommerce.herokuapp.com/api/login_cliente";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        requestQueue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(v -> {
            login();
        });
    }

    private void login(){
        // Mapeo de los pares clave-valor
        HashMap<String, String> parametros = new HashMap();
        parametros.put("email", etEmail.getText().toString());
        parametros.put("password", etPassword.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                LOGIN_CLIENTE_URL,
                new JSONObject(parametros),
                response -> {
                    try {
                        if (response.has("data")){
                            //Recuperar objeto data
                            JSONObject res = response.getJSONObject("data");
                            Toast.makeText(this, "Bienvenido, " + res.getString("nombres"), Toast.LENGTH_LONG).show();

                            //Limpiar SharedPreferences
                            SharedPreferences.Editor editor = getSharedPreferences("usuario", Context.MODE_PRIVATE).edit();
                            editor.clear().apply();

                            //Guardar Autenticacion SharedPreferences
                            SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

                            //Guardar
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString("token", response.getString("token"));
                            edit.putString("_id", res.getString("_id"));
                            edit.putString("fullname", res.getString("nombres") + " " + res.getString("apellidos"));
                            edit.commit();

                            //Limpiar Inputs
                            etEmail.setText("");
                            etPassword.setText("");

                            //Enviar al home
                            Intent intent = new Intent(this, BottomNavigationActivity.class);
                            this.startActivity(intent);

                        }else{
                            String msg = response.getString("message");
                            etEmail.setText("");
                            etPassword.setText("");
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
                });
        requestQueue.add(jsonObjectRequest);
    }



}