package com.ttps.backend.services;

import java.util.Collection;

import com.ttps.backend.models.PagoPlan;
import com.ttps.backend.models.Plan;

public interface PlanService {
    Plan get(Long id);

    Collection<Plan> list(int limit);

    Plan create(Plan plan);

    Plan update(Plan plan);

    Boolean delete(Long id);

    Boolean addPagoToPlan(Long idPlan, PagoPlan idPagoPlan);
}


