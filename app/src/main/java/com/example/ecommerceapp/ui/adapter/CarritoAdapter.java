package com.example.ecommerceapp.ui.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.modelos.Carrito;
import com.example.ecommerceapp.modelos.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {
    public List<Carrito> carritoList;
    private Context context;
    private static final String AGREGAR_CARRITO_URL = "https://central-park-ecommerce.herokuapp.com/api/agregar_carrito_cliente";
    private static final String DISMINUIR_CANTIDAD_URL = "https://central-park-ecommerce.herokuapp.com/api/disminuir_carrito_cantidad";
    private static final String ELIMINAR_CARRITO_URL = "https://central-park-ecommerce.herokuapp.com/api/eliminar_carrito_cliente/";
    RequestQueue requestQueue;

    // Obtener referencias de los componentes visuales para cada elemento
    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo, tvPrecio, tvCantidad;
        private ImageView ivPortada;
        private ImageButton btnAumentarCantidad, btnDisminuirCantidad, btnDelete;
        public CarritoViewHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvcTitulo);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvcPrecio);
            ivPortada = (ImageView) itemView.findViewById(R.id.ivcPortada);
            tvCantidad = (TextView) itemView.findViewById(R.id.tvcCantidad);
            btnAumentarCantidad = itemView.findViewById(R.id.btnAumentarCantidad);
            btnDisminuirCantidad = itemView.findViewById(R.id.btnDisminuirCantidad);
            btnDelete = itemView.findViewById(R.id.eliminarProducto);

        }
    }

    public CarritoAdapter(List<Carrito> carritoList) {
        this.carritoList = carritoList;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrito_view, parent, false);
        context = parent.getContext();
        requestQueue = Volley.newRequestQueue(context);

        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        final Carrito carrito = carritoList.get(position);
        holder.tvTitulo.setText(carrito.getProducto().getTitulo());

        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        holder.tvPrecio.setText(String.valueOf(formatoImporte.format(carrito.getProducto().getPrecio())));

        Picasso.get()
                .load("https://central-park-ecommerce.herokuapp.com/api/obtener_portada/"+carrito.getProducto().getPortada())
                .placeholder(R.drawable.default_placeholder)
                .into(holder.ivPortada);

        holder.tvCantidad.setText(String.valueOf(carrito.getCantidad()));

        holder.btnDelete.setOnClickListener( v -> {

            // Header (Token)
            Map<String, String> mHeaders = new ArrayMap<String, String>();
            mHeaders.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2MTlkOWVjYTM3YWNiMGVhZmVkOTc5ZjciLCJub21icmVzIjoiQ2FtaWxvIiwiYXBlbGxpZG9zIjoiRGlheiIsImVtYWlsIjoiY2RpYXpAbWFpbC5jb20iLCJpYXQiOjE2MzgxNTYwODYsImV4cCI6MTYzODc2MDg4Nn0.j8l_iPbx6bwCtLwGiRrJsW5qoZOrvmTdVJbdsqQFUls");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.DELETE,
                    ELIMINAR_CARRITO_URL + carrito.get_id(),
                    null,
                    response -> {
                        carritoList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Producto eliminado", Toast.LENGTH_LONG).show();

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

        holder.btnAumentarCantidad.setOnClickListener(v -> {

            // Mapeo de los pares clave-valor
            HashMap<String, String> parametros = new HashMap();
            parametros.put("producto", carrito.getProducto().get_id());
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
                        holder.tvCantidad.setText(String.valueOf(carrito.getCantidad() + 1));
                        carrito.setCantidad(carrito.getCantidad() + 1);
                        carritoList.set(position, carrito);
                        Toast.makeText(context, "Aumentado en 1", Toast.LENGTH_LONG).show();
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

        holder.btnDisminuirCantidad.setOnClickListener(v -> {

            // Mapeo de los pares clave-valor
            HashMap<String, String> parametros = new HashMap();
            parametros.put("producto", carrito.getProducto().get_id());
            parametros.put("cliente", "619d9eca37acb0eafed979f7");
            parametros.put("cantidad", "1");

            // Header (Token)
            Map<String, String> mHeaders = new ArrayMap<String, String>();
            mHeaders.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2MTlkOWVjYTM3YWNiMGVhZmVkOTc5ZjciLCJub21icmVzIjoiQ2FtaWxvIiwiYXBlbGxpZG9zIjoiRGlheiIsImVtYWlsIjoiY2RpYXpAbWFpbC5jb20iLCJpYXQiOjE2MzgxNTYwODYsImV4cCI6MTYzODc2MDg4Nn0.j8l_iPbx6bwCtLwGiRrJsW5qoZOrvmTdVJbdsqQFUls");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    DISMINUIR_CANTIDAD_URL,
                    new JSONObject(parametros),
                    response -> {
                        holder.tvCantidad.setText(String.valueOf(carrito.getCantidad() - 1));
                        carrito.setCantidad(carrito.getCantidad() - 1);
                        carritoList.set(position, carrito);
                        Toast.makeText(context, "Disminuido en 1", Toast.LENGTH_LONG).show();
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
        return carritoList.size();
    }

}
