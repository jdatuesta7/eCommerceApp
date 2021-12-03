package com.example.ecommerceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerceapp.modelos.Product;
import com.example.ecommerceapp.ui.adapter.ProductsAdapter;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private ImageCarousel carousel;
    private List<CarouselItem> list;
    private TextView textView1;
    RequestQueue requestQueue;
    private static final String URL1 = "https://my-json-server.typicode.com/typicode/demo/comments";
    private static final String URL2 = "https://my-json-server.typicode.com/typicode/demo/posts";
    private static final String URL3 = "https://my-json-server.typicode.com/typicode/demo/db";
    private static final String PRODUCTOS_URL = "https://central-park-ecommerce.herokuapp.com/api/listar_productos_publicos/";
    private RecyclerView pRecyclerView;
    private List<Product> products = new ArrayList<>();
    ProductsAdapter productAdapter;
    public ImageButton btnBuscarProductos;
    private EditText etBuscarProductos;

    public DiscoverFragment() {
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
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        //My backend here
        Context c = root.getContext();

        btnBuscarProductos = root.findViewById(R.id.btnBuscarProducto);
        etBuscarProductos = root.findViewById(R.id.etBuscarProductos);


        btnBuscarProductos.setOnClickListener(v -> {
            this.products.clear();
            listarProductos(etBuscarProductos.getText().toString());
            Toast.makeText(c, "Busqueda realizada", Toast.LENGTH_LONG).show();
        });

        //Recycler view de Productos
        pRecyclerView = root.findViewById(R.id.rvproducts);
        pRecyclerView.setHasFixedSize(true);

        //Asignamos el LayoutManager al RecyclerView
        LinearLayoutManager pLayoutManager = new LinearLayoutManager(c);
        pRecyclerView.setLayoutManager(pLayoutManager);

        requestQueue = Volley.newRequestQueue(c);
        listarProductos("");

        return root;
    }

    private void stringReq(){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL1,
                response -> textView1.setText(response),
                error -> {

                }
        );
        requestQueue.add(request);
    }

    private void jsonArrayRequest(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL2,
                null,
                response -> {
                    int size = response.length();
                    for (int i = 0; i<size; i++){
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(i).toString());
                            String title = jsonObject.getString("title");
                            textView1.append("Title: "+title+ "\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void listarProductos(String filtro){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PRODUCTOS_URL + filtro,
                null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        int size = jsonArray.length();
                        for (int i = 0; i<size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String _id = jsonObject.getString("_id");
                                String titulo = jsonObject.getString("titulo");
                                String categoria = jsonObject.getString("categoria");
                                Double precio = jsonObject.getDouble("precio");
                                String portada = jsonObject.getString("portada");
                                String descripcion = jsonObject.getString("descripcion");
                                String contenido = jsonObject.getString("contenido");
                                String nombre_local = jsonObject.getJSONObject("admin").getString("nombre_local");
                                String id_local = jsonObject.getJSONObject("admin").getString("id_local");

                                this.products.add(new Product(_id, titulo, precio , categoria, portada, descripcion, contenido, nombre_local, id_local));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Asignamos todos los datos de la lista al adaptador
                        productAdapter = new ProductsAdapter(this.products);
                        pRecyclerView.setAdapter(productAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonObjectRequest);
    }


}