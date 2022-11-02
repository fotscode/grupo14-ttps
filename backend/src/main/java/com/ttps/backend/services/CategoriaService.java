package com.ttps.backend.services;

import java.util.Collection;

import com.ttps.backend.models.Categoria;

public interface CategoriaService {
    Categoria get(Long id);

    Collection<Categoria> list(int limit);

    Categoria create(Categoria categoria);

    Categoria update(Categoria categoria);

    Boolean delete(Long id);
}
