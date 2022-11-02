package com.ttps.backend.services.implementations;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ttps.backend.models.Manguito;
import com.ttps.backend.repositories.ManguitoRepo;
import com.ttps.backend.services.ManguitoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ManguitoServiceImpl implements ManguitoService {
    private final ManguitoRepo manguitoRepo;

    @Override
    public Manguito get(Long id) {
        log.info("Obtener manguito con id: {}", id);
        return manguitoRepo.findById(id).orElse(null);
    }

    @Override
    public Collection<Manguito> list(int limit) {
        log.info("Listando manguitos");
        return manguitoRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Manguito create(Manguito manguito) {
        log.info("Guardando nuevo manguito de la persona: {}", manguito.getNombrePersona());
        return manguitoRepo.save(manguito);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando manguito con id: {}", id);
        manguitoRepo.deleteById(id);
        return true;
    }

}
