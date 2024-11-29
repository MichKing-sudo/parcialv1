package com.empresa.hardware.repositories;

import com.empresa.hardware.models.Usuario;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByUsername(String username);
    List<Usuario> findByRole(String role);  // Filtra por rol

}
