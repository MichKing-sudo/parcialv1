package com.empresa.hardware.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "ventas")
public class Venta {
    @Id
    private String id;

    @DBRef
    private Usuario usuario; // Relación con el objeto Usuario completo

    @DBRef
    private Producto producto; // Relación con el objeto Producto completo

    private int cantidad; // Cantidad de productos vendidos
    private double total; // Total de la venta
    private Date fechaVenta; // Fecha de la venta

    // Constructor
    public Venta() {
        this.fechaVenta = new Date(); // Asignar la fecha actual al momento de crear la venta
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    // Método para calcular el total de la venta
    public void calcularTotal() {
        if (producto != null) {
            this.total = cantidad * producto.getPrecio(); // Asumiendo que Producto tiene un método getPrecio()
        }
    }

    // Método para obtener el nombre del usuario
    public String obtenerNombreUsuario() {
        return usuario != null ? usuario.getUsername() : "Usuario desconocido";
    }

    // Método para imprimir un resumen de la venta
    public String imprimirResumen() {
        return "Venta ID: " + id + "\n" +
               "Usuario: " + obtenerNombreUsuario() + "\n" +  // Usamos el método para obtener el nombre
               "Producto: " + producto.getNombre() + "\n" + // Asumiendo que Producto tiene un método getNombre()
               "Cantidad: " + cantidad + "\n" +
               "Total: $" + total + "\n" +
               "Fecha: " + fechaVenta;
    }
}


