package com.example.ecommerceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerceapp.modelos.Product;
import com.example.ecommerceapp.ui.adapter.ProductsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    RequestQueue requestQueue;
    private static final String PRODUCTOS_URL = "https://central-park-ecommerce.herokuapp.com/api/listar_productos_tendencia_publicos";
    private RecyclerView pRecyclerView;
    private ProductsAdapter productAdapter;
    private List<Product> products = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //BACKEND HERE ----------
        //Activity Context
        Context c = root.getContext();

        //Recycler view de Productos
        pRecyclerView = root.findViewById(R.id.rvproductsHome);
        pRecyclerView.setHasFixedSize(true);

        //Asignamos el LayoutManager al RecyclerView
        LinearLayoutManager pLayoutManager = new LinearLayoutManager(c);
        pRecyclerView.setLayoutManager(pLayoutManager);

        requestQueue = Volley.newRequestQueue(c);
        jsonObjectRequest();

        return root;
    }

    private void jsonObjectRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PRODUCTOS_URL,
                null,
                (Response.Listener<JSONObject>) response -> {
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

                                this.products.add(new Product(titulo, precio , categoria, portada));

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