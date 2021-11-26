package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.modelos.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView imgItemDetail;
    private TextView tvdTitulo;
    private TextView tvdPrecio;
    private TextView tvdCategoria;
    private TextView tvdLocal;
    private TextView tvdContenido;
    private Spinner sCantidad;
    private Product productDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();

        Button btnCarrito = findViewById(R.id.btnCarrito);
        btnCarrito.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Producto añadido al carrito", Toast.LENGTH_LONG).show();
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

        sCantidad.getSelectedItem();
    }
}