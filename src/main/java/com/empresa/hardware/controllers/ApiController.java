package com.empresa.hardware.controllers;

import com.empresa.hardware.models.Producto;
import com.empresa.hardware.models.Venta;
import com.empresa.hardware.repositories.ProductoRepository;
import com.empresa.hardware.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

  
    // CRUD de Productos (Auxiliar de Bodega y Admin)
    @PostMapping("/productos")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @GetMapping("/productos")
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/productos/{id}")
    public Optional<Producto> obtenerProducto(@PathVariable String id) {
        return productoRepository.findById(id);
    }

    @PutMapping("/productos/{id}")
    public Producto actualizarProducto(@PathVariable String id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoRepository.save(producto);
    }

    @DeleteMapping("/productos/{id}")
    public void eliminarProducto(@PathVariable String id) {
        productoRepository.deleteById(id);
    }

    // CRUD de Ventas (Vendedor)
    @PostMapping("/ventas")
    public Venta crearVenta(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }

    @GetMapping("/ventas")
    public List<Venta> obtenerVentas() {
        return ventaRepository.findAll();
    }

}
