package com.example.ecommerceapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerceapp.DetailProductActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.modelos.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private static final String AGREGAR_CARRITO_URL = "https://central-park-ecommerce.herokuapp.com/api/agregar_carrito_cliente";
    public List<Product> productList;
    private Context context;
    RequestQueue requestQueue;

    // Obtener referencias de los componentes visuales para cada elemento
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo, tvPrecio, tvCategoria;
        private ImageView ivPortada;
        private ImageButton btnCarrito;
        public ProductViewHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
            ivPortada = (ImageView) itemView.findViewById(R.id.ivPortada);
            btnCarrito = itemView.findViewById(R.id.btnCarrito);
        }
    }

    public ProductsAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        context = parent.getContext();
        requestQueue = Volley.newRequestQueue(context);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.tvTitulo.setText(product.getTitulo());
        holder.tvPrecio.setText(String.valueOf(product.getPrecio()));

        Picasso.get()
            .load("https://central-park-ecommerce.herokuapp.com/api/obtener_portada/"+product.getPortada())
            .placeholder(R.drawable.default_placeholder)
            .into(holder.ivPortada);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailProductActivity.class);
            intent.putExtra("productDetails", product);
            holder.itemView.getContext().startActivity(intent);
        });

        holder.btnCarrito.setOnClickListener(v -> {

            // Mapeo de los pares clave-valor
            HashMap<String, String> parametros = new HashMap();
            parametros.put("producto", product.get_id());
            parametros.put("cliente", "619d9eca37acb0eafed979f7");
            parametros.put("cantidad", "1");

            // Header (Token)
            Map<String, String> mHeaders = new ArrayMap<String, String>();
            mHeaders.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2MTlkOWVjYTM3YWNiMGVhZmVkOTc5ZjciLCJub21icmVzIjoiQ2FtaWxvIiwiYXBlbGxpZG9zIjoiRGlheiIsImVtYWlsIjoiY2RpYXpAbWFpbC5jb20iLCJpYXQiOjE2MzgxNTYwODYsImV4cCI6MTYzODc2MDg4Nn0.j8l_iPbx6bwCtLwGiRrJsW5qoZOrvmTdVJbdsqQFUls");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    AGREGAR_CARRITO_URL,
                    new JSONObject(parametros),
                    response -> {
                        Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_LONG).show();
                    },
                    error -> {
                        Toast.makeText(context, "Error :/", Toast.LENGTH_LONG).show();
                    }){
                @Override
                public Map<String, String> getHeaders() {
                    return mHeaders;
                }
            };
            requestQueue.add(jsonObjectRequest);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
