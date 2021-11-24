package com.example.ecommerceapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.modelos.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    public List<Product> productList;

    // Obtener referencias de los componentes visuales para cada elemento
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo, tvPrecio, tvCategoria;
        private ImageView ivPortada;
        public ProductViewHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
            ivPortada = (ImageView) itemView.findViewById(R.id.ivPortada);
        }
    }

    public ProductsAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.tvTitulo.setText(productList.get(position).getTitulo());
        holder.tvPrecio.setText(String.valueOf(productList.get(position).getPrecio()));
        Picasso.get()
                .load("https://central-park-ecommerce.herokuapp.com/api/obtener_portada/"+productList.get(position).getPortada())
                .placeholder(R.drawable.default_placeholder)
                .into(holder.ivPortada);
//        holder.ivPortada.setImageResource(productList.get(position).getPortada());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    //    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
//    public ProductsAdapter(String[] myDataSet) {
//        mDataSet = myDataSet;
//    }
//
//    // El layout manager invoca este método
//    // para renderizar cada elemento del RecyclerView
//    @Override
//    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                         int viewType) {
//        // Creamos una nueva vista
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.product_view, parent, false);
//
//        // Aquí podemos definir tamaños, márgenes, paddings, etc
//        return new ViewHolder(v);
//    }
//
//    // Este método asigna valores para cada elemento de la lista
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        // - obtenemos un elemento del dataset según su posición
//        // - reemplazamos el contenido usando tales datos
//
//        holder..setText(mDataSet[position]);
//    }
//
//    // Método que define la cantidad de elementos del RecyclerView
//    // Puede ser más complejo (por ejem, si implementamos filtros o búsquedas)
//    @Override
//    public int getItemCount() {
//        return mDataSet.length;
//    }
}
