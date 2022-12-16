package com.ttps.backend.services.implementations;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ttps.backend.models.PagoPlan;
import com.ttps.backend.models.Plan;
import com.ttps.backend.repositories.PagoPlanRepo;
import com.ttps.backend.repositories.PlanRepo;
import com.ttps.backend.services.PlanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanServiceImpl implements PlanService {
    private final PlanRepo planRepo;
    private final PagoPlanRepo pagoPlanRepo;

    @Override
    public Plan get(Long id) {
        log.info("Obtener plan con id: {}", id);
        return planRepo.findById(id).orElse(null);
    }

    @Override
    public Collection<Plan> list(int limit, int page) {
        log.info("Listando planes");
        return planRepo.findAll(PageRequest.of(page, limit)).toList();
    }

    @Override
    public Plan create(Plan plan) {
        log.info("Guardando nuevo plan: {}", plan.getTitulo());
        return planRepo.save(plan);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando plan con id: {}", id);
        Plan plan = planRepo.findById(id).orElse(null);
        if (plan == null) {
            return false;
        }
        planRepo.deleteById(id);
        return true;
    }

    @Override
    public Plan update(Plan plan) {
        log.info("Actualizando plan con titulo:{}", plan.getTitulo());
        return planRepo.save(plan);
    }

    @Override
    public Boolean addPagoToPlan(Long idPlan, PagoPlan pagoPlan) {
        Plan plan = planRepo.findById(idPlan).orElse(null);
        log.info("Creando pagoPlan con nombre de persona:{}", pagoPlan.getNombrePersona());
        pagoPlanRepo.save(pagoPlan);
        log.info(
                "Agregar pagoPlan con nombre de persona:{} al emprendimiento con id:{}",
                pagoPlan.getNombrePersona(),
                idPlan);
        if (plan != null) {
            plan.addPago(pagoPlan);
            return true;
        }
        return false;
    }

}
