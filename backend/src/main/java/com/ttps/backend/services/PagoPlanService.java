package com.ttps.backend.services;

import java.util.Collection;

import com.ttps.backend.models.PagoPlan;

public interface PagoPlanService {
    PagoPlan get(Long id);

    Collection<PagoPlan> list(int limit);

    PagoPlan create(PagoPlan pagoPlan);

    Boolean delete(Long id);
}

