package com.example.ecommerceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerceapp.modelos.Carrito;
import com.example.ecommerceapp.modelos.Product;
import com.example.ecommerceapp.ui.adapter.CarritoAdapter;
import com.example.ecommerceapp.ui.adapter.ProductsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {

    RequestQueue requestQueue;
    private static final String CARRITO_URL = "https://central-park-ecommerce.herokuapp.com/api/obtener_carrito_cliente/619d9eca37acb0eafed979f7";
    private RecyclerView cRecyclerView;
    private List<Carrito> carrito_cliente = new ArrayList<>();
    private CarritoAdapter carritoAdapter;
    private Context c;
    private TextView tvTotal, tvSinProductos, tvTituloTotal;
    private CardView cvTotales;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        //My backend here
        c = root.getContext();

        //TextViews
        tvTotal = root.findViewById(R.id.tvcTotal);
        tvSinProductos = root.findViewById(R.id.tvSinProductos);
        cvTotales = root.findViewById(R.id.cvTotales);
        tvTituloTotal = root.findViewById(R.id.tvTotalTitulo);

        //Recycler view de Productos
        cRecyclerView = root.findViewById(R.id.rvCarritoCliente);
        cRecyclerView.setHasFixedSize(true);

        //Asignamos el LayoutManager al RecyclerView
        LinearLayoutManager cLayoutManager = new LinearLayoutManager(c);
        cRecyclerView.setLayoutManager(cLayoutManager);

        requestQueue = Volley.newRequestQueue(c);
        listarCarritoCliente();

        return root;
    }

    private void listarCarritoCliente(){
        // Header (Token)
        Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2MTlkOWVjYTM3YWNiMGVhZmVkOTc5ZjciLCJub21icmVzIjoiQ2FtaWxvIiwiYXBlbGxpZG9zIjoiRGlheiIsImVtYWlsIjoiY2RpYXpAbWFpbC5jb20iLCJpYXQiOjE2MzgxNTYwODYsImV4cCI6MTYzODc2MDg4Nn0.j8l_iPbx6bwCtLwGiRrJsW5qoZOrvmTdVJbdsqQFUls");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                CARRITO_URL,
                null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        int size = jsonArray.length();

                        if (size != 0){
                            Double precioTotal = 0.0;
                            for (int i = 0; i<size; i++){
                                try {
                                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                    String _id = jsonObject.getString("_id");
    //                                Toast.makeText(c, "Aumentar 1", Toast.LENGTH_LONG).show();
                                    Product producto = new Product();
                                    producto.set_id(jsonObject.getJSONObject("producto").getString("_id"));
                                    producto.setTitulo(jsonObject.getJSONObject("producto").getString("titulo"));
                                    producto.setPortada(jsonObject.getJSONObject("producto").getString("portada"));
                                    producto.setPrecio(jsonObject.getJSONObject("producto").getDouble("precio"));
    //                                precioTotal = precioTotal + producto.getPrecio();

                                    String cliente = jsonObject.getString("cliente");
                                    int cantidad = jsonObject.getInt("cantidad");

                                    this.carrito_cliente.add(new Carrito(_id, cliente, cantidad, producto));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //Asignamos todos los datos de la lista al adaptador
                            carritoAdapter = new CarritoAdapter(this.carrito_cliente);
                            cRecyclerView.setAdapter(carritoAdapter);

                            //Precio total
                            for (Carrito c: carrito_cliente) {
                                precioTotal += c.getProducto().getPrecio() * c.getCantidad();
                            }
                            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
                            tvTotal.setText(String.valueOf(formatoImporte.format(precioTotal)));
                            tvTituloTotal.setVisibility(View.VISIBLE);
                            cvTotales.setVisibility(View.VISIBLE);
                        }else{
                            tvSinProductos.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}