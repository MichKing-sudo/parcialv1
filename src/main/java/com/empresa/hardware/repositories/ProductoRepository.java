package com.empresa.hardware.repositories;

import com.empresa.hardware.models.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ProductoRepository extends MongoRepository<Producto, String> {
    Optional<Producto> findById(String id);  // Método para encontrar un producto por su ID
}
