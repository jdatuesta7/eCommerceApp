package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerceapp.modelos.Product;
import com.example.ecommerceapp.ui.adapter.ProductsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView imgItemDetail;
    private TextView tvdTitulo;
    private TextView tvdPrecio;
    private TextView tvdCategoria;
    private TextView tvdLocal;
    private TextView tvdContenido;
    private Spinner sCantidad;
    private String prodCantidad;
    private Product productDetails;
    private static final String AGREGAR_CARRITO_URL = "https://central-park-ecommerce.herokuapp.com/api/agregar_carrito_cliente";
    RequestQueue requestQueue;
    private SharedPreferences preferences;
    private String token, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();

        requestQueue = Volley.newRequestQueue(this);

        Button btnCarrito = findViewById(R.id.btnCarrito);
        btnCarrito.setOnClickListener(v -> {

            añadirProdCarrito();
        });
    }

    private void initViews() {
        imgItemDetail = findViewById(R.id.ivdPortada);
        tvdTitulo = findViewById(R.id.tvdTitulo);
        tvdPrecio = findViewById(R.id.tvdPrecio);
        tvdCategoria = findViewById(R.id.tvdCategoria);
        tvdLocal = findViewById(R.id.tvdLocal);
        tvdContenido = findViewById(R.id.tvdContenido);
        sCantidad = findViewById(R.id.spinner_cantidad);
    }

    @SuppressLint("SetTextI18n")
    private void initValues() {
        productDetails = (Product) getIntent().getExtras().getSerializable("productDetails");

        Picasso.get()
                .load("https://central-park-ecommerce.herokuapp.com/api/obtener_portada/"+productDetails.getPortada())
                .placeholder(R.drawable.default_placeholder)
                .into(imgItemDetail);

        tvdTitulo.setText(productDetails.getTitulo());

        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        tvdPrecio.setText(String.valueOf(formatoImporte.format(productDetails.getPrecio())));

        String local = "Local: " + productDetails.getNombre_local() + " " + productDetails.getId_local();
        tvdCategoria.setText("Categoria: " +productDetails.getCategoria());
        tvdLocal.setText(local);

        String contenido = "<h3><b>Detalles</b></h3>" + productDetails.getDescripcion() +  productDetails.getContenido();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvdContenido.setText(Html.fromHtml(contenido,Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvdContenido.setText(Html.fromHtml(contenido));
        }

        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.cantidades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sCantidad.setAdapter(adapter);

        //SharedPreferences
        preferences = this.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        _id = preferences.getString("_id", "");
    }

    private void añadirProdCarrito(){
        prodCantidad = sCantidad.getSelectedItem().toString();

        // Mapeo de los pares clave-valor
        HashMap<String, String> parametros = new HashMap();
        parametros.put("producto", productDetails.get_id());
        parametros.put("cliente", _id);
        parametros.put("cantidad", prodCantidad);

        // Header (Token)
        Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("Authorization", token);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                AGREGAR_CARRITO_URL,
                new JSONObject(parametros),
                response -> {
                    Toast.makeText(getApplicationContext(), "Producto añadido al carrito", Toast.LENGTH_LONG).show();
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error :/", Toast.LENGTH_LONG).show();
                }){
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}