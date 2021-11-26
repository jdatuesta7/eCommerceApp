package com.example.ecommerceapp.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.DetailProductActivity;
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
//        private Button btnCarrito;
        public ProductViewHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
            ivPortada = (ImageView) itemView.findViewById(R.id.ivPortada);
//            btnCarrito = itemView.findViewById(R.id.btnCarrito);
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
//        holder.btnCarrito.setOnClickListener(v -> {
//
//        });
//        holder.ivPortada.setImageResource(productList.get(position).getPortada());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
