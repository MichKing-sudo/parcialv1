package com.empresa.hardware.controllers;

import com.empresa.hardware.models.Producto;
import com.empresa.hardware.models.Usuario;
import com.empresa.hardware.models.Venta;
import com.empresa.hardware.repositories.ProductoRepository;
import com.empresa.hardware.repositories.UsuarioRepository;
import com.empresa.hardware.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositorio de usuarios (clientes)

    @Autowired
    private ProductoRepository productoRepository; // Repositorio de productos

    @Autowired
    private VentaRepository ventaRepository; // Repositorio de ventas



    @GetMapping("/nueva")
    public String nuevaVenta(Model model) {
        // Filtrar solo usuarios con rol "CLIENTE"
        List<Usuario> clientes = usuarioRepository.findAll().stream()
                .filter(usuario -> "CLIENTE".equals(usuario.getRole()))
                .collect(Collectors.toList());

        // Obtener todos los productos disponibles
        List<Producto> productos = productoRepository.findAll();

        // Pasar los datos al modelo para que estén disponibles en la vista
        model.addAttribute("usuarios", clientes); // Lista de clientes
        model.addAttribute("productos", productos); // Lista de productos
        model.addAttribute("venta", new Venta()); // Objeto vacío de Venta
        return "nuevaVenta"; // Nombre de la plantilla Thymeleaf
    }

    /**
     * Método para guardar una nueva venta en la base de datos.
     */
    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute("venta") Venta venta) {
        try {
            venta.calcularTotal();  // Calcular el total antes de guardar
            ventaRepository.save(venta); // Guardar la venta en la base de datos
            return "redirect:/ventas/listar"; // Redirigir a la lista de ventas
        } catch (Exception e) {
            return "error"; // Redirigir a una página de error en caso de fallo
        }
    }

    /**
     * Método para listar todas las ventas registradas.
     */
    @GetMapping("/listar")
    public String listarVentas(Model model) {
        List<Venta> ventas = ventaRepository.findAll();  // Obtén todas las ventas
        model.addAttribute("ventas", ventas);  // Pasa la lista de ventas al modelo
        return "listarVentas";  // Nombre de la vista Thymeleaf
    }

    /**
     * Método para eliminar una venta por su ID.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable("id") String id) {
        try {
            ventaRepository.deleteById(id); // Eliminar la venta de la base de datos
            return "redirect:/ventas/listar"; // Redirigir a la lista de ventas
        } catch (Exception e) {
            return "error"; // Redirigir a una página de error en caso de fallo
        }
    }
}
