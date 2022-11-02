package com.ttps.backend.services;

import java.util.Collection;

import com.ttps.backend.models.Manguito;

public interface ManguitoService {
    Manguito get(Long id);

    Collection<Manguito> list(int limit);

    Manguito create(Manguito manguito);

    Boolean delete(Long id);
}
