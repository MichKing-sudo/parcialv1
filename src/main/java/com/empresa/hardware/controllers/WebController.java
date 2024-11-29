package com.empresa.hardware.controllers;

import com.empresa.hardware.models.Producto;
import com.empresa.hardware.models.Usuario;
import com.empresa.hardware.models.Venta;
import com.empresa.hardware.repositories.ProductoRepository;
import com.empresa.hardware.repositories.UsuarioRepository;
import com.empresa.hardware.repositories.VentaRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private VentaRepository ventaRepository;


    // Página de aterrizaje
    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }
    @GetMapping("/autores")
    public String autor() {
        return "autores";
    }

    // Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            // Guardar el usuario en la sesión
            session.setAttribute("usuario", usuario);

            // Redirigir según el rol
            switch (usuario.getRole()) {
                case "CLIENTE":
                    return "redirect:/dashboard/cliente";
                case "VENDEDOR":
                    return "redirect:/dashboard/vendedor";
                case "AUXILIAR":
                    return "redirect:/dashboard/auxiliar";
                case "ADMINISTRADOR":
                    return "redirect:/dashboard/administrador";
                default:
                    break;
            }
        }

        model.addAttribute("error", "Usuario o contraseña incorrectos.");
        return "login";
    }



    // Dashboard para vendedor
    @GetMapping("/dashboard/vendedor")
    public String dashboardVendedor(Model model, @SessionAttribute("usuario") Usuario usuario) {
        model.addAttribute("usuario", usuario);  // Pasar el usuario al modelo
        return "dashboard_vendedor";
    }

    // Dashboard para auxiliar de bodega
    @GetMapping("/dashboard/auxiliar")
    public String dashboardAuxiliar(Model model, @SessionAttribute("usuario") Usuario usuario) {
        model.addAttribute("usuario", usuario);  // Pasar el usuario al modelo
        return "dashboard_auxiliar";
    }

    // Dashboard para administrador
    @GetMapping("/dashboard/administrador")
    public String dashboardAdministrador(Model model, @SessionAttribute("usuario") Usuario usuario) {
        model.addAttribute("usuario", usuario);  // Pasar el usuario al modelo
        return "dashboard_administrador";
    }
    
    @GetMapping("/dashboard/cliente")
    public String getDashboard(Model model, @SessionAttribute("usuario") Usuario usuario) {
        // Consultar las ventas del usuario usando su objeto Usuario
        List<Venta> ventas = ventaRepository.findByUsuario(usuario); // Encontrar ventas por el objeto Usuario

        // Pasar las ventas y el usuario al modelo
        model.addAttribute("usuario", usuario);
        model.addAttribute("ventas", ventas);

        return "dashboard_cliente";  // La vista correspondiente
    }

}
