package com.example.ecommerceapp.modelos;

import java.io.Serializable;

public class Product implements Serializable {
    private String titulo, categoria, portada, descripcion, contenido, nombre_local, id_local;
    private Double precio;

    public Product(){

    }

    public Product(String titulo, Double precio, String categoria, String portada, String descripcion, String contenido, String nombre_local, String id_local) {
        this.titulo = titulo;
        this.precio = precio;
        this.categoria = categoria;
        this.portada = portada;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.nombre_local = nombre_local;
        this.id_local = id_local;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getPortada() {
        return portada;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getContenido() {
        return contenido;
    }

    public String getId_local() {
        return id_local;
    }

    public String getNombre_local() {
        return nombre_local;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setId_local(String id_local) {
        this.id_local = id_local;
    }

    public void setNombre_local(String nombre_local) {
        this.nombre_local = nombre_local;
    }
}
