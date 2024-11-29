package com.empresa.hardware.controllers;

import com.empresa.hardware.models.Producto;
import com.empresa.hardware.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Ver productos
    @GetMapping
    public String verProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "ver_productos";
    }

    // Crear un nuevo producto
    @GetMapping("/nuevo")
    public String crearProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "crear_producto";
    }

    @PostMapping("/nuevo")
    public String crearProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // Editar un producto existente
    @GetMapping("/{id}/editar")
    public String editarProductoForm(@PathVariable String id, Model model) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "editar_producto";
        }
        return "redirect:/productos";
    }

    @PostMapping("/{id}/editar")
    public String editarProducto(@PathVariable String id, @ModelAttribute Producto producto) {
        producto.setId(id);
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // Eliminar un producto
    @GetMapping("/{id}/eliminar")
    public String eliminarProducto(@PathVariable String id) {
        productoRepository.deleteById(id);
        return "redirect:/productos";
    }
}
