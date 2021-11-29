package com.example.ecommerceapp.modelos;

public class Carrito {
    private String _id, cliente;
    private int cantidad;
    private Product producto;

    public Carrito() {
    }

    public Carrito(String _id, String cliente, int cantidad, Product producto) {
        this._id = _id;
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }
}
