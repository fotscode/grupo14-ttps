package com.ttps.backend.services.implementations;

import com.ttps.backend.models.Categoria;
import com.ttps.backend.repositories.CategoriaRepo;
import com.ttps.backend.services.CategoriaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepo categoriaRepo;

    @Override
    public Categoria get(Long id) {
        log.info("Obtener categoria con id: {}", id);
        return categoriaRepo.findById(id).orElse(null);
    }

    @Override
    public Collection<Categoria> list(int limit) {
        log.info("Listando categoriaes");
        return categoriaRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Categoria create(Categoria categoria) {
        log.info("Guardando nuevo categoria: {}", categoria.getNombre());
        return categoriaRepo.save(categoria);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando categoria con id: {}", id);
        Categoria categoria = categoriaRepo.findById(id).orElse(null);
        if (categoria == null) {
            return false;
        }
        categoriaRepo.deleteById(id);
        return true;
    }

    @Override
    public Categoria update(Categoria categoria) {
        log.info("Actualizando categoria con nombre:{}", categoria.getNombre());
        return categoriaRepo.save(categoria);
    }
}
