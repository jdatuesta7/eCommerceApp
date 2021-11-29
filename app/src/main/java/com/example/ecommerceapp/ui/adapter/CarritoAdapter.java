package com.example.ecommerceapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.modelos.Carrito;
import com.example.ecommerceapp.modelos.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {
    public List<Carrito> carritoList;
    private Context context;

    // Obtener referencias de los componentes visuales para cada elemento
    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo, tvPrecio, tvCantidad;
        private ImageView ivPortada;
        private ImageButton btnAumentarCantidad, btnDisminuirCantidad;
        public CarritoViewHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvcTitulo);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvcPrecio);
            ivPortada = (ImageView) itemView.findViewById(R.id.ivcPortada);
            tvCantidad = (TextView) itemView.findViewById(R.id.tvcCantidad);
            btnAumentarCantidad = itemView.findViewById(R.id.btnAumentarCantidad);
            btnDisminuirCantidad = itemView.findViewById(R.id.btnDisminuirCantidad);
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
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        final Carrito carrito = carritoList.get(position);
        holder.tvTitulo.setText(carrito.getProducto().getTitulo());

        Double precio = carrito.getProducto().getPrecio() * carrito.getCantidad();

        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        holder.tvPrecio.setText(String.valueOf(formatoImporte.format(carrito.getProducto().getPrecio())));

        Picasso.get()
                .load("https://central-park-ecommerce.herokuapp.com/api/obtener_portada/"+carrito.getProducto().getPortada())
                .placeholder(R.drawable.default_placeholder)
                .into(holder.ivPortada);

        holder.tvCantidad.setText(String.valueOf(carrito.getCantidad()));

        holder.btnAumentarCantidad.setOnClickListener(v -> {
            Toast.makeText(context, "Aumentar 1", Toast.LENGTH_LONG).show();
        });

        holder.btnDisminuirCantidad.setOnClickListener(v -> {
            Toast.makeText(context, "Disminuir 1", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return carritoList.size();
    }


}
