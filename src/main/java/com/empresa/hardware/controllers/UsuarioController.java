package com.empresa.hardware.controllers;

import com.empresa.hardware.models.Usuario;
import com.empresa.hardware.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Ver usuarios
    @GetMapping
    public String verUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "ver_usuarios";  // Vista para ver usuarios
    }

    // Crear un nuevo usuario
    @GetMapping("/nuevo")
    public String crearUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "crear_usuario";  // Vista para crear usuario
    }

    @PostMapping("/nuevo")
    public String crearUsuario(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";  // Redirigir después de guardar
    }

    // Editar un usuario existente
    @GetMapping("/{id}/editar")
    public String editarUsuarioForm(@PathVariable String id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);  // Pasar el usuario a la vista
            return "editar_usuario";  // Vista para editar usuario
        }
        return "redirect:/usuarios";  // Si no se encuentra, redirigir a la lista
    }

    @PostMapping("/{id}/editar")
    public String editarUsuario(@PathVariable String id, @ModelAttribute Usuario usuario) {
        // Buscar el usuario existente por id
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);

        if (usuarioExistente != null) {
            // Actualizar los datos del usuario con los nuevos valores
            usuarioExistente.setUsername(usuario.getUsername());
            usuarioExistente.setPassword(usuario.getPassword());  // Se mantiene la contraseña actualizada
            usuarioExistente.setRole(usuario.getRole());

            // Guardar los cambios
            usuarioRepository.save(usuarioExistente);
        }

        return "redirect:/usuarios";  // Redirigir a la lista después de actualizar
    }

    // Eliminar un usuario
    @GetMapping("/{id}/eliminar")
    public String eliminarUsuario(@PathVariable String id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";  // Redirigir a la lista después de eliminar
    }
}

