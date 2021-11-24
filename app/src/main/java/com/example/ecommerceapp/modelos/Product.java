package com.example.ecommerceapp.modelos;

public class Product {
    private String titulo, categoria, portada;
    private Double precio;

    public Product(){

    }

    public Product(String titulo, Double precio, String categoria, String portada) {
        this.titulo = titulo;
        this.precio = precio;
        this.categoria = categoria;
        this.portada = portada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
