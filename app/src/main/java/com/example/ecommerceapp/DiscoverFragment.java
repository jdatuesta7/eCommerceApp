package com.example.ecommerceapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

//    private Button btnNextCategory, btnPreviousCategory;
//    private EditText etSearch;
    private ImageCarousel carousel;
    private List<CarouselItem> list;

    private TextView textView1;
    RequestQueue requestQueue;

    private static final String URL1 = "https://my-json-server.typicode.com/typicode/demo/comments";
    private static final String URL2 = "https://my-json-server.typicode.com/typicode/demo/posts";
    private static final String URL3 = "https://my-json-server.typicode.com/typicode/demo/db";
    private static final String PRODUCTOS_URL = "https://central-park-ecommerce.herokuapp.com/api/listar_productos_publicos/";

    private RecyclerView pRecyclerView;
    private ProductsAdapter productAdapter;
    private List<Product> products = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        //My backend here

        Context c = root.getContext();

        //Recycler view de Productos
        pRecyclerView = root.findViewById(R.id.rvproducts);
        pRecyclerView.setHasFixedSize(true);

        //Asignamos el LayoutManager al RecyclerView
        LinearLayoutManager pLayoutManager = new LinearLayoutManager(c);
        pRecyclerView.setLayoutManager(pLayoutManager);



        requestQueue = Volley.newRequestQueue(c);
//        stringReq();
//        jsonArrayRequest();
        jsonObjectRequest();

        return root;
    }

//    public List<Product> obtenerProductos(){
//        List<Product> products = new ArrayList<>();
//        products.add(new Product("Laptop AsusRog",3250000.0 , "Laptops", R.drawable.portatil_asus_rog));
//        products.add(new Product("Laptop Huawei",3250000.0 , "Laptops", R.drawable.portatil_asus_rog));
//        products.add(new Product("Laptop Xiaomi",3250000.0 , "Laptops", R.drawable.portatil_asus_rog));
//        products.add(new Product("Laptop AMD",3250000.0 , "Laptops", R.drawable.portatil_asus_rog));
//        products.add(new Product("Laptop Intel",3250000.0 , "Laptops", R.drawable.portatil_asus_rog));
//
//        return products;
//    }

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

    private void jsonObjectRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PRODUCTOS_URL,
                null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        int size = jsonArray.length();
                        for (int i = 0; i<size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String titulo = jsonObject.getString("titulo");
                                String categoria = jsonObject.getString("categoria");
                                Double precio = jsonObject.getDouble("precio");
                                String portada = jsonObject.getString("portada");
                                String descripcion = jsonObject.getString("descripcion");
                                String contenido = jsonObject.getString("contenido");
                                String nombre_local = jsonObject.getJSONObject("admin").getString("nombre_local");
                                String id_local = jsonObject.getJSONObject("admin").getString("id_local");

                                this.products.add(new Product(titulo, precio , categoria, portada, descripcion, contenido, nombre_local, id_local));

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