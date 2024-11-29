package com.empresa.hardware.repositories;

import com.empresa.hardware.models.Usuario;
import com.empresa.hardware.models.Venta;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VentaRepository extends MongoRepository<Venta, String> {
    // Usando dot notation para acceder al campo 'username' dentro del objeto 'usuario'
    List<Venta> findByUsuario(Usuario usuario);  // Encontrar ventas por objeto Usuario
}
