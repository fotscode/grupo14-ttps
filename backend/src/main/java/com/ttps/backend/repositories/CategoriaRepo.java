package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.backend.models.Categoria;

@Repository
public interface CategoriaRepo extends JpaRepository<Categoria, Long> {
    Categoria findByNombre(String nombre);
}
